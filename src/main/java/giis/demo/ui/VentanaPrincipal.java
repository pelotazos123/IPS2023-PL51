package giis.demo.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class VentanaPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean esDirectivo;
	
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


	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
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
			
		}
		return pnPrincipalSocio;
	}
	private JPanel getPnPrincipalDirectivo() {
		if (pnPrincipalDirectivo == null) {
			pnPrincipalDirectivo = new JPanel();
			pnPrincipalDirectivo.setLayout(null);
			pnPrincipalDirectivo.add(getLbProvisionalDirectivo());
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
				public void actionPerformed(ActionEvent e) {
					pnPrincipalDirectivo.add(getBtTramitarLicencia());
					pnPrincipalDirectivo.add(getBtRenovarLicencia());
					((CardLayout)pnPrincipal.getLayout()).show(pnPrincipal,"PrincipalDirectivo");
					esDirectivo = true;
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
				public void actionPerformed(ActionEvent e) {
					pnPrincipalSocio.add(getBtTramitarLicencia());
					pnPrincipalSocio.add(getBtRenovarLicencia());
					((CardLayout)pnPrincipal.getLayout()).show(pnPrincipal,"PrincipalSocio");
					esDirectivo = false;
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
					VentanaTramitarLicencia frame = new VentanaTramitarLicencia(esDirectivo);
					frame.setVisible(true);
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
					VentanaRenovarLicencia frame = new VentanaRenovarLicencia(esDirectivo);
					frame.setVisible(true);
				}
			});
		}
		return btRenovarLicencia;
	}
}
