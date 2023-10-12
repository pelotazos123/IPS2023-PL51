package giis.demo.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import giis.demo.model.Generos;
import giis.demo.model.CrearLicencias.TiposLicencia;
import giis.demo.model.CrearLicencias.servicio.TramitarLicencia;
import giis.demo.util.FileUtil;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;

public class VentanaTramitarDatos extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String FICHERO_POLITICA_PROTECCION_DATOS = "src/main/resources/PoliticaDeProteccionDeDatos.txt";
	
	private TramitarLicencia tramitarLicencia;
	private boolean politicaAceptada;
	
	
	private JPanel pnPrincipal;
	private JPanel pnTramitarLicencia;
	private JPanel pnSur;
	private JPanel pnDatos;
	private JPanel pnDatosPersonales;
	private JPanel pnDatosSocio;
	private JLabel lbDatosSocio;
	private JPanel pnNombreSocio;
	private JLabel lbNombreSocio;
	private JTextField txNombreSocio;
	private JPanel pnApellidosSocio;
	private JLabel lbApellidosSocio;
	private JTextField txApellidosSocio;
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
	private JTextField txApellidosTutor;
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
	private JPanel pnTipoLicencia;
	private JLabel lbTipoLicencia;
	private JComboBox<TiposLicencia> cbTipoLicencia;
	private JPanel pnCrearYCancelarLicencia;
	private JButton btCrearLicencia;
	private JButton btCancelarLicencia;
	private JPanel pnPoliticaDeDatos;
	private JButton btPoliticaDeDatos;
	private JPanel pnAceptarPoliticaDatos;
	private JPanel pnAceptarOCancelarPoliticaDeDAtos;
	private JButton btRechazarPoliticaDeDatos;
	private JButton btAceparPoliticaDeDatos;
	private JPanel pnLabelPoliticaDeDatos;
	private JLabel lbPoliticaDeDatos;
	private JTextArea txPoliticaDeDatos;
	private JScrollPane scrPoliticaDeDatos;


	/**
	 * Create the frame.
	 */
	public VentanaTramitarDatos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870, 618);
		pnPrincipal = new JPanel();
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnPrincipal);
		setLocationRelativeTo(null);
		pnPrincipal.setLayout(new CardLayout(0, 0));
		pnPrincipal.add(getPnTramitarLicencia(), "pnTramitarLicencia");
		pnPrincipal.add(getPnAceptarPoliticaDatos(), "pnPoliticaDeDatos");
		this.tramitarLicencia = new TramitarLicencia();
		cargarDatos();
	}
	private void cargarDatos() {
		getTxNombreSocio().setText(tramitarLicencia.getSocio().getNombre());
		getTxApellidosSocio().setText(tramitarLicencia.getSocio().getApellidos());
		getCbGeneroSocio().setSelectedItem(tramitarLicencia.getSocio().getGenero());
		getCbEdadSocio().setSelectedIndex(tramitarLicencia.getSocio().getEdad());
		
	}
	private JPanel getPnTramitarLicencia() {
		if (pnTramitarLicencia == null) {
			pnTramitarLicencia = new JPanel();
			pnTramitarLicencia.setLayout(new BorderLayout(0, 0));
			pnTramitarLicencia.add(getPnSur(), BorderLayout.SOUTH);
			pnTramitarLicencia.add(getPnDatos(), BorderLayout.CENTER);
		}
		return pnTramitarLicencia;
	}
	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
			pnSur.setLayout(new GridLayout(0, 2, 0, 0));
			pnSur.add(getPnPoliticaDeDatos());
			pnSur.add(getPnCrearYCancelarLicencia());
		}
		return pnSur;
	}
	private JPanel getPnDatos() {
		if (pnDatos == null) {
			pnDatos = new JPanel();
			pnDatos.setLayout(new BorderLayout(0, 0));
			pnDatos.add(getPnDatosPersonales(), BorderLayout.CENTER);
			pnDatos.add(getPnDatosFacturacionYLicencia(), BorderLayout.SOUTH);
		}
		return pnDatos;
	}
	private JPanel getPnDatosPersonales() {
		if (pnDatosPersonales == null) {
			pnDatosPersonales = new JPanel();
			pnDatosPersonales.setLayout(new GridLayout(0, 2, 0, 0));
			pnDatosPersonales.add(getPnDatosSocio());
			pnDatosPersonales.add(getPnDatosTutor());
		}
		return pnDatosPersonales;
	}
	private JPanel getPnDatosSocio() {
		if (pnDatosSocio == null) {
			pnDatosSocio = new JPanel();
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
			txNombreSocio.setColumns(10);
		}
		return txNombreSocio;
	}
	private JPanel getPnApellidosSocio() {
		if (pnApellidosSocio == null) {
			pnApellidosSocio = new JPanel();
			pnApellidosSocio.add(getLbApellidosSocio());
			pnApellidosSocio.add(getTxApellidosSocio());
		}
		return pnApellidosSocio;
	}
	private JLabel getLbApellidosSocio() {
		if (lbApellidosSocio == null) {
			lbApellidosSocio = new JLabel("Apellidos:");
		}
		return lbApellidosSocio;
	}
	private JTextField getTxApellidosSocio() {
		if (txApellidosSocio == null) {
			txApellidosSocio = new JTextField();
			txApellidosSocio.setColumns(10);
		}
		return txApellidosSocio;
	}
	private JPanel getPnGeneroSocio() {
		if (pnGeneroSocio == null) {
			pnGeneroSocio = new JPanel();
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
			for (int i = 0; i < años.length-1; i++) {
				años[i] = ""+i;
			}
			cbEdadSocio.setModel(new DefaultComboBoxModel<String>(años));
			cbEdadSocio.setBounds(146, 66, 106, 22);
		}
		return cbEdadSocio;
	}
	private JPanel getPnDatosTutor() {
		if (pnDatosTutor == null) {
			pnDatosTutor = new JPanel();
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
			pnApellidosTutor.add(getLbApellidosTutor());
			pnApellidosTutor.add(getTxApellidosTutor());
		}
		return pnApellidosTutor;
	}
	private JLabel getLbApellidosTutor() {
		if (lbApellidosTutor == null) {
			lbApellidosTutor = new JLabel("Apellidos:");
		}
		return lbApellidosTutor;
	}
	private JTextField getTxApellidosTutor() {
		if (txApellidosTutor == null) {
			txApellidosTutor = new JTextField();
			txApellidosTutor.setColumns(10);
		}
		return txApellidosTutor;
	}
	private JPanel getPnGeneroTutor() {
		if (pnGeneroTutor == null) {
			pnGeneroTutor = new JPanel();
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
			pnDatosFacturacionYLicencia.setLayout(new GridLayout(1, 0, 0, 0));
			pnDatosFacturacionYLicencia.add(getPnDireccionFacturacion());
			pnDatosFacturacionYLicencia.add(getPnInfoFacturacion());
			pnDatosFacturacionYLicencia.add(getPnTipoLicencia());
		}
		return pnDatosFacturacionYLicencia;
	}
	private JPanel getPnDireccionFacturacion() {
		if (pnDireccionFacturacion == null) {
			pnDireccionFacturacion = new JPanel();
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
	private JPanel getPnTipoLicencia() {
		if (pnTipoLicencia == null) {
			pnTipoLicencia = new JPanel();
			pnTipoLicencia.add(getLbTipoLicencia());
			pnTipoLicencia.add(getCbTipoLicencia());
		}
		return pnTipoLicencia;
	}
	private JLabel getLbTipoLicencia() {
		if (lbTipoLicencia == null) {
			lbTipoLicencia = new JLabel("Tipo de licencia:");
		}
		return lbTipoLicencia;
	}
	private JComboBox<TiposLicencia> getCbTipoLicencia() {
		if (cbTipoLicencia == null) {
			cbTipoLicencia = new JComboBox<TiposLicencia>();
			TiposLicencia[] licencias = TiposLicencia.values();
			cbTipoLicencia.setModel(new DefaultComboBoxModel<TiposLicencia>(licencias));
			cbTipoLicencia.setBounds(146, 66, 106, 22);
		}
		return cbTipoLicencia;
	}
	private JPanel getPnCrearYCancelarLicencia() {
		if (pnCrearYCancelarLicencia == null) {
			pnCrearYCancelarLicencia = new JPanel();
			pnCrearYCancelarLicencia.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			pnCrearYCancelarLicencia.add(getBtCancelarLicencia());
			pnCrearYCancelarLicencia.add(getBtCrearLicencia());
		}
		return pnCrearYCancelarLicencia;
	}
	private JButton getBtCrearLicencia() {
		if (btCrearLicencia == null) {
			btCrearLicencia = new JButton("Crear Licencia");
			btCrearLicencia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(comprobarDatosCorrectos()) {
						crearLicencia();
						dispose();
					}
				}
			});
			btCrearLicencia.setEnabled(false);
			btCrearLicencia.setForeground(Color.WHITE);
			btCrearLicencia.setBackground(new Color(50, 205, 50));
		}
		return btCrearLicencia;
	}
	
	private boolean comprobarDatosCorrectos() {
		if(getTxNombreSocio().getText().isBlank() || getTxApellidosSocio().getText().isBlank() 
				|| getTxDireccionFacturacion().getText().isBlank() || getTxInfoFacturacion().getText().isBlank()) {
			JOptionPane.showMessageDialog(this,"Debe rellenar los campos Nombre, Apellidos, Dirección de facturación e Información de facturación ",
					"Campo", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}else if( Integer.parseInt((String) getCbEdadSocio().getSelectedItem()) < 18) {
			if(getTxNombreTutor().getText().isBlank() || getTxApellidosTutor().getText().isBlank()) {
				JOptionPane.showMessageDialog(this,"Debe rellenar los campos Nombre y Apellidos del tutor",
						"Campo", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}else {
				return true;
			}
		}else {
			return true;
		}
		
	}
	
	private void crearLicencia() {
		String nombreTutor = getTxNombreTutor().getText();
		String apellidoTutor = getTxApellidosTutor().getText();
		String edadTutor = (String) getCbEdadTutor().getSelectedItem();
		String direccionFacturacion = getTxDireccionFacturacion().getText();
		String infoFacturacion = getTxInfoFacturacion().getText();
		TiposLicencia licencia = (TiposLicencia) getCbTipoLicencia().getSelectedItem();
		if(Integer.parseInt((String) getCbEdadSocio().getSelectedItem()) < 18) {
			tramitarLicencia.crearLicencia(nombreTutor, apellidoTutor, edadTutor, direccionFacturacion, infoFacturacion, licencia);
		}else {
			tramitarLicencia.crearLicencia("noTutor", "noTutor", null, direccionFacturacion, infoFacturacion, licencia);
		}
		
	}
	
	private JButton getBtCancelarLicencia() {
		if (btCancelarLicencia == null) {
			btCancelarLicencia = new JButton("Cancelar");
			btCancelarLicencia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btCancelarLicencia.setForeground(Color.WHITE);
			btCancelarLicencia.setBackground(Color.RED);
		}
		return btCancelarLicencia;
	}
	private JPanel getPnPoliticaDeDatos() {
		if (pnPoliticaDeDatos == null) {
			pnPoliticaDeDatos = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnPoliticaDeDatos.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnPoliticaDeDatos.add(getBtPoliticaDeDatos());
		}
		return pnPoliticaDeDatos;
	}
	private JButton getBtPoliticaDeDatos() {
		if (btPoliticaDeDatos == null) {
			btPoliticaDeDatos = new JButton("Pol\u00EDtica de protecci\u00F3n de datos");
			btPoliticaDeDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout) getContentPane().getLayout()).show(getContentPane(),"pnPoliticaDeDatos");
				}
			});
		}
		return btPoliticaDeDatos;
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
					((CardLayout) getContentPane().getLayout()).show(getContentPane(),"pnTramitarLicencia");
					getBtCrearLicencia().setEnabled(false);
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
					((CardLayout) getContentPane().getLayout()).show(getContentPane(),"pnTramitarLicencia");
					getBtCrearLicencia().setEnabled(true);
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
			FlowLayout flowLayout = (FlowLayout) pnLabelPoliticaDeDatos.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
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
	private JTextArea getTxPoliticaDeDatos() {
		if (txPoliticaDeDatos == null) {
			txPoliticaDeDatos = new JTextArea();
			txPoliticaDeDatos.setEditable(false);
			txPoliticaDeDatos.setText(cargarPoliticaDeDatos());
		}
		return txPoliticaDeDatos;
	}
	private String cargarPoliticaDeDatos() {
		return FileUtil.loadFileTickets(FICHERO_POLITICA_PROTECCION_DATOS);
	}
	private JScrollPane getScrPoliticaDeDatos() {
		if (scrPoliticaDeDatos == null) {
			scrPoliticaDeDatos = new JScrollPane();
			scrPoliticaDeDatos.setViewportView(getTxPoliticaDeDatos());
		}
		return scrPoliticaDeDatos;
	}
}