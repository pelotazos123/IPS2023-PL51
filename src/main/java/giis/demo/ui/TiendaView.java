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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
	private JTextField txtUdsEq;
	private JTextField txtUdsChd;
	private JTextField txtUdsChq;
	private JLabel lblPrecioEq;
	private JLabel lblPrecioChd;
	private JLabel lblPrecioChq;
	private JLabel lblTotal;
	private JLabel lblPrecioTotal;
	private JButton btnComprar;

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
		
		contentPanel.add(getTxtUdsEq());
		contentPanel.add(getTxtUdsChd());
		contentPanel.add(getTxtUdsChq());
		contentPanel.add(getLblPrecioEq());
		contentPanel.add(getLblPrecioChd());
		contentPanel.add(getLblPrecioChq());
		contentPanel.add(getLblTotal());
		contentPanel.add(getLblPrecioTotal());

		contentPanel.add(getBtnComprar());
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
			cbTallaEq.setModel(new DefaultComboBoxModel<String>(new String[] {"S","M","L","XL"}));
			cbTallaEq.setBounds(378, 152, 55, 22);
		}
		return cbTallaEq;
	}

	public JComboBox<String> getCbTallaChd() {
		if (cbTallaChd == null) {
			cbTallaChd = new JComboBox<String>();
			cbTallaChd.setModel(new DefaultComboBoxModel<String>(new String[] {"S","M","L","XL"}));
			cbTallaChd.setBounds(378, 212, 55, 22);
		}
		return cbTallaChd;
	}

	public JComboBox<String> getCbTallaChq() {
		if (cbTallaChq == null) {
			cbTallaChq = new JComboBox<String>();
			cbTallaChq.setModel(new DefaultComboBoxModel<String>(new String[] {"S","M","L","XL"}));
			cbTallaChq.setBounds(378, 270, 55, 22);
		}
		return cbTallaChq;
	}
	private JTextField getTxtUdsEq() {
		if (txtUdsEq == null) {
			txtUdsEq = new JTextField();
			txtUdsEq.setHorizontalAlignment(SwingConstants.CENTER);
			txtUdsEq.setText("0");
			txtUdsEq.setBounds(479, 152, 55, 22);
			txtUdsEq.setColumns(10);
		}
		return txtUdsEq;
	}
	private JTextField getTxtUdsChd() {
		if (txtUdsChd == null) {
			txtUdsChd = new JTextField();
			txtUdsChd.setHorizontalAlignment(SwingConstants.CENTER);
			txtUdsChd.setText("0");
			txtUdsChd.setColumns(10);
			txtUdsChd.setBounds(479, 213, 55, 22);
		}
		return txtUdsChd;
	}
	private JTextField getTxtUdsChq() {
		if (txtUdsChq == null) {
			txtUdsChq = new JTextField();
			txtUdsChq.setHorizontalAlignment(SwingConstants.CENTER);
			txtUdsChq.setText("0");
			txtUdsChq.setColumns(10);
			txtUdsChq.setBounds(479, 271, 55, 22);
		}
		return txtUdsChq;
	}
	public JLabel getLblPrecioEq() {
		if (lblPrecioEq == null) {
			lblPrecioEq = new JLabel("");
			lblPrecioEq.setBounds(569, 152, 63, 23);
		}
		return lblPrecioEq;
	}
	public JLabel getLblPrecioChd() {
		if (lblPrecioChd == null) {
			lblPrecioChd = new JLabel("");
			lblPrecioChd.setBounds(569, 212, 63, 23);
		}
		return lblPrecioChd;
	}
	public JLabel getLblPrecioChq() {
		if (lblPrecioChq == null) {
			lblPrecioChq = new JLabel("");
			lblPrecioChq.setBounds(569, 270, 63, 23);
		}
		return lblPrecioChq;
	}
	private JLabel getLblTotal() {
		if (lblTotal == null) {
			lblTotal = new JLabel("Total:");
			lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblTotal.setBounds(300, 333, 55, 29);
		}
		return lblTotal;
	}
	public JLabel getLblPrecioTotal() {
		if (lblPrecioTotal == null) {
			lblPrecioTotal = new JLabel("");
			lblPrecioTotal.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblPrecioTotal.setBounds(365, 333, 84, 29);
		}
		return lblPrecioTotal;
	}
	public JButton getBtnComprar() {
		if (btnComprar == null) {
			btnComprar = new JButton("Comprar");
			btnComprar.setBackground(new Color(0, 128, 0));
			btnComprar.setForeground(new Color(255, 255, 255));
			btnComprar.setBounds(300, 373, 189, 45);
		}
		return btnComprar;
	}
	
}
