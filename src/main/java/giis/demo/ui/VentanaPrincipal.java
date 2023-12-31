package giis.demo.ui;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import giis.demo.business.ActasController;
import giis.demo.business.AsambleasController;
import giis.demo.business.AsambleasModel;
import giis.demo.business.GestionRecibosController;
import giis.demo.business.RecibosController;
import giis.demo.business.RecibosModel;
import giis.demo.business.TiendaController;
import giis.demo.business.TiendaModel;
import giis.demo.logica.ServiciosMeteorologicos;
import giis.demo.model.CrearLicencias.servicio.TramitarLicencia;
import giis.demo.model.auditoria.Auditoria;
import giis.demo.model.competiciones.servicio.GestionarCompeticiones;
import giis.demo.model.loggin.servicio.GestionarLoggin;
import giis.demo.model.politicaDeDatos.PoliticaDeDatos;
import giis.demo.ui.auditoria.VentanaAuditor;
import giis.demo.ui.competiciones.VentanaInscripcionCompeticiones;
import giis.demo.ui.licencias.VentanaRenovarLicencia;
import giis.demo.ui.licencias.VentanaTramitarLicencia;
import giis.demo.ui.politicaProteccionDatos.VentanaMostrarSolicitudes;
import giis.demo.ui.politicaProteccionDatos.VentanaVerDatos;
import giis.demo.util.Database;

