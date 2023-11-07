package giis.demo.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

public class VentanaSeleccionTest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btCooper;
	private JButton btnTestDeRockport;
	private JButton btResultadosAnteriores;
	private JButton btResultadosEntrenados;
	private VentanaPrincipal vp;
	private int id;

	/**
	 * Create the frame.
	 * 
	 * @param ventanaPrincipal
	 */
	public VentanaSeleccionTest(VentanaPrincipal ventanaPrincipal) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setTitle("Gestión Deportiva");
		setResizable(false);
		setMinimumSize(new Dimension(300, 450));
		this.vp = ventanaPrincipal;
		this.id = 101;

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtCooper());
		contentPane.add(getBtnTestDeRockport());
		contentPane.add(getBtResultadosAnteriores());
		contentPane.add(getBtResultadosEntrenados());
		if(id == 101) {
			activaEntrenador();
		}
	}
	/**
	 * Si el usuario es entrenador activa el componente de Ver Resultados de
	 * entrenados
	 */
	private void activaEntrenador() {
		// if usuario es entrenador
			getBtResultadosEntrenados().setEnabled(true);
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
		VentanaTestCooper vtc = new VentanaTestCooper(this);
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
		VentanaTestRockport vtr = new VentanaTestRockport(this);
		vtr.setVisible(true);
	}

	private JButton getBtResultadosAnteriores() {
		if (btResultadosAnteriores == null) {
			btResultadosAnteriores = new JButton("Test anteriores");
			btResultadosAnteriores.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					creaVentanaTestAnteriores();
				}
			});
			btResultadosAnteriores.setMnemonic('a');
			btResultadosAnteriores.setBounds(60, 191, 142, 89);
		}
		return btResultadosAnteriores;
	}

	private void creaVentanaTestAnteriores() {
		VentanaTestAnteriores vta = new VentanaTestAnteriores(this);
		vta.setVisible(true);
	}

	private JButton getBtResultadosEntrenados() {
		if (btResultadosEntrenados == null) {
			btResultadosEntrenados = new JButton("Test entrenados");
			btResultadosEntrenados.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					activaVentanaEntrenados();
				}
			});
			btResultadosEntrenados.setEnabled(false);
			btResultadosEntrenados.setMnemonic('e');
			btResultadosEntrenados.setBounds(228, 191, 141, 89);
		}
		return btResultadosEntrenados;
	}

	private void activaVentanaEntrenados() {
		VentanaEntrenados ve = new VentanaEntrenados(this);
		ve.setVisible(true);
	}

	public VentanaPrincipal getVp() {
		return vp;
	}
}

