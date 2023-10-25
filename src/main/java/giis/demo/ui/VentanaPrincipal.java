package giis.demo.ui;


import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import giis.demo.business.AsambleasController;
import giis.demo.business.AsambleasModel;
import giis.demo.business.GestionRecibosController;
import giis.demo.business.RecibosController;
import giis.demo.business.RecibosModel;
import giis.demo.model.CrearLicencias.servicio.TramitarLicencia;
import giis.demo.util.Database;

public class VentanaPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TramitarLicencia tramitarLicencia;
	
	private JPanel pnPrincipal;
	private JPanel pnInicio;
	private JPanel pnPrincipalSocio;
	private JPanel pnPrincipalDirectivo;
	private JLabel lbProvisionalSocio;
	private JLabel lbProvisionalDirectivo;

	private JPanel pnBotones;
	private JButton btnDirectivo;
	private JButton btnSocio;
	private JLabel lbBienvenida;
	private JButton btTramitarLicencia;
	private JButton btRenovarLicencia;
	private JButton btPagoTransferencia;
	private VentanaReservas vr;
	private VentanaListaSocios vLS;
	private JButton btnReservas;
	private JButton btnAsambleas;
	private JButton btnListadoSocios;
	private Database db;
	private JButton btnGestionRecibos;

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal(Database db) {
		setTitle("Gestión deportiva");
		this.db = db;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870, 618);
		pnPrincipal = new JPanel();
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnPrincipal);
		setLocationRelativeTo(null);
		pnPrincipal.setLayout(new CardLayout(0, 0));
		pnPrincipal.add(getPnInicio(), "inicio");
		pnPrincipal.add(getPnPrincipalSocio(), "PrincipalSocio");
		pnPrincipal.add(getPnPrincipalDirectivo(), "PrincipalDirectivo");
		tramitarLicencia = new TramitarLicencia(db);
	}

	private JPanel getPnInicio() {
		if (pnInicio == null) {
			pnInicio = new JPanel();
			pnInicio.setLayout(new GridLayout(0, 1, 0, 0));
			pnInicio.add(getLbBienvenida());
			pnInicio.add(getPnBotones());
		}
		return pnInicio;
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
		VentanaSeleccionTest vst = new VentanaSeleccionTest();
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
			btnGeneracionRecibos.setBounds(281, 205, 185, 60);
			pnPrincipalDirectivo.add(btnGeneracionRecibos);
			pnPrincipalDirectivo.add(getBtnListadoSocios());
			pnPrincipalDirectivo.add(getBtnGestionRecibos());
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

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnBotones.getLayout();
			flowLayout.setHgap(20);
			pnBotones.add(getBtnDirectivo());
			pnBotones.add(getBtnSocio());
		}
		return pnBotones;
	}

	private JButton getBtnDirectivo() {
		if (btnDirectivo == null) {
			btnDirectivo = new JButton("Directivo");
			btnDirectivo.setFont(new Font("Tahoma", Font.PLAIN, 30));
			btnDirectivo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					((CardLayout) pnPrincipal.getLayout()).show(pnPrincipal, "PrincipalDirectivo");
					pnPrincipalDirectivo.add(getBtTramitarLicencia());
					pnPrincipalDirectivo.add(getBtRenovarLicencia());
					pnPrincipalDirectivo.add(getBtnReservas());
					((CardLayout)pnPrincipal.getLayout()).show(pnPrincipal,"PrincipalDirectivo");
					tramitarLicencia.loggearSocio(true);
				}
			});
		}
		return btnDirectivo;
	}

	private JButton getBtnSocio() {
		if (btnSocio == null) {
			btnSocio = new JButton("Socio");
			btnSocio.setFont(new Font("Tahoma", Font.PLAIN, 30));
			btnSocio.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					((CardLayout) pnPrincipal.getLayout()).show(pnPrincipal, "PrincipalSocio");
					pnPrincipalSocio.add(getBtTramitarLicencia());
					pnPrincipalSocio.add(getBtRenovarLicencia());
					pnPrincipalSocio.add(getBtnReservas());
					((CardLayout)pnPrincipal.getLayout()).show(pnPrincipal,"PrincipalSocio");
					tramitarLicencia.loggearSocio(false);
				}
			});
		}
		return btnSocio;
	}

	private JLabel getLbBienvenida() {
		if (lbBienvenida == null) {
			lbBienvenida = new JLabel("Bienvenidos al club");
			lbBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
			lbBienvenida.setFont(new Font("Tahoma", Font.PLAIN, 40));
		}
		return lbBienvenida;
	}

	private JButton getBtTramitarLicencia() {
		if (btTramitarLicencia == null) {
			btTramitarLicencia = new JButton("Tramitar Licencia");
			btTramitarLicencia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(comprobarSocioConAlgunaLicenciaDisponible()) {
						VentanaTramitarLicencia frame = new VentanaTramitarLicencia(tramitarLicencia);
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
					if(comprobarSocioConLicenciaPagada()) {
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
		} else {
			JOptionPane.showMessageDialog(this,"No tienes ninguna licencia para renovar",
					"Licencias", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	private boolean comprobarSocioConAlgunaLicenciaDisponible() {
		if(tramitarLicencia.socioConAlgunaLicenciaDisponible()) {
			return true;
		}else {
			JOptionPane.showMessageDialog(this,"Tienes todas las licencias",
					"Licencias", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
  
	private JButton getBtnGestionRecibos() {
		if (btnGestionRecibos == null) {
			btnGestionRecibos = new JButton("Gestionar Recibos");
			btnGestionRecibos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					GestionRecibosView view = new GestionRecibosView();
					RecibosModel model = new RecibosModel();
					GestionRecibosController controller = new GestionRecibosController(model,view);
					
					controller.initController();
				}
			});
			btnGestionRecibos.setBounds(476, 205, 185, 60);
		}
		return btnGestionRecibos;
	}
}
