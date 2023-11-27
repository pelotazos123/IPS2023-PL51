package giis.demo.ui.licencias;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import giis.demo.model.Generos;
import giis.demo.model.TiposDeportes;
import giis.demo.model.CrearLicencias.TiposLicencia;
import giis.demo.model.CrearLicencias.servicio.TramitarLicencia;
import giis.demo.model.loggin.servicio.GestionarLoggin;
import giis.demo.util.FileUtil;

public class VentanaTramitarLicencia extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String FICHERO_POLITICA_PROTECCION_DATOS = "src/main/resources/PoliticaDeProteccionDeDatos.txt";
	
	private TramitarLicencia tramitarLicencia;
	private GestionarLoggin loggin;
	private boolean esDirectivo;
	
	
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
	private JPanel pnDatosFacturacionYLicencia;
	private JPanel pnDireccionFacturacion;
	private JLabel lbDireccionFacturacion;
	private JTextField txDireccionFacturacion;
	private JPanel pnInfoFacturacion;
	private JLabel lbInfoFacturacion;
	private JTextField txInfoFacturacion;
	private JPanel pnLicencia;
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
	private JDateChooser dcFechaNacimientoSocio;
	private JDateChooser dcFechaNacimientoTutor;
	private JPanel pnTelfSocio;
	private JLabel lbTelfSocio;
	private JTextField txTelfSocio;
	private JPanel pnTelfTutor;
	private JLabel lbTelfTutor;
	private JTextField txTelfTutor;
	private JPanel pnCorreoSocio;
	private JLabel lbCorreoSocio;
	private JTextField txCorreoSocio;
	private JPanel pnCorreoTutor;
	private JLabel lbCorreoTutor;
	private JTextField txCorreoTutor;
	private JPanel pnDniSocio;
	private JLabel lbDniSocio;
	private JTextField txDniSocio;
	private JPanel pnDniTutor;
	private JLabel lbDniTutor;
	private JTextField txDniTutor;
	private JPanel pnSeleccionarDeporte;
	private JLabel lbSeleccionarDeporte;
	private JComboBox<TiposDeportes> cbDeporte;
	private JLabel lbTipoLicencia;
	private JComboBox<TiposLicencia> cbTipoLicencia;


	/**
	 * Create the frame.
	 */
	public VentanaTramitarLicencia(TramitarLicencia tramitarLicencia, GestionarLoggin loggin) {
		setMinimumSize(new Dimension(1400, 477));
		setBackground(Color.WHITE);
		this.tramitarLicencia = tramitarLicencia;
		this.loggin = loggin;
		setTitle("Club Deportivo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1288, 658);
		pnPrincipal = new JPanel();
		pnPrincipal.setBackground(Color.WHITE);
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnPrincipal);
		setLocationRelativeTo(null);
		pnPrincipal.setLayout(new CardLayout(0, 0));
		pnPrincipal.add(getPnTramitarLicencia(), "pnTramitarLicencia");
		pnPrincipal.add(getPnAceptarPoliticaDatos(), "pnPoliticaDeDatos");
		cargarDatos();
	}
	private void cargarDatos() {
		esDirectivo = tramitarLicencia.esDirectivo();
		if(!esDirectivo) {
			getTxNombreSocio().setText(tramitarLicencia.getSocio().getNombre());
			getTxApellidosSocio().setText(tramitarLicencia.getSocio().getApellidos());
			getCbGeneroSocio().setSelectedItem(tramitarLicencia.getSocio().getGenero());
			getTxDniSocio().setText(tramitarLicencia.getSocio().getDni());
			getTxTelfSocio().setText(""+tramitarLicencia.getSocio().getTelefono());
			getTxCorreoSocio().setText(tramitarLicencia.getSocio().getCorreo());
			
			
			Date fecha = Date.from(tramitarLicencia.getSocio().getFechaNacimiento().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			getDcFechaNacimientoSocio().setDate(fecha);
			comprobarEdad();
		}else {
			TiposLicencia[] licencias = tramitarLicencia.getLicenciasDisponibles(true);
			cbTipoLicencia.setModel(new DefaultComboBoxModel<TiposLicencia>(licencias));
		}
		TiposDeportes[] deportes = TiposDeportes.values();
		getCbDeporte().setModel(new DefaultComboBoxModel<TiposDeportes>(deportes));
	}
	private JPanel getPnTramitarLicencia() {
		if (pnTramitarLicencia == null) {
			pnTramitarLicencia = new JPanel();
			pnTramitarLicencia.setBackground(Color.WHITE);
			pnTramitarLicencia.setBorder(new TitledBorder(null, "Tramitar Licencia", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnTramitarLicencia.setLayout(new BorderLayout(0, 0));
			pnTramitarLicencia.add(getPnSur(), BorderLayout.SOUTH);
			pnTramitarLicencia.add(getPnDatos(), BorderLayout.CENTER);
		}
		return pnTramitarLicencia;
	}
	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
			pnSur.setBackground(Color.WHITE);
			pnSur.setLayout(new GridLayout(0, 2, 0, 0));
			pnSur.add(getPnPoliticaDeDatos());
			pnSur.add(getPnCrearYCancelarLicencia());
		}
		return pnSur;
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
			pnDatosSocio.add(getPnDniSocio());
			pnDatosSocio.add(getPnNombreSocio());
			pnDatosSocio.add(getPnApellidosSocio());
			pnDatosSocio.add(getPnGeneroSocio());
			pnDatosSocio.add(getPnEdadSocio());
			pnDatosSocio.add(getPnTelfSocio());
			pnDatosSocio.add(getPnCorreoSocio());
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
			txNombreSocio.setColumns(10);
		}
		return txNombreSocio;
	}
	private JPanel getPnApellidosSocio() {
		if (pnApellidosSocio == null) {
			pnApellidosSocio = new JPanel();
			pnApellidosSocio.setBackground(Color.WHITE);
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
			cbGeneroSocio.setSelectedItem(Generos.OTRO);
			cbGeneroSocio.setBounds(146, 66, 106, 22);
		}
		return cbGeneroSocio;
	}
	private JPanel getPnEdadSocio() {
		if (pnEdadSocio == null) {
			pnEdadSocio = new JPanel();
			pnEdadSocio.setBackground(Color.WHITE);
			pnEdadSocio.add(getLbEdadSocio());
			pnEdadSocio.add(getDcFechaNacimientoSocio());
		}
		return pnEdadSocio;
	}
	private JLabel getLbEdadSocio() {
		if (lbEdadSocio == null) {
			lbEdadSocio = new JLabel("Edad:");
		}
		return lbEdadSocio;
	}
	private JPanel getPnDatosTutor() {
		if (pnDatosTutor == null) {
			pnDatosTutor = new JPanel();
			pnDatosTutor.setBackground(Color.WHITE);
			pnDatosTutor.setLayout(new GridLayout(0, 1, 0, 0));
			pnDatosTutor.add(getLbDatosTutor());
			pnDatosTutor.add(getPnDniTutor());
			pnDatosTutor.add(getPnNombreTutor());
			pnDatosTutor.add(getPnApellidosTutor());
			pnDatosTutor.add(getPnGeneroTutor());
			pnDatosTutor.add(getPnEdadTutor());
			pnDatosTutor.add(getPnTelfTutor());
			pnDatosTutor.add(getPnCorreoTutor());
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
			txNombreTutor.setEnabled(false);
			txNombreTutor.setColumns(10);
		}
		return txNombreTutor;
	}
	private JPanel getPnApellidosTutor() {
		if (pnApellidosTutor == null) {
			pnApellidosTutor = new JPanel();
			pnApellidosTutor.setBackground(Color.WHITE);
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
			txApellidosTutor.setEnabled(false);
			txApellidosTutor.setColumns(10);
		}
		return txApellidosTutor;
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
			cbGeneroTutor.setEnabled(false);
			Generos[] generos = Generos.values();
			cbGeneroTutor.setModel(new DefaultComboBoxModel<Generos>(generos));
			cbGeneroTutor.setSelectedItem(Generos.OTRO);
			cbGeneroTutor.setBounds(146, 66, 106, 22);
		}
		return cbGeneroTutor;
	}
	private JPanel getPnEdadTutor() {
		if (pnEdadTutor == null) {
			pnEdadTutor = new JPanel();
			pnEdadTutor.setBackground(Color.WHITE);
			pnEdadTutor.add(getLbEdadTutor());
			pnEdadTutor.add(getDcFechaNacimientoTutor());
		}
		return pnEdadTutor;
	}
	private JLabel getLbEdadTutor() {
		if (lbEdadTutor == null) {
			lbEdadTutor = new JLabel("Edad:");
		}
		return lbEdadTutor;
	}
	private JPanel getPnDatosFacturacionYLicencia() {
		if (pnDatosFacturacionYLicencia == null) {
			pnDatosFacturacionYLicencia = new JPanel();
			pnDatosFacturacionYLicencia.setBackground(Color.WHITE);
			pnDatosFacturacionYLicencia.setLayout(new GridLayout(1, 0, 0, 0));
			pnDatosFacturacionYLicencia.add(getPnDireccionFacturacion());
			pnDatosFacturacionYLicencia.add(getPnInfoFacturacion());
			pnDatosFacturacionYLicencia.add(getPnLicencia());
			pnDatosFacturacionYLicencia.add(getPnSeleccionarDeporte());
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
	private JPanel getPnLicencia() {
		if (pnLicencia == null) {
			pnLicencia = new JPanel();
			pnLicencia.setBackground(Color.WHITE);
			pnLicencia.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnLicencia.add(getLbTipoLicencia());
			pnLicencia.add(getCbTipoLicencia());
		}
		return pnLicencia;
	}
	private JPanel getPnCrearYCancelarLicencia() {
		if (pnCrearYCancelarLicencia == null) {
			pnCrearYCancelarLicencia = new JPanel();
			pnCrearYCancelarLicencia.setBackground(Color.WHITE);
			pnCrearYCancelarLicencia.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			pnCrearYCancelarLicencia.add(getBtCancelarLicencia());
			pnCrearYCancelarLicencia.add(getBtCrearLicencia());
		}
		return pnCrearYCancelarLicencia;
	}
	private JButton getBtCrearLicencia() {
		if (btCrearLicencia == null) {
			btCrearLicencia = new JButton("Crear Licencia");
			btCrearLicencia.setFocusPainted(false);
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
		if(getDcFechaNacimientoSocio().getDate() == null) {
			JOptionPane.showMessageDialog(this,"Debe seleccionar su fecha de nacimiento",
					"Datos no rellenados", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}else {
			LocalDate fechaSocio = getDcFechaNacimientoSocio().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int diaSocio = fechaSocio.getDayOfMonth();
			int mesSocio = fechaSocio.getMonthValue();
			int añoSocio = fechaSocio.getYear();
			
			if(getTxNombreSocio().getText().isBlank() || getTxApellidosSocio().getText().isBlank() 
					|| getTxDireccionFacturacion().getText().isBlank() || getTxInfoFacturacion().getText().isBlank() 
					|| getTxDniSocio().getText().isBlank() || getTxCorreoSocio().getText().isBlank() || getTxTelfSocio().getText().isBlank()) {
				JOptionPane.showMessageDialog(this,"Debe rellenar los campos Nombre, Apellidos, Direccion de facturacion e Informacion de facturacion ",
						"Datos no rellenados", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}else if(!tramitarLicencia.comprobarMayorEdad(diaSocio, mesSocio, añoSocio)) {
				if(getTxNombreTutor().getText().isBlank() || getTxApellidosTutor().getText().isBlank()
						|| getTxTelfTutor().getText().isBlank() || getTxCorreoTutor().getText().isBlank()
						|| getTxDniTutor().getText().isBlank()) {
					JOptionPane.showMessageDialog(this,"Debe rellenar los campos Nombre y Apellidos del tutor",
							"Datos no rellenados", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}else if(!comprobarTutorMayorEdad()) {
					JOptionPane.showMessageDialog(this,"El tutor debe ser mayor de edad",
							"Datos no rellenados", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}else {
					return true;
				}
			}else {
				return true;
			}
		}
	}
	
	private void crearLicencia() {
		String dniSocio = getTxDniSocio().getText();
		String nombreSocio = getTxNombreSocio().getText();
		String apellidoSocio = getTxApellidosSocio().getText();
		Generos generoSocio = (Generos) getCbGeneroSocio().getSelectedItem();
		int telfSocio = Integer.parseInt(getTxTelfSocio().getText());
		String correoSocio = getTxCorreoSocio().getText();
		
		
		LocalDate fechaNacimiento = getDcFechaNacimientoSocio().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int diaSocio = fechaNacimiento.getDayOfMonth();
		int mesSocio = fechaNacimiento.getMonthValue();
		int añoSocio = fechaNacimiento.getYear();
		
		String direccionFacturacion = getTxDireccionFacturacion().getText();
		String infoFacturacion = getTxInfoFacturacion().getText();
		TiposLicencia licencia = (TiposLicencia) getCbTipoLicencia().getSelectedItem();
		TiposDeportes deporte = (TiposDeportes) getCbDeporte().getSelectedItem();
		
		if(!tramitarLicencia.comprobarMayorEdad(diaSocio, mesSocio, añoSocio)) {
			String dniTutor = getTxDniTutor().getText();
			String nombreTutor = getTxNombreTutor().getText();
			String apellidoTutor = getTxApellidosTutor().getText();
			Generos generoTutor = (Generos) getCbGeneroTutor().getSelectedItem();
			int telfTutor = Integer.parseInt(getTxTelfTutor().getText());
			String correoTutor = getTxCorreoTutor().getText();
			
			LocalDate fechaNacimientoTutor = getDcFechaNacimientoTutor().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			if(esDirectivo) {
				tramitarLicencia.crearSocio(dniSocio, nombreSocio, apellidoSocio, correoSocio, telfSocio, generoSocio, fechaNacimiento);
				if(!loggin.generarLoggin(dniSocio)) {
					JOptionPane.showMessageDialog(this,"Error al enviar nueva contraseña a: "+loggin.getCorreoDeUsuario(dniSocio),
							"Iniciar Sesion", JOptionPane.INFORMATION_MESSAGE);
				}
			}else {
				tramitarLicencia.modificarDatosSocio(dniSocio,nombreSocio, apellidoSocio, generoSocio,telfSocio,correoSocio, fechaNacimiento);
				tramitarLicencia.guardarDatosModificadosSocio();
			}
			tramitarLicencia.crearLicencia(nombreTutor, apellidoTutor,dniTutor,telfTutor,correoTutor, fechaNacimientoTutor, generoTutor, direccionFacturacion, infoFacturacion, licencia, deporte);
		}else {
			if(esDirectivo) {
				tramitarLicencia.crearSocio(dniSocio, nombreSocio, apellidoSocio, correoSocio, telfSocio, generoSocio, fechaNacimiento);
				if(!loggin.generarLoggin(dniSocio)) {
					JOptionPane.showMessageDialog(this,"Error al enviar nueva contraseña a: "+loggin.getCorreoDeUsuario(dniSocio),
							"Iniciar Sesion", JOptionPane.INFORMATION_MESSAGE);
				}
			}else {
				tramitarLicencia.modificarDatosSocio(dniSocio,nombreSocio, apellidoSocio, generoSocio,telfSocio,correoSocio, fechaNacimiento);
				tramitarLicencia.guardarDatosModificadosSocio();
			}
			tramitarLicencia.crearLicencia("noTutor", "noTutor", "noTutor",-1,"noTutor", null, null, direccionFacturacion, infoFacturacion, licencia, deporte);
		}
		
	}
	
	private JButton getBtCancelarLicencia() {
		if (btCancelarLicencia == null) {
			btCancelarLicencia = new JButton("Cancelar");
			btCancelarLicencia.setFocusPainted(false);
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
			pnPoliticaDeDatos.setBackground(Color.WHITE);
			FlowLayout flowLayout = (FlowLayout) pnPoliticaDeDatos.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnPoliticaDeDatos.add(getBtPoliticaDeDatos());
		}
		return pnPoliticaDeDatos;
	}
	private JButton getBtPoliticaDeDatos() {
		if (btPoliticaDeDatos == null) {
			btPoliticaDeDatos = new JButton("Pol\u00EDtica de protecci\u00F3n de datos");
			btPoliticaDeDatos.setFocusPainted(false);
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
			btAceparPoliticaDeDatos.setFocusPainted(false);
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
			pnLabelPoliticaDeDatos.setBackground(Color.WHITE);
			FlowLayout flowLayout = (FlowLayout) pnLabelPoliticaDeDatos.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnLabelPoliticaDeDatos.add(getLbPoliticaDeDatos());
		}
		return pnLabelPoliticaDeDatos;
	}
	private JLabel getLbPoliticaDeDatos() {
		if (lbPoliticaDeDatos == null) {
			lbPoliticaDeDatos = new JLabel("Politica de proteccion de datos");
		}
		return lbPoliticaDeDatos;
	}
	private JTextArea getTxPoliticaDeDatos() {
		if (txPoliticaDeDatos == null) {
			txPoliticaDeDatos = new JTextArea();
			txPoliticaDeDatos.setLineWrap(true);
			txPoliticaDeDatos.setWrapStyleWord(true);
			txPoliticaDeDatos.setEditable(false);
			txPoliticaDeDatos.setText(cargarPoliticaDeDatos());
		}
		return txPoliticaDeDatos;
	}
	private String cargarPoliticaDeDatos() {
		return FileUtil.loadFilePoliticaDatos(FICHERO_POLITICA_PROTECCION_DATOS);
	}
	private JScrollPane getScrPoliticaDeDatos() {
		if (scrPoliticaDeDatos == null) {
			scrPoliticaDeDatos = new JScrollPane();
			scrPoliticaDeDatos.setViewportView(getTxPoliticaDeDatos());
		}
		return scrPoliticaDeDatos;
	}
	
	private void comprobarEdad() {
		if (getDcFechaNacimientoSocio().getDate() == null)
			return;
		LocalDate fechaNacimiento = getDcFechaNacimientoSocio().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int diaSocio = fechaNacimiento.getDayOfMonth();
		int mesSocio = fechaNacimiento.getMonthValue();
		int añoSocio = fechaNacimiento.getYear();
		
		boolean mayorEdad = tramitarLicencia.comprobarMayorEdad(diaSocio, mesSocio, añoSocio);
		
		// Se pasa el booleano opuesto
		// para deshabilitar los campos
		// Si es true, se deshabilitan, si es false, se habilitan
		enableTutor(!mayorEdad);
	}
	
	private void enableTutor(boolean option) {
		// Si option es false -> se deshabilita el tutor
		// Si es true -> se habilita
		getDcFechaNacimientoTutor().setEnabled(option);
		getCbGeneroTutor().setEnabled(option);
		getTxApellidosTutor().setEnabled(option);
		getTxNombreTutor().setEnabled(option);
		getTxTelfTutor().setEnabled(option);
		getTxCorreoTutor().setEnabled(option);
		getTxDniTutor().setEnabled(option);
		
		//solo permitimos deportista si es menor de edad (siempre el valor opuesto de option)
		TiposLicencia[] licencias = tramitarLicencia.getLicenciasDisponibles(!option);
		cbTipoLicencia.setModel(new DefaultComboBoxModel<TiposLicencia>(licencias));
	}	
	
	private boolean comprobarTutorMayorEdad() {
		LocalDate fechaNacimiento = getDcFechaNacimientoTutor().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int añoTutor = fechaNacimiento.getYear();
		int mesTutor = fechaNacimiento.getMonthValue();
		int diaTutor = fechaNacimiento.getDayOfMonth();
		
		boolean mayorEdad = tramitarLicencia.comprobarMayorEdad(diaTutor, mesTutor, añoTutor);
		return mayorEdad;
	}
	private JDateChooser getDcFechaNacimientoSocio() {
		if (dcFechaNacimientoSocio == null) {
			dcFechaNacimientoSocio = new JDateChooser();
			dcFechaNacimientoSocio.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					comprobarEdad();
				}
			});
			dcFechaNacimientoSocio.setDateFormatString("yyyy-MM-dd");
		}
		return dcFechaNacimientoSocio;
	}
	private JDateChooser getDcFechaNacimientoTutor() {
		if (dcFechaNacimientoTutor == null) {
			dcFechaNacimientoTutor = new JDateChooser();
			dcFechaNacimientoTutor.setEnabled(false);
			dcFechaNacimientoTutor.setDateFormatString("yyyy-MM-dd");
		}
		return dcFechaNacimientoTutor;
	}
	private JPanel getPnTelfSocio() {
		if (pnTelfSocio == null) {
			pnTelfSocio = new JPanel();
			pnTelfSocio.setBackground(Color.WHITE);
			pnTelfSocio.add(getLbTelfSocio());
			pnTelfSocio.add(getTxTelfSocio());
		}
		return pnTelfSocio;
	}
	private JLabel getLbTelfSocio() {
		if (lbTelfSocio == null) {
			lbTelfSocio = new JLabel("Telefono:");
		}
		return lbTelfSocio;
	}
	private JTextField getTxTelfSocio() {
		if (txTelfSocio == null) {
			txTelfSocio = new JTextField();
			txTelfSocio.setColumns(10);
		}
		return txTelfSocio;
	}
	private JPanel getPnTelfTutor() {
		if (pnTelfTutor == null) {
			pnTelfTutor = new JPanel();
			pnTelfTutor.setBackground(Color.WHITE);
			pnTelfTutor.add(getLbTelfTutor());
			pnTelfTutor.add(getTxTelfTutor());
		}
		return pnTelfTutor;
	}
	private JLabel getLbTelfTutor() {
		if (lbTelfTutor == null) {
			lbTelfTutor = new JLabel("Telefono:");
		}
		return lbTelfTutor;
	}
	private JTextField getTxTelfTutor() {
		if (txTelfTutor == null) {
			txTelfTutor = new JTextField();
			txTelfTutor.setEnabled(false);
			txTelfTutor.setColumns(10);
		}
		return txTelfTutor;
	}
	private JPanel getPnCorreoSocio() {
		if (pnCorreoSocio == null) {
			pnCorreoSocio = new JPanel();
			pnCorreoSocio.setBackground(Color.WHITE);
			pnCorreoSocio.add(getLbCorreoSocio());
			pnCorreoSocio.add(getTxCorreoSocio());
		}
		return pnCorreoSocio;
	}
	private JLabel getLbCorreoSocio() {
		if (lbCorreoSocio == null) {
			lbCorreoSocio = new JLabel("Correo:");
		}
		return lbCorreoSocio;
	}
	private JTextField getTxCorreoSocio() {
		if (txCorreoSocio == null) {
			txCorreoSocio = new JTextField();
			txCorreoSocio.setColumns(10);
		}
		return txCorreoSocio;
	}
	private JPanel getPnCorreoTutor() {
		if (pnCorreoTutor == null) {
			pnCorreoTutor = new JPanel();
			pnCorreoTutor.setBackground(Color.WHITE);
			pnCorreoTutor.add(getLbCorreoTutor());
			pnCorreoTutor.add(getTxCorreoTutor());
		}
		return pnCorreoTutor;
	}
	private JLabel getLbCorreoTutor() {
		if (lbCorreoTutor == null) {
			lbCorreoTutor = new JLabel("Correo:");
		}
		return lbCorreoTutor;
	}
	private JTextField getTxCorreoTutor() {
		if (txCorreoTutor == null) {
			txCorreoTutor = new JTextField();
			txCorreoTutor.setEnabled(false);
			txCorreoTutor.setColumns(10);
		}
		return txCorreoTutor;
	}
	private JPanel getPnDniSocio() {
		if (pnDniSocio == null) {
			pnDniSocio = new JPanel();
			pnDniSocio.setBackground(Color.WHITE);
			pnDniSocio.add(getLbDniSocio());
			pnDniSocio.add(getTxDniSocio());
		}
		return pnDniSocio;
	}
	private JLabel getLbDniSocio() {
		if (lbDniSocio == null) {
			lbDniSocio = new JLabel("Dni:");
		}
		return lbDniSocio;
	}
	private JTextField getTxDniSocio() {
		if (txDniSocio == null) {
			txDniSocio = new JTextField();
			txDniSocio.setText((String) null);
			txDniSocio.setColumns(10);
		}
		return txDniSocio;
	}
	private JPanel getPnDniTutor() {
		if (pnDniTutor == null) {
			pnDniTutor = new JPanel();
			pnDniTutor.setBackground(Color.WHITE);
			pnDniTutor.add(getLbDniTutor());
			pnDniTutor.add(getTxDniTutor());
		}
		return pnDniTutor;
	}
	private JLabel getLbDniTutor() {
		if (lbDniTutor == null) {
			lbDniTutor = new JLabel("Dni:");
		}
		return lbDniTutor;
	}
	private JTextField getTxDniTutor() {
		if (txDniTutor == null) {
			txDniTutor = new JTextField();
			txDniTutor.setEnabled(false);
			txDniTutor.setText((String) null);
			txDniTutor.setColumns(10);
		}
		return txDniTutor;
	}
	private JPanel getPnSeleccionarDeporte() {
		if (pnSeleccionarDeporte == null) {
			pnSeleccionarDeporte = new JPanel();
			pnSeleccionarDeporte.setBackground(Color.WHITE);
			pnSeleccionarDeporte.add(getLbSeleccionarDeporte());
			pnSeleccionarDeporte.add(getCbDeporte());
		}
		return pnSeleccionarDeporte;
	}
	private JLabel getLbSeleccionarDeporte() {
		if (lbSeleccionarDeporte == null) {
			lbSeleccionarDeporte = new JLabel("Selecciona deporte:");
		}
		return lbSeleccionarDeporte;
	}
	private JComboBox<TiposDeportes> getCbDeporte() {
		if (cbDeporte == null) {
			cbDeporte = new JComboBox<TiposDeportes>();
		}
		return cbDeporte;
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
		}
		return cbTipoLicencia;
	}
}
