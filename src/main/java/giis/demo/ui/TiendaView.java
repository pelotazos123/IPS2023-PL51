package giis.demo.ui;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;

public class TiendaView {
	
	private JFrame frame;
	private JPanel contentPanel;
	private JLabel lblTienda;
	private JLabel lblEquipacion;
	private JLabel lblChandal;
	private JLabel lblChaqueta;
	private JComboBox<String> cbTallaEq;
	private JComboBox<String> cbTallaChd;
	private JComboBox<String> cbTallaChq;
	private JComboBox<String> cbUdsEq;
	private JComboBox<String> cbUdsChd;
	private JComboBox<String> cbUdsChq;

	public TiendaView() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Tienda");
		frame.setName("Tienda");
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPanel);
		contentPanel.setLayout(null);

		contentPanel.add(getLblTienda());
		contentPanel.add(getLblEquipacion());
		contentPanel.add(getLblChandal());
		contentPanel.add(getLblChaqueta());
		contentPanel.add(getCbTallaEq());
		contentPanel.add(getCbTallaChd());
		contentPanel.add(getCbTallaChq());
		contentPanel.add(getCbUdsEq());
		contentPanel.add(getCbUdsChd());
		contentPanel.add(getCbUdsChq());
		
		JButton btnComprar = new JButton("Comprar");
		btnComprar.setBackground(new Color(0, 128, 0));
		btnComprar.setForeground(new Color(255, 255, 255));
		btnComprar.setBounds(300, 373, 189, 45);
		contentPanel.add(btnComprar);
		
	}

	public JFrame getFrame() {
		return frame;
	}

	public JLabel getLblTienda() {
		if (lblTienda == null) {
			lblTienda = new JLabel("Tienda");
			lblTienda.setFont(new Font("Tahoma", Font.PLAIN, 25));
			lblTienda.setBounds(349, 51, 84, 45);
		}
		return lblTienda;
	}

	public JLabel getLblEquipacion() {
		if (lblEquipacion == null) {
			lblEquipacion = new JLabel("Equipación:");
			lblEquipacion.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblEquipacion.setBounds(234, 147, 84, 29);
		}
		return lblEquipacion;
	}

	public JLabel getLblChandal() {
		if (lblChandal == null) {
			lblChandal = new JLabel("Chandal:");
			lblChandal.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblChandal.setBounds(234, 207, 84, 29);
		}
		return lblChandal;
	}
	
	public JLabel getLblChaqueta() {
		if (lblChaqueta == null) {
			lblChaqueta = new JLabel("Chaqueta:");
			lblChaqueta.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblChaqueta.setBounds(234, 265, 84, 29);
		}
		return lblChaqueta;
	}

	public JComboBox<String> getCbTallaEq() {
		if (cbTallaEq == null) {
			cbTallaEq = new JComboBox<String>();
			cbTallaEq.setModel(new DefaultComboBoxModel<String>(new String[] {"XS","S","M","L","XL","XXL"}));
			cbTallaEq.setBounds(378, 152, 55, 22);
		}
		return cbTallaEq;
	}

	public JComboBox<String> getCbTallaChd() {
		if (cbTallaChd == null) {
			cbTallaChd = new JComboBox<String>();
			cbTallaChd.setModel(new DefaultComboBoxModel<String>(new String[] {"XS","S","M","L","XL","XXL"}));
			cbTallaChd.setBounds(378, 212, 55, 22);
		}
		return cbTallaChd;
	}

	public JComboBox<String> getCbTallaChq() {
		if (cbTallaChq == null) {
			cbTallaChq = new JComboBox<String>();
			cbTallaChq.setModel(new DefaultComboBoxModel<String>(new String[] {"XS","S","M","L","XL","XXL"}));
			cbTallaChq.setBounds(378, 270, 55, 22);
		}
		return cbTallaChq;
	}
	
	private JComboBox<String> getCbUdsEq() {
		if (cbUdsEq == null) {
			cbUdsEq = new JComboBox<String>();
			cbUdsEq.setModel(new DefaultComboBoxModel<String>(new String[] {"0","1","2","3","4","5","6","7","8","9"}));
			cbUdsEq.setBounds(479, 152, 55, 22);
		}
		return cbUdsEq;
	}
	private JComboBox<String> getCbUdsChd() {
		if (cbUdsChd == null) {
			cbUdsChd = new JComboBox<String>();
			cbUdsChd.setModel(new DefaultComboBoxModel<String>(new String[] {"0","1","2","3","4","5","6","7","8","9"}));
			cbUdsChd.setBounds(479, 212, 55, 22);
		}
		return cbUdsChd;
	}
	private JComboBox<String> getCbUdsChq() {
		if (cbUdsChq == null) {
			cbUdsChq = new JComboBox<String>();
			cbUdsChq.setModel(new DefaultComboBoxModel<String>(new String[] {"0","1","2","3","4","5","6","7","8","9"}));
			cbUdsChq.setBounds(479, 270, 55, 22);
		}
		return cbUdsChq;
	}
}