public class VentanaPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TramitarLicencia tramitarLicencia;
	private GestionarLoggin loggin;
	private GestionarCompeticiones gestorCompeticiones;
	private PoliticaDeDatos politicaDeDatos;
	private Auditoria auditor;
	
	private JPanel pnPrincipal;
	private JPanel pnPrincipalSocio;
	private JPanel pnPrincipalDirectivo;
	private JButton btTramitarLicencia;
	private JButton btRenovarLicencia;
	private JButton btPagoTransferencia;
	private JButton btTestsFisiologicos;
	private JButton btTienda;
	private VentanaReservas vr;
	private VentanaListaSocios vLS;
	private JButton btnReservas;
	private JButton btnAsambleas;
	private JButton btnActas;
	private JButton btnListadoSocios;
	private JButton btnAñadirCompeticiones;
	private Database db;
	private JButton btnGestionRecibos;
	private JPanel pnInicio;
	private JPanel pnLoggin;
	private JPanel pnDni;
	private JLabel lbBienvenida;
	private JPanel pnSelectorFecha;
	private JDateChooser dcFechaAplicacion;
	private JLabel lbSeleccionarFecha;
	private JPanel pnContraseña;
	private JPanel pnBoton;
	private JButton btnGeneracionRecibos;
	private JButton btnEntrar;
	private JPanel pnEscribirContraseña;
	private JLabel lblContraseña;
	private JPasswordField pfContraseña;
	private JPanel pnRestablecerContraseña;
	private JButton btRestablecerContraseña;
	private PintaBotonRestablecerContraseña pBR = new PintaBotonRestablecerContraseña();
	private JPanel pnColocarCentroDni;
	private JLabel lblDniDelUsuario;
	private JTextField txDniUsuario;
	private JButton btCambiarContraseña;
	private JButton btVerDatos;
	private JButton btVerSolicitudes;
	private JPanel pnSelectorFechaSocio;
	private JLabel lbSeleccionarFechaSocio;
	private JPanel pnSeccionSocio;
	private JPanel pnSeccionSocioPersonal;
	private JPanel pnSeccionSocioDeportiva;
	private JPanel pnBienvenidoAlClub;
	private JLabel lbBienvenidoSocio;
	private JPanel pnBotonesDeportiva;
	private JPanel pnSelectorFechaDirectivo;
	private JLabel lbSeleccionarFechaDirectivo;
	private JPanel pnSeccionDirectivo;
	private JPanel pnSeccionDirectivoPersonal;
	private JPanel pnSeccionDirectivoFunciones;
	private JPanel pnBienvenidoAlClubDirectivo;
	private JLabel lbBienvenidoDirectivo;
	private JPanel pnBotonesDeportivoDirectivo;
	private JPanel pnSeccionDirectivoAdministracion;
	private JButton btInscripcionCompeticiones;
	private JButton btnCrearCursillos;
	private VentanaCrearCursillos vcc;
	private JButton btGestionCursos;


	/**
	 * Create the frame.
	 */
	public VentanaPrincipal(Database db) {
		setBackground(Color.WHITE);
		setTitle("Gestión deportiva");
		this.db = db;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870, 618);
		pnPrincipal = new JPanel();
		pnPrincipal.setBackground(Color.WHITE);
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnPrincipal);
		setLocationRelativeTo(null);
		pnPrincipal.setLayout(new CardLayout(0, 0));
		pnPrincipal.add(getPnInicio(), "inicio");
		pnPrincipal.add(getPnPrincipalSocio(), "PrincipalSocio");
		pnPrincipal.add(getPnPrincipalDirectivo(), "PrincipalDirectivo");
		tramitarLicencia = new TramitarLicencia(db);
		loggin = new GestionarLoggin(db);
		gestorCompeticiones = new GestionarCompeticiones(db);
		politicaDeDatos = new PoliticaDeDatos(db);
		auditor = new Auditoria(db);
		cargarFecha();
		setMinimumSize(new Dimension(517, 517));
	}

	private void cargarFecha() {
		Date fecha = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		dcFechaAplicacion.setDate(fecha);
		
	}

	private JPanel getPnPrincipalSocio() {
		if (pnPrincipalSocio == null) {
			pnPrincipalSocio = new JPanel();
			pnPrincipalSocio.setLayout(new BorderLayout(0, 0));
			pnPrincipalSocio.add(getPnSelectorFechaSocio(), BorderLayout.NORTH);
			pnPrincipalSocio.add(getPnSeccionSocio(), BorderLayout.CENTER);
		}
		return pnPrincipalSocio;
	}
	
	private JButton getBtTestsFisiologicos() {
		if(btTestsFisiologicos == null) {
			btTestsFisiologicos = new JButton("Tests Fisiológicos");
			btTestsFisiologicos.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					creaVentanasTest();
				}
			});
			btTestsFisiologicos.setMnemonic('f');
		}
		return btTestsFisiologicos;
	}

	private void creaVentanasTest() {
		VentanaSeleccionTest vst = new VentanaSeleccionTest(this);
		vst.setVisible(true);
	}

	private void openReservas() {
		vr = new VentanaReservas(db);
		vr.setModal(true);
		vr.setLocationRelativeTo(this);
		vr.setVisible(true);
	}

	private JPanel getPnPrincipalDirectivo() {
		if (pnPrincipalDirectivo == null) {
			pnPrincipalDirectivo = new JPanel();			
			pnPrincipalDirectivo.setLayout(new BorderLayout(0, 0));
			pnPrincipalDirectivo.add(getPnSelectorFechaDirectivo(), BorderLayout.NORTH);
			pnPrincipalDirectivo.add(getPnSeccionDirectivo(), BorderLayout.CENTER);
		}
		return pnPrincipalDirectivo;
	}
	
	 private JButton getBtnGeneracionRecibos() {
			if (btnGeneracionRecibos == null) {
				btnGeneracionRecibos = new JButton("Generar Recibos");
				btnGeneracionRecibos.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						RecibosView view = new RecibosView(getDcFechaAplicacion().getCalendar());
						RecibosModel model = new RecibosModel(db);
						RecibosController controller = new RecibosController(model,view);
						
						controller.initController();
					}
				});
				btnGeneracionRecibos.setBounds(183, 358, 139, 52);
			}
			return btnGeneracionRecibos;
		}

	private JButton getBtTramitarLicencia() {
		if (btTramitarLicencia == null) {
			btTramitarLicencia = new JButton("Tramitar Licencia");
			btTramitarLicencia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					irTramitarLicencia();
				}
			});
		}
		return btTramitarLicencia;
	}
	
	private void irTramitarLicencia() {
		if(tramitarLicencia.esDirectivo()) {
			VentanaTramitarLicencia frame = new VentanaTramitarLicencia(tramitarLicencia,loggin);
			frame.setVisible(true);
		}else if(comprobarSocioConAlgunaLicenciaDisponible()) {
			VentanaTramitarLicencia frame = new VentanaTramitarLicencia(tramitarLicencia,loggin);
			frame.setVisible(true);
		}
	}

	private JButton getBtRenovarLicencia() {
		if (btRenovarLicencia == null) {
			btRenovarLicencia = new JButton("Renovar Licencia");
			btRenovarLicencia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					irRenovarLicencia();
				}
			});
		}
		return btRenovarLicencia;
	}
	
	private void irRenovarLicencia() {
		if(comprobarSocioConLicenciaPagada()) {
			VentanaRenovarLicencia frame = new VentanaRenovarLicencia(tramitarLicencia);
			frame.setVisible(true);
		}
	}
  
  private JButton getBtPagoTransferencia() {
    if (btPagoTransferencia == null) {
      btPagoTransferencia = new JButton("Pagar por transferencia");
      btPagoTransferencia.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          muestraVentanaPagoTransf();
        }
      });
      btPagoTransferencia.setMnemonic('t');
    }
    return btPagoTransferencia;
  }

	private void muestraVentanaPagoTransf() {
		VentanaPayByTransf pbt = new VentanaPayByTransf();
		pbt.setVisible(true);	
	}
  
  private JButton getBtnReservas() {
		if (btnReservas == null) {
			btnReservas = new JButton("Reservas");
			btnReservas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openReservas();
				}
			});
			btnReservas.setBounds(183, 358, 139, 52);
		}
		return btnReservas;
	}

	private JButton getBtnAsambleas() {
		if (btnAsambleas == null) {
			btnAsambleas = new JButton("Convocar asamblea");
			btnAsambleas.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AsambleasView view = new AsambleasView(getDcFechaAplicacion().getCalendar());
					AsambleasModel model = new AsambleasModel(db);
					AsambleasController controller = new AsambleasController(model, view);

					controller.initController();
				}
			});
		}
		return btnAsambleas;
	}
	private JButton getBtnActas() {
		if (btnActas == null) {
			btnActas = new JButton("Actas");
			btnActas.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ActasView view = new ActasView();
					AsambleasModel model = new AsambleasModel(db);
					ActasController controller = new ActasController(model, view);

					controller.initController();
				}
			});
		}
		return btnActas;
	}
	private JButton getBtnListadoSocios() {
		if (btnListadoSocios == null) {
			btnListadoSocios = new JButton("Ver socios");
			btnListadoSocios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openListadoSocios();
				}
			});
		}
		return btnListadoSocios;
	}
	
	private JButton getBtnAñadirCompeticiones() {
		if (btnAñadirCompeticiones == null) {
			btnAñadirCompeticiones = new JButton("Añadir competiciones");
			btnAñadirCompeticiones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					añadirCompeticones();
				}
			});
		}
		return btnAñadirCompeticiones;
	}
	
	private void añadirCompeticones() {
		JFileChooser cargar = new JFileChooser();
		int resp = cargar.showOpenDialog(null);
		if(resp == JFileChooser.APPROVE_OPTION) {
			gestorCompeticiones.añadirCompeticiones(cargar.getSelectedFile());
		}
		
	}
	
	private void openListadoSocios() {
		vLS = new VentanaListaSocios(db);
		vLS.setModal(true);
		vLS.setLocationRelativeTo(this);
		vLS.setVisible(true);
	}
	
	private boolean comprobarSocioConLicenciaPagada() {
		if(tramitarLicencia.socioConLicenciasPagadas()) {
			return true;
		} else {
			JOptionPane.showMessageDialog(this,"No tienes ninguna licencia para renovar",
					"Licencias", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	private boolean comprobarSocioConAlgunaLicenciaDisponible() {
		boolean mayor = tramitarLicencia.comprobarSocioMayorEdad();
		if(tramitarLicencia.socioConAlgunaLicenciaDisponible(mayor)) {
			return true;
		}else {
			JOptionPane.showMessageDialog(this,"Tienes todas las licencias",
					"Licencias", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
  
	private JPanel getPnInicio() {
		if (pnInicio == null) {
			pnInicio = new JPanel();
			pnInicio.setBackground(Color.WHITE);
			pnInicio.setLayout(new BorderLayout(0, 0));
			pnInicio.add(getPnLoggin(), BorderLayout.CENTER);
			pnInicio.add(getPnSelectorFecha(), BorderLayout.NORTH);
		}
		return pnInicio;
	}
	private JPanel getPnLoggin() {
		if (pnLoggin == null) {
			pnLoggin = new JPanel();
			pnLoggin.setBackground(Color.WHITE);
			pnLoggin.setLayout(new GridLayout(0, 1, 0, 0));
			pnLoggin.add(getLbBienvenida());
			pnLoggin.add(getPnDni());
			pnLoggin.add(getPnContraseña());
			pnLoggin.add(getPnBoton());
		}
		return pnLoggin;
	}
	private JPanel getPnDni() {
		if (pnDni == null) {
			pnDni = new JPanel();
			pnDni.setBackground(Color.WHITE);
			FlowLayout fl_pnDni = new FlowLayout(FlowLayout.CENTER, 5, 50);
			pnDni.setLayout(fl_pnDni);
			pnDni.add(getPnColocarCentroDni());
		}
		return pnDni;
	}
	private JLabel getLbBienvenida() {
		if (lbBienvenida == null) {
			lbBienvenida = new JLabel("Bienvenidos al club");
			lbBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
			lbBienvenida.setFont(new Font("Tahoma", Font.PLAIN, 40));
		}
		return lbBienvenida;
	}
	private JPanel getPnSelectorFecha() {
		if (pnSelectorFecha == null) {
			pnSelectorFecha = new JPanel();
			pnSelectorFecha.setBackground(Color.WHITE);
			FlowLayout flowLayout = (FlowLayout) pnSelectorFecha.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnSelectorFecha.add(getLbSeleccionarFecha());
			pnSelectorFecha.add(getDcFechaAplicacion());
		}
		return pnSelectorFecha;
	}
	private JDateChooser getDcFechaAplicacion() {
		if (dcFechaAplicacion == null) {
			dcFechaAplicacion = new JDateChooser();
			dcFechaAplicacion.setDateFormatString("yyyy-MM-dd");
		}
		return dcFechaAplicacion;
	}
	private JLabel getLbSeleccionarFecha() {
		if (lbSeleccionarFecha == null) {
			lbSeleccionarFecha = new JLabel("Seleccionar fecha de la aplicacion:");
			lbSeleccionarFecha.setFont(new Font("Tahoma", Font.PLAIN, 19));
		}
		return lbSeleccionarFecha;
	}
	private JPanel getPnContraseña() {
		if (pnContraseña == null) {
			pnContraseña = new JPanel();
			pnContraseña.setBackground(Color.WHITE);
			pnContraseña.setLayout(new GridLayout(0, 1, 0, 0));
			pnContraseña.add(getPnEscribirContraseña());
			pnContraseña.add(getPnRestablecerContraseña());
		}
		return pnContraseña;
	}
	private JPanel getPnBoton() {
		if (pnBoton == null) {
			pnBoton = new JPanel();
			pnBoton.setBackground(Color.WHITE);
			pnBoton.add(getBtnEntrar());
		}
		return pnBoton;
	}
	private JButton getBtnEntrar() {
		if (btnEntrar == null) {
			btnEntrar = new JButton("Entrar");
			btnEntrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(comprobarUsuario()) {
						loggearUsuario();
					}
				}
			});
			btnEntrar.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return btnEntrar;
	}
	
	private void loggearUsuario() {
		if(loggin.esAuditor(getTxDniUsuario().getText())) {
			VentanaAuditor frame = new VentanaAuditor(auditor);
			frame.setVisible(true);
		}else {
			tramitarLicencia.loggearSocio(getTxDniUsuario().getText());
			if(tramitarLicencia.esDirectivo()) {
				loggearDirectivo();
			}else {
				loggearSocio();
			}
		}
	}

	private void loggearSocio() {
		pnSeccionSocioPersonal.add(getBtTramitarLicencia());
		pnSeccionSocioPersonal.add(getBtPagoTransferencia());
		pnSeccionSocioPersonal.add(getBtRenovarLicencia());
		pnSeccionSocioPersonal.add(getBtCambiarContraseña());
		pnSeccionSocioPersonal.add(getBtVerDatos());
		
		pnBotonesDeportiva.add(getBtnReservas());
		pnBotonesDeportiva.add(getBtInscripcionCompeticiones());
		pnBotonesDeportiva.add(getBtTestsFisiologicos());
		pnBotonesDeportiva.add(getBtGestionCursos());
		
		pnSelectorFechaSocio.add(getDcFechaAplicacion());
		
		pnBotonesDeportiva.add(getBtTestsFisiologicos());
		pnBotonesDeportiva.add(getBtTienda());
		getLbBienvenidoSocio().setText("Bienvenido al club "+tramitarLicencia.getSocio().getNombre());
		((CardLayout)pnPrincipal.getLayout()).show(pnPrincipal,"PrincipalSocio");
		setMinimumSize(new Dimension(920, 517));
	}

	private void loggearDirectivo() {
		pnSeccionDirectivoPersonal.add(getBtTramitarLicencia());
		pnSeccionDirectivoPersonal.add(getBtPagoTransferencia());
		pnSeccionDirectivoPersonal.add(getBtRenovarLicencia());
		pnSeccionDirectivoPersonal.add(getBtCambiarContraseña());
		pnSeccionDirectivoPersonal.add(getBtVerDatos());
		
		pnSelectorFechaDirectivo.add(getDcFechaAplicacion());
		
		pnBotonesDeportivoDirectivo.add(getBtnReservas());
		pnBotonesDeportivoDirectivo.add(getBtTestsFisiologicos());
		pnBotonesDeportivoDirectivo.add(getBtInscripcionCompeticiones());
		pnBotonesDeportivoDirectivo.add(getBtGestionCursos());
		pnBotonesDeportivoDirectivo.add(getBtTienda());
		
		pnSeccionDirectivoAdministracion.add(getBtnAsambleas());
		pnSeccionDirectivoAdministracion.add(getBtnActas());
		pnSeccionDirectivoAdministracion.add(getBtnGeneracionRecibos());
		pnSeccionDirectivoAdministracion.add(getBtnGestionRecibos());
		pnSeccionDirectivoAdministracion.add(getBtnListadoSocios());
		pnSeccionDirectivoAdministracion.add(getBtnAñadirCompeticiones());
		pnSeccionDirectivoAdministracion.add(getBtVerSolicitudes());
    pnSeccionDirectivoAdministracion.add(getBtnCrearCursillos());
    
		checkWeather(); // Llamada para comprobar el tiempo y cancelaciones
		
		getLbBienvenidoDirectivo().setText("Bienvenido al club "+tramitarLicencia.getDirectivo().getNombre());
		
		((CardLayout) pnPrincipal.getLayout()).show(pnPrincipal, "PrincipalDirectivo");
		setMinimumSize(new Dimension(800, 517));
    
  }
  
	private void checkWeather() {
		ServiciosMeteorologicos sm = new ServiciosMeteorologicos(this, db);
		sm.checkTiempoParallelCompeticiones();
		sm.checkTiempoParallelReservas();
	}
	
	private JButton getBtnCrearCursillos() {
		if (btnCrearCursillos == null) {
			btnCrearCursillos = new JButton("Crear cursillos");
			btnCrearCursillos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openCrearCursillos();
				}
			});
		}
		return btnCrearCursillos;
	}
	
	private void openCrearCursillos() {
		vcc = new VentanaCrearCursillos(db);
		vcc.setModal(true);
		vcc.setLocationRelativeTo(this);
		vcc.setVisible(true);
	}

	private boolean comprobarUsuario() {
		String dniUsuario = getTxDniUsuario().getText();
		if( loggin.existeUsuario(dniUsuario)) {
			LocalDate fechaActual = getDcFechaAplicacion().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); 
			if(loggin.comprobarBloqueado(dniUsuario,fechaActual)) {
				JOptionPane.showMessageDialog(this,"El usuario esta bloqueado",
						"Iniciar Sesion", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			
			if(getPfContraseña().getPassword().length == 0) {
				JOptionPane.showMessageDialog(this,"Debe escribir una contraseña",
						"Iniciar Sesion", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			String contraseña =  String.valueOf(getPfContraseña().getPassword());
			if(loggin.comprobarContraseñaCorrecta(dniUsuario, contraseña)) {
				return true;
			}else {
				
				if(loggin.getNumIntentosLoggin() >= 3) {
					loggin.bloquearUsuario(dniUsuario);
					getTxDniUsuario().setText("");
					getPfContraseña().setText("");
					JOptionPane.showMessageDialog(this,"La contraseña es incorrecta\nNumero maximo de intentos permitidos superado\nUsuario bloqueado durante 3 dias",
							"Iniciar Sesion", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(this,"La contraseña es incorrecta",
							"Iniciar Sesion", JOptionPane.INFORMATION_MESSAGE);
				}
				return false;
			}
		}else {
			JOptionPane.showMessageDialog(this,"No existe ese usuario",
					"Iniciar Sesion", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
	}
	private JPanel getPnEscribirContraseña() {
		if (pnEscribirContraseña == null) {
			pnEscribirContraseña = new JPanel();
			pnEscribirContraseña.setBackground(Color.WHITE);
			FlowLayout flowLayout = (FlowLayout) pnEscribirContraseña.getLayout();
			flowLayout.setVgap(20);
			pnEscribirContraseña.add(getLblContraseña());
			pnEscribirContraseña.add(getPfContraseña());
		}
		return pnEscribirContraseña;
	}
	private JLabel getLblContraseña() {
		if (lblContraseña == null) {
			lblContraseña = new JLabel("Contraseña :");
			lblContraseña.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblContraseña;
	}
	private JPasswordField getPfContraseña() {
		if (pfContraseña == null) {
			pfContraseña = new JPasswordField();
			pfContraseña.setToolTipText("");
			pfContraseña.setPreferredSize(new Dimension(134, 19));
			pfContraseña.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return pfContraseña;
	}
	private JPanel getPnRestablecerContraseña() {
		if (pnRestablecerContraseña == null) {
			pnRestablecerContraseña = new JPanel();
			pnRestablecerContraseña.setBackground(Color.WHITE);
			FlowLayout flowLayout = (FlowLayout) pnRestablecerContraseña.getLayout();
			flowLayout.setVgap(20);
			pnRestablecerContraseña.add(getBtRestablecerContraseña());
		}
		return pnRestablecerContraseña;
	}
	private JButton getBtRestablecerContraseña() {
		if (btRestablecerContraseña == null) {
			btRestablecerContraseña = new JButton("Restablecer contraseña");
			btRestablecerContraseña.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					restablecerContraseña();
				}
			});
			btRestablecerContraseña.setFocusPainted(false);
			btRestablecerContraseña.setForeground(Color.BLACK);
			btRestablecerContraseña.setBackground(Color.WHITE);
			btRestablecerContraseña.setBorder(null);
			btRestablecerContraseña.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btRestablecerContraseña.addMouseListener(pBR);
		}
		return btRestablecerContraseña;
	}
	
	private void restablecerContraseña() {
		String dniUsuario = getTxDniUsuario().getText();
		if(dniUsuario.isEmpty() || !loggin.existeUsuario(dniUsuario)) {
			JOptionPane.showMessageDialog(null,
					"Usuario incorrecto o inexistente", "Iniciar Sesion",
					JOptionPane.INFORMATION_MESSAGE);
		}else {
			String correo= JOptionPane.showInputDialog(this,"Introduzca correo de recuperacion","Iniciar Sesion",JOptionPane.QUESTION_MESSAGE);
			if(correo != null) {
				if (loggin.restablecerContraseña(dniUsuario,correo)) {
					JOptionPane.showMessageDialog(null, "Contraseña restablecida\nSe ha enviado la nueva contraseña a: "
							+ loggin.getCorreoDeUsuario(dniUsuario), "Iniciar Sesion", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null,
							"Error al enviar nueva contraseña", "Iniciar Sesion",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		getTxDniUsuario().setText("");
		getPfContraseña().setText("");
	}
	
	public class PintaBotonRestablecerContraseña extends MouseAdapter{
		public void mouseEntered(MouseEvent e) {
			JButton BtPulsada = (JButton) e.getSource();
			BtPulsada.setForeground(Color.BLUE);
		}
		
		public void mouseExited(MouseEvent e) {
			JButton BtPulsada = (JButton) e.getSource();
			BtPulsada.setForeground(Color.BLACK);
		}
	}
	private JPanel getPnColocarCentroDni() {
		if (pnColocarCentroDni == null) {
			pnColocarCentroDni = new JPanel();
			pnColocarCentroDni.setBackground(Color.WHITE);
			pnColocarCentroDni.add(getLblDniDelUsuario());
			pnColocarCentroDni.add(getTxDniUsuario());
		}
		return pnColocarCentroDni;
	}
	private JLabel getLblDniDelUsuario() {
		if (lblDniDelUsuario == null) {
			lblDniDelUsuario = new JLabel("Dni del usuario: ");
			lblDniDelUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblDniDelUsuario;
	}
	private JTextField getTxDniUsuario() {
		if (txDniUsuario == null) {
			txDniUsuario = new JTextField();
			txDniUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txDniUsuario.setColumns(10);
		}
		return txDniUsuario;
	}
	
	private JButton getBtCambiarContraseña() {
		if (btCambiarContraseña == null) {
			btCambiarContraseña = new JButton("Cambiar Contraseña");
			btCambiarContraseña.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cambiarContraseña();
				}
			});
			btCambiarContraseña.setBounds(584, 269, 134, 54);
		}
		return btCambiarContraseña;
	}
	
	private void cambiarContraseña() {
		String dniUsuario = tramitarLicencia.getUsuario().getDni();
		
		PasswordPane pp = new PasswordPane();
		
		Boolean continuar = null;
		
		do {
			int act = JOptionPane.showConfirmDialog(null, pp.getPn(), "Nueva contraseña",JOptionPane.OK_CANCEL_OPTION);
			
			if (act == JOptionPane.CANCEL_OPTION || act == JOptionPane.CLOSED_OPTION) // Cancela la operación si cancelar es presionado (Devuelve -1)
				return;				
			continuar = loggin.comprobarNuevaContraseñaValida(pp.getPswd().getPassword());
			if (!continuar) pp.getPswd().setText("");
		}while(!continuar);
		loggin.cambiarContraseña(dniUsuario, pp.getPswd().getPassword());
		JOptionPane.showMessageDialog(this,"Contraseña actualizada",
				"Cambiar contraseña", JOptionPane.INFORMATION_MESSAGE);
	}
	private JPanel getPnSelectorFechaSocio() {
		if (pnSelectorFechaSocio == null) {
			pnSelectorFechaSocio = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnSelectorFechaSocio.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnSelectorFechaSocio.setBackground(Color.WHITE);
			pnSelectorFechaSocio.add(getLbSeleccionarFechaSocio());
		}
		return pnSelectorFechaSocio;
	}
	private JLabel getLbSeleccionarFechaSocio() {
		if (lbSeleccionarFechaSocio == null) {
			lbSeleccionarFechaSocio = new JLabel("Seleccionar fecha de la aplicacion:");
			lbSeleccionarFechaSocio.setFont(new Font("Tahoma", Font.PLAIN, 19));
		}
		return lbSeleccionarFechaSocio;
	}
	private JPanel getPnSeccionSocio() {
		if (pnSeccionSocio == null) {
			pnSeccionSocio = new JPanel();
			pnSeccionSocio.setBackground(Color.WHITE);
			pnSeccionSocio.setLayout(new BorderLayout(0, 0));
			pnSeccionSocio.add(getPnSeccionSocioPersonal(), BorderLayout.NORTH);
			pnSeccionSocio.add(getPnSeccionSocioDeportiva(), BorderLayout.CENTER);
		}
		return pnSeccionSocio;
	}
	private JPanel getPnSeccionSocioPersonal() {
		if (pnSeccionSocioPersonal == null) {
			pnSeccionSocioPersonal = new JPanel();
			pnSeccionSocioPersonal.setBorder(new TitledBorder(null, "Personal", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnSeccionSocioPersonal.setBackground(Color.WHITE);
		}
		return pnSeccionSocioPersonal;
	}
	private JPanel getPnSeccionSocioDeportiva() {
		if (pnSeccionSocioDeportiva == null) {
			pnSeccionSocioDeportiva = new JPanel();
			pnSeccionSocioDeportiva.setBorder(null);
			pnSeccionSocioDeportiva.setBackground(Color.WHITE);
			pnSeccionSocioDeportiva.setLayout(new GridLayout(0, 1, 0, 0));
			pnSeccionSocioDeportiva.add(getPnBienvenidoAlClub());
			pnSeccionSocioDeportiva.add(getPnBotonesDeportiva());
		}
		return pnSeccionSocioDeportiva;
	}
	private JPanel getPnBienvenidoAlClub() {
		if (pnBienvenidoAlClub == null) {
			pnBienvenidoAlClub = new JPanel();
			pnBienvenidoAlClub.setBackground(Color.WHITE);
			FlowLayout flowLayout = (FlowLayout) pnBienvenidoAlClub.getLayout();
			flowLayout.setVgap(80);
			pnBienvenidoAlClub.add(getLbBienvenidoSocio());
		}
		return pnBienvenidoAlClub;
	}
	private JLabel getLbBienvenidoSocio() {
		if (lbBienvenidoSocio == null) {
			lbBienvenidoSocio = new JLabel("Bienvenido");
			lbBienvenidoSocio.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return lbBienvenidoSocio;
	}
	private JPanel getPnBotonesDeportiva() {
		if (pnBotonesDeportiva == null) {
			pnBotonesDeportiva = new JPanel();
			pnBotonesDeportiva.setBorder(new TitledBorder(null, "Deportiva", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnBotonesDeportiva.setBackground(Color.WHITE);
		}
		return pnBotonesDeportiva;
	}
	private JPanel getPnSelectorFechaDirectivo() {
		if (pnSelectorFechaDirectivo == null) {
			pnSelectorFechaDirectivo = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnSelectorFechaDirectivo.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnSelectorFechaDirectivo.setBackground(Color.WHITE);
			pnSelectorFechaDirectivo.add(getLbSeleccionarFechaDirectivo());
		}
		return pnSelectorFechaDirectivo;
	}
	private JLabel getLbSeleccionarFechaDirectivo() {
		if (lbSeleccionarFechaDirectivo == null) {
			lbSeleccionarFechaDirectivo = new JLabel("Seleccionar fecha de la aplicacion:");
			lbSeleccionarFechaDirectivo.setFont(new Font("Tahoma", Font.PLAIN, 19));
		}
		return lbSeleccionarFechaDirectivo;
	}
	private JPanel getPnSeccionDirectivo() {
		if (pnSeccionDirectivo == null) {
			pnSeccionDirectivo = new JPanel();
			pnSeccionDirectivo.setBackground(Color.WHITE);
			pnSeccionDirectivo.setLayout(new BorderLayout(0, 0));
			pnSeccionDirectivo.add(getPnSeccionDirectivoPersonal(), BorderLayout.NORTH);
			pnSeccionDirectivo.add(getPnSeccionDirectivoFunciones(), BorderLayout.CENTER);
		}
		return pnSeccionDirectivo;
	}
	private JPanel getPnSeccionDirectivoPersonal() {
		if (pnSeccionDirectivoPersonal == null) {
			pnSeccionDirectivoPersonal = new JPanel();
			pnSeccionDirectivoPersonal.setBorder(new TitledBorder(null, "Personal", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnSeccionDirectivoPersonal.setBackground(Color.WHITE);
		}
		return pnSeccionDirectivoPersonal;
	}
	private JPanel getPnSeccionDirectivoFunciones() {
		if (pnSeccionDirectivoFunciones == null) {
			pnSeccionDirectivoFunciones = new JPanel();
			pnSeccionDirectivoFunciones.setBackground(Color.WHITE);
			pnSeccionDirectivoFunciones.setLayout(new GridLayout(0, 1, 0, 0));
			pnSeccionDirectivoFunciones.add(getPnBienvenidoAlClubDirectivo());
			pnSeccionDirectivoFunciones.add(getPnBotonesDeportivoDirectivo());
			pnSeccionDirectivoFunciones.add(getPnSeccionDirectivoAdministracion());
		}
		return pnSeccionDirectivoFunciones;
	}
	private JPanel getPnBienvenidoAlClubDirectivo() {
		if (pnBienvenidoAlClubDirectivo == null) {
			pnBienvenidoAlClubDirectivo = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnBienvenidoAlClubDirectivo.getLayout();
			flowLayout.setVgap(60);
			pnBienvenidoAlClubDirectivo.setBackground(Color.WHITE);
			pnBienvenidoAlClubDirectivo.add(getLbBienvenidoDirectivo());
		}
		return pnBienvenidoAlClubDirectivo;
	}
	private JLabel getLbBienvenidoDirectivo() {
		if (lbBienvenidoDirectivo == null) {
			lbBienvenidoDirectivo = new JLabel("Bienvenido");
			lbBienvenidoDirectivo.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return lbBienvenidoDirectivo;
	}
	private JPanel getPnBotonesDeportivoDirectivo() {
		if (pnBotonesDeportivoDirectivo == null) {
			pnBotonesDeportivoDirectivo = new JPanel();
			pnBotonesDeportivoDirectivo.setBorder(new TitledBorder(null, "Deportiva", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnBotonesDeportivoDirectivo.setBackground(Color.WHITE);
		}
		return pnBotonesDeportivoDirectivo;
	}
	private JPanel getPnSeccionDirectivoAdministracion() {
		if (pnSeccionDirectivoAdministracion == null) {
			pnSeccionDirectivoAdministracion = new JPanel();
			pnSeccionDirectivoAdministracion.setBorder(new TitledBorder(null, "Administracion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnSeccionDirectivoAdministracion.setBackground(Color.WHITE);
		}
		return pnSeccionDirectivoAdministracion;
	}
	
	private JButton getBtInscripcionCompeticiones() {
		if (btInscripcionCompeticiones == null) {
			btInscripcionCompeticiones = new JButton("Competiciones");
			btInscripcionCompeticiones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					irCompeticiones();
				}
			});
			btInscripcionCompeticiones.setBounds(584, 269, 134, 54);
		}
		return btInscripcionCompeticiones;
	}
	
	private JButton getBtTienda() {
		if (btTienda == null) {
			btTienda = new JButton("Tienda");
			btTienda.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TiendaView view = new TiendaView(getDcFechaAplicacion().getCalendar());
					TiendaModel model = new TiendaModel(db);
					TiendaController controller = new TiendaController(model,view, new RecibosModel(db), tramitarLicencia);
					
					controller.initController();
				}
			});
			btTienda.setBounds(584, 269, 134, 54);
		}
		return btTienda;
	}
	
	private void irCompeticiones() {
		gestorCompeticiones.cargarCompeticiones();
		VentanaInscripcionCompeticiones frame = new VentanaInscripcionCompeticiones(gestorCompeticiones,tramitarLicencia);
		frame.setVisible(true);
	}
	private JButton getBtnGestionRecibos() {
		if (btnGestionRecibos == null) {
			btnGestionRecibos = new JButton("Gestionar Recibos");
			btnGestionRecibos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					GestionRecibosView view = new GestionRecibosView(getDcFechaAplicacion().getCalendar());
					RecibosModel model = new RecibosModel(db);
					GestionRecibosController controller = new GestionRecibosController(model,view);
					
					controller.initController();
				}
			});
			btnGestionRecibos.setBounds(476, 205, 185, 60);
		}
		return btnGestionRecibos;
	}

	public Database getDb() {
		return db;
	}
	
	public TramitarLicencia getTramitarLicencia() {
		return tramitarLicencia;
	}
	
	public JDateChooser getDcFecha() {
		return dcFechaAplicacion;
	}
	
	private JButton getBtVerDatos() {
		if (btVerDatos == null) {
			btVerDatos = new JButton("Ver Datos");
			btVerDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					irVentanaVerDatos();
				}
			});
			btVerDatos.setBounds(584, 269, 134, 54);
		}
		return btVerDatos;
	}
	
	private void irVentanaVerDatos() {
		VentanaVerDatos frame = new VentanaVerDatos(tramitarLicencia, politicaDeDatos,this);
		frame.setVisible(true);
	}
	
	private JButton getBtVerSolicitudes() {
		if (btVerSolicitudes == null) {
			btVerSolicitudes = new JButton("Ver Solicitudes");
			btVerSolicitudes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaMostrarSolicitudes frame = new VentanaMostrarSolicitudes(politicaDeDatos);
					frame.setVisible(true);
				}
			});
			btVerSolicitudes.setBounds(584, 269, 134, 54);
		}
		return btVerSolicitudes;
	}
	
	public void darseDeBaja() {
		tramitarLicencia = new TramitarLicencia(db);
		loggin = new GestionarLoggin(db);
		gestorCompeticiones = new GestionarCompeticiones(db);
		politicaDeDatos = new PoliticaDeDatos(db);
		getTxDniUsuario().setText("");
		getPfContraseña().setText("");
		((CardLayout) pnPrincipal.getLayout()).show(pnPrincipal, "inicio");
	}
	
	
	private class PasswordPane {
		
		private JPanel pn;
		private JPasswordField pswd;
		
		public PasswordPane() {
			pn = new JPanel();
			pn.setLayout(new BorderLayout());
			pswd = new JPasswordField();
			JLabel lbl = new JLabel("<html>Debe contener mayusculas, <br> minusculas y al menos un numero.</html>");
			pn.add(pswd, BorderLayout.CENTER);
			pn.add(lbl, BorderLayout.NORTH);
		}
		
		public JPasswordField getPswd() {
			return pswd;
		}
		
		public JPanel getPn() {
			return pn;
		}

	}
	
	private JButton getBtGestionCursos() {
		if (btGestionCursos == null) {
			btGestionCursos = new JButton("Gestion cursos");
			btGestionCursos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					muestraVentanaGestionCursos();
				}
			});
		}
		return btGestionCursos;
	}

	protected void muestraVentanaGestionCursos() {
		VentanaGestionCursos vgcs = new VentanaGestionCursos(this);
		vgcs.setVisible(true);
	}
	
}
