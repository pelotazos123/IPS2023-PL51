package giis.demo.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.border.TitledBorder;

import giis.demo.model.CrearLicencias.servicio.TramitarLicencia;
import giis.demo.util.FileUtil;

import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import giis.demo.model.Generos;
import giis.demo.model.CrearLicencias.Licencia;

public class VentanaRenovarLicencia extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private TramitarLicencia tramitarLicencia;
	String nombreSocio;
	String apellidoSocio;
	String edadSocio;
	Generos generoSocio;
	
	String nombreTutor;
	String apellidoTutor;
	String edadTutor;
	Generos generoTutor;
	
	String direccionFacturacion;
	String infoFacturacion;

	
	
	private JPanel pnPrincipal;
	private JPanel pnRenovarLicencia;
	private JPanel pnBotones;
	private JPanel pnAceptarOCancelar;
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
	private JPanel pnDatosSocio;
	private JLabel lbDatosSocio;
	private JPanel pnNombreSocio;
	private JLabel lbNombreSocio;
	private JTextField txNombreSocio;
	private JPanel pnApellidosSocio;
	private JLabel lbApellidosSocio;
	private JTextField txApellidoSocio;
	private JPanel pnGeneroSocio;
	private JLabel lbGeneroSocio;
	private JComboBox<Generos> cbGeneroSocio;
	private JPanel pnEdadSocio;
	private JLabel lbEdadSocio;
	private JComboBox<String> cbEdadSocio;
	private JPanel pnDatosTutor;
	private JLabel lbDatosTutor;
	private JPanel pnNombreTutor;
	private JLabel lbNombreTutor;
	private JTextField txNombreTutor;
	private JPanel pnApellidosTutor;
	private JLabel lbApellidosTutor;
	private JTextField txApellidoTutor;
	private JPanel pnGeneroTutor;
	private JLabel lbGeneroTutor;
	private JComboBox<Generos> cbGeneroTutor;
	private JPanel pnEdadTutor;
	private JLabel lbEdadTutor;
	private JComboBox<String> cbEdadTutor;
	private JPanel pnDatosFacturacionYLicencia;
	private JPanel pnDireccionFacturacion;
	private JLabel lbDireccionFacturacion;
	private JTextField txDireccionFacturacion;
	private JPanel pnInfoFacturacion;
	private JLabel lbInfoFacturacion;
	private JTextField txInfoFacturacion;
	private JPanel pnPoliticaDeDatos;
	private JPanel pnModificarDatosYSeleccionarLicencia;
	private JButton btCancelar;
	private JPanel pnModificarDatos;
	private JPanel pnSeleccionarLicencia;
	private JLabel lbSeleccionarLicencia;
	private JComboBox<Licencia> cbSeleccionarLicencia;

	/**
	 * Create the frame.
	 */
	public VentanaRenovarLicencia(TramitarLicencia tramitarLicencia) {
		setMinimumSize(new Dimension(645, 440));
		setBackground(Color.WHITE);
		this.tramitarLicencia = tramitarLicencia;
		setTitle("Club Deportivo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 663, 440);
		setLocationRelativeTo(null);
		pnPrincipal = new JPanel();
		pnPrincipal.setBackground(Color.WHITE);
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(pnPrincipal);
		pnPrincipal.setLayout(new CardLayout(0, 0));
		pnPrincipal.add(getPnRenovarLicencia(), "pnRenovarLicencia");
		pnPrincipal.add(getPnAceptarPoliticaDatos(), "pnPoliticaDeDatos");
		pnPrincipal.add(getPnModificarLicencia(), "pnModificarDatos");
		
	}

	private JPanel getPnRenovarLicencia() {
		if (pnRenovarLicencia == null) {
			pnRenovarLicencia = new JPanel();
			pnRenovarLicencia.setBackground(Color.WHITE);
			pnRenovarLicencia.setBorder(new TitledBorder(null, "Renovar Licencia", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnRenovarLicencia.setLayout(new GridLayout(0, 1, 0, 0));
			pnRenovarLicencia.add(getPnBotones());
			pnRenovarLicencia.add(getPnAceptarOCancelar());
		}
		return pnRenovarLicencia;
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setBackground(Color.WHITE);
			pnBotones.setLayout(new GridLayout(0, 1, 0, 0));
			pnBotones.add(getPnPoliticaDeDatos());
			pnBotones.add(getPnModificarDatosYSeleccionarLicencia());
		}
		return pnBotones;
	}
	private JPanel getPnAceptarOCancelar() {
		if (pnAceptarOCancelar == null) {
			pnAceptarOCancelar = new JPanel();
			pnAceptarOCancelar.setBackground(Color.WHITE);
			FlowLayout fl_pnAceptarOCancelar = (FlowLayout) pnAceptarOCancelar.getLayout();
			fl_pnAceptarOCancelar.setHgap(30);
			fl_pnAceptarOCancelar.setVgap(70);
			pnAceptarOCancelar.add(getBtCancelar());
			pnAceptarOCancelar.add(getBtRenovarLicencia());
		}
		return pnAceptarOCancelar;
	}
	private JButton getBtPoliticaDeDatos() {
		if (btPoliticaDeDatos == null) {
			btPoliticaDeDatos = new JButton("Política de protección de datos");
			btPoliticaDeDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setBounds(100, 100, 870, 618);
					setLocationRelativeTo(null);
					setMinimumSize(new Dimension(1050, 477));
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
			btModificarDatos.setFocusPainted(false);
			btModificarDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(licenciaSeleccionada()) {
						setBounds(100, 100, 870, 618);
						setLocationRelativeTo(null);
						setMinimumSize(new Dimension(1050, 477));
						((CardLayout) getContentPane().getLayout()).show(getContentPane(),"pnModificarDatos");
					}
				}
			});
			btModificarDatos.setHorizontalAlignment(SwingConstants.RIGHT);
			btModificarDatos.setForeground(Color.BLACK);
			btModificarDatos.setBackground(Color.YELLOW);
			btModificarDatos.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return btModificarDatos;
	}
	
	private void cargarDatos() {
		getTxNombreSocio().setText(tramitarLicencia.getSocio().getNombre());
		getTxApellidoSocio().setText(tramitarLicencia.getSocio().getApellidos());
		getCbGeneroSocio().setSelectedItem(tramitarLicencia.getSocio().getGenero());
		getCbEdadSocio().setSelectedIndex(tramitarLicencia.getSocio().getEdad());
		
		String nombre = tramitarLicencia.getLicenciaSeleccionada().getNombreTutor().equals("noTutor")?"":tramitarLicencia.getLicenciaSeleccionada().getNombreTutor();
		String apellido = tramitarLicencia.getLicenciaSeleccionada().getApellidosTutor().equals("noTutor")?"":tramitarLicencia.getLicenciaSeleccionada().getApellidosTutor();
		getTxNombreTutor().setText(nombre);
		getTxApellidoTutor().setText(apellido);
		getCbGeneroTutor().setSelectedItem(tramitarLicencia.getLicenciaSeleccionada().getGeneroTutor());
		getCbEdadTutor().setSelectedIndex(tramitarLicencia.getLicenciaSeleccionada().getEdadTutor());
		
		getTxDireccionFacturacion().setText(tramitarLicencia.getLicenciaSeleccionada().getDireccionFacturacion());
		getTxInfoFacturacion().setText(tramitarLicencia.getLicenciaSeleccionada().getInfoFacturacion());
		
	}
	
	private JButton getBtRenovarLicencia() {
		if (btRenovarLicencia == null) {
			btRenovarLicencia = new JButton("Renovar licencia");
			btRenovarLicencia.setFocusPainted(false);
			btRenovarLicencia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(licenciaSeleccionada()) {
						modificarDatos();
						guardarDatosModificados();
						dispose();
					}
				}
			});
			btRenovarLicencia.setForeground(Color.WHITE);
			btRenovarLicencia.setEnabled(false);
			btRenovarLicencia.setBackground(new Color(50, 205, 50));
			btRenovarLicencia.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return btRenovarLicencia;
	}
	
	private boolean licenciaSeleccionada() {
		if(tramitarLicencia.getLicenciaSeleccionada() != null) {
			return true;
		}else {
			JOptionPane.showMessageDialog(this,"No tienes ninguna licencia seleccionada",
					"Licencias", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	private JPanel getPnAceptarPoliticaDatos() {
		if (pnAceptarPoliticaDatos == null) {
			pnAceptarPoliticaDatos = new JPanel();
			pnAceptarPoliticaDatos.setBackground(Color.WHITE);
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
			pnAceptarOCancelarPoliticaDeDAtos.setBackground(Color.WHITE);
			pnAceptarOCancelarPoliticaDeDAtos.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			pnAceptarOCancelarPoliticaDeDAtos.add(getBtRechazarPoliticaDeDatos());
			pnAceptarOCancelarPoliticaDeDAtos.add(getBtAceparPoliticaDeDatos());
		}
		return pnAceptarOCancelarPoliticaDeDAtos;
	}
	private JButton getBtRechazarPoliticaDeDatos() {
		if (btRechazarPoliticaDeDatos == null) {
			btRechazarPoliticaDeDatos = new JButton("Rechazar");
			btRechazarPoliticaDeDatos.setFocusPainted(false);
			btRechazarPoliticaDeDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setMinimumSize(new Dimension(645, 440));
					((CardLayout) getContentPane().getLayout()).show(getContentPane(),"pnRenovarLicencia");
					getBtRenovarLicencia().setEnabled(false);
					setBounds(100, 100, 663, 440);
					setLocationRelativeTo(null);
					getBtPoliticaDeDatos().setEnabled(true);
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
			btAceparPoliticaDeDatos.setFocusPainted(false);
			btAceparPoliticaDeDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setMinimumSize(new Dimension(645, 440));
					setBounds(100, 100, 663, 440);
					((CardLayout) getContentPane().getLayout()).show(getContentPane(),"pnRenovarLicencia");
					getBtRenovarLicencia().setEnabled(true);
					setLocationRelativeTo(null);
					getBtPoliticaDeDatos().setEnabled(false);
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
			pnLabelPoliticaDeDatos.setBackground(Color.WHITE);
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
			pnModificarLicencia.setBackground(Color.WHITE);
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
			pnSur.setBackground(Color.WHITE);
			pnSur.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			pnSur.add(getBtCancelarModificacion());
			pnSur.add(getBtModificarLicencia());
		}
		return pnSur;
	}
	private JButton getBtCancelarModificacion() {
		if (btCancelarModificacion == null) {
			btCancelarModificacion = new JButton("Cancelar");
			btCancelarModificacion.setFocusPainted(false);
			btCancelarModificacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout) getContentPane().getLayout()).show(getContentPane(),"pnRenovarLicencia");
					setMinimumSize(new Dimension(645, 440));
					setBounds(100, 100, 663, 440);
					setLocationRelativeTo(null);
				}
			});
			btCancelarModificacion.setForeground(Color.WHITE);
			btCancelarModificacion.setBackground(Color.RED);
		}
		return btCancelarModificacion;
	}
	private JButton getBtModificarLicencia() {
		if (btModificarLicencia == null) {
			btModificarLicencia = new JButton("Modificar Licencia");
			btModificarLicencia.setFocusPainted(false);
			btModificarLicencia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout) getContentPane().getLayout()).show(getContentPane(),"pnRenovarLicencia");
					setMinimumSize(new Dimension(645, 440));
					setBounds(100, 100, 663, 440);
					setLocationRelativeTo(null);
					modificarDatos();
				}
			});
			btModificarLicencia.setForeground(Color.WHITE);
			btModificarLicencia.setBackground(new Color(50, 205, 50));
		}
		return btModificarLicencia;
	}
	
	private void modificarDatos() {
		nombreSocio = getTxNombreSocio().getText();
		apellidoSocio = getTxApellidoSocio().getText();
		edadSocio = (String) getCbEdadSocio().getSelectedItem();
		generoSocio = (Generos) getCbGeneroSocio().getSelectedItem();
		
		nombreTutor = getTxNombreTutor().getText();
		apellidoTutor = getTxApellidoTutor().getText();
		edadTutor = (String) getCbEdadTutor().getSelectedItem();
		generoTutor = (Generos) getCbGeneroTutor().getSelectedItem();
		
		direccionFacturacion = getTxDireccionFacturacion().getText();
		infoFacturacion = getTxInfoFacturacion().getText();
		if(Integer.parseInt((String) edadSocio) < 18) {
			tramitarLicencia.modificarDatosLicencia(nombreTutor, apellidoTutor, edadTutor, generoTutor, direccionFacturacion, infoFacturacion);
			tramitarLicencia.modificarDatosSocio(nombreSocio, apellidoSocio, generoSocio, edadSocio);
		}else {
			tramitarLicencia.modificarDatosLicencia("noTutor", "noTutor", null, null, direccionFacturacion, infoFacturacion);
			tramitarLicencia.modificarDatosSocio(nombreSocio, apellidoSocio, generoSocio, edadSocio);
		}
	}
	
	private void guardarDatosModificados() {
		tramitarLicencia.guardarDatosModificadosLicencia();
		tramitarLicencia.guardarDatosModificadosSocio();
		
	}
	
	private JPanel getPnDatos() {
		if (pnDatos == null) {
			pnDatos = new JPanel();
			pnDatos.setBackground(Color.WHITE);
			pnDatos.setLayout(new BorderLayout(0, 0));
			pnDatos.add(getPnDatosPersonales(), BorderLayout.CENTER);
			pnDatos.add(getPnDatosFacturacionYLicencia(), BorderLayout.SOUTH);
		}
		return pnDatos;
	}
	private JPanel getPnDatosPersonales() {
		if (pnDatosPersonales == null) {
			pnDatosPersonales = new JPanel();
			pnDatosPersonales.setBackground(Color.WHITE);
			pnDatosPersonales.setLayout(new GridLayout(0, 2, 0, 0));
			pnDatosPersonales.add(getPnDatosSocio());
			pnDatosPersonales.add(getPnDatosTutor());
		}
		return pnDatosPersonales;
	}
	private JPanel getPnDatosSocio() {
		if (pnDatosSocio == null) {
			pnDatosSocio = new JPanel();
			pnDatosSocio.setBackground(Color.WHITE);
			pnDatosSocio.setLayout(new GridLayout(0, 1, 0, 0));
			pnDatosSocio.add(getLbDatosSocio());
			pnDatosSocio.add(getPnNombreSocio());
			pnDatosSocio.add(getPnApellidosSocio());
			pnDatosSocio.add(getPnGeneroSocio());
			pnDatosSocio.add(getPnEdadSocio());
		}
		return pnDatosSocio;
	}
	private JLabel getLbDatosSocio() {
		if (lbDatosSocio == null) {
			lbDatosSocio = new JLabel("Datos del socio:");
		}
		return lbDatosSocio;
	}
	private JPanel getPnNombreSocio() {
		if (pnNombreSocio == null) {
			pnNombreSocio = new JPanel();
			pnNombreSocio.setBackground(Color.WHITE);
			pnNombreSocio.add(getLbNombreSocio());
			pnNombreSocio.add(getTxNombreSocio());
		}
		return pnNombreSocio;
	}
	private JLabel getLbNombreSocio() {
		if (lbNombreSocio == null) {
			lbNombreSocio = new JLabel("Nombre:");
		}
		return lbNombreSocio;
	}
	private JTextField getTxNombreSocio() {
		if (txNombreSocio == null) {
			txNombreSocio = new JTextField();
			txNombreSocio.setText((String) null);
			txNombreSocio.setColumns(10);
		}
		return txNombreSocio;
	}
	private JPanel getPnApellidosSocio() {
		if (pnApellidosSocio == null) {
			pnApellidosSocio = new JPanel();
			pnApellidosSocio.setBackground(Color.WHITE);
			pnApellidosSocio.add(getLbApellidosSocio());
			pnApellidosSocio.add(getTxApellidoSocio());
		}
		return pnApellidosSocio;
	}
	private JLabel getLbApellidosSocio() {
		if (lbApellidosSocio == null) {
			lbApellidosSocio = new JLabel("Apellidos:");
		}
		return lbApellidosSocio;
	}
	private JTextField getTxApellidoSocio() {
		if (txApellidoSocio == null) {
			txApellidoSocio = new JTextField();
			txApellidoSocio.setText((String) null);
			txApellidoSocio.setColumns(10);
		}
		return txApellidoSocio;
	}
	private JPanel getPnGeneroSocio() {
		if (pnGeneroSocio == null) {
			pnGeneroSocio = new JPanel();
			pnGeneroSocio.setBackground(Color.WHITE);
			pnGeneroSocio.add(getLbGeneroSocio());
			pnGeneroSocio.add(getCbGeneroSocio());
		}
		return pnGeneroSocio;
	}
	private JLabel getLbGeneroSocio() {
		if (lbGeneroSocio == null) {
			lbGeneroSocio = new JLabel("Genero:");
		}
		return lbGeneroSocio;
	}
	private JComboBox<Generos> getCbGeneroSocio() {
		if (cbGeneroSocio == null) {
			cbGeneroSocio = new JComboBox<Generos>();
			Generos[] generos = Generos.values();
			cbGeneroSocio.setModel(new DefaultComboBoxModel<Generos>(generos));
			cbGeneroSocio.setBounds(146, 66, 106, 22);
		}
		return cbGeneroSocio;
	}
	private JPanel getPnEdadSocio() {
		if (pnEdadSocio == null) {
			pnEdadSocio = new JPanel();
			pnEdadSocio.setBackground(Color.WHITE);
			pnEdadSocio.add(getLbEdadSocio());
			pnEdadSocio.add(getCbEdadSocio());
		}
		return pnEdadSocio;
	}
	private JLabel getLbEdadSocio() {
		if (lbEdadSocio == null) {
			lbEdadSocio = new JLabel("Edad:");
		}
		return lbEdadSocio;
	}
	private JComboBox<String> getCbEdadSocio() {
		if (cbEdadSocio == null) {
			cbEdadSocio = new JComboBox<String>();
			// crear el array de string y rellenar con bucle for
			String[] años = new String[102];
			for (int i = 0; i < años.length - 1; i++) {
				años[i] = "" + i;
			}
			cbEdadSocio.setModel(new DefaultComboBoxModel<String>(años));
			cbEdadSocio.setBounds(146, 66, 106, 22);
		}
		return cbEdadSocio;
	}
	private JPanel getPnDatosTutor() {
		if (pnDatosTutor == null) {
			pnDatosTutor = new JPanel();
			pnDatosTutor.setBackground(Color.WHITE);
			pnDatosTutor.setLayout(new GridLayout(0, 1, 0, 0));
			pnDatosTutor.add(getLbDatosTutor());
			pnDatosTutor.add(getPnNombreTutor());
			pnDatosTutor.add(getPnApellidosTutor());
			pnDatosTutor.add(getPnGeneroTutor());
			pnDatosTutor.add(getPnEdadTutor());
		}
		return pnDatosTutor;
	}
	private JLabel getLbDatosTutor() {
		if (lbDatosTutor == null) {
			lbDatosTutor = new JLabel("Datos del tutor:");
		}
		return lbDatosTutor;
	}
	private JPanel getPnNombreTutor() {
		if (pnNombreTutor == null) {
			pnNombreTutor = new JPanel();
			pnNombreTutor.setBackground(Color.WHITE);
			pnNombreTutor.add(getLbNombreTutor());
			pnNombreTutor.add(getTxNombreTutor());
		}
		return pnNombreTutor;
	}
	private JLabel getLbNombreTutor() {
		if (lbNombreTutor == null) {
			lbNombreTutor = new JLabel("Nombre:");
		}
		return lbNombreTutor;
	}
	private JTextField getTxNombreTutor() {
		if (txNombreTutor == null) {
			txNombreTutor = new JTextField();
			txNombreTutor.setColumns(10);
		}
		return txNombreTutor;
	}
	private JPanel getPnApellidosTutor() {
		if (pnApellidosTutor == null) {
			pnApellidosTutor = new JPanel();
			pnApellidosTutor.setBackground(Color.WHITE);
			pnApellidosTutor.add(getLbApellidosTutor());
			pnApellidosTutor.add(getTxApellidoTutor());
		}
		return pnApellidosTutor;
	}
	private JLabel getLbApellidosTutor() {
		if (lbApellidosTutor == null) {
			lbApellidosTutor = new JLabel("Apellidos:");
		}
		return lbApellidosTutor;
	}
	private JTextField getTxApellidoTutor() {
		if (txApellidoTutor == null) {
			txApellidoTutor = new JTextField();
			txApellidoTutor.setColumns(10);
		}
		return txApellidoTutor;
	}
	private JPanel getPnGeneroTutor() {
		if (pnGeneroTutor == null) {
			pnGeneroTutor = new JPanel();
			pnGeneroTutor.setBackground(Color.WHITE);
			pnGeneroTutor.add(getLbGeneroTutor());
			pnGeneroTutor.add(getCbGeneroTutor());
		}
		return pnGeneroTutor;
	}
	private JLabel getLbGeneroTutor() {
		if (lbGeneroTutor == null) {
			lbGeneroTutor = new JLabel("Genero:");
		}
		return lbGeneroTutor;
	}
	private JComboBox<Generos> getCbGeneroTutor() {
		if (cbGeneroTutor == null) {
			cbGeneroTutor = new JComboBox<Generos>();
			Generos[] generos = Generos.values();
			cbGeneroTutor.setModel(new DefaultComboBoxModel<Generos>(generos));
			cbGeneroTutor.setBounds(146, 66, 106, 22);
		}
		return cbGeneroTutor;
	}
	private JPanel getPnEdadTutor() {
		if (pnEdadTutor == null) {
			pnEdadTutor = new JPanel();
			pnEdadTutor.setBackground(Color.WHITE);
			pnEdadTutor.add(getLbEdadTutor());
			pnEdadTutor.add(getCbEdadTutor());
		}
		return pnEdadTutor;
	}
	private JLabel getLbEdadTutor() {
		if (lbEdadTutor == null) {
			lbEdadTutor = new JLabel("Edad:");
		}
		return lbEdadTutor;
	}
	private JComboBox<String> getCbEdadTutor() {
		if (cbEdadTutor == null) {
			cbEdadTutor = new JComboBox<String>();
			String[] años = new String[104];
			for (int i = 0; i < años.length-1; i++) {
				años[i] = ""+(i+18);
			}
			cbEdadTutor.setModel(new DefaultComboBoxModel<String>(años));
			cbEdadTutor.setBounds(146, 66, 106, 22);
		}
		return cbEdadTutor;
	}
	private JPanel getPnDatosFacturacionYLicencia() {
		if (pnDatosFacturacionYLicencia == null) {
			pnDatosFacturacionYLicencia = new JPanel();
			pnDatosFacturacionYLicencia.setBackground(Color.WHITE);
			pnDatosFacturacionYLicencia.setLayout(new GridLayout(1, 0, 0, 0));
			pnDatosFacturacionYLicencia.add(getPnDireccionFacturacion());
			pnDatosFacturacionYLicencia.add(getPnInfoFacturacion());
		}
		return pnDatosFacturacionYLicencia;
	}
	private JPanel getPnDireccionFacturacion() {
		if (pnDireccionFacturacion == null) {
			pnDireccionFacturacion = new JPanel();
			pnDireccionFacturacion.setBackground(Color.WHITE);
			pnDireccionFacturacion.add(getLbDireccionFacturacion());
			pnDireccionFacturacion.add(getTxDireccionFacturacion());
		}
		return pnDireccionFacturacion;
	}
	private JLabel getLbDireccionFacturacion() {
		if (lbDireccionFacturacion == null) {
			lbDireccionFacturacion = new JLabel("Direccion de facturacion:");
		}
		return lbDireccionFacturacion;
	}
	private JTextField getTxDireccionFacturacion() {
		if (txDireccionFacturacion == null) {
			txDireccionFacturacion = new JTextField();
			txDireccionFacturacion.setColumns(10);
		}
		return txDireccionFacturacion;
	}
	private JPanel getPnInfoFacturacion() {
		if (pnInfoFacturacion == null) {
			pnInfoFacturacion = new JPanel();
			pnInfoFacturacion.setBackground(Color.WHITE);
			pnInfoFacturacion.add(getLbInfoFacturacion());
			pnInfoFacturacion.add(getTxInfoFacturacion());
		}
		return pnInfoFacturacion;
	}
	private JLabel getLbInfoFacturacion() {
		if (lbInfoFacturacion == null) {
			lbInfoFacturacion = new JLabel("Informacion de facturacion:");
		}
		return lbInfoFacturacion;
	}
	private JTextField getTxInfoFacturacion() {
		if (txInfoFacturacion == null) {
			txInfoFacturacion = new JTextField();
			txInfoFacturacion.setColumns(10);
		}
		return txInfoFacturacion;
	}
	private JPanel getPnPoliticaDeDatos() {
		if (pnPoliticaDeDatos == null) {
			pnPoliticaDeDatos = new JPanel();
			pnPoliticaDeDatos.setBackground(Color.WHITE);
			FlowLayout flowLayout = (FlowLayout) pnPoliticaDeDatos.getLayout();
			flowLayout.setVgap(22);
			pnPoliticaDeDatos.add(getBtPoliticaDeDatos());
		}
		return pnPoliticaDeDatos;
	}
	private JPanel getPnModificarDatosYSeleccionarLicencia() {
		if (pnModificarDatosYSeleccionarLicencia == null) {
			pnModificarDatosYSeleccionarLicencia = new JPanel();
			pnModificarDatosYSeleccionarLicencia.setBackground(Color.WHITE);
			pnModificarDatosYSeleccionarLicencia.setLayout(new GridLayout(0, 2, 0, 0));
			pnModificarDatosYSeleccionarLicencia.add(getPnModificarDatos());
			pnModificarDatosYSeleccionarLicencia.add(getPnSeleccionarLicencia());
		}
		return pnModificarDatosYSeleccionarLicencia;
	}
	private JButton getBtCancelar() {
		if (btCancelar == null) {
			btCancelar = new JButton("Cancelar");
			btCancelar.setFocusPainted(false);
			btCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tramitarLicencia.restaurarSocio(tramitarLicencia.getSocio().getId());
					dispose();
				}
			});
			btCancelar.setForeground(Color.WHITE);
			btCancelar.setFont(new Font("Tahoma", Font.PLAIN, 30));
			btCancelar.setBackground(Color.RED);
		}
		return btCancelar;
	}
	private JPanel getPnModificarDatos() {
		if (pnModificarDatos == null) {
			pnModificarDatos = new JPanel();
			pnModificarDatos.setBackground(Color.WHITE);
			FlowLayout flowLayout = (FlowLayout) pnModificarDatos.getLayout();
			flowLayout.setVgap(22);
			pnModificarDatos.add(getBtModificarDatos());
		}
		return pnModificarDatos;
	}
	private JPanel getPnSeleccionarLicencia() {
		if (pnSeleccionarLicencia == null) {
			pnSeleccionarLicencia = new JPanel();
			pnSeleccionarLicencia.setBackground(Color.WHITE);
			FlowLayout flowLayout = (FlowLayout) pnSeleccionarLicencia.getLayout();
			flowLayout.setVgap(30);
			pnSeleccionarLicencia.add(getLbSeleccionarLicencia());
			pnSeleccionarLicencia.add(getCbSeleccionarLicencia());
		}
		return pnSeleccionarLicencia;
	}
	private JLabel getLbSeleccionarLicencia() {
		if (lbSeleccionarLicencia == null) {
			lbSeleccionarLicencia = new JLabel("Seleccionar licencia");
		}
		return lbSeleccionarLicencia;
	}
	private JComboBox<Licencia> getCbSeleccionarLicencia() {
		if (cbSeleccionarLicencia == null) {
			cbSeleccionarLicencia = new JComboBox<Licencia>();
			cbSeleccionarLicencia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Licencia licenciaSeleccionada = (Licencia) getCbSeleccionarLicencia().getSelectedItem();
					tramitarLicencia.setLicenciaSeleccionada(licenciaSeleccionada);
					cargarDatos();
				}
			});
			Licencia[] licencias = tramitarLicencia.getLicenciasPagadas();
			cbSeleccionarLicencia.setModel(new DefaultComboBoxModel<Licencia>(licencias));
			cbSeleccionarLicencia.setBounds(146, 66, 106, 22);
		}
		return cbSeleccionarLicencia;
	}
}
