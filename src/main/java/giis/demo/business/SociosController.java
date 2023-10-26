package giis.demo.business;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	
	private final static String SQL_CARGAR_TODOS_SOCIOS = "SELECT * FROM socios ";
	private final static String WHERE = "WHERE";
	
	private static String selectedValue = "";
	
	private static String[] columns;
	
	public static TableModel setTableModel(Database db, String filter, JTable tabla) {
		TableModel model = SwingUtil.getTableModelFromPojos(getSociosForTabla(db, filter), 
				new String[] {"id", "dni", "name", "surname", "email", "cuota_type", "iban","height", "weight", "gender", "birth_date", "directive"});
		
		columns = new String[model.getColumnCount()+1];
		columns[2] = "UPDATE socios SET name=? WHERE id=?";
		columns[3] = "UPDATE socios SET surname=? WHERE id=?";
		columns[4] = "UPDATE socios SET email=? WHERE id=?";
		columns[5] = "UPDATE socios SET cuota_type=? WHERE id=?";
		columns[6] = "UPDATE socios SET iban=? WHERE id=?";
		columns[7] = "UPDATE socios SET height=? WHERE id=?";
		columns[8] = "UPDATE socios SET weight=? WHERE id=?";
		columns[9] = "UPDATE socios SET gender=? WHERE id=?";
		columns[10] = "UPDATE socios SET birth_date=? WHERE id=?";
		columns[11] = "UPDATE socios SET directive=? WHERE id=?";
		
		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (tabla.getSelectedColumn() != 10 && tabla.getSelectedColumn() != 11) {
						selectedValue = String.valueOf(tabla.getModel().getValueAt(tabla.getSelectedRow(), tabla.getSelectedColumn()));
						System.out.println("Fila: " + tabla.getSelectedRow() + "Columna: " + tabla.getSelectedColumn());					
					}
				} catch (ArrayIndexOutOfBoundsException e1) {
					System.err.println("Click detectado dentro de la tabla pero fuera de una fila válida.");
				}
			}
		});
		
		model.addTableModelListener(new TableModelListener(){
			@Override
            public void tableChanged(TableModelEvent e) {
            	System.out.println(e.getColumn());
                actualizaSocio(e, db, tabla);
            }
		});
		return model;
	}
	
	private static void actualizaSocio(TableModelEvent e, Database db, JTable tabla) {
		if (e.getType() == TableModelEvent.UPDATE) {
			AbstractTableModel modelo = ((AbstractTableModel) e.getSource());
	        int fila = e.getFirstRow();
	        int columna = e.getColumn();

	        String dato=String.valueOf(modelo.getValueAt(fila,columna));	
	        String id_user = String.valueOf(modelo.getValueAt(fila, 0));
	        System.out.println(dato + "??");
	        
	        String update = columns[columna];
	        
	        if (columna == 11) {
	        	update = "UPDATE socios SET directive="+dato+" WHERE id=?";
	        	System.out.println(update);
	        	db.executeUpdate(update, id_user);
	        	
	        } else if (!dato.isEmpty()) {
	        	db.executeUpdate(update, dato, id_user);
	        	if (selectedValue != dato)
	        		JOptionPane.showMessageDialog(null, "Campo actualizado correctamente.", "INFO", JOptionPane.INFORMATION_MESSAGE);
	        	System.out.println(update);
	        } else {
	        	if (columna != 10) {
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
		
		System.out.println(query);
		
		return query;
	}
	
}
