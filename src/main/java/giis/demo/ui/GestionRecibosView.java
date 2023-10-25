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
import javax.swing.JMenuBar;

public class GestionRecibosView {

	private JFrame frame;
	private JPanel contentPanel;
	private JButton btnGenerarRecibos;
	private JLabel lblRecibos;
	private JScrollPane spRecibos;
	private JTable tabRecibos;
	
	/**
	 * Create the application.
	 */
	public GestionRecibosView() {
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
		frame.setBounds(100, 100, 820, 450);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPanel);
		contentPanel.setLayout(null);
		contentPanel.add(getBtnGenerarRecibos());
		contentPanel.add(getLblRecibos());
		contentPanel.add(getSpRecibos());
		
		
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
			btnGenerarRecibos.setBounds(314, 338, 148, 46);
		}
		return btnGenerarRecibos;
	}
	private JLabel getLblRecibos() {
		if (lblRecibos == null) {
			lblRecibos = new JLabel("Recibos:");
			lblRecibos.setLabelFor(getSpRecibos());
			lblRecibos.setHorizontalAlignment(SwingConstants.CENTER);
			lblRecibos.setOpaque(true);
			lblRecibos.setBorder(new EmptyBorder(1, 1, 1, 1));
			lblRecibos.setBounds(50, 27, 64, 25);
		}
		return lblRecibos;
	}
	private JScrollPane getSpRecibos() {
		if (spRecibos == null) {
			spRecibos = new JScrollPane();
			spRecibos.setViewportBorder(new EmptyBorder(1, 1, 1, 1));
			spRecibos.setBounds(50, 63, 703, 264);
			spRecibos.setViewportView(getTabRecibos());
		}
		return spRecibos;
	}
	public JTable getTabRecibos() {
		if (tabRecibos == null) {
			tabRecibos = new JTable();
			tabRecibos.setDefaultEditor(Object.class, null);
		}
		return tabRecibos;
	}

	public JFrame getFrame() {
		return frame;
	}
}
