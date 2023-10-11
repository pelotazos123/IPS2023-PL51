package giis.demo.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SeleccionTest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btCooper;
	private JButton btnTestDeRockport;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SeleccionTest frame = new SeleccionTest();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public SeleccionTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		// TODO Auto-generated method stub
		VentanaTestRockport vtr = new VentanaTestRockport();
		vtr.setVisible(true);
	}
}
