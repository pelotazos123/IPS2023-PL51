package giis.demo.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.demo.logica.TestsFisiologicos;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class VentanaTestRockport extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lbTestRockport;
	private JLabel lbPeso;
	private JLabel lbEdad;
	private JLabel lbSexo;
	private JLabel lblTiempo;
	private JLabel lblPulsaciones;
	private JComboBox<String> cbSexo;
	private JTextField txTiempo;
	private JTextField txPulsaciones;
	private JComboBox<Integer> cbPeso;
	private JButton btCalcular;
	private JLabel lbResultado;
	private JTextField txResultado;
	private JComboBox<Integer> cbEdad;
	private VentanaSeleccionTest vst;
	private TestsFisiologicos tf;

	/**
	 * Create the frame.
	 * 
	 * @param ventanaSeleccionTest
	 */
	public VentanaTestRockport(VentanaSeleccionTest ventanaSeleccionTest) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 389, 383);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setTitle("Test de Rockport");
		setResizable(false);
		this.vst = ventanaSeleccionTest;
		tf = new TestsFisiologicos(vst);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLbTestRockport());
		contentPane.add(getLbPeso());
		contentPane.add(getLbEdad());
		contentPane.add(getLbSexo());
		contentPane.add(getLblTiempo());
		contentPane.add(getLblPulsaciones());
		contentPane.add(getCbSexo());
		contentPane.add(getTxTiempo());
		contentPane.add(getTxPulsaciones());
		contentPane.add(getCbPeso());
		contentPane.add(getBtCalcular());
		contentPane.add(getLbResultado());
		contentPane.add(getTxResultado());
		contentPane.add(getCbEdad());
//		cargaValores();
	}
