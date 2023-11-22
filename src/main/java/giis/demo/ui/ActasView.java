package giis.demo.ui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ActasView {

	private JFrame frame;
	private JPanel contentPanel;
	private JButton btnAñadirActa;
	private JScrollPane spAsambleas;
	private JTable tabAsambleas;
	private JTextPane txtActa;
	private JScrollPane spActa;

	public ActasView() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Actas");
		frame.setName("Actas");
		frame.setBounds(100, 100, 900, 650);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPanel);
		contentPanel.setLayout(null);
		
		contentPanel.add(getBtnAñadirActa());
		contentPanel.add(getSpAsambleas());
		contentPanel.add(getSpActa());
	}
	
	public JFrame getFrame() { 
		return this.frame; 
	}
	
	public JButton getBtnAñadirActa() {
		if (btnAñadirActa == null) {
			btnAñadirActa = new JButton("Añadir acta");
			btnAñadirActa.setBounds(351, 528, 176, 60);
		}
		return btnAñadirActa;
	}
	
	private JScrollPane getSpAsambleas() {
		if (spAsambleas == null) {
			spAsambleas = new JScrollPane();
			spAsambleas.setViewportBorder(new EmptyBorder(1, 1, 1, 1));
			spAsambleas.setBounds(33, 35, 815, 200);
			spAsambleas.setViewportView(getTabAsambleas());
		}
		return spAsambleas;
	}
	
	public JTable getTabAsambleas() {
		if (tabAsambleas == null) {
			tabAsambleas = new JTable();
			tabAsambleas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabAsambleas.setDefaultEditor(Object.class, null);
			tabAsambleas.getTableHeader().setReorderingAllowed(false);
		}
		return tabAsambleas;
	}
	
	private JScrollPane getSpActa() {
		if (spActa == null) {
			spActa = new JScrollPane();
			spActa.setBounds(43, 246, 791, 257);
			spActa.setViewportView(getTxtActa());
		}
		return spActa;
	}
	public JTextPane getTxtActa() {
		if (txtActa == null) {
			txtActa = new JTextPane();
			txtActa.setBorder(new LineBorder(new Color(0, 0, 0)));
		}
		return txtActa;
	}
	
	
}
