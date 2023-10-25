package giis.demo.ui;

import javax.swing.JDialog;
import java.awt.Color;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import java.awt.GridLayout;

import javax.swing.JScrollPane;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import javax.swing.border.LineBorder;

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
	private JPanel pnTxtFields;
	private JLabel lblFiltrar;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JLabel lblNombre;
	private JLabel lblApellido;
	private JDateChooser dateTo;
	private JDateChooser dateFrom;
	private JLabel lblFromDate;
	private JLabel lblToDate;
	private JCheckBox chkSub18;
	private JCheckBox chkVeterano;
	private JCheckBox chkSenior;

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
		setBounds(100, 100, 535, 293);

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
			pnBtnFiltro.setLayout(new GridLayout(3, 1, 0, 0));
			pnBtnFiltro.add(getChkHombres());
			pnBtnFiltro.add(getChkSub18());
			pnBtnFiltro.add(getChkMujeres());
			pnBtnFiltro.add(getChkSenior());
			pnBtnFiltro.add(getChkDirectivo());
			pnBtnFiltro.add(getChkVeterano());
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
		
		String filterDateFrom = checkDateFrom();
		String filterDateTo = checkDateTo();
		
		String filter = buildFilter(filterMale, filterFemale, filterDir, name, surname, filterDateFrom, filterDateTo);	
		
		if (dateFrom.getDate() != null && dateTo.getDate() != null && dateFrom.getDate().compareTo(dateTo.getDate()) > 0) {
			JOptionPane.showMessageDialog(null, "La fecha destino no puede ser menor que la fecha origen", "ERROR", JOptionPane.ERROR_MESSAGE);
		} else {
			vLS.actualizar(filter);
			dispose();			
		}
		
	}

	private String buildFilter(String male, String female, String filterDir, String name, String surname, String filterDateFrom, String filterDateTo) {
		String filter = "";
		
		if (!getChkHombres().isSelected() && !getChkMujeres().isSelected()) {
			male="'HOMBRE'";
			female="'MUJER'";
		}
		
		filter = String.format(" name=%s AND surname=%s AND (gender=%s OR gender=%s) AND directive=%s AND (birth_date >= %s AND birth_date <= %s)",
				name, surname, male, female, filterDir, filterDateFrom, filterDateTo);
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
	}
	
	private String checkDateFrom() {
		if (getDateFrom().getDate() == null)
			return "birth_date";
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
		Date dateFrom = getDateFrom().getDate();
		String dateFormatted = formatDate.format(dateFrom);
		return dateFormatted;
	}
	
	private String checkDateTo() {
		if (getDateTo().getDate() == null)
			return "birth_date";
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
		Date dateTo = getDateTo().getDate();
		String dateFormatted = formatDate.format(dateTo);
		return dateFormatted;
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
			pnCenter.add(getPnTxtFields());
		}
		return pnCenter;
	}
	private JPanel getPnTxtFields() {
		if (pnTxtFields == null) {
			pnTxtFields = new JPanel();
			pnTxtFields.setBorder(new LineBorder(new Color(0, 0, 0)));
			pnTxtFields.setBackground(Color.WHITE);
			pnTxtFields.setLayout(null);
			pnTxtFields.add(getTxtNombre());
			pnTxtFields.add(getTxtApellido());
			pnTxtFields.add(getLblNombre());
			pnTxtFields.add(getDateFrom());
			pnTxtFields.add(getDateTo());
			pnTxtFields.add(getLblApellido());
			pnTxtFields.add(getLblFromDate());
			pnTxtFields.add(getLblToDate());
		}
		return pnTxtFields;
	}
	private JLabel getLblFiltrar() {
		if (lblFiltrar == null) {
			lblFiltrar = new JLabel("    Filtrar:");
			lblFiltrar.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return lblFiltrar;
	}
	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setBounds(110, 26, 115, 20);
			txtNombre.setColumns(10);
		}
		return txtNombre;
	}
	private JTextField getTxtApellido() {
		if (txtApellido == null) {
			txtApellido = new JTextField();
			txtApellido.setBounds(110, 63, 115, 20);
			txtApellido.setColumns(10);
		}
		return txtApellido;
	}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre: ");
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblNombre.setBounds(24, 28, 76, 14);
			lblNombre.setLabelFor(getTxtNombre());
		}
		return lblNombre;
	}
	private JLabel getLblApellido() {
		if (lblApellido == null) {
			lblApellido = new JLabel("Apellidos: ");
			lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblApellido.setBounds(24, 65, 76, 14);
			lblApellido.setLabelFor(getTxtApellido());
		}
		return lblApellido;
	}
	private JDateChooser getDateTo() {
		if (dateTo == null) {
			dateTo = new JDateChooser();
			dateTo.setDateFormatString("dd-MM-yyyy");
			dateTo.setBounds(110, 165, 115, 20);
		}
		return dateTo;
	}
	private JDateChooser getDateFrom() {
		if (dateFrom == null) {
			dateFrom = new JDateChooser();
			dateFrom.setDateFormatString("dd-MM-yyyy");
			dateFrom.setBounds(110, 128, 115, 20);
		}
		return dateFrom;
	}
	private JLabel getLblFromDate() {
		if (lblFromDate == null) {
			lblFromDate = new JLabel("Desde: ");
			lblFromDate.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblFromDate.setBounds(24, 128, 44, 14);
			lblFromDate.setLabelFor(getDateFrom());
		}
		return lblFromDate;
	}
	private JLabel getLblToDate() {
		if (lblToDate == null) {
			lblToDate = new JLabel("Hasta: ");
			lblToDate.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblToDate.setBounds(24, 171, 44, 14);
			lblToDate.setLabelFor(getDateTo());
		}
		return lblToDate;
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
