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

import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import giis.demo.business.AsambleasController;
import giis.demo.business.AsambleasModel;
import giis.demo.business.RecibosController;
import giis.demo.business.RecibosModel;
import giis.demo.model.CrearLicencias.servicio.TramitarLicencia;
import giis.demo.model.loggin.GestionarLoggin;
import giis.demo.ui.licencias.VentanaRenovarLicencia;
import giis.demo.ui.licencias.VentanaTramitarLicencia;
import giis.demo.util.Database;

public class VentanaPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TramitarLicencia tramitarLicencia;
	private GestionarLoggin loggin;
	
	private JPanel pnPrincipal;
	private JPanel pnPrincipalSocio;
	private JPanel pnPrincipalDirectivo;
	private JLabel lbProvisionalSocio;
	private JLabel lbProvisionalDirectivo;
	private JButton btTramitarLicencia;
	private JButton btRenovarLicencia;
	private JButton btPagoTransferencia;
	private VentanaReservas vr;
	private VentanaListaSocios vLS;
	private JButton btnReservas;
	private JButton btnAsambleas;
	private JButton btnListadoSocios;
	private Database db;
	private JPanel pnInicio;
	private JPanel pnLoggin;
	private JPanel pnDni;
	private JLabel lbBienvenida;
	private JPanel pnSelectorFecha;
	private JDateChooser dcFechaAplicacion;
	private JLabel lbSeleccionarFecha;
	private JPanel pnContraseña;
	private JPanel pnBoton;
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
		cargarFecha();
	}

	private void cargarFecha() {
		Date fecha = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		dcFechaAplicacion.setDate(fecha);
		
	}

	private JPanel getPnPrincipalSocio() {
		if (pnPrincipalSocio == null) {
			pnPrincipalSocio = new JPanel();
			pnPrincipalSocio.setLayout(null);
			pnPrincipalSocio.add(getLbProvisionalSocio());
			pnPrincipalSocio.add(getBtTramitarLicencia());
			pnPrincipalSocio.add(getBtRenovarLicencia());
			JButton btTestsFisiologicos = new JButton("Tests Fisiológicos");
			btTestsFisiologicos.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					creaVentanasTest();
				}
			});
			btTestsFisiologicos.setMnemonic('f');
			btTestsFisiologicos.setBounds(156, 252, 191, 52);
			pnPrincipalSocio.add(btTestsFisiologicos);
			pnPrincipalSocio.add(getBtPagoTransferencia());
		}
		return pnPrincipalSocio;
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
			pnPrincipalDirectivo.setLayout(null);
			pnPrincipalDirectivo.add(getLbProvisionalDirectivo());
			pnPrincipalDirectivo.add(getBtnAsambleas());

			JButton btnGeneracionRecibos = new JButton("Generar Recibos");
			btnGeneracionRecibos.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					RecibosView view = new RecibosView();
					RecibosModel model = new RecibosModel();
					RecibosController controller = new RecibosController(model,view);
					
					controller.initController();
				}
			});
			btnGeneracionRecibos.setBounds(86, 304, 185, 60);
			pnPrincipalDirectivo.add(btnGeneracionRecibos);
			pnPrincipalDirectivo.add(getBtnListadoSocios());
		}
		return pnPrincipalDirectivo;
	}

	private JLabel getLbProvisionalSocio() {
		if (lbProvisionalSocio == null) {
			lbProvisionalSocio = new JLabel("Pantalla principal del socio");
			lbProvisionalSocio.setBounds(248, 10, 225, 13);
		}
		return lbProvisionalSocio;
	}

	private JLabel getLbProvisionalDirectivo() {
		if (lbProvisionalDirectivo == null) {
			lbProvisionalDirectivo = new JLabel("Pantalla principal del directivo");

			lbProvisionalDirectivo.setBounds(188, 116, 223, 32);
		}
		return lbProvisionalDirectivo;
	}

	private JButton getBtTramitarLicencia() {
		if (btTramitarLicencia == null) {
			btTramitarLicencia = new JButton("Tramitar Licencia");
			btTramitarLicencia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tramitarLicencia.esDirectivo()) {
						VentanaTramitarLicencia frame = new VentanaTramitarLicencia(tramitarLicencia,loggin);
						frame.setVisible(true);
					}else if(comprobarSocioConAlgunaLicenciaDisponible()) {
						VentanaTramitarLicencia frame = new VentanaTramitarLicencia(tramitarLicencia,loggin);
						frame.setVisible(true);
					}
				}
			});
			btTramitarLicencia.setBounds(405, 358, 139, 52);
		}
		return btTramitarLicencia;
	}

	private JButton getBtRenovarLicencia() {
		if (btRenovarLicencia == null) {
			btRenovarLicencia = new JButton("Renovar Licencia");
			btRenovarLicencia.setBounds(410, 252, 134, 54);
			btRenovarLicencia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tramitarLicencia.esDirectivo()) {
						VentanaRenovarLicencia frame = new VentanaRenovarLicencia(tramitarLicencia);
						frame.setVisible(true);
					}else if(comprobarSocioConLicenciaPagada()) {
						VentanaRenovarLicencia frame = new VentanaRenovarLicencia(tramitarLicencia);
						frame.setVisible(true);
					}
				}
			});
		}
		return btRenovarLicencia;
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
      btPagoTransferencia.setBounds(156, 433, 191, 52);
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
					AsambleasView view = new AsambleasView();
					AsambleasModel model = new AsambleasModel();
					AsambleasController controller = new AsambleasController(model, view);

					controller.initController();
				}
			});
			btnAsambleas.setBounds(86, 205, 185, 60);
		}
		return btnAsambleas;
	}
	private JButton getBtnListadoSocios() {
		if (btnListadoSocios == null) {
			btnListadoSocios = new JButton("Ver socios");
			btnListadoSocios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openListadoSocios();
				}
			});
			btnListadoSocios.setBounds(86, 399, 185, 53);
		}
		return btnListadoSocios;
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
		}else {
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

	public Database getDb() {
		return db;
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
						loggearSocio();
					}
				}
			});
			btnEntrar.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return btnEntrar;
	}
	
	private void loggearSocio() {
		tramitarLicencia.loggearSocio(getTxDniUsuario().getText());
		if(tramitarLicencia.esDirectivo()) {
			pnPrincipalDirectivo.add(getBtTramitarLicencia());
			pnPrincipalDirectivo.add(getBtRenovarLicencia());
			pnPrincipalDirectivo.add(getBtnReservas());
			pnPrincipalDirectivo.add(getBtCambiarContraseña());
			((CardLayout) pnPrincipal.getLayout()).show(pnPrincipal, "PrincipalDirectivo");
		}else {
			pnPrincipalSocio.add(getBtTramitarLicencia());
			pnPrincipalSocio.add(getBtRenovarLicencia());
			pnPrincipalSocio.add(getBtnReservas());
			pnPrincipalSocio.add(getBtCambiarContraseña());
			((CardLayout)pnPrincipal.getLayout()).show(pnPrincipal,"PrincipalSocio");
		}
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
		try {
			loggin.restablecerContraseña(dniUsuario);
			JOptionPane.showMessageDialog(this,"Contraseña restablecida\nSe ha enviado la nueva contraseña a: "+loggin.getCorreoDeUsuario(dniUsuario),
					"Iniciar Sesion", JOptionPane.INFORMATION_MESSAGE);
		}catch (MessagingException e) {
			System.err.println("Ha ocurrido un error al enviar el correo");
			JOptionPane.showMessageDialog(this,"Error al enviar nueva contraseña a: "+loggin.getCorreoDeUsuario(dniUsuario),
					"Iniciar Sesion", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}finally {
			getTxDniUsuario().setText("");
			getPfContraseña().setText("");
		}
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
		String dniUsuario = tramitarLicencia.esDirectivo()? tramitarLicencia.getDirectivo().getDni():tramitarLicencia.getSocio().getDni();
		String nuevaContraseña = "";
		do {
			nuevaContraseña = JOptionPane.showInputDialog(this,"Nueva Contraseña\nDebe contener mayusculas, minusculas y al menos un numero","Cambiar contraseña",JOptionPane.QUESTION_MESSAGE);
			JOptionPane.showMessageDialog(this,"La contraseña debe contener mayusculas, minusculas y al menos un numero",
					"Cambiar contraseña", JOptionPane.INFORMATION_MESSAGE);
		}while(!loggin.comprobarNuevaContraseñaValida(nuevaContraseña));
		loggin.cambiarContraseña(dniUsuario, nuevaContraseña);
		JOptionPane.showMessageDialog(this,"Contraseña actualizada",
				"Cambiar contraseña", JOptionPane.INFORMATION_MESSAGE);
	}
}
