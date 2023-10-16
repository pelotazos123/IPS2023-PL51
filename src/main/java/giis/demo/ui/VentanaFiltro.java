package giis.demo.ui;

import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;

public class VentanaFiltro extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel pnFiltro;
	private JPanel pnOrden;
	private JLabel lbOrden;
	private JPanel pnBtnOrden;
	private JRadioButton rdbtnAZ;
	private JRadioButton rdbtZA;
	private JRadioButton rdbtNoPagado;
	private JPanel pnBtnFiltro;
	private JLabel lblFiltro;
	private JCheckBox chkPendientePago;
	private JCheckBox chkCuotaAdulto;
	private JCheckBox chkCuotaJoven;
	private JCheckBox chkCuotaJubilado;
	private JRadioButton rdbtPagado;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();

	/**
	 * Create the dialog.
	 */
	public VentanaFiltro() {
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(getPnFiltro(), BorderLayout.NORTH);
		getContentPane().add(getPnOrden(), BorderLayout.SOUTH);
		setBounds(100, 100, 310, 306);

	}

	private JPanel getPnFiltro() {
		if (pnFiltro == null) {
			pnFiltro = new JPanel();
			pnFiltro.setBackground(Color.WHITE);
			pnFiltro.setLayout(new BorderLayout(0, 0));
			pnFiltro.add(getPnBtnFiltro(), BorderLayout.SOUTH);
			pnFiltro.add(getLblFiltro(), BorderLayout.NORTH);
		}
		return pnFiltro;
	}
	private JPanel getPnOrden() {
		if (pnOrden == null) {
			pnOrden = new JPanel();
			pnOrden.setBackground(Color.WHITE);
			pnOrden.setLayout(new BorderLayout(0, 0));
			pnOrden.add(getLbOrden(), BorderLayout.NORTH);
			pnOrden.add(getPnBtnOrden(), BorderLayout.SOUTH);
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
			pnBtnOrden.setBackground(Color.WHITE);
			pnBtnOrden.setLayout(new GridLayout(5, 1, 0, 0));
			pnBtnOrden.add(getRdbtnAZ());
			pnBtnOrden.add(getRdbtZA());
			pnBtnOrden.add(getRdbtNoPagado());
			pnBtnOrden.add(getRdbtPagado());
		}
		return pnBtnOrden;
	}
	private JRadioButton getRdbtnAZ() {
		if (rdbtnAZ == null) {
			rdbtnAZ = new JRadioButton("A-Z");
			buttonGroup.add(rdbtnAZ);
			rdbtnAZ.setBackground(Color.WHITE);
		}
		return rdbtnAZ;
	}
	private JRadioButton getRdbtZA() {
		if (rdbtZA == null) {
			rdbtZA = new JRadioButton("Z-A");
			buttonGroup.add(rdbtZA);
			rdbtZA.setBackground(Color.WHITE);
		}
		return rdbtZA;
	}
	private JRadioButton getRdbtNoPagado() {
		if (rdbtNoPagado == null) {
			rdbtNoPagado = new JRadioButton("No Pagado");
			buttonGroup.add(rdbtNoPagado);
			rdbtNoPagado.setBackground(Color.WHITE);
		}
		return rdbtNoPagado;
	}
	private JPanel getPnBtnFiltro() {
		if (pnBtnFiltro == null) {
			pnBtnFiltro = new JPanel();
			pnBtnFiltro.setBackground(Color.WHITE);
			pnBtnFiltro.setLayout(new GridLayout(4, 1, 0, 0));
			pnBtnFiltro.add(getChkCuotaJoven());
			pnBtnFiltro.add(getChkCuotaAdulto());
			pnBtnFiltro.add(getChkCuotaJubilado());
			pnBtnFiltro.add(getChkPendientePago());
		}
		return pnBtnFiltro;
	}
	private JLabel getLblFiltro() {
		if (lblFiltro == null) {
			lblFiltro = new JLabel("Filtrar: ");
		}
		return lblFiltro;
	}
	private JCheckBox getChkPendientePago() {
		if (chkPendientePago == null) {
			chkPendientePago = new JCheckBox("Pendiente Pago");
			chkPendientePago.setBackground(Color.WHITE);
		}
		return chkPendientePago;
	}
	private JCheckBox getChkCuotaAdulto() {
		if (chkCuotaAdulto == null) {
			chkCuotaAdulto = new JCheckBox("Cuota Adulto");
			buttonGroup_1.add(chkCuotaAdulto);
			chkCuotaAdulto.setBackground(Color.WHITE);
		}
		return chkCuotaAdulto;
	}
	private JCheckBox getChkCuotaJoven() {
		if (chkCuotaJoven == null) {
			chkCuotaJoven = new JCheckBox("Cuota Joven");
			buttonGroup_1.add(chkCuotaJoven);
			chkCuotaJoven.setBackground(Color.WHITE);
		}
		return chkCuotaJoven;
	}
	private JCheckBox getChkCuotaJubilado() {
		if (chkCuotaJubilado == null) {
			chkCuotaJubilado = new JCheckBox("Cuota Jubilado");
			buttonGroup_1.add(chkCuotaJubilado);
			chkCuotaJubilado.setBackground(Color.WHITE);
		}
		return chkCuotaJubilado;
	}
	private JRadioButton getRdbtPagado() {
		if (rdbtPagado == null) {
			rdbtPagado = new JRadioButton("Pagado");
			buttonGroup.add(rdbtPagado);
			rdbtPagado.setBackground(Color.WHITE);
		}
		return rdbtPagado;
	}
}
