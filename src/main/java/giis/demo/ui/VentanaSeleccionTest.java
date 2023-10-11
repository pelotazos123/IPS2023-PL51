package giis.demo.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaSeleccionTest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btCooper;
	private JButton btnTestDeRockport;

	/**
	 * Create the frame.
	 */
	public VentanaSeleccionTest() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setTitle("Selección de tipo de test");
		setResizable(false);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtCooper());
		contentPane.add(getBtnTestDeRockport());
	}
	private JButton getBtCooper() {
		if (btCooper == null) {
			btCooper = new JButton("Test de Cooper");
			btCooper.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					muestraVentanaCooper();
				}
			});
			btCooper.setMnemonic('c');
			btCooper.setBounds(60, 91, 141, 68);
		}
		return btCooper;
	}
	private void muestraVentanaCooper() {
		VentanaTestCooper vtc = new VentanaTestCooper();
		vtc.setVisible(true);
	}

	private JButton getBtnTestDeRockport() {
		if (btnTestDeRockport == null) {
			btnTestDeRockport = new JButton("Test de Rockport");
			btnTestDeRockport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					muestraVentanaRockport();
				}
			});
			btnTestDeRockport.setMnemonic('r');
			btnTestDeRockport.setBounds(228, 91, 141, 68);
		}
		return btnTestDeRockport;
	}

	private void muestraVentanaRockport() {
		VentanaTestRockport vtr = new VentanaTestRockport();
		vtr.setVisible(true);
	}
}
