package giis.demo.business;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import giis.demo.business.entities.SocioEntity;
import giis.demo.util.Database;
import giis.demo.util.SwingUtil;

public abstract class SociosController {
	
	private final static String SQL_CARGAR_TODOS_SOCIOS = "SELECT id, dni, name, surname, email, telf, cuota_type, iban, gender, birth_date, directive FROM socios ";
	private final static String SQL_CARGAR_DNI_SOCIOS = "SELECT dni FROM socios";
	private final static String WHERE = "WHERE";
	
	private final static int NAME = 2;
	private final static int SURNAME = 3;
	private final static int EMAIL = 4;
	private final static int TELF = 5;
	private final static int CUOTA_TYPE = 6;
	private final static int IBAN = 7;
	private final static int GENDER = 8;
	private final static int BIRTH_DATE = 9;
	private final static int DIRECTIVE = 10;
	
	private static String selectedValue = "";
	
	private static HashMap<Integer, String> columnas = new HashMap<Integer, String>(){
		private static final long serialVersionUID = -2242980961209539019L;
		{
			put(NAME, "UPDATE socios SET name=? WHERE id=?");
			put(SURNAME, "UPDATE socios SET surname=? WHERE id=?");
			put(EMAIL, "UPDATE socios SET email=? WHERE id=?");
			put(TELF, "UPDATE socios SET telf=? WHERE id=?");
			put(CUOTA_TYPE, "UPDATE socios SET cuota_type=? WHERE id=?");
			put(IBAN, "UPDATE socios SET iban=? WHERE id=?");
			put(GENDER, "UPDATE socios SET gender=? WHERE id=?");
			put(BIRTH_DATE, "UPDATE socios SET birth_date=? WHERE id=?");
			put(DIRECTIVE, "UPDATE socios SET directive=? WHERE id=?");
		}
	};
	
	public static TableModel setTableModel(Database db, String filter, JTable tabla) {
		TableModel model = SwingUtil.getTableModelFromPojos(getSociosForTabla(db, filter), 
				new String[] {"id", "dni", "name", "surname", "email", "telf", "cuota_type", "iban", "gender", "birth_date", "directive"});
		
		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (tabla.getSelectedColumn() != BIRTH_DATE && tabla.getSelectedColumn() != DIRECTIVE) {
						selectedValue = String.valueOf(tabla.getModel().getValueAt(tabla.getSelectedRow(), tabla.getSelectedColumn()));					
					}
				} catch (ArrayIndexOutOfBoundsException e1) {
					System.err.println("Click detectado dentro de la tabla pero fuera de una fila válida.");
				}
			}
		});
		
		model.addTableModelListener(new TableModelListener(){
			@Override
            public void tableChanged(TableModelEvent e) {
                actualizaSocio(e, db, tabla);
            }
		});
		return model;
	}
	
	public static List<String> getDni(Database db) {
		List<Object[]> result = db.executeQueryArray(SQL_CARGAR_DNI_SOCIOS);
		List<String> dnis = new ArrayList<String>();
		
		String dni = "";
		
		for (Object[] objects : result) {
			dni = (String) objects[0];
			dnis.add(dni);
		}		
		
		return dnis;
	}
	
	private static void actualizaSocio(TableModelEvent e, Database db, JTable tabla) {
		if (e.getType() == TableModelEvent.UPDATE) {
			AbstractTableModel modelo = ((AbstractTableModel) e.getSource());
	        int fila = e.getFirstRow();
	        int columna = e.getColumn();
	        
	        int res = 0;

	        String dato=String.valueOf(modelo.getValueAt(fila,columna));	
	        String id_user = String.valueOf(modelo.getValueAt(fila, 0));
	        System.out.println(dato + " ?");
	        
	        String update = columnas.get(columna);
	        
	        if (columna == DIRECTIVE) {
	        	update = "UPDATE socios SET directive="+dato+" WHERE id=?";
	        	db.executeUpdate(update, id_user);
	        	
	        } else if (!dato.isEmpty()) {
	        	db.executeUpdate(update, dato, id_user);
	        	if (selectedValue != dato)
	        		res = JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere realizar este cambio?", "Confirmación", JOptionPane.YES_NO_OPTION);
	        		if (res == JOptionPane.NO_OPTION || res == JOptionPane.CLOSED_OPTION) {
	        			modelo.setValueAt(selectedValue, fila, columna);
	        			return;
	        		} else if (res == JOptionPane.YES_OPTION){
	        			JOptionPane.showMessageDialog(null, "Campo actualizado correctamente.", "INFO", JOptionPane.INFORMATION_MESSAGE);
	        		}
	        	System.out.println(update);
	        } else {
	        	if (columna != BIRTH_DATE) {
	        		modelo.setValueAt(selectedValue, fila, columna);
	        		JOptionPane.showMessageDialog(null, "No puedes dejar el campo vacío.", "ERROR", JOptionPane.ERROR_MESSAGE);	        		
	        		return;
	        	}	        	
	        }
		}	
	}
	
	private static List<SocioEntity> getSociosForTabla(Database db, String filter){
		return db.executeQueryPojo(SocioEntity.class, buildQuery(filter));
	}
  
	private static String buildQuery(String filter) {
		String query;
		
		if (!filter.isEmpty()) {
			query = SQL_CARGAR_TODOS_SOCIOS + WHERE + filter;
		} else {
			query = SQL_CARGAR_TODOS_SOCIOS;
		}
		
		return query;
	}
	
}
