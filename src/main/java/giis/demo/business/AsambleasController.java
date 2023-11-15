package giis.demo.business;

import java.awt.CardLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;

import javax.mail.MessagingException;
import javax.swing.JOptionPane;

import giis.demo.model.loggin.Correo;
import giis.demo.ui.AsambleasView;
import giis.demo.util.SwingUtil;

public class AsambleasController {

	private AsambleasModel model;
	private AsambleasView view;
	
	private Correo correo = new Correo();
	
	public AsambleasController(AsambleasModel model, AsambleasView view) {
		this.model = model;
		this.view = view;
		
		this.initView();
	}
	
	public void initView() {
		view.getFrame().setVisible(true); 
	}
	
	public void initController() {
		view.getBtnConvocarOrd().addActionListener(
				e -> SwingUtil.exceptionWrapper(() -> addAsambleaOrdinaria()));
		
		view.getBtnConvocarExt().addActionListener(
				e -> SwingUtil.exceptionWrapper(() -> addAsambleaExtraordinaria()));
	}
	
	private void addAsamblea(String type, String announcement, String date1, String date2, String orderOfDay, String acta) {
		if(hasAsamblea(type, announcement)) {
			JOptionPane.showMessageDialog(null, "Ya existe una asamblea de este tipo el mismo dia.", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(orderOfDay.isBlank()) {
			JOptionPane.showMessageDialog(null, "Orden del día necesaria.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else if(acta.isBlank()) {
			JOptionPane.showMessageDialog(null, "Acta anterior necesaria.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else {
			model.addAsamblea(type, announcement, date1, date2, orderOfDay, acta);
			enviarAsamblea(type, announcement, date1, date2, orderOfDay, acta);
			JOptionPane.showMessageDialog(null, "Se ha convocado correctamente.", "Correcto", JOptionPane.INFORMATION_MESSAGE);			
			((CardLayout)view.getFrame().getContentPane().getLayout()).show(view.getFrame().getContentPane(),"EleccionAsambleas");
		}
	}

	private boolean hasAsamblea(String type, String announcement) {
		return model.hasAsamblea(type, announcement);
	}
	
	private void addAsambleaOrdinaria() {
		Calendar date = Calendar.getInstance();
		
		int year = Integer.parseInt(view.getCbYear().getItemAt(view.getCbYear().getSelectedIndex()));
		int month = view.getCbMonth().getSelectedIndex();
		
		date.set(Calendar.YEAR, year);
		date.set(Calendar.MONTH, month);
		
		if(date.before(view.getDate())) {
			JOptionPane.showMessageDialog(null, "Fecha incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else {
			date.set(Calendar.DAY_OF_MONTH, 1);
			if(date.get(Calendar.MONTH) == view.getDate().get(Calendar.MONTH))
				date.add(Calendar.MONTH, 1);
			addAsamblea("Ordinaria", new SimpleDateFormat("yyyy-MM-dd").format(date.getTime()), "8:00", "8:30", view.getTxtOrdenDiaOrd().getText(), view.getTxtActaOrd().getText());
		}
	}
	
	private void addAsambleaExtraordinaria() {
		String date = view.getTxtFecha().getText();
		String conv1 = view.getTxtConv1().getText();
		String conv2 = view.getTxtConv2().getText();
		String orderOfDay = view.getTxtOrdenDiaExt().getText();
		String acta = view.getTxtActaExt().getText();
		
		if(comprobarFecha(date) && comprobarConvocatorias(conv1, conv2)) 
			addAsamblea("Extraordinaria", date, conv1, conv2, orderOfDay, acta);
		
		
	}
	
	// Metodos privados de comprobacion
	private boolean comprobarFecha(String date) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		formatoFecha.setLenient(false);

		try {
			formatoFecha.parse(date);
			
			Calendar dateC = Calendar.getInstance();
			dateC.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]));
			dateC.set(Calendar.MONTH, Integer.parseInt(date.split("-")[1]));
			dateC.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.split("-")[2]));
			
			if(dateC.before(view.getDate())) {
				JOptionPane.showMessageDialog(null, "Fecha incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
			return true;
			
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Formato de fecha: \"yyyy-MM-dd\"", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	private boolean comprobarConvocatorias(String conv1, String conv2) {
		if(comprobarHora(conv1) && comprobarHora(conv2)) {
            LocalTime horaC1 = LocalTime.of(Integer.parseInt(conv1.split("\\:")[0]), Integer.parseInt(conv1.split("\\:")[1]));
            LocalTime horaC2 = LocalTime.of(Integer.parseInt(conv2.split("\\:")[0]), Integer.parseInt(conv2.split("\\:")[1]));
            if(horaC2.isAfter(horaC1)) {
            	return true;
            }
            else {
            	JOptionPane.showMessageDialog(null, "La primera convocatoria tiene que ser antes de la segunda.", "Error", JOptionPane.ERROR_MESSAGE);
            	return false;
            }
		}
		
		return false;
	}
	private boolean comprobarHora(String hora) {
		String[] partes = hora.split("\\:");

        if (partes.length != 2) {
        	JOptionPane.showMessageDialog(null, "Formato de convocatorias: \"HH:mm\".", "Error", JOptionPane.ERROR_MESSAGE);
            return false; 
        }

        try {
            int horas = Integer.parseInt(partes[0]);
            int minutos = Integer.parseInt(partes[1]);

            if (horas >= 0 && horas <= 23)
            	if (minutos >= 0 && minutos <= 59)
            		if (partes[1].length() == 2)
                        return true;
            		else
            			JOptionPane.showMessageDialog(null, "Los minutos deben expresarse con dos cifras", "Error", JOptionPane.ERROR_MESSAGE);
            	else
            		JOptionPane.showMessageDialog(null, "Los minutos deben estar en el rango [0-59]", "Error", JOptionPane.ERROR_MESSAGE);
            else
            	JOptionPane.showMessageDialog(null, "Las horas deben estar en el rango [0-23]", "Error", JOptionPane.ERROR_MESSAGE);
            
        } catch (NumberFormatException e) {
        	return false;
        }

        return false;
	}
	
	private void enviarAsamblea(String type, String announcement, String date1, String date2, String orderOfDay, String acta) {
		
		String asamblea = "asamblea " + type + " date: " + announcement + ", conv1: "+ date1 + ", conv2: "+ date2 + ", order of the day: "+ orderOfDay+ ", acta: "+ acta;
		
		model.getCorreos().parallelStream().forEach(e -> {
			try {
				correo.mandarCorreo((String) e[0], asamblea);
			} catch (MessagingException e1) {
				e1.printStackTrace();
			}
		});
		
	}
	
}
