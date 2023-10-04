package giis.demo.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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
	private JButton btnAsambleas;
	private JPanel pnEleccionAsambleas;
	private JButton btnOrdinaria;
	private JButton btnExtraordinaria;
	private JPanel pnFormularioAsamblea;
	private JLabel lblFecha;
	private JLabel lblConvocatoria1;
	private JLabel lblConvocatoria2;
	private JButton btnConvocar;
	private JTextField txtFecha;
	private JTextField txtConv1;
	private JTextField txtConv2;


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
		pn.add(getPnEleccionAsambleas(), "EleccionAsambleas");
		pn.add(getPnFormularioAsamblea(), "FormularioAsamblea");
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
				@Override
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
				@Override
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
			pnPrincipalDirectivo.add(getBtnAsambleas());
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
			lbProvisionalDirectivo.setBounds(10, 11, 223, 32);
		}
		return lbProvisionalDirectivo;
	}
	private JButton getBtnAsambleas() {
		if (btnAsambleas == null) {
			btnAsambleas = new JButton("Convocar asamblea");
			btnAsambleas.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					((CardLayout)pn.getLayout()).show(pn,"EleccionAsambleas");
				}
			});
			btnAsambleas.setBounds(48, 80, 185, 40);
		}
		return btnAsambleas;
	}
	private JPanel getPnEleccionAsambleas() {
		if (pnEleccionAsambleas == null) {
			pnEleccionAsambleas = new JPanel();
			pnEleccionAsambleas.setLayout(null);
			pnEleccionAsambleas.add(getBtnOrdinaria());
			pnEleccionAsambleas.add(getBtnExtraordinaria());
		}
		return pnEleccionAsambleas;
	}
	private JButton getBtnOrdinaria() {
		if (btnOrdinaria == null) {
			btnOrdinaria = new JButton("Ordinaria");
			btnOrdinaria.setBounds(118, 124, 150, 55);
		}
		return btnOrdinaria;
	}
	private JButton getBtnExtraordinaria() {
		if (btnExtraordinaria == null) {
			btnExtraordinaria = new JButton("Extraordinaria");
			btnExtraordinaria.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					((CardLayout)pn.getLayout()).show(pn,"FormularioAsamblea");
				}
			});
			btnExtraordinaria.setBounds(300, 124, 150, 55);
		}
		return btnExtraordinaria;
	}
	private JPanel getPnFormularioAsamblea() {
		if (pnFormularioAsamblea == null) {
			pnFormularioAsamblea = new JPanel();
			pnFormularioAsamblea.setLayout(null);
			pnFormularioAsamblea.add(getLblFecha());
			pnFormularioAsamblea.add(getLblConvocatoria1());
			pnFormularioAsamblea.add(getLblConvocatoria2());
			pnFormularioAsamblea.add(getBtnConvocar());
			pnFormularioAsamblea.add(getTxtFecha());
			pnFormularioAsamblea.add(getTxtConv1());
			pnFormularioAsamblea.add(getTxtConv2());
		}
		return pnFormularioAsamblea;
	}
	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha:");
			lblFecha.setBounds(149, 78, 69, 24);
		}
		return lblFecha;
	}
	private JLabel getLblConvocatoria1() {
		if (lblConvocatoria1 == null) {
			lblConvocatoria1 = new JLabel("Hora de 1° convocatoria:");
			lblConvocatoria1.setBounds(149, 124, 153, 39);
		}
		return lblConvocatoria1;
	}
	private JLabel getLblConvocatoria2() {
		if (lblConvocatoria2 == null) {
			lblConvocatoria2 = new JLabel("Hora de 2° convocatoria:");
			lblConvocatoria2.setBounds(149, 185, 153, 39);
		}
		return lblConvocatoria2;
	}
	private JButton getBtnConvocar() {
		if (btnConvocar == null) {
			btnConvocar = new JButton("Convocar");
			btnConvocar.setBackground(new Color(0, 128, 0));
			btnConvocar.setBounds(221, 282, 120, 39);
		}
		return btnConvocar;
	}
	private JTextField getTxtFecha() {
		if (txtFecha == null) {
			txtFecha = new JTextField();
			txtFecha.setText("XX/XX/XXXX");
			txtFecha.setBounds(307, 80, 86, 20);
			txtFecha.setColumns(10);
		}
		return txtFecha;
	}
	private JTextField getTxtConv1() {
		if (txtConv1 == null) {
			txtConv1 = new JTextField();
			txtConv1.setColumns(10);
			txtConv1.setBounds(307, 133, 86, 20);
		}
		return txtConv1;
	}
	private JTextField getTxtConv2() {
		if (txtConv2 == null) {
			txtConv2 = new JTextField();
			txtConv2.setColumns(10);
			txtConv2.setBounds(307, 194, 86, 20);
		}
		return txtConv2;
	}
}
