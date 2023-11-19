package giis.demo.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import giis.demo.logica.GestionCursosSocios;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.event.ActionEvent;

public class VentanaGestionCursosSocios extends JFrame {

	private static final long serialVersionUID = 1L;
	private VentanaPrincipal vp;
	private Object[][] datos;
	private String[] columnas; // TODO CREAR AQUÍ CON LOS NOMBRES DE LAS COLUMNAS

	private JPanel contentPane;
	private JScrollPane spCursos;
	private JTable tableCursos;
	private JButton btConfirmar;
	private GestionCursosSocios gestionCursos;

	/**
	 * Create the frame.
	 */
	public VentanaGestionCursosSocios(VentanaPrincipal vp) {
		// TODO TÍTULO Y ESAS COSAS
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 730, 474);
		setLocationRelativeTo(null);
		this.vp = vp;
		this.gestionCursos = new GestionCursosSocios(this);

		creaTablaPrueba();
//		this.datos = gestionCursos.cargaFilas();
//		this.columnas = gestionCursos.cargaNomColumnas();
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getSpCursos());
		contentPane.add(getBtConfirmar());
	}

	private void creaTablaPrueba() {
		JButton btInsc = new JButton("Inscribirse");
		JButton btBorr = new JButton("Desapuntarse");
		this.datos = new Object[][] {{1,"Iniciación", "Tenis", btBorr},
			{2,"Avanzado", "Tenis", btInsc},
			{3,"Iniciación", "Tiro con arco", btBorr}};
		this.columnas = new String[]{"ID CURSO","NOMBRE CURSO","DEPORTE", "OPCIÓN"};
	}

	private JScrollPane getSpCursos() {
		if (spCursos == null) {
			spCursos = new JScrollPane();
			spCursos.setBounds(67, 33, 559, 347);
			spCursos.setViewportView(getTableCursos());
		}
		return spCursos;
	}

	public JTable getTableCursos() {
		if (tableCursos == null) {
//			cargaDatos();
			tableCursos = new JTable(datos, columnas);
			tableCursos.setRowSelectionAllowed(true);
			tableCursos.getColumnModel().getColumn(3).setCellRenderer(new BotonRenderer());		// TODO CAMBIAR EL GETCOLUMN
			tableCursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);					// TODO PREGUNTAR 
			tableCursos.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {	
				@Override
				public void valueChanged(ListSelectionEvent e) {
					System.out.println(tableCursos.getSelectedRow());
//					btConfirmar.setEnabled(true);					
				}
			});
		}
		return tableCursos;
	}

	private JButton getBtConfirmar() {
		if (btConfirmar == null) {
			btConfirmar = new JButton("Borrar de curso");
			btConfirmar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borrarDeCurso();
				}
			});
			btConfirmar.setMnemonic('b');
			btConfirmar.setEnabled(false); //TODO CAMBIAR
			btConfirmar.setBounds(248, 391, 156, 33);
		}
		return btConfirmar;
	}

	protected void borrarDeCurso() {
		gestionCursos.borrar();
	}

	public VentanaPrincipal getVp() {
		return vp;
	}

	public Object[][] getDatos() {
		return datos;
	}

	public JTable getTable() {
		return tableCursos;
	}
	
	class BotonRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		@Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Component) {
                return (Component) value;
            } else {
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        }
    }
}