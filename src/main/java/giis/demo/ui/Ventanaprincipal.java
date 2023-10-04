package giis.demo.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ventanaprincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pn;
	private JPanel pnInicio;
	private JButton btnDirectivo;
	private JButton btnSocio;
	private JPanel pnPrincipalSocio;
	private JPanel pnPrincipalDirectivo;
	private JLabel lbProvisionalSocio;
	private JLabel lbProvisionalDirectivo;


	/**
	 * Create the frame.
	 */
	public Ventanaprincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 381);
		pn = new JPanel();
		pn.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pn);
		pn.setLayout(new CardLayout(0, 0));
		pn.add(getPnInicio(), "inicio");
		pn.add(getPnPrincipalSocio(), "PrincipalSocio");
		pn.add(getPnPrincipalDirectivo(), "PrincipalDirectivo");
	}

	private JPanel getPnInicio() {
		if (pnInicio == null) {
			pnInicio = new JPanel();
			pnInicio.setLayout(null);
			pnInicio.add(getBtnDirectivo());
			pnInicio.add(getBtnSocio());
		}
		return pnInicio;
	}
	private JButton getBtnDirectivo() {
		if (btnDirectivo == null) {
			btnDirectivo = new JButton("Directivo");
			btnDirectivo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout)pn.getLayout()).show(pn,"PrincipalDirectivo");
				}
			});
			btnDirectivo.setBounds(44, 125, 198, 65);
		}
		return btnDirectivo;
	}
	private JButton getBtnSocio() {
		if (btnSocio == null) {
			btnSocio = new JButton("Socio");
			btnSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout)pn.getLayout()).show(pn,"PrincipalSocio");
				}
			});
			btnSocio.setBounds(293, 125, 186, 65);
		}
		return btnSocio;
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
			lbProvisionalSocio.setBounds(183, 122, 225, 34);
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
}
