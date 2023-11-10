package giis.demo.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartPanel;

import giis.demo.logica.TestsFisiologicos;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaEntrenados extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaSeleccionTest vst;
	private TestsFisiologicos tf;
	private JPanel pnEntrenados;
	private JPanel pnGrafica;
	private JPanel pnIdsYNombres;
	private JPanel pnTipoTest;
	private JButton btCooper;
	private JButton btRockport;
	private int idSelected = 0;
	
	/**
	 * Create the frame.
	 * @param ventanaSeleccionTest 
	 */
	public VentanaEntrenados(VentanaSeleccionTest ventanaSeleccionTest) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		setTitle("Gestión Deportiva");
		setLocationRelativeTo(null);
		setResizable(false);
		this.vst = ventanaSeleccionTest;
		this.tf = vst.getTf();
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPnEntrenados(), BorderLayout.NORTH);
		contentPane.add(getPnGrafica());
		activaEntrenados();
	}
	private void activaEntrenados() {
		int filas = tf.getIdEntrenados().length / 2;
		if(tf.getIdEntrenados().length == 1)
			getPnIdsYNombres().setLayout(new GridLayout(1, 1));
		else 
			getPnIdsYNombres().setLayout(new GridLayout(filas, 2));
		creaBotonesEntrenados();
	}
	private void creaBotonesEntrenados() {
		for(int i = 0; i < tf.getIdEntrenados().length; i++) {
			JButton bt = new JButton();
			int id = tf.getIdEntrenados()[i];
			bt.setText(id + "  " + tf.getNombre(id)); 
			bt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					idSelected = id; 
					activaBotones();
				}
			});
			getPnIdsYNombres().add(bt);
		}
	}
	
	protected void activaBotones() {
		pnGrafica.removeAll();
		pnGrafica.repaint();
		getBtCooper().setEnabled(false);
		getBtRockport().setEnabled(false);
		if(tf.tieneTestTipo(idSelected, TestsFisiologicos.COOPER))
			getBtCooper().setEnabled(true);
		if(tf.tieneTestTipo(idSelected, TestsFisiologicos.ROCKPORT))
			getBtRockport().setEnabled(true);
	}
	protected void creaGrafica(String tipo) {
		pnGrafica.removeAll();
		getPnGrafica().setLayout(new BorderLayout(0, 0));
		getPnEntrenados().getComponents();
		ChartPanel cp = new ChartPanel(tf.creaGrafica(idSelected, tipo));
		getPnGrafica().add(cp, BorderLayout.CENTER);
		getPnGrafica().validate();
	}
	
	private JPanel getPnEntrenados() {
		if (pnEntrenados == null) {
			pnEntrenados = new JPanel();
			pnEntrenados.setLayout(new BorderLayout(0, 0));
			pnEntrenados.add(getPnIdsYNombres(), BorderLayout.CENTER);
			pnEntrenados.add(getPnTipoTest(), BorderLayout.SOUTH);
		}
		return pnEntrenados;
	}
	private JPanel getPnGrafica() {
		if (pnGrafica == null) {
			pnGrafica = new JPanel();
			pnGrafica.setLayout(null);
		}
		return pnGrafica;
	}
	private JPanel getPnIdsYNombres() {
		if (pnIdsYNombres == null) {
			pnIdsYNombres = new JPanel();
		}
		return pnIdsYNombres;
	}
	private JPanel getPnTipoTest() {
		if (pnTipoTest == null) {
			pnTipoTest = new JPanel();
			pnTipoTest.setLayout(new GridLayout(1, 2, 10, 0));
			pnTipoTest.add(getBtCooper());
			pnTipoTest.add(getBtRockport());
		}
		return pnTipoTest;
	}
	private JButton getBtCooper() {
		if (btCooper == null) {
			btCooper = new JButton("COOPER");
			btCooper.setEnabled(false);
			btCooper.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					creaGrafica(TestsFisiologicos.COOPER);
				}
			});
			btCooper.setMnemonic('c');
		}
		return btCooper;
	}
	private JButton getBtRockport() {
		if (btRockport == null) {
			btRockport = new JButton("ROCKPORT");
			btRockport.setEnabled(false);
			btRockport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					creaGrafica(TestsFisiologicos.ROCKPORT);
				}
			});
			btRockport.setMnemonic('r');
		}
		return btRockport;
	}
}
