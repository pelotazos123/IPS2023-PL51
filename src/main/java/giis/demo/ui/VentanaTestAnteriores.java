package giis.demo.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartPanel;

import demo.ChartPanelSerializationTest;
import giis.demo.logica.TestsFisiologicos;

public class VentanaTestAnteriores extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaSeleccionTest vst;
	private TestsFisiologicos tf;

	/**
	 * Create the frame.
	 * 
	 * @param ventanaSeleccionTest
	 */
	public VentanaTestAnteriores(VentanaSeleccionTest ventanaSeleccionTest) {
		this.vst = ventanaSeleccionTest;
		this.tf = new TestsFisiologicos(vst);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 574, 411);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		ChartPanel cp = new ChartPanel(tf.creaGrafica());
		
		setContentPane(contentPane);
		setTitle("Gestión Deportiva");
		setLocationRelativeTo(null);
		contentPane.add(cp);
	}

}
