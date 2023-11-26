package giis.demo.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import giis.demo.logica.GestionCursosSocios;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaGestionCursos extends JFrame {

	private static final long serialVersionUID = 1L;
	private VentanaPrincipal vp;
	private Object[][] datos;
	private String[] columnas;
	private GestionCursosSocios gestionCursos;

	private JPanel contentPane;
	private JScrollPane spCursos;
	private JTable tableCursos;
	private JPanel pnBoton;
	private JButton btAccion;

	/**
	 * Create the frame.
	 */
	public VentanaGestionCursos(VentanaPrincipal vp) {
		setResizable(false);
		setTitle("Club deportivo. Gestión cursos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 969, 599);
		setLocationRelativeTo(null);

		this.vp = vp;
		this.gestionCursos = new GestionCursosSocios(this);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getSpCursos());
		contentPane.add(getPnBoton());
	}

	private JScrollPane getSpCursos() {
		if (spCursos == null) {
			spCursos = new JScrollPane();
			spCursos.setBounds(21, 11, 847, 476);
			spCursos.setViewportView(getTableCursos());
		}
		return spCursos;
	}

	public JTable getTableCursos() {
		if (tableCursos == null) {
			this.datos = gestionCursos.cargaFilas();
			this.columnas = gestionCursos.cargaNomColumnas();
			DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
				@Override
				public boolean isCellEditable(int row, int column) {
					// Hacer todas las celdas no editables
					return false;
				}
			};
			tableCursos = new JTable(modelo);
			tableCursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableCursos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					activaBt();
				}
			});

		}
		return tableCursos;
	}

	protected void activaBt() {
		btAccion.setEnabled(true);
		String accion = datos[tableCursos.getSelectedRows()[0]][datos[0].length - 1].toString();
		if(accion.equals(GestionCursosSocios.INSCRITO))
			btAccion.setText("Borrarse");
		else
			btAccion.setText("Inscribirse");
	}

	public VentanaPrincipal getVp() {
		return vp;
	}

	public Object[][] getDatos() {
		return datos;
	}

	public String[] getColumnas() {
		return columnas;
	}

	public GestionCursosSocios getGestionCursos() {
		return gestionCursos;
	}

	private JPanel getPnBoton() {
		if (pnBoton == null) {
			pnBoton = new JPanel();
			pnBoton.setBounds(21, 498, 282, 51);
			pnBoton.setLayout(null);
			pnBoton.add(getBtAccion());
		}
		return pnBoton;
	}

	private JButton getBtAccion() {
		if (btAccion == null) {
			btAccion = new JButton("");
			btAccion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					realizaAccion();
				}
			});
			btAccion.setEnabled(false);
			btAccion.setBounds(10, 11, 151, 33);
		}
		return btAccion;
	}

	protected void realizaAccion() {
		gestionCursos.realizaAccion(datos[tableCursos.getSelectedRows()[0]][datos[0].length - 1].toString());
		this.dispose();
	}
}