//
//	private void cargaValores() {
//		String[] valores = tf.cargaValores();
//		if (valores != null) {
//			int edad = Integer.parseInt(valores[0]);
//			int peso = Integer.parseInt(valores[1]);
//			if (edad != 0 && peso != 0) {
//				getCbEdad().setSelectedItem(edad);
//				getCbPeso().setSelectedItem(peso);
//				getCbSexo().setSelectedItem(valores[2]);
//			}
//		}
//	}

	private JLabel getLbTestRockport() {
		if (lbTestRockport == null) {
			lbTestRockport = new JLabel("Test de Rockport");
			lbTestRockport.setHorizontalAlignment(SwingConstants.CENTER);
			lbTestRockport.setFont(new Font("Tahoma", Font.BOLD, 18));
			lbTestRockport.setBounds(0, 11, 363, 45);
		}
		return lbTestRockport;
	}

	private JLabel getLbPeso() {
		if (lbPeso == null) {
			lbPeso = new JLabel("Peso (en kg):");
			lbPeso.setLabelFor(getCbPeso());
			lbPeso.setDisplayedMnemonic('p');
			lbPeso.setBounds(10, 112, 126, 29);
		}
		return lbPeso;
	}

	private JLabel getLbEdad() {
		if (lbEdad == null) {
			lbEdad = new JLabel("Edad");
			lbEdad.setDisplayedMnemonic('e');
			lbEdad.setLabelFor(getCbEdad());
			lbEdad.setBounds(10, 83, 126, 29);
		}
		return lbEdad;
	}

	private JLabel getLbSexo() {
		if (lbSexo == null) {
			lbSexo = new JLabel("Sexo:");
			lbSexo.setDisplayedMnemonic('s');
			lbSexo.setLabelFor(getCbSexo());
			lbSexo.setBounds(10, 147, 126, 29);
		}
		return lbSexo;
	}

	private JLabel getLblTiempo() {
		if (lblTiempo == null) {
			lblTiempo = new JLabel("Tiempo para recorrer 1,6 km (en min):");
			lblTiempo.setDisplayedMnemonic('t');
			lblTiempo.setLabelFor(getTxTiempo());
			lblTiempo.setBounds(10, 180, 215, 29);
		}
		return lblTiempo;
	}

	private JLabel getLblPulsaciones() {
		if (lblPulsaciones == null) {
			lblPulsaciones = new JLabel("Pulsaciones por minuto en la carrera:");
			lblPulsaciones.setLabelFor(getTxPulsaciones());
			lblPulsaciones.setDisplayedMnemonic('l');
			lblPulsaciones.setBounds(10, 213, 215, 29);
		}
		return lblPulsaciones;
	}

	private JComboBox<String> getCbSexo() {
		if (cbSexo == null) {
			cbSexo = new JComboBox<String>();
			cbSexo.setModel(new DefaultComboBoxModel<String>(new String[] { "Hombre", "Mujer" }));
			cbSexo.setBounds(245, 147, 97, 29);
		}
		return cbSexo;
	}

	private JTextField getTxTiempo() {
		if (txTiempo == null) {
			txTiempo = new JTextField();
			txTiempo.setBounds(245, 180, 97, 29);
			txTiempo.setColumns(10);
		}
		return txTiempo;
	}

	private JTextField getTxPulsaciones() {
		if (txPulsaciones == null) {
			txPulsaciones = new JTextField();
			txPulsaciones.setColumns(10);
			txPulsaciones.setBounds(245, 213, 97, 29);
		}
		return txPulsaciones;
	}

	private JComboBox<Integer> getCbPeso() {
		if (cbPeso == null) {
			cbPeso = new JComboBox<Integer>(new DefaultComboBoxModel<Integer>(rellenaPeso()));
			cbPeso.setBounds(245, 115, 97, 25);
		}
		return cbPeso;
	}

	private Integer[] rellenaPeso() {
		Integer[] nums = new Integer[146];
		for (int i = 45; i <= 190; i++)
			nums[i - 45] = i;
		return nums;
	}

	private JButton getBtCalcular() {
		if (btCalcular == null) {
			btCalcular = new JButton("Calcular");
			btCalcular.setMnemonic('c');
			btCalcular.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Comprueba que hay datos
					if (compruebaDatos()) {
						// Calcula y habilita componentes
						activaComponentes();
						muestraResultado();
					}
				}
			});
			btCalcular.setBounds(117, 253, 147, 35);
		}
		return btCalcular;
	}

	private void muestraResultado() {
		int edad = (int) getCbEdad().getSelectedItem();
		int peso = (int) getCbPeso().getSelectedItem();
		int sexo = 0;
		if (getCbSexo().getSelectedItem().equals("Hombre"))
			sexo = TestsFisiologicos.HOMBRE;
		else if (getCbSexo().getSelectedItem().equals("Mujer"))
			sexo = TestsFisiologicos.MUJER;
		double tiempo = Double.parseDouble(getTxTiempo().getText());
		int pulsaciones = Integer.parseInt(getTxPulsaciones().getText());
		Double resultado = tf.getTestRockport(peso, edad, sexo, tiempo, pulsaciones);
		getTxResultado().setText(resultado.toString() + " ml/kg/min");
	}

	private void activaComponentes() {
		getLbResultado().setEnabled(true);
		getTxResultado().setEnabled(true);
	}

	private boolean compruebaDatos() {
		try {
			Double.parseDouble(getTxTiempo().getText());
			Integer.parseInt(getTxPulsaciones().getText());
		} catch (NumberFormatException | NullPointerException e) {
			desactivaComponentes();
			JOptionPane.showMessageDialog(null, "No se ha introducido ningún dato o no son de tipo correcto");
			return false;
		}
		return true;
	}

	private void desactivaComponentes() {
		getLbResultado().setEnabled(false);
		getTxResultado().setText("");
		getTxResultado().setEnabled(false);
	}

	private JLabel getLbResultado() {
		if (lbResultado == null) {
			lbResultado = new JLabel("Resultado:");
			lbResultado.setEnabled(false);
			lbResultado.setBounds(31, 299, 104, 35);
		}
		return lbResultado;
	}

	private JTextField getTxResultado() {
		if (txResultado == null) {
			txResultado = new JTextField();
			txResultado.setEnabled(false);
			txResultado.setEditable(false);
			txResultado.setColumns(10);
			txResultado.setBounds(135, 299, 196, 35);
		}
		return txResultado;
	}

	private JComboBox<Integer> getCbEdad() {
		if (cbEdad == null) {
			cbEdad = new JComboBox<Integer>();
			cbEdad.setModel(new DefaultComboBoxModel<Integer>(rellenaEdad()));
			cbEdad.setBounds(245, 79, 97, 25);
		}
		return cbEdad;
	}

	private Integer[] rellenaEdad() {
		Integer[] nums = new Integer[76];
		for (int i = 15; i <= 90; i++)
			nums[i - 15] = i;
		return nums;
	}
}

