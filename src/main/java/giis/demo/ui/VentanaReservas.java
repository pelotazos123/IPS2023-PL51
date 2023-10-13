package giis.demo.ui;

import javax.swing.JPanel;

import com.toedter.calendar.JCalendar;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.util.Date;

import giis.demo.model.Instalacion;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class VentanaReservas extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JPanel pnPrincipal;
	private JPanel pnSelector;
	private JList<String> list;
	private JCalendar calendar;
	private JComboBox<Instalacion> cbInstalaciones;
	private DefaultListModel<String> modeloListaHoras;
	private JButton btnReserva;
	
	public VentanaReservas() {
		setTitle("fsdjf");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 870, 618);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPnPrincipal(), BorderLayout.CENTER);
		getContentPane().add(getPnSelector(), BorderLayout.EAST);
		
	}
	
	private JPanel getPnPrincipal() {
		if (pnPrincipal == null) {
			pnPrincipal = new JPanel();
			pnPrincipal.setBackground(Color.WHITE);
			pnPrincipal.setLayout(new BorderLayout(0, 0));
			pnPrincipal.add(getCalendar(), BorderLayout.CENTER);
			//pnPrincipal.add(getCbInstalaciones(), BorderLayout.NORTH);
		}
		return pnPrincipal;
	}
	
	private JPanel getPnSelector() {
		if (pnSelector == null) {
			pnSelector = new JPanel();
			pnSelector.setVisible(false);
			pnSelector.setLayout(new BorderLayout(0, 0));
			pnSelector.add(getList(), BorderLayout.CENTER);
			pnSelector.add(getCbInstalaciones(), BorderLayout.NORTH);
			pnSelector.setPreferredSize(new Dimension(200, 100));
			pnSelector.add(getBtnReserva(), BorderLayout.SOUTH);
		}
		return pnSelector;
	}
	
	private JCalendar getCalendar() {
		if(calendar == null) {
			calendar = new JCalendar();
			calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					getPnSelector().setVisible(true);
				}
			});
			
		}
		return calendar;
	}
	
	@SuppressWarnings("deprecation")
	private void reservar() {
		int selectedVal = getList().getSelectedIndex();
		String hora = modeloListaHoras.get(selectedVal);
		modeloListaHoras.remove(selectedVal);
		System.out.println("Hora seleccionada: " + hora);
		Date selectedDate = getCalendar().getDate();
		selectedDate.setHours(Integer.parseInt(hora.split(":")[0]));
		selectedDate.setMinutes(0);
		selectedDate.setSeconds(0);
		System.out.println(selectedDate.toString());
		JOptionPane.showMessageDialog(null, "Reserva confirmada de " + getCbInstalaciones().getSelectedItem().toString() +": " + selectedDate);
		this.dispose();
	}

	private JList<String> getList() {
		if (list == null) {
			modeloListaHoras = new DefaultListModel<String>();
			list = new JList<String>();
			list.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					getBtnReserva().setEnabled(true);
				}
			});
			list.setModel(modeloListaHoras);
			if (modeloListaHoras.isEmpty()) {
				for (int i = 9; i <= 23; i++) {
					modeloListaHoras.addElement(i+":00");
				}
			}
		}
		return list;
	}
	private JComboBox<Instalacion> getCbInstalaciones() {
		if (cbInstalaciones == null) {
			Instalacion[] instalaciones = new Instalacion[5];
			for (int i = 0; i < 5; i++) 
				instalaciones[i] = new Instalacion("Instalacion"+i, "1341");
			cbInstalaciones = new JComboBox<Instalacion>();
			cbInstalaciones.setModel(new DefaultComboBoxModel<Instalacion>(instalaciones));
		}
		return cbInstalaciones;
	}
	private JButton getBtnReserva() {
		if (btnReserva == null) {
			btnReserva = new JButton("Reservar");
			btnReserva.setEnabled(false);
			btnReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					reservar();
				}
			});
		}
		return btnReserva;
	}
}
