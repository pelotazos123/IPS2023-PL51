package giis.demo.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartPanel;

import giis.demo.logica.TestsFisiologicos;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaTestAnteriores extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaSeleccionTest vst;
	private TestsFisiologicos tf;
	private JPanel pnBotones;
	private JButton btCooper;
	private JButton btRockport;
	private JPanel pnGrafica;

	/**
	 * Create the frame.
	 * 
	 * @param ventanaSeleccionTest
	 */
	public VentanaTestAnteriores(VentanaSeleccionTest ventanaSeleccionTest) {
		this.vst = ventanaSeleccionTest;
		this.tf = vst.getTf();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 676, 483);
		this.setMinimumSize(new Dimension(676, 483));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setModal(true);
		setContentPane(contentPane);
		setTitle("Gestión Deportiva");
		setLocationRelativeTo(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPnBotones(), BorderLayout.NORTH);
		contentPane.add(getPnGrafica());
		habilitaBotones();
	}
	private void habilitaBotones() {
		if(!tf.tieneTestTipo(tf.getId(), TestsFisiologicos.COOPER))
			getBtCooper().setEnabled(false);
		if(!tf.tieneTestTipo(tf.getId(), TestsFisiologicos.ROCKPORT))
			getBtRockport().setEnabled(false);
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new GridLayout(1, 2, 10, 0));
			pnBotones.add(getBtCooper());
			pnBotones.add(getBtRockport());
		}
		return pnBotones;
	}
	private JButton getBtCooper() {
		if (btCooper == null) {
			btCooper = new JButton("COOPER");
			btCooper.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					muestraGraficaCooper();
				}
			});
			btCooper.setMnemonic('c');
		}
		return btCooper;
	}
	protected void muestraGraficaCooper() {
		pnGrafica.removeAll();
		getPnGrafica().setLayout(new BorderLayout(0, 0));
		ChartPanel cp = new ChartPanel(tf.creaGrafica(tf.getId(), TestsFisiologicos.COOPER));
		getPnGrafica().add(cp, BorderLayout.CENTER);
		getPnGrafica().validate();
	}
	private JButton getBtRockport() {
		if (btRockport == null) {
			btRockport = new JButton("ROCKPORT");
			btRockport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					muestraGraficaRockport();
				}
			});
			btRockport.setMnemonic('r');
		}
		return btRockport;
	}
	protected void muestraGraficaRockport() {
		getPnGrafica().removeAll();
		getPnGrafica().setLayout(new BorderLayout(0, 0));
		ChartPanel cp = new ChartPanel(tf.creaGrafica(tf.getId(), TestsFisiologicos.ROCKPORT));
		getPnGrafica().add(cp, BorderLayout.CENTER);
		getPnGrafica().validate();
	}
	
	private JPanel getPnGrafica() {
		if (pnGrafica == null) {
			pnGrafica = new JPanel();
			pnGrafica.setLayout(new BorderLayout(0, 0));
		}
		return pnGrafica;
	}
}
