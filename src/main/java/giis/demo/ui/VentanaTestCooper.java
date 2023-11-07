package giis.demo.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.demo.logica.TestsFisiologicos;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaTestCooper extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lbDistancia;
	private JButton btCalcular;
	private JLabel lbTestCooper;
	private JLabel lbResultado;
	private JTextField txResultado;
	private JTextField txDistancia;
	private VentanaSeleccionTest vst;
	private TestsFisiologicos tf;

	/**
	 * Create the frame.
	 * @param ventanaSeleccionTest 
	 */
	public VentanaTestCooper(VentanaSeleccionTest ventanaSeleccionTest) {
		setTitle("Test de Cooper");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 365, 234);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setTitle("Test de Cooper");
		setResizable(false);
		vst = ventanaSeleccionTest;
		tf = new TestsFisiologicos(vst);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLbDistancia());
		contentPane.add(getBtCalcular());
		contentPane.add(getLbTestCooper());
		contentPane.add(getLbResultado());
		contentPane.add(getTxResultado());
		contentPane.add(getTxDistancia());
	}
	
	private JLabel getLbDistancia() {
		if (lbDistancia == null) {
			lbDistancia = new JLabel("Distancia recorrida en 12 minutos (en km):");
			lbDistancia.setDisplayedMnemonic('d');
			lbDistancia.setLabelFor(getTxDistancia());
			lbDistancia.setBounds(10, 48, 247, 27);
		}
		return lbDistancia;
	}
	
	private JButton getBtCalcular() {
		if (btCalcular == null) {
			btCalcular = new JButton("Calcular");
			btCalcular.setMnemonic('c');
			btCalcular.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Comprueba que haya datos y sean double
					if(compruebaDatos()) {
						//Activa la etiqueta y el tx Resultado y lo calcula y lo muestra
						activaComponentes();
						muestraResultado();
					} 
				}
			});
			btCalcular.setBounds(106, 86, 147, 35);
		}
		return btCalcular;
	}
	
	private void muestraResultado() {
		Double distance = Double.parseDouble(getTxDistancia().getText());
		getTxResultado().setText(tf.getTestCooper(distance).toString() + " ml/kg/min");
	}

	private void activaComponentes() {
		getLbResultado().setEnabled(true);
		getTxResultado().setEnabled(true);
	}

	private boolean compruebaDatos() {
		try {
			Double.parseDouble(getTxDistancia().getText());
		} catch (NumberFormatException | NullPointerException e) {
			desactivaComponentes();
			JOptionPane.showMessageDialog(null, "No se ha introducido ningún dato o no es de tipo double");
			return false;
		}
		return true;
	}

	private void desactivaComponentes() {
		getLbResultado().setEnabled(false);
		getTxResultado().setText("");
		getTxResultado().setEnabled(false);
	}

	private JLabel getLbTestCooper() {
		if (lbTestCooper == null) {
			lbTestCooper = new JLabel("TEST DE COOPER");
			lbTestCooper.setHorizontalAlignment(SwingConstants.CENTER);
			lbTestCooper.setFont(new Font("Tahoma", Font.BOLD, 18));
			lbTestCooper.setBounds(129, 11, 166, 27);
		}
		return lbTestCooper;
	}
	private JLabel getLbResultado() {
		if (lbResultado == null) {
			lbResultado = new JLabel("Resultado:");
			lbResultado.setEnabled(false);
			lbResultado.setBounds(36, 132, 104, 35);
		}
		return lbResultado;
	}
	private JTextField getTxResultado() {
		if (txResultado == null) {
			txResultado = new JTextField();
			txResultado.setEnabled(false);
			txResultado.setEditable(false);
			txResultado.setBounds(140, 132, 196, 35);
			txResultado.setColumns(10);
		}
		return txResultado;
	}
	private JTextField getTxDistancia() {
		if (txDistancia == null) {
			txDistancia = new JTextField();
			txDistancia.setBounds(266, 48, 74, 27);
			txDistancia.setColumns(10);
		}
		return txDistancia;
	}
}
