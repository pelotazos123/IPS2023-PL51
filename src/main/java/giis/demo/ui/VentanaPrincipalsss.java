package giis.demo.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipalsss extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipalsss frame = new VentanaPrincipalsss();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaPrincipalsss() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 772, 501);
		pn = new JPanel();
		pn.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pn);
		pn.setLayout(new CardLayout(0, 0));
		
		JPanel pnInicio = new JPanel();
		pn.add(pnInicio, "name_2732719017400");
		pnInicio.setLayout(null);
		
		JButton btnDirectivo = new JButton("Directivo");
		btnDirectivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDirectivo.setBounds(157, 176, 198, 65);
		pnInicio.add(btnDirectivo);
		
		JButton btnSocio = new JButton("Socio");
		btnSocio.setBounds(410, 176, 186, 65);
		pnInicio.add(btnSocio);
		
		JPanel pnPantallaPrincipalSocio = new JPanel();
		pn.add(pnPantallaPrincipalSocio, "name_3481982160900");
		pnPantallaPrincipalSocio.setLayout(null);
		
		JLabel lbProvisionalSocio = new JLabel("Pantalla principal del socio");
		lbProvisionalSocio.setBounds(276, 160, 225, 105);
		pnPantallaPrincipalSocio.add(lbProvisionalSocio);
		
		JPanel pnPantallaPrincipalDirectivo = new JPanel();
		pn.add(pnPantallaPrincipalDirectivo, "name_3780487682200");
		pnPantallaPrincipalDirectivo.setLayout(null);
		
		JLabel lbProvisionalDirectivo = new JLabel("Pantalla principal del directivo");
		lbProvisionalDirectivo.setBounds(293, 178, 152, 32);
		pnPantallaPrincipalDirectivo.add(lbProvisionalDirectivo);
	}

}
