package giis.demo.ui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public class ActasView {

	private JFrame frame;
	private JPanel contentPanel;
	private JButton btnAñadirActa;
	private JScrollPane spAsambleas;
	private JTable tabAsambleas;

	public ActasView() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Actas");
		frame.setName("Actas");
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPanel);
		contentPanel.setLayout(null);
		
		contentPanel.add(getBtnAñadirActa());
		contentPanel.add(getSpAsambleas());
	}
	
	public JFrame getFrame() { 
		return this.frame; 
	}
	
	public JButton getBtnAñadirActa() {
		if (btnAñadirActa == null) {
			btnAñadirActa = new JButton("Añadir acta");
			btnAñadirActa.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					((CardLayout)contentPanel.getLayout()).show(contentPanel,"FormularioAsambleaOrd");
				}
			});
			btnAñadirActa.setBounds(303, 390, 176, 60);
		}
		return btnAñadirActa;
	}
	
	private JScrollPane getSpAsambleas() {
		if (spAsambleas == null) {
			spAsambleas = new JScrollPane();
			spAsambleas.setViewportBorder(new EmptyBorder(1, 1, 1, 1));
			spAsambleas.setBounds(33, 35, 723, 330);
			spAsambleas.setViewportView(getTabAsambleas());
		}
		return spAsambleas;
	}
	
	public JTable getTabAsambleas() {
		if (tabAsambleas == null) {
			tabAsambleas = new JTable();
			tabAsambleas.setDefaultEditor(Object.class, null);
		}
		return tabAsambleas;
	}
	
	
}
