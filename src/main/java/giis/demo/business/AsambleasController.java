package giis.demo.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;

import javax.swing.JOptionPane;

import giis.demo.ui.AsambleasView;
import giis.demo.util.SwingUtil;

public class AsambleasController {

	private AsambleasModel model;
	private AsambleasView view;
	
	public AsambleasController(AsambleasModel model, AsambleasView view) {
		this.model = model;
		this.view = view;
		
		this.initView();
	}
	
	public void initView() {
		view.getFrame().setVisible(true); 
	}
	
	public void initController() {
		view.getBtnConvocar().addActionListener(
				e -> SwingUtil.exceptionWrapper(() -> addAsambleaExtraordinaria()));
		
		view.getBtnOrdinaria().addActionListener(
				e -> SwingUtil.exceptionWrapper(() -> addAsambleaOrdinaria()));
	}
	
	private void addAsamblea(String type, String announcement, String date1, String date2) {
		model.addAsamblea(type, announcement, date1, date2);
		JOptionPane.showMessageDialog(null, "Se ha convocado correctamente.", "Correcto", JOptionPane.INFORMATION_MESSAGE);
		view.getFrame().dispose();
	}
	
	private void addAsambleaOrdinaria() {
		Calendar date = Calendar.getInstance();
		
		date.add(Calendar.MONTH, 1);
		date.set(Calendar.DAY_OF_MONTH, 1);
		
		addAsamblea("Ordinaria", new SimpleDateFormat("dd-MM-yyyy").format(date.getTime()), "8.00", "8.30");
	}
	
	private void addAsambleaExtraordinaria() {
		String date = view.getTxtFecha().getText();
		String conv1 = view.getTxtConv1().getText();
		String conv2 = view.getTxtConv2().getText();
		
		if(comprobarFecha(date) && comprobarConvocatorias(conv1, conv2)) {
			addAsamblea("Ordinaria", date, conv1, conv2);
		}
		
		
	}
	
	// Metodos privados de comprobacion
	private boolean comprobarFecha(String date) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
		formatoFecha.setLenient(false);

		try {
			formatoFecha.parse(date);
			
			return true;
			
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Formato de fecha: \"dd-MM-yyyy\"", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	private boolean comprobarConvocatorias(String conv1, String conv2) {
		if(comprobarHora(conv1) && comprobarHora(conv2)) {
            LocalTime horaC1 = LocalTime.of(Integer.parseInt(conv1.split("\\.")[0]), Integer.parseInt(conv1.split("\\.")[1]));
            LocalTime horaC2 = LocalTime.of(Integer.parseInt(conv2.split("\\.")[0]), Integer.parseInt(conv2.split("\\.")[1]));
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
		String[] partes = hora.split("\\.");

        if (partes.length != 2) {
        	JOptionPane.showMessageDialog(null, "Formato de convocatorias: \"HH.mm\".", "Error", JOptionPane.ERROR_MESSAGE);
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
	
}
