package giis.demo.ui;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import com.toedter.calendar.JDayChooser;

import giis.demo.model.Instalacion;

import javax.swing.JList;
import javax.swing.JComboBox;

public class VentanaReservas extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JPanel pnPrincipal;
	private JPanel panel;
	private JDayChooser dayChooser;
	private JList list;
	private JComboBox comboBox;
	
	public VentanaReservas() {
		setTitle("fsdjf");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 864, 538);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPnPrincipal(), BorderLayout.CENTER);
		getContentPane().add(getPanel(), BorderLayout.EAST);
		
	}
	
	private JPanel getPnPrincipal() {
		if (pnPrincipal == null) {
			pnPrincipal = new JPanel();
			pnPrincipal.setBackground(Color.WHITE);
			pnPrincipal.setLayout(new BorderLayout(0, 0));
			pnPrincipal.add(getDayChooser(), BorderLayout.CENTER);
		}
		return pnPrincipal;
	}
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getList(), BorderLayout.CENTER);
			panel.add(getComboBox(), BorderLayout.NORTH);
		}
		return panel;
	}
	private JDayChooser getDayChooser() {
		if (dayChooser == null) {
			dayChooser = new JDayChooser();
		}
		return dayChooser;
	}
	private JList<T> getList() {
		if (list == null) {
			list = new JList<T>();
		}
		return list;
	}
	private JComboBox<Instalacion> getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox<Instalacion>();
		}
		return comboBox;
	}
}
