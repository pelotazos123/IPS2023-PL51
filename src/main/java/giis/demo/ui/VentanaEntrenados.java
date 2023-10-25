package giis.demo.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VentanaEntrenados extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaSeleccionTest vst;

	/**
	 * Create the frame.
	 * @param ventanaSeleccionTest 
	 */
	public VentanaEntrenados(VentanaSeleccionTest ventanaSeleccionTest) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setTitle("Gestión Deportiva");
		setLocationRelativeTo(null);
		this.vst = ventanaSeleccionTest;
	}

}
