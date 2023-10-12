package giis.demo.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
	private JLabel lblSocio;
	private JLabel lblCuotas;
	private JScrollPane spSocios;
	private JScrollPane spCuotas;
	private JTable tabSocios;
	private JTable tabCuotas;
	
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
		contentPanel.add(getLblSocio());
		contentPanel.add(getLblCuotas());
		contentPanel.add(getSpSocios());
		contentPanel.add(getSpCuotas());
		
		
	}
	public JButton getBtnGenerarRecibos() {
		if (btnGenerarRecibos == null) {
			btnGenerarRecibos = new JButton("Generar Recibos");
			btnGenerarRecibos.setForeground(Color.WHITE);
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
	private JLabel getLblSocio() {
		if (lblSocio == null) {
			lblSocio = new JLabel("Socio:");
			lblSocio.setLabelFor(getSpSocios());
			lblSocio.setHorizontalAlignment(SwingConstants.CENTER);
			lblSocio.setForeground(new Color(255, 255, 255));
			lblSocio.setOpaque(true);
			lblSocio.setBorder(new EmptyBorder(1, 1, 1, 1));
			lblSocio.setBackground(new Color(0, 0, 205));
			lblSocio.setBounds(50, 27, 64, 25);
		}
		return lblSocio;
	}
	private JLabel getLblCuotas() {
		if (lblCuotas == null) {
			lblCuotas = new JLabel("Cuotas:");
			lblCuotas.setLabelFor(getTabCuotas());
			lblCuotas.setHorizontalAlignment(SwingConstants.CENTER);
			lblCuotas.setForeground(new Color(255, 255, 255));
			lblCuotas.setOpaque(true);
			lblCuotas.setBackground(new Color(0, 0, 205));
			lblCuotas.setBounds(321, 27, 64, 25);
		}
		return lblCuotas;
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
			spCuotas.setBounds(321, 63, 205, 188);
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
}
