package giis.demo.ui;

import javax.swing.JPanel;

import com.toedter.calendar.JCalendar;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.util.Date;

import giis.demo.business.InstalacionController;
import giis.demo.business.ReservationController;
import giis.demo.model.Instalacion;
import giis.demo.model.Reserva;
import giis.demo.util.Database;

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
	private ReservationController reMan;
	private String chosenDay;
	private Database db;
	
	public VentanaReservas(Database db) {
		this.db = db;
		reMan = new ReservationController(this.db);
		chosenDay = "";
		setTitle("Reservas");
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
			calendar.getDayChooser().setAlwaysFireDayProperty(false);
			calendar.getDayChooser().setDayBordersVisible(true);
			calendar.setWeekOfYearVisible(false);
			calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					dayPicker(evt);
				}
			});
			
		}
		return calendar;
	}
	
	private void dayPicker(PropertyChangeEvent evt) {
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
		chosenDay = formatDate.format(calendar.getDate());
		getPnSelector().setVisible(true);
		System.out.println(chosenDay);
		generaHoras();
	}
	
	@SuppressWarnings("deprecation")
	private void reservar() {
		int selectedVal = getList().getSelectedIndex();
		String hora = modeloListaHoras.get(selectedVal);
		Date selectedDate = getCalendar().getDate();
		
		selectedDate.setHours(Integer.parseInt(hora.split(":")[0]));
		selectedDate.setMinutes(0);
		
		String pattern = "dd/MM/yyyy - HH:mm";
		SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
		String dateS = formatDate.format(selectedDate);

		Instalacion instalacion = ((Instalacion)getCbInstalaciones().getSelectedItem());
		reMan.reservar(selectedDate, dateS, instalacion.getCode());
		
		JOptionPane.showMessageDialog(null, "Reserva confirmada de " + instalacion.toString() +": " + dateS);
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
		}
		return list;
	}

	private void generaHoras() {
		modeloListaHoras.removeAllElements();
		for (int i = 9; i <= 23; i++) {
			modeloListaHoras.addElement(i+":00");
		}		
		
		for (Reserva reserva : reMan.getReservas()) {			
			if (reserva.getFecha().trim().equals(chosenDay)
					&& modeloListaHoras.contains(reserva.getHora().trim())
							&& reserva.getInstalacionId().trim().equals(((Instalacion)getCbInstalaciones().getSelectedItem()).getCode().toString())) {
				System.out.println("Ya hay una reserva");
				modeloListaHoras.removeElement(reserva.getHora().trim());
			}
		}
	}
	
	private JComboBox<Instalacion> getCbInstalaciones() {
		if (cbInstalaciones == null) {
			cbInstalaciones = new JComboBox<Instalacion>();
			cbInstalaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					generaHoras();
				}
			});
			cbInstalaciones.setModel(new DefaultComboBoxModel<Instalacion>(InstalacionController.getInstalaciones(db)));
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
