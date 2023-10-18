package giis.demo.ui;

import javax.swing.JDialog;
import java.awt.Color;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.GridLayout;

import javax.swing.JScrollPane;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaFiltro extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel pnFiltro;
	private JPanel pnOrden;
	private JPanel pnBtnFiltro;
	private JLabel lblFiltro;
	private JCheckBox chkHombres;
	private JScrollPane scrlFiltro;
	private JButton btnAplicar;
	private VentanaListaSocios vLS;
	private JCheckBox chkMujeres;
	private JPanel pnButtonsSouth;

	/**
	 * Create the dialog.
	 */
	public VentanaFiltro(VentanaListaSocios vLS) {
		this.vLS = vLS;
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(getPnFiltro(), BorderLayout.CENTER);
		getContentPane().add(getPnOrden(), BorderLayout.SOUTH);
		setBounds(100, 100, 310, 352);

	}

	private JPanel getPnFiltro() {
		if (pnFiltro == null) {
			pnFiltro = new JPanel();
			pnFiltro.setBackground(Color.WHITE);
			pnFiltro.setLayout(new BorderLayout(0, 0));
			pnFiltro.add(getScrlFiltro(), BorderLayout.SOUTH);
			pnFiltro.add(getLblFiltro(), BorderLayout.NORTH);
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
			pnBtnFiltro.setLayout(new GridLayout(5, 1, 0, 0));
			pnBtnFiltro.add(getChkHombres());
			pnBtnFiltro.add(getChkMujeres());
		}
		return pnBtnFiltro;
	}
	
	private JLabel getLblFiltro() {
		if (lblFiltro == null) {
			lblFiltro = new JLabel("Filtrar: ");
		}
		return lblFiltro;
	}
	
	private JCheckBox getChkHombres() {
		if (chkHombres == null) {
			chkHombres = new JCheckBox("Hombres");
			chkHombres.setBackground(Color.WHITE);
		}
		return chkHombres;
	}
	
	private JCheckBox getChkMujeres() {
		if (chkMujeres == null) {
			chkMujeres = new JCheckBox("Mujeres");
		}
		return chkMujeres;
	}
	
	private JScrollPane getScrlFiltro() {
		if (scrlFiltro == null) {
			scrlFiltro = new JScrollPane();
			scrlFiltro.setViewportView(getPnBtnFiltro());
		}
		return scrlFiltro;
	}
	
	private JButton getBtnAplicar() {
		if (btnAplicar == null) {
			btnAplicar = new JButton("Aplicar");
			btnAplicar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					aplicarFiltros();
				}
			});
		}
		return btnAplicar;
	}

	private void aplicarFiltros() {
		String filter = "";
		
		if (getChkHombres().isSelected() && !getChkMujeres().isSelected()) 
			filter += "WHERE gender='HOMBRE'";
		else if (getChkMujeres().isSelected() && !getChkHombres().isSelected())
			filter += "WHERE gender='MUJER'";
		
		vLS.actualizar(filter);
		dispose();
	}

	private JPanel getPnButtonsSouth() {
		if (pnButtonsSouth == null) {
			pnButtonsSouth = new JPanel();
			pnButtonsSouth.setLayout(new GridLayout(0, 1, 0, 0));
			pnButtonsSouth.add(getBtnAplicar());
		}
		return pnButtonsSouth;
	}


}
