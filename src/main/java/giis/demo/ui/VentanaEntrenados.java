package giis.demo.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartPanel;

import giis.demo.logica.TestsFisiologicos;

public class VentanaEntrenados extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaSeleccionTest vst;
	private TestsFisiologicos tf;
	
	/**
	 * Create the frame.
	 * @param ventanaSeleccionTest 
	 */
	public VentanaEntrenados(VentanaSeleccionTest ventanaSeleccionTest) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		setTitle("Gestión Deportiva");
		setLocationRelativeTo(null);
		setResizable(false);
		this.vst = ventanaSeleccionTest;
		this.tf = new TestsFisiologicos(vst);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		ChartPanel cp = new ChartPanel(tf.creaGraficaEntrenados());
		
		contentPane.add(cp);
	}

}
