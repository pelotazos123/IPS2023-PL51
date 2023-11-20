package giis.demo.ui;

import javax.swing.JDialog;

import giis.demo.business.InstalacionController;
import giis.demo.model.Instalacion;
import giis.demo.util.Database;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaCrearCursillos extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel pnCreacion;
	private JDateChooser dcInicio;
	private JDateChooser dcFinal;
	private JButton btnCrear;
	private JSpinner spnMaxSocios;
	private JLabel lblMaxSocios;
	private JComboBox<Instalacion> cbInstalaciones;
	private Database db;
	private JLabel lblInfo;
	private JLabel lblDiaInicio;
	private JLabel lblDiaFinal;
	private JLabel lblInstalacion;

	public VentanaCrearCursillos(Database db) {
		this.db = db;
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(getPnCreacion(), BorderLayout.CENTER);
		getContentPane().add(getBtnCrear(), BorderLayout.SOUTH);
		getContentPane().add(getLblInfo(), BorderLayout.NORTH);
		setTitle("Reservas");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 430, 453);

	}

	private JPanel getPnCreacion() {
		if (pnCreacion == null) {
			pnCreacion = new JPanel();
			pnCreacion.setBackground(Color.WHITE);
			pnCreacion.setBorder(new TitledBorder(null, "Creaci\u00F3n de cursillos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnCreacion.setLayout(null);
			pnCreacion.add(getDcInicio());
			pnCreacion.add(getDcFinal());
			pnCreacion.add(getSpnMaxSocios());
			pnCreacion.add(getLblMaxSocios());
			pnCreacion.add(getCbInstalaciones());
			pnCreacion.add(getLblDiaInicio());
			pnCreacion.add(getLblDiaFinal());
			pnCreacion.add(getLblInstalacion());
		}
		return pnCreacion;
	}
	private JDateChooser getDcInicio() {
		if (dcInicio == null) {
			dcInicio = new JDateChooser();
			dcInicio.setFont(new Font("Tahoma", Font.PLAIN, 15));
			dcInicio.setDateFormatString("yyyy-MM-dd");
			dcInicio.setBounds(159, 107, 115, 26);
		}
		return dcInicio;
	}
	private JDateChooser getDcFinal() {
		if (dcFinal == null) {
			dcFinal = new JDateChooser();
			dcFinal.setFont(new Font("Tahoma", Font.PLAIN, 15));
			dcFinal.setDateFormatString("yyyy-MM-dd");
			dcFinal.setBounds(159, 162, 115, 26);
		}
		return dcFinal;
	}
	private JButton getBtnCrear() {
		if (btnCrear == null) {
			btnCrear = new JButton("Crear curso");
			btnCrear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearCurso();
				}
			});
		}
		return btnCrear;
	}
	
	private void crearCurso() {
				
	}

	private JSpinner getSpnMaxSocios() {
		if (spnMaxSocios == null) {
			spnMaxSocios = new JSpinner();
			spnMaxSocios.setFont(new Font("Tahoma", Font.PLAIN, 15));
			spnMaxSocios.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
			spnMaxSocios.setBounds(238, 218, 36, 26);
		}
		return spnMaxSocios;
	}
	private JLabel getLblMaxSocios() {
		if (lblMaxSocios == null) {
			lblMaxSocios = new JLabel("Nº máximo de socios");
			lblMaxSocios.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblMaxSocios.setLabelFor(getSpnMaxSocios());
			lblMaxSocios.setBounds(27, 224, 159, 14);
		}
		return lblMaxSocios;
	}
	private JComboBox<Instalacion> getCbInstalaciones() {
		if (cbInstalaciones == null) {
			cbInstalaciones = new JComboBox<Instalacion>();
			cbInstalaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					prepareCursillo();
				}
			});
			cbInstalaciones.setBackground(Color.WHITE);
			cbInstalaciones.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbInstalaciones.setBounds(27, 44, 343, 31);
			cbInstalaciones.setModel(new DefaultComboBoxModel<Instalacion>(InstalacionController.getInstalaciones(db)));
		}
		return cbInstalaciones;
	}
	
	private void prepareCursillo() {
		Instalacion instalacion = ((Instalacion)cbInstalaciones.getSelectedItem());
		spnMaxSocios.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(instalacion.getMinReserva()), Integer.valueOf(instalacion.getMaxReserva()), Integer.valueOf(1)));
		
	}

	private JLabel getLblInfo() {
		if (lblInfo == null) {
			lblInfo = new JLabel("info");
		}
		return lblInfo;
	}
	private JLabel getLblDiaInicio() {
		if (lblDiaInicio == null) {
			lblDiaInicio = new JLabel("Dia de inicio:");
			lblDiaInicio.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblDiaInicio.setBounds(27, 107, 88, 14);
		}
		return lblDiaInicio;
	}
	private JLabel getLblDiaFinal() {
		if (lblDiaFinal == null) {
			lblDiaFinal = new JLabel("Dia de finalización:");
			lblDiaFinal.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblDiaFinal.setBounds(27, 162, 115, 14);
		}
		return lblDiaFinal;
	}
	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalación del curso: ");
			lblInstalacion.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblInstalacion.setBounds(26, 23, 160, 14);
		}
		return lblInstalacion;
	}
}
