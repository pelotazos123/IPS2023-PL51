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
import javax.swing.JTextField;
import java.awt.FlowLayout;
import com.toedter.calendar.JDateChooser;
import java.awt.Font;

public class VentanaFiltro extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel pnFiltro;
	private JPanel pnOrden;
	private JPanel pnBtnFiltro;
	private JCheckBox chkHombres;
	private JScrollPane scrlFiltro;
	private JButton btnAplicar;
	private VentanaListaSocios vLS;
	private JCheckBox chkMujeres;
	private JPanel pnButtonsSouth;
	private JCheckBox chkDirectivo;
	private JPanel pnCenter;
	private JPanel panel;
	private JLabel lblFiltrar;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JLabel lblNombre;
	private JLabel lblApellido;
	private JDateChooser dateTo;
	private JDateChooser dateFrom;
	private JLabel lblFromDate;
	private JLabel lblToDate;

	/**
	 * Create the dialog.
	 */
	public VentanaFiltro(VentanaListaSocios vLS) {
		this.vLS = vLS;
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(getPnOrden(), BorderLayout.SOUTH);
		getContentPane().add(getPnCenter(), BorderLayout.CENTER);
		getContentPane().add(getLblFiltrar(), BorderLayout.NORTH);
		setBounds(100, 100, 535, 352);

	}

	private JPanel getPnFiltro() {
		if (pnFiltro == null) {
			pnFiltro = new JPanel();
			pnFiltro.setBorder(null);
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
			pnBtnFiltro.setLayout(new GridLayout(5, 1, 0, 0));
			pnBtnFiltro.add(getChkHombres());
			pnBtnFiltro.add(getChkMujeres());
			pnBtnFiltro.add(getChkDirectivo());
		}
		return pnBtnFiltro;
	}
	
	private JCheckBox getChkHombres() {
		if (chkHombres == null) {
			chkHombres = new JCheckBox("Hombres");
			chkHombres.setFont(new Font("Tahoma", Font.PLAIN, 13));
			chkHombres.setBackground(Color.WHITE);
		}
		return chkHombres;
	}
	
	private JCheckBox getChkMujeres() {
		if (chkMujeres == null) {
			chkMujeres = new JCheckBox("Mujeres");
			chkMujeres.setFont(new Font("Tahoma", Font.PLAIN, 13));
			chkMujeres.setBackground(Color.WHITE);
		}
		return chkMujeres;
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
					aplicarFiltros();
				}
			});
		}
		return btnAplicar;
	}

	private void aplicarFiltros() {
		String name = filterName();
		String surname = filterSurname();
		
		String filterMale = checkMale();
		String filterFemale = checkFemale();
		
		String filterDir = checkDirective();
		
		String filter = buildFilter(filterMale, filterFemale, filterDir, name, surname);	
		
		vLS.actualizar(filter);
		dispose();
	}

	private String buildFilter(String male, String female, String filterDir, String name, String surname) {
		String filter = "";
		
		if (!getChkHombres().isSelected() && !getChkMujeres().isSelected()) {
			male="'HOMBRE'";
			female="'MUJER'";
		}
		
		filter = String.format(" name=%s AND surname=%s AND (gender=%s OR gender=%s) AND directive=%s", name, surname, male, female, filterDir);
		return filter;
	}

	private String filterName() {
		String name = getTxtNombre().getText();
		return !name.isEmpty() ? "'"+name+"'" : "name";
	}
	
	private String filterSurname() {
		String surname = getTxtApellido().getText();
		return !surname.isEmpty() ? "'"+surname+"'" : "surname";
	}
	
	private String checkDirective() {
		return getChkDirectivo().isSelected() ? "true" : "directive";
	}
	
	private String checkMale() {
		return getChkHombres().isSelected() ? "'HOMBRE'" : "''";
	}

	private String checkFemale() {
		return getChkMujeres().isSelected() ? "'MUJER'" : "''";
		
//		if (getChkHombres().isSelected() && !getChkMujeres().isSelected()) 
//			filter += " gender='HOMBRE' ";
//		else if (getChkMujeres().isSelected() && !getChkHombres().isSelected())
//			filter += " gender='MUJER' ";
	}

	private JPanel getPnButtonsSouth() {
		if (pnButtonsSouth == null) {
			pnButtonsSouth = new JPanel();
			pnButtonsSouth.setLayout(new GridLayout(0, 1, 0, 0));
			pnButtonsSouth.add(getBtnAplicar());
		}
		return pnButtonsSouth;
	}


	private JCheckBox getChkDirectivo() {
		if (chkDirectivo == null) {
			chkDirectivo = new JCheckBox("Directivos");
			chkDirectivo.setFont(new Font("Tahoma", Font.PLAIN, 13));
			chkDirectivo.setBackground(Color.WHITE);
		}
		return chkDirectivo;
	}
	private JPanel getPnCenter() {
		if (pnCenter == null) {
			pnCenter = new JPanel();
			pnCenter.setLayout(new GridLayout(0, 2, 0, 0));
			pnCenter.add(getPnFiltro());
			pnCenter.add(getPanel());
		}
		return pnCenter;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setLayout(null);
			panel.add(getTxtNombre());
			panel.add(getTxtApellido());
			panel.add(getLblNombre());
			panel.add(getDateFrom());
			panel.add(getDateTo());
			panel.add(getLblApellido());
			panel.add(getLblFromDate());
			panel.add(getLblToDate());
		}
		return panel;
	}
	private JLabel getLblFiltrar() {
		if (lblFiltrar == null) {
			lblFiltrar = new JLabel("    Filtrar:");
		}
		return lblFiltrar;
	}
	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setBounds(110, 11, 115, 20);
			txtNombre.setColumns(10);
		}
		return txtNombre;
	}
	private JTextField getTxtApellido() {
		if (txtApellido == null) {
			txtApellido = new JTextField();
			txtApellido.setBounds(110, 42, 115, 20);
			txtApellido.setColumns(10);
		}
		return txtApellido;
	}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre: ");
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblNombre.setBounds(24, 14, 76, 14);
			lblNombre.setLabelFor(getTxtNombre());
		}
		return lblNombre;
	}
	private JLabel getLblApellido() {
		if (lblApellido == null) {
			lblApellido = new JLabel("Apellidos: ");
			lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblApellido.setBounds(24, 44, 76, 14);
			lblApellido.setLabelFor(getTxtApellido());
		}
		return lblApellido;
	}
	private JDateChooser getDateTo() {
		if (dateTo == null) {
			dateTo = new JDateChooser();
			dateTo.setDateFormatString("dd/MM/yy");
			dateTo.setBounds(110, 120, 115, 20);
		}
		return dateTo;
	}
	private JDateChooser getDateFrom() {
		if (dateFrom == null) {
			dateFrom = new JDateChooser();
			dateFrom.setDateFormatString("dd/MM/yy");
			dateFrom.setBounds(110, 84, 115, 20);
		}
		return dateFrom;
	}
	private JLabel getLblFromDate() {
		if (lblFromDate == null) {
			lblFromDate = new JLabel("Desde: ");
			lblFromDate.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblFromDate.setBounds(24, 84, 44, 14);
			lblFromDate.setLabelFor(getDateFrom());
		}
		return lblFromDate;
	}
	private JLabel getLblToDate() {
		if (lblToDate == null) {
			lblToDate = new JLabel("Hasta: ");
			lblToDate.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblToDate.setBounds(24, 120, 44, 14);
			lblToDate.setLabelFor(getDateTo());
		}
		return lblToDate;
	}
}
