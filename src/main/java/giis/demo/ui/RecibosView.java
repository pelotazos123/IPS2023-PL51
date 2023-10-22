package giis.demo.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class RecibosView {
	
	private JFrame frame;
	private JPanel contentPanel;
	private JButton btnGenerarRecibos;
	private JLabel lblSocios;
	private JLabel lblCuota;
	private JScrollPane spSocios;
	private JScrollPane spCuotas;
	private JTable tabSocios;
	private JTable tabCuotas;
	private JLabel lblYear;
	private JComboBox<String> cbYear;
	private JLabel lblMonth;
	private JComboBox<String> cbMonth;
	
	/**
	 * Create the application.
	 */
	public RecibosView() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Recibos");
		frame.setName("Recibos");
		frame.setBounds(100, 100, 584, 381);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPanel);
		contentPanel.setLayout(null);
		contentPanel.add(getBtnGenerarRecibos());
		contentPanel.add(getLblSocios());
		contentPanel.add(getLblCuota());
		contentPanel.add(getSpSocios());
		contentPanel.add(getSpCuotas());
		contentPanel.add(getLblYear());
		contentPanel.add(getCbYear());
		contentPanel.add(getLblMonth());
		contentPanel.add(getCbMonth());
		
		
	}
	public JButton getBtnGenerarRecibos() {
		if (btnGenerarRecibos == null) {
			btnGenerarRecibos = new JButton("Generar Recibos");
			btnGenerarRecibos.setForeground(new Color(255, 255, 255));
			btnGenerarRecibos.setBackground(new Color(0, 128, 0));
			btnGenerarRecibos.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				}
			});
			btnGenerarRecibos.setBounds(217, 267, 148, 46);
		}
		return btnGenerarRecibos;
	}
	private JLabel getLblSocios() {
		if (lblSocios == null) {
			lblSocios = new JLabel("Socios:");
			lblSocios.setLabelFor(getSpSocios());
			lblSocios.setHorizontalAlignment(SwingConstants.CENTER);
			lblSocios.setOpaque(true);
			lblSocios.setBorder(new EmptyBorder(1, 1, 1, 1));
			lblSocios.setBounds(50, 27, 64, 25);
		}
		return lblSocios;
	}
	private JLabel getLblCuota() {
		if (lblCuota == null) {
			lblCuota = new JLabel("Cuotas:");
			lblCuota.setLabelFor(getTabCuotas());
			lblCuota.setHorizontalAlignment(SwingConstants.CENTER);
			lblCuota.setOpaque(true);
			lblCuota.setBounds(321, 27, 64, 25);
		}
		return lblCuota;
	}
	private JScrollPane getSpSocios() {
		if (spSocios == null) {
			spSocios = new JScrollPane();
			spSocios.setViewportBorder(new EmptyBorder(1, 1, 1, 1));
			spSocios.setBounds(50, 63, 205, 188);
			spSocios.setViewportView(getTabSocios());
		}
		return spSocios;
	}
	private JScrollPane getSpCuotas() {
		if (spCuotas == null) {
			spCuotas = new JScrollPane();
			spCuotas.setViewportBorder(new EmptyBorder(1, 1, 1, 1));
			spCuotas.setBounds(321, 63, 205, 83);
			spCuotas.setViewportView(getTabCuotas());
		}
		return spCuotas;
	}
	public JTable getTabSocios() {
		if (tabSocios == null) {
			tabSocios = new JTable();
			tabSocios.setDefaultEditor(Object.class, null);
		}
		return tabSocios;
	}
	public JTable getTabCuotas() {
		if (tabCuotas == null) {
			tabCuotas = new JTable();
			tabCuotas.setDefaultEditor(Object.class, null);
		}
		return tabCuotas;
	}

	public JFrame getFrame() {
		return frame;
	}
	private JLabel getLblYear() {
		if (lblYear == null) {
			lblYear = new JLabel("Año:");
			lblYear.setBounds(321, 167, 69, 24);
		}
		return lblYear;
	}
	public JComboBox<String> getCbYear() {
		if (cbYear == null) {
			cbYear = new JComboBox<String>();

			String[] años = new String[5];
			for(int i = 0; i < 5; i++) {
				años[i] = "" + (Calendar.getInstance().get(Calendar.YEAR) + i);
			}
			cbYear.setModel(new DefaultComboBoxModel<String>(años));
			cbYear.setBounds(420, 167, 106, 22);
		}
		return cbYear;
	}
	private JLabel getLblMonth() {
		if (lblMonth == null) {
			lblMonth = new JLabel("Mes:");
			lblMonth.setBounds(321, 213, 69, 24);
		}
		return lblMonth;
	}
	public JComboBox<String> getCbMonth() {
		if (cbMonth == null) {
			cbMonth = new JComboBox<String>();

			String[] meses = {
		            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
		            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
		    };
			cbMonth.setModel(new DefaultComboBoxModel<String>(meses));
			cbMonth.setBounds(420, 214, 106, 22);
		}
		return cbMonth;
	}
}
