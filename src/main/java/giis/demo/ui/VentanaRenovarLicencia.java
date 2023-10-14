package giis.demo.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.border.TitledBorder;

import giis.demo.model.CrearLicencias.servicio.TramitarLicencia;
import giis.demo.util.FileUtil;

import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VentanaRenovarLicencia extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private TramitarLicencia tramitarLicencia;
	
	private JPanel pnPrincipal;
	private JPanel pnRenovarLicencia;
	private JPanel pnPoliticaDeDatos;
	private JPanel pnBotones;
	private JButton btPoliticaDeDatos;
	private JButton btModificarDatos;
	private JButton btRenovarLicencia;
	private JPanel pnAceptarPoliticaDatos;
	private JPanel pnAceptarOCancelarPoliticaDeDAtos;
	private JButton btRechazarPoliticaDeDatos;
	private JButton btAceparPoliticaDeDatos;
	private JPanel pnLabelPoliticaDeDatos;
	private JLabel lbPoliticaDeDatos;
	private JScrollPane scrPoliticaDeDatos;
	private JTextArea txPoliticaDeDatos;
	private JPanel pnModificarLicencia;
	private JPanel pnSur;
	private JButton btCancelarModificacion;
	private JButton btModificarLicencia;
	private JPanel pnDatos;
	private JPanel pnDatosPersonales;
	private JPanel pnDatos_1;

	/**
	 * Create the frame.
	 */
	public VentanaRenovarLicencia(boolean esDirectivo) {
		setTitle("Club Deportivo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 663, 440);
		setLocationRelativeTo(null);
		pnPrincipal = new JPanel();
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(pnPrincipal);
		pnPrincipal.setLayout(new CardLayout(0, 0));
		pnPrincipal.add(getPnRenovarLicencia(), "pnRenovarLicencia");
		pnPrincipal.add(getPnAceptarPoliticaDatos(), "pnPoliticaDeDatos");
		pnPrincipal.add(getPnModificarLicencia(), "name_6298875107700");
		
		this.tramitarLicencia = new TramitarLicencia(esDirectivo);
	}

	private JPanel getPnRenovarLicencia() {
		if (pnRenovarLicencia == null) {
			pnRenovarLicencia = new JPanel();
			pnRenovarLicencia.setBorder(new TitledBorder(null, "Renovar Licencia", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnRenovarLicencia.setLayout(new GridLayout(0, 1, 0, 0));
			pnRenovarLicencia.add(getPnPoliticaDeDatos());
			pnRenovarLicencia.add(getPnBotones());
		}
		return pnRenovarLicencia;
	}
	private JPanel getPnPoliticaDeDatos() {
		if (pnPoliticaDeDatos == null) {
			pnPoliticaDeDatos = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnPoliticaDeDatos.getLayout();
			flowLayout.setVgap(70);
			pnPoliticaDeDatos.add(getBtPoliticaDeDatos());
		}
		return pnPoliticaDeDatos;
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnBotones.getLayout();
			flowLayout.setHgap(30);
			flowLayout.setVgap(70);
			pnBotones.add(getBtModificarDatos());
			pnBotones.add(getBtRenovarLicencia());
		}
		return pnBotones;
	}
	private JButton getBtPoliticaDeDatos() {
		if (btPoliticaDeDatos == null) {
			btPoliticaDeDatos = new JButton("Política de protección de datos");
			btPoliticaDeDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setBounds(100, 100, 870, 618);
					((CardLayout) getContentPane().getLayout()).show(getContentPane(),"pnPoliticaDeDatos");
				}
			});
			btPoliticaDeDatos.setFocusPainted(false);
			btPoliticaDeDatos.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return btPoliticaDeDatos;
	}
	private JButton getBtModificarDatos() {
		if (btModificarDatos == null) {
			btModificarDatos = new JButton("Modificar datos");
			btModificarDatos.setHorizontalAlignment(SwingConstants.RIGHT);
			btModificarDatos.setForeground(Color.BLACK);
			btModificarDatos.setBackground(Color.YELLOW);
			btModificarDatos.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return btModificarDatos;
	}
	private JButton getBtRenovarLicencia() {
		if (btRenovarLicencia == null) {
			btRenovarLicencia = new JButton("Renovar licencia");
			btRenovarLicencia.setForeground(Color.WHITE);
			btRenovarLicencia.setEnabled(false);
			btRenovarLicencia.setBackground(new Color(50, 205, 50));
			btRenovarLicencia.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return btRenovarLicencia;
	}
	private JPanel getPnAceptarPoliticaDatos() {
		if (pnAceptarPoliticaDatos == null) {
			pnAceptarPoliticaDatos = new JPanel();
			pnAceptarPoliticaDatos.setLayout(new BorderLayout(0, 0));
			pnAceptarPoliticaDatos.add(getPnAceptarOCancelarPoliticaDeDAtos(), BorderLayout.SOUTH);
			pnAceptarPoliticaDatos.add(getPnLabelPoliticaDeDatos(), BorderLayout.NORTH);
			pnAceptarPoliticaDatos.add(getScrPoliticaDeDatos(), BorderLayout.CENTER);
		}
		return pnAceptarPoliticaDatos;
	}
	private JPanel getPnAceptarOCancelarPoliticaDeDAtos() {
		if (pnAceptarOCancelarPoliticaDeDAtos == null) {
			pnAceptarOCancelarPoliticaDeDAtos = new JPanel();
			pnAceptarOCancelarPoliticaDeDAtos.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			pnAceptarOCancelarPoliticaDeDAtos.add(getBtRechazarPoliticaDeDatos());
			pnAceptarOCancelarPoliticaDeDAtos.add(getBtAceparPoliticaDeDatos());
		}
		return pnAceptarOCancelarPoliticaDeDAtos;
	}
	private JButton getBtRechazarPoliticaDeDatos() {
		if (btRechazarPoliticaDeDatos == null) {
			btRechazarPoliticaDeDatos = new JButton("Rechazar");
			btRechazarPoliticaDeDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout) getContentPane().getLayout()).show(getContentPane(),"pnRenovarLicencia");
					getBtRenovarLicencia().setEnabled(false);
					setBounds(100, 100, 663, 440);
					setLocationRelativeTo(null);
				}
			});
			btRechazarPoliticaDeDatos.setForeground(Color.WHITE);
			btRechazarPoliticaDeDatos.setBackground(Color.RED);
		}
		return btRechazarPoliticaDeDatos;
	}
	private JButton getBtAceparPoliticaDeDatos() {
		if (btAceparPoliticaDeDatos == null) {
			btAceparPoliticaDeDatos = new JButton("Aceptar");
			btAceparPoliticaDeDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setBounds(100, 100, 663, 440);
					((CardLayout) getContentPane().getLayout()).show(getContentPane(),"pnRenovarLicencia");
					getBtRenovarLicencia().setEnabled(true);
					setLocationRelativeTo(null);
				}
			});
			btAceparPoliticaDeDatos.setForeground(Color.WHITE);
			btAceparPoliticaDeDatos.setBackground(new Color(50, 205, 50));
		}
		return btAceparPoliticaDeDatos;
	}
	private JPanel getPnLabelPoliticaDeDatos() {
		if (pnLabelPoliticaDeDatos == null) {
			pnLabelPoliticaDeDatos = new JPanel();
			pnLabelPoliticaDeDatos.add(getLbPoliticaDeDatos());
		}
		return pnLabelPoliticaDeDatos;
	}
	private JLabel getLbPoliticaDeDatos() {
		if (lbPoliticaDeDatos == null) {
			lbPoliticaDeDatos = new JLabel("Política de protección de datos");
		}
		return lbPoliticaDeDatos;
	}
	private JScrollPane getScrPoliticaDeDatos() {
		if (scrPoliticaDeDatos == null) {
			scrPoliticaDeDatos = new JScrollPane();
			scrPoliticaDeDatos.setViewportView(getTxPoliticaDeDatos());
		}
		return scrPoliticaDeDatos;
	}
	private JTextArea getTxPoliticaDeDatos() {
		if (txPoliticaDeDatos == null) {
			txPoliticaDeDatos = new JTextArea();
			txPoliticaDeDatos.setText(cargarPoliticaDeDatos());
			txPoliticaDeDatos.setEditable(false);
		}
		return txPoliticaDeDatos;
	}
	
	private String cargarPoliticaDeDatos() {
		return FileUtil.loadFileTickets(VentanaTramitarLicencia.FICHERO_POLITICA_PROTECCION_DATOS);
	}
	private JPanel getPnModificarLicencia() {
		if (pnModificarLicencia == null) {
			pnModificarLicencia = new JPanel();
			pnModificarLicencia.setBorder(new TitledBorder(null, "Tramitar Licencia", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnModificarLicencia.setLayout(new BorderLayout(0, 0));
			pnModificarLicencia.add(getPnSur(), BorderLayout.SOUTH);
			pnModificarLicencia.add(getPnDatos(), BorderLayout.CENTER);
		}
		return pnModificarLicencia;
	}
	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
			pnSur.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			pnSur.add(getBtCancelarModificacion());
			pnSur.add(getBtModificarLicencia());
		}
		return pnSur;
	}
	private JButton getBtCancelarModificacion() {
		if (btCancelarModificacion == null) {
			btCancelarModificacion = new JButton("Cancelar");
			btCancelarModificacion.setForeground(Color.WHITE);
			btCancelarModificacion.setBackground(Color.RED);
		}
		return btCancelarModificacion;
	}
	private JButton getBtModificarLicencia() {
		if (btModificarLicencia == null) {
			btModificarLicencia = new JButton("Modificar Licencia");
			btModificarLicencia.setForeground(Color.WHITE);
			btModificarLicencia.setEnabled(false);
			btModificarLicencia.setBackground(new Color(50, 205, 50));
		}
		return btModificarLicencia;
	}
	private JPanel getPnDatos() {
		if (pnDatos == null) {
			pnDatos = new JPanel();
			pnDatos.setLayout(new BorderLayout(0, 0));
			pnDatos.add(getPnDatosPersonales(), BorderLayout.NORTH);
		}
		return pnDatos;
	}
	private JPanel getPnDatosPersonales() {
		if (pnDatosPersonales == null) {
			pnDatosPersonales = new JPanel();
			pnDatosPersonales.setLayout(new GridLayout(0, 2, 0, 0));
			pnDatosPersonales.add(getPnDatos_1());
		}
		return pnDatosPersonales;
	}
	private JPanel getPnDatos_1() {
		if (pnDatos_1 == null) {
			pnDatos_1 = new JPanel();
			pnDatos_1.setLayout(new BorderLayout(0, 0));
		}
		return pnDatos_1;
	}
}
