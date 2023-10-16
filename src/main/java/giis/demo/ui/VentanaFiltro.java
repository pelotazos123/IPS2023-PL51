package giis.demo.ui;

import javax.swing.JDialog;
import java.awt.Color;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import java.awt.GridLayout;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import giis.demo.business.SociosController;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.awt.event.ActionEvent;

public class VentanaFiltro extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel pnFiltro;
	private JPanel pnOrden;
	private JLabel lbOrden;
	private JPanel pnBtnOrden;
	private JRadioButton rdbtnAZ;
	private JRadioButton rdbtZA;
	private JPanel pnBtnFiltro;
	private JLabel lblFiltro;
	private JCheckBox chkHombres;
	private JCheckBox chkCuotaAdulto;
	private JCheckBox chkCuotaJoven;
	private JCheckBox chkCuotaJubilado;
	private final ButtonGroup btnGroupOrden = new ButtonGroup();
	private JScrollPane scrlOrden;
	private JScrollPane scrlFiltro;
	private JButton btnAplicar;
	private VentanaListaSocios vLS;
	private JRadioButton rdbtIdAsc;
	private JRadioButton rdbtIdDesc;
	private JCheckBox chkMujeres;
	private JPanel pnButtonsSouth;

	/**
	 * Create the dialog.
	 */
	public VentanaFiltro(VentanaListaSocios vLS) {
		this.vLS = vLS;
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(getPnFiltro(), BorderLayout.NORTH);
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
			pnOrden.add(getLbOrden(), BorderLayout.NORTH);
			pnOrden.add(getScrlOrden(), BorderLayout.SOUTH);
			pnOrden.add(getScrlOrden(), BorderLayout.CENTER);
			pnOrden.add(getPnButtonsSouth(), BorderLayout.SOUTH);
		}
		return pnOrden;
	}
	private JLabel getLbOrden() {
		if (lbOrden == null) {
			lbOrden = new JLabel("Ordenar:");
		}
		return lbOrden;
	}
	private JPanel getPnBtnOrden() {
		if (pnBtnOrden == null) {
			pnBtnOrden = new JPanel();
			pnBtnOrden.setBorder(null);
			pnBtnOrden.setBackground(Color.WHITE);
			pnBtnOrden.setLayout(new GridLayout(5, 1, 0, 0));
			pnBtnOrden.add(getRdbtnAZ());
			pnBtnOrden.add(getRdbtZA());
			pnBtnOrden.add(getRdbtIdAsc());
			pnBtnOrden.add(getRdbtIdDesc());
		}
		return pnBtnOrden;
	}
	private JRadioButton getRdbtnAZ() {
		if (rdbtnAZ == null) {
			rdbtnAZ = new JRadioButton("A-Z");
			btnGroupOrden.add(rdbtnAZ);
			rdbtnAZ.setBackground(Color.WHITE);
		}
		return rdbtnAZ;
	}
	private JRadioButton getRdbtZA() {
		if (rdbtZA == null) {
			rdbtZA = new JRadioButton("Z-A");
			btnGroupOrden.add(rdbtZA);
			rdbtZA.setBackground(Color.WHITE);
		}
		return rdbtZA;
	}
	private JPanel getPnBtnFiltro() {
		if (pnBtnFiltro == null) {
			pnBtnFiltro = new JPanel();
			pnBtnFiltro.setBorder(null);
			pnBtnFiltro.setBackground(Color.WHITE);
			pnBtnFiltro.setLayout(new GridLayout(5, 1, 0, 0));
			pnBtnFiltro.add(getChkCuotaJoven());
			pnBtnFiltro.add(getChkCuotaAdulto());
			pnBtnFiltro.add(getChkCuotaJubilado());
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
	private JRadioButton getRdbtIdDesc() {
		if (rdbtIdDesc == null) {
			rdbtIdDesc = new JRadioButton("ID ↓");
			rdbtIdDesc.setSelected(true);
			btnGroupOrden.add(rdbtIdDesc);
		}
		return rdbtIdDesc;
	}
	
	private JCheckBox getChkMujeres() {
		if (chkMujeres == null) {
			chkMujeres = new JCheckBox("Mujeres");
		}
		return chkMujeres;
	}
	
	private JCheckBox getChkCuotaAdulto() {
		if (chkCuotaAdulto == null) {
			chkCuotaAdulto = new JCheckBox("Cuota Adulto");
			chkCuotaAdulto.setBackground(Color.WHITE);
		}
		return chkCuotaAdulto;
	}
	private JCheckBox getChkCuotaJoven() {
		if (chkCuotaJoven == null) {
			chkCuotaJoven = new JCheckBox("Cuota Joven");
			chkCuotaJoven.setBackground(Color.WHITE);
		}
		return chkCuotaJoven;
	}
	private JCheckBox getChkCuotaJubilado() {
		if (chkCuotaJubilado == null) {
			chkCuotaJubilado = new JCheckBox("Cuota Jubilado");
			chkCuotaJubilado.setBackground(Color.WHITE);
		}
		return chkCuotaJubilado;
	}
	
	private JScrollPane getScrlOrden() {
		if (scrlOrden == null) {
			scrlOrden = new JScrollPane();
			scrlOrden.setViewportView(getPnBtnOrden());
		}
		return scrlOrden;
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
	
	private JRadioButton getRdbtIdAsc() {
		if (rdbtIdAsc == null) {
			rdbtIdAsc = new JRadioButton("ID ↑");
			rdbtIdAsc.setSelected(true);
			btnGroupOrden.add(rdbtIdAsc);
		}
		return rdbtIdAsc;
	}

	private void aplicarFiltros() {
		String filterCuota = checkCuota();
		String genero = checkGenre();
		String order = checkOrden();
		String filter = "";
		filter = filterQueryBuilder(SociosController.WHERE, SociosController.AND, filterCuota, genero);
		vLS.actualizar(filter, order);
		dispose();
	}

	private String checkCuota() {
		String type = "";
		if (getChkCuotaJoven().isSelected()) {
			type = " cuota_type='CUOTA_JOVEN' ";
		}
		if (getChkCuotaAdulto().isSelected()) {
			type += type.isEmpty() ? " cuota_type='CUOTA_ADULTO' " : " or cuota_type='CUOTA_ADULTO' ";
		}
		if (getChkCuotaJubilado().isSelected()) {
			type += type.isEmpty() ? " cuota_type='CUOTA_JUBILADO' " : " or cuota_type='CUOTA_JUBILADO' ";
		}
		
		return type;
	}
	
	private String checkGenre() {
		String genero = "";
		if (getChkMujeres().isSelected()) {
			genero = " gender='MUJER'";
		} 
		
		if (getChkHombres().isSelected()) {
			genero += genero.isBlank() ? " gender='HOMBRE' " : " or gender='HOMBRE'";
		} 
		return genero;
	}

	private String filterQueryBuilder(String where, String and, String filterCuota, String genero) {
		String filter = "";
		if (!filterCuota.isEmpty() && !genero.isEmpty()) {
			filter = where + filterCuota + and + genero;
		} else if (filterCuota.isEmpty() && !genero.isEmpty()){
			filter = where + genero;
		} else if (!filterCuota.isEmpty() && genero.isEmpty()){
			filter = where + filterCuota;
		}
		return filter;
	}

	private String checkOrden() {
		String res = "";
		for (Enumeration<AbstractButton> buttons = btnGroupOrden.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                res = button.getText();
            }			
		}
		
		res = SociosController.checkOrden(res);
		
		return res;
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
