package giis.demo.ui;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.demo.business.AsambleasController;
import giis.demo.business.AsambleasModel;
import giis.demo.business.AsambleasView;
import giis.demo.util.Database;

public class Ventanaprincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pn;
	private JPanel pnInicio;
	private JButton btnDirectivo;
	private JButton btnSocio;
	private JPanel pnPrincipalSocio;
	private JPanel pnPrincipalDirectivo;
	private JLabel lbProvisionalSocio;
	private JLabel lbProvisionalDirectivo;
	private JButton btnAsambleas;


	public static void main(String[] args) {
		
		Database db=new Database();
		db.createDatabase(false);
		db.loadDatabase();
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Ventanaprincipal vp = new Ventanaprincipal();
					vp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ventanaprincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 381);
		setLocationRelativeTo(null);
		pn = new JPanel();
		pn.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pn);
		pn.setLayout(new CardLayout(0, 0));
		pn.add(getPnInicio(), "inicio");
		pn.add(getPnPrincipalSocio(), "PrincipalSocio");
		pn.add(getPnPrincipalDirectivo(), "PrincipalDirectivo");
	}

	private JPanel getPnInicio() {
		if (pnInicio == null) {
			pnInicio = new JPanel();
			pnInicio.setLayout(null);
			pnInicio.add(getBtnDirectivo());
			pnInicio.add(getBtnSocio());
		}
		return pnInicio;
	}
	private JButton getBtnDirectivo() {
		if (btnDirectivo == null) {
			btnDirectivo = new JButton("Directivo");
			btnDirectivo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					((CardLayout)pn.getLayout()).show(pn,"PrincipalDirectivo");
				}
			});
			btnDirectivo.setBounds(44, 125, 198, 65);
		}
		return btnDirectivo;
	}
	private JButton getBtnSocio() {
		if (btnSocio == null) {
			btnSocio = new JButton("Socio");
			btnSocio.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					((CardLayout)pn.getLayout()).show(pn,"PrincipalSocio");
				}
			});
			btnSocio.setBounds(293, 125, 186, 65);
		}
		return btnSocio;
	}
	private JPanel getPnPrincipalSocio() {
		if (pnPrincipalSocio == null) {
			pnPrincipalSocio = new JPanel();
			pnPrincipalSocio.setLayout(null);
			pnPrincipalSocio.add(getLbProvisionalSocio());
		}
		return pnPrincipalSocio;
	}
	private JPanel getPnPrincipalDirectivo() {
		if (pnPrincipalDirectivo == null) {
			pnPrincipalDirectivo = new JPanel();
			pnPrincipalDirectivo.setLayout(null);
			pnPrincipalDirectivo.add(getLbProvisionalDirectivo());
			pnPrincipalDirectivo.add(getBtnAsambleas());
		}
		return pnPrincipalDirectivo;
	}
	private JLabel getLbProvisionalSocio() {
		if (lbProvisionalSocio == null) {
			lbProvisionalSocio = new JLabel("Pantalla principal del socio");
			lbProvisionalSocio.setBounds(183, 122, 225, 34);
		}
		return lbProvisionalSocio;
	}
	private JLabel getLbProvisionalDirectivo() {
		if (lbProvisionalDirectivo == null) {
			lbProvisionalDirectivo = new JLabel("Pantalla principal del directivo");
			lbProvisionalDirectivo.setBounds(10, 11, 223, 32);
		}
		return lbProvisionalDirectivo;
	}
	private JButton getBtnAsambleas() {
		if (btnAsambleas == null) {
			btnAsambleas = new JButton("Convocar asamblea");
			btnAsambleas.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AsambleasView view = new AsambleasView();
					AsambleasModel model = new AsambleasModel();
					AsambleasController controller = new AsambleasController(model,view);
					
					controller.initController();
				}
			});
			btnAsambleas.setBounds(48, 80, 185, 40);
		}
		return btnAsambleas;
	}

}
