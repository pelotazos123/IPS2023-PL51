package giis.demo.ui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GestionRecibosView {

	private JFrame frame;
	private JPanel contentPanel;
	private JButton btnCargarRecibos;
	private JLabel lblRecibos;
	private JScrollPane spRecibos;
	private JTable tabRecibos;
	private JButton btnReclamar;

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
		frame.setBounds(100, 100, 1000, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPanel);
		contentPanel.setLayout(null);
		contentPanel.add(getLblRecibos());
		contentPanel.add(getSpRecibos());
		contentPanel.add(getBtnCargarRecibos());
		contentPanel.add(getBtnReclamar());

	}

	public JButton getBtnCargarRecibos() {
		if (btnCargarRecibos == null) {
			btnCargarRecibos = new JButton("Cargar Recibos");
			btnCargarRecibos.setForeground(Color.WHITE);
			btnCargarRecibos.setBackground(new Color(139, 0, 0));
			btnCargarRecibos.setBounds(150, 400, 148, 46);
		}
		return btnCargarRecibos;
	}

	public JButton getBtnReclamar() {
		if (btnReclamar == null) {
			btnReclamar = new JButton("Reclamar");
			btnReclamar.setForeground(Color.WHITE);
			btnReclamar.setBackground(new Color(255, 153, 51));
			btnReclamar.setBounds(671, 400, 148, 46);
		}
		return btnReclamar;
	}

	private JLabel getLblRecibos() {
		if (lblRecibos == null) {
			lblRecibos = new JLabel("Recibos:");
			lblRecibos.setLabelFor(getSpRecibos());
			lblRecibos.setHorizontalAlignment(SwingConstants.CENTER);
			lblRecibos.setOpaque(true);
			lblRecibos.setBorder(new EmptyBorder(1, 1, 1, 1));
			lblRecibos.setBounds(50, 27, 98, 25);
		}
		return lblRecibos;
	}

	private JScrollPane getSpRecibos() {
		if (spRecibos == null) {
			spRecibos = new JScrollPane();
			spRecibos.setViewportBorder(new EmptyBorder(1, 1, 1, 1));
			spRecibos.setBounds(50, 63, 891, 315);
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
