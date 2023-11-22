package giis.demo.ui.competiciones;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import giis.demo.model.Socio;
import giis.demo.model.TiposCuotas;
import giis.demo.model.competiciones.Competicion;
import giis.demo.model.competiciones.servicio.GestionarCompeticiones;

public class VentanaSeleccionarCategoria extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel pnFiltro;
	private JPanel pnOrden;
	private JPanel pnBtnFiltro;
	private JScrollPane scrlFiltro;
	private JButton btnAplicar;
	private JPanel pnButtonsSouth;
	private JPanel pnCenter;
	private JLabel lbSeleccionar;
	
	private GestionarCompeticiones gestorCompeticiones;
	private Competicion compe;
	private Socio socio;
	private JRadioButton rdbtnSub18;
	private JRadioButton rdbtnSenior;
	private JRadioButton rdbtnVeterano;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the dialog.
	 * @param socioId 
	 * @param compe 
	 */
	public VentanaSeleccionarCategoria(GestionarCompeticiones gestor, Competicion compe, Socio socio) {
		gestorCompeticiones = gestor;
		this.compe = compe;
		this.socio = socio;
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(getPnOrden(), BorderLayout.SOUTH);
		getContentPane().add(getPnCenter(), BorderLayout.CENTER);
		getContentPane().add(getLbSeleccionar(), BorderLayout.NORTH);
		setBounds(100, 100, 375, 293);
		mostrarDisponibles();

	}

	private void mostrarDisponibles() {
		List<TiposCuotas> categorias = compe.getCategorias();
		if(categorias.contains(TiposCuotas.SUB18) && socio.getTipoCuota().equals(TiposCuotas.SUB18)) {
			getRdbtnSub18().setEnabled(true);
		}
		if(categorias.contains(TiposCuotas.SENIOR)) {
			getRdbtnSenior().setEnabled(true);
		}
		if(categorias.contains(TiposCuotas.VETERANO) && socio.getTipoCuota().equals(TiposCuotas.VETERANO)) {
			getRdbtnVeterano().setEnabled(true);
		}
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
			pnBtnFiltro.add(getRdbtnSub18());
			pnBtnFiltro.add(getRdbtnSenior());
			pnBtnFiltro.add(getRdbtnVeterano());
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
					inscribirse();
				}
			});
		}
		return btnAplicar;
	}
	
	private void inscribirse() {
		gestorCompeticiones.inscribirSocio(compe.getId(), socio.getId());
		dispose();
		JOptionPane.showMessageDialog(this,"Ha sido inscrito en "+compe.getNombre(),
				"Competiciones", JOptionPane.INFORMATION_MESSAGE);
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
	private JLabel getLbSeleccionar() {
		if (lbSeleccionar == null) {
			lbSeleccionar = new JLabel("    Selecciona categoria:");
			lbSeleccionar.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return lbSeleccionar;
	}
	private JRadioButton getRdbtnSub18() {
		if (rdbtnSub18 == null) {
			rdbtnSub18 = new JRadioButton("Sub 18");
			rdbtnSub18.setEnabled(false);
			buttonGroup.add(rdbtnSub18);
			rdbtnSub18.setBackground(Color.WHITE);
		}
		return rdbtnSub18;
	}
	private JRadioButton getRdbtnSenior() {
		if (rdbtnSenior == null) {
			rdbtnSenior = new JRadioButton("Senior");
			rdbtnSenior.setEnabled(false);
			buttonGroup.add(rdbtnSenior);
			rdbtnSenior.setBackground(Color.WHITE);
		}
		return rdbtnSenior;
	}
	private JRadioButton getRdbtnVeterano() {
		if (rdbtnVeterano == null) {
			rdbtnVeterano = new JRadioButton("Veterano");
			rdbtnVeterano.setEnabled(false);
			buttonGroup.add(rdbtnVeterano);
			rdbtnVeterano.setBackground(Color.WHITE);
		}
		return rdbtnVeterano;
	}
}
