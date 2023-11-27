package giis.demo.business;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;

import giis.demo.model.CrearLicencias.servicio.TramitarLicencia;
import giis.demo.ui.TiendaView;
import giis.demo.util.SwingUtil;
import giis.demo.util.Util;

public class TiendaController {

	private TramitarLicencia tramitarLicencia;
	private TiendaModel model;
	private TiendaView view;
	private RecibosModel recibos;
	
	public TiendaController(TiendaModel m, TiendaView v, RecibosModel recibos, TramitarLicencia tramitarLicencia) {
		this.model = m;
		this.view = v;
		this.recibos = recibos;
		this.tramitarLicencia = tramitarLicencia;
	
		this.initView();
	}

	private void initView() {
		view.getFrame().setVisible(true); 
	}

	public void initController() {
		getPrices();
		actualizarPrecio();
		
		view.getBtnTotal().addActionListener(
				e -> SwingUtil.exceptionWrapper(() -> actualizarPrecio()));
		
		view.getBtnComprar().addActionListener(
				e -> SwingUtil.exceptionWrapper(() -> comprar()));
	}

	private void getPrices() {
		view.getLblPrecioEq().setText(model.getPrice("equipacion") + "€");
		view.getLblPrecioChd().setText(model.getPrice("chandal") + "€");
		view.getLblPrecioChq().setText(model.getPrice("chaqueta") + "€");
	}

	private void actualizarPrecio() {
		if(comprobarUnidades()) {
			view.getLblPrecioTotal().setText(precioTotal() + "€");
		}
	}
	
	private void comprar() {
		if(comprobarUnidades() && comprobarPrecio()) {
			String iban;
			
			Calendar v_date = Calendar.getInstance();
			v_date.set(Calendar.YEAR, view.getDate().get(Calendar.YEAR));
			v_date.set(Calendar.MONTH, view.getDate().get(Calendar.MONTH));
			v_date.add(Calendar.MONTH, 1);
			v_date.set(Calendar.DAY_OF_MONTH, 1);
			String value_date = new SimpleDateFormat("yyyy-MM-dd").format(v_date.getTime());

			Calendar c_date = Calendar.getInstance();
			c_date.set(Calendar.YEAR, view.getDate().get(Calendar.YEAR));
			c_date.set(Calendar.MONTH, view.getDate().get(Calendar.MONTH));
			c_date.add(Calendar.MONTH, 1);
			c_date.set(Calendar.DAY_OF_MONTH, 15);
			String charge_date = new SimpleDateFormat("yyyy-MM-dd").format(c_date.getTime());
			
			if(tramitarLicencia.esDirectivo()) {
				iban = tramitarLicencia.getDirectivo().getNumeroIban();
			}
			else {
				iban = tramitarLicencia.getSocio().getNumeroIban();
			}
			double precio = precioTotal();
			int number = recibos.getLastNumber() + 1;
			recibos.generateRecibo(iban, number, precio, value_date, charge_date, "Compra", "Domiciliado", "Pendiente");
			saveRecibos();
			JOptionPane.showMessageDialog(null, "Compra realizada correctamente.", "Recibos", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private boolean comprobarPrecio() {
		if(precioTotal() > 0)
			return true;
		JOptionPane.showMessageDialog(null, "Seleccione articulos para comprar.", "Recibos", JOptionPane.INFORMATION_MESSAGE);
		return false;
	}

	private boolean comprobarUnidades() {
		String udsEq = view.getTxtUdsEq().getText();
		String udsChd = view.getTxtUdsChd().getText();
		String udsChq = view.getTxtUdsChq().getText();
		
		try {
	        int udsEqInt = Integer.parseInt(udsEq);
	        int udsChdInt = Integer.parseInt(udsChd);
	        int udsChqInt = Integer.parseInt(udsChq);
	        
	        if(udsEqInt < 0 || udsChdInt < 0 || udsChqInt < 0) {
	        	JOptionPane.showMessageDialog(null, "Las unidades deben ser mayores a 0.", "Error", JOptionPane.ERROR_MESSAGE);
	        	return false;
	        }
	        
	        return true;
	        
	    } catch (NumberFormatException e) {
	    	JOptionPane.showMessageDialog(null, "Las unidades deben ser números.", "Error", JOptionPane.ERROR_MESSAGE);
	    	return false;
	    }
	}
	
	private double precioTotal() {
		double priceEquipacion = Double.parseDouble(model.getPrice("equipacion")) * Integer.parseInt(view.getTxtUdsEq().getText());
		double priceChandal = Double.parseDouble(model.getPrice("chandal")) * Integer.parseInt(view.getTxtUdsChd().getText());
		double priceChaqueta = Double.parseDouble(model.getPrice("chaqueta")) * Integer.parseInt(view.getTxtUdsChq().getText());
		return priceEquipacion+priceChandal+priceChaqueta;
	}
	
	private void saveRecibos() {
		String[] fields = {"owner_iban", "number", "amount", "value_date", "charge_date", "concept", "type_recibo", "state"};
		String csv = Util.pojosToCsv(recibos.getListaRecibos(), fields);
		
		System.out.println(csv);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/files/recibos.csv"))) {
	        writer.write(csv);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
}
