package giis.demo.ui.competiciones;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import giis.demo.model.competiciones.servicio.GestionarCompeticiones;

public class VentanaFiltrarCompeticiones extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel pnFiltro;
	private JPanel pnOrden;
	private JPanel pnBtnFiltro;
	private JScrollPane scrlFiltro;
	private JButton btnAplicar;
	private VentanaInscripcionCompeticiones vIC;
	private JPanel pnButtonsSouth;
	private JPanel pnCenter;
	private JLabel lblFiltrar;
	private JCheckBox chkSub18;
	private JCheckBox chkVeterano;
	private JCheckBox chkSenior;
	
	private GestionarCompeticiones gestorCompeticiones;

	/**
	 * Create the dialog.
	 */
	public VentanaFiltrarCompeticiones(VentanaInscripcionCompeticiones v,GestionarCompeticiones gestor) {
		this.vIC = v;
		gestorCompeticiones = gestor;
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(getPnOrden(), BorderLayout.SOUTH);
		getContentPane().add(getPnCenter(), BorderLayout.CENTER);
		getContentPane().add(getLblFiltrar(), BorderLayout.NORTH);
		setBounds(100, 100, 375, 293);

	}

	private JPanel getPnFiltro() {
		if (pnFiltro == null) {
			pnFiltro = new JPanel();
			pnFiltro.setBorder(new LineBorder(new Color(0, 0, 0)));
			pnFiltro.setBackground(Color.WHITE);
			pnFiltro.setLayout(new BorderLayout(0, 0));
			pnFiltro.add(getScrlFiltro(), BorderLayout.SOUTH);
			pnFiltro.add(getScrlFiltro(), BorderLayout.CENTER);
		}
		return pnFiltro;
	}
	
	private JPanel getPnOrden() {
		if (pnOrden == null) {
			pnOrden = new JPanel();
			pnOrden.setBackground(Color.WHITE);
			pnOrden.setLayout(new BorderLayout(0, 0));
			pnOrden.add(getPnButtonsSouth(), BorderLayout.SOUTH);
		}
		return pnOrden;
	}
	
	private JPanel getPnBtnFiltro() {
		if (pnBtnFiltro == null) {
			pnBtnFiltro = new JPanel();
			pnBtnFiltro.setBorder(null);
			pnBtnFiltro.setBackground(Color.WHITE);
			pnBtnFiltro.setLayout(new GridLayout(1, 1, 0, 0));
			pnBtnFiltro.add(getChkSub18());
			pnBtnFiltro.add(getChkSenior());
			pnBtnFiltro.add(getChkVeterano());
		}
		return pnBtnFiltro;
	}
	
	private JScrollPane getScrlFiltro() {
		if (scrlFiltro == null) {
			scrlFiltro = new JScrollPane();
			scrlFiltro.setBorder(null);
			scrlFiltro.setViewportView(getPnBtnFiltro());
		}
		return scrlFiltro;
	}
	
	private JButton getBtnAplicar() {
		if (btnAplicar == null) {
			btnAplicar = new JButton("Aplicar");
			btnAplicar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					vIC.filtrarCompeticiones(gestorCompeticiones.getCompeticionesFiltradas(getChkSub18().isSelected(), getChkSenior().isSelected(), getChkVeterano().isSelected()));
					dispose();
				}
			});
		}
		return btnAplicar;
	}

	private JPanel getPnButtonsSouth() {
		if (pnButtonsSouth == null) {
			pnButtonsSouth = new JPanel();
			pnButtonsSouth.setLayout(new GridLayout(0, 1, 0, 0));
			pnButtonsSouth.add(getBtnAplicar());
		}
		return pnButtonsSouth;
	}
	private JPanel getPnCenter() {
		if (pnCenter == null) {
			pnCenter = new JPanel();
			pnCenter.setLayout(new GridLayout(0, 1, 0, 0));
			pnCenter.add(getPnFiltro());
		}
		return pnCenter;
	}
	private JLabel getLblFiltrar() {
		if (lblFiltrar == null) {
			lblFiltrar = new JLabel("    Filtrar:");
			lblFiltrar.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return lblFiltrar;
	}
	private JCheckBox getChkSub18() {
		if (chkSub18 == null) {
			chkSub18 = new JCheckBox("Sub18");
			chkSub18.setFont(new Font("Tahoma", Font.PLAIN, 13));
			chkSub18.setBackground(Color.WHITE);
		}
		return chkSub18;
	}
	private JCheckBox getChkVeterano() {
		if (chkVeterano == null) {
			chkVeterano = new JCheckBox("Veterano");
			chkVeterano.setFont(new Font("Tahoma", Font.PLAIN, 13));
			chkVeterano.setBackground(Color.WHITE);
		}
		return chkVeterano;
	}
	private JCheckBox getChkSenior() {
		if (chkSenior == null) {
			chkSenior = new JCheckBox("Senior");
			chkSenior.setFont(new Font("Tahoma", Font.PLAIN, 13));
			chkSenior.setBackground(Color.WHITE);
		}
		return chkSenior;
	}
}
