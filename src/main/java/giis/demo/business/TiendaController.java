package giis.demo.business;

import javax.swing.JOptionPane;

import giis.demo.ui.TiendaView;
import giis.demo.util.SwingUtil;

public class TiendaController {

	private TiendaModel model;
	private TiendaView view;
	
	public TiendaController(TiendaModel m, TiendaView v) {
		this.model = m;
		this.view = v;
	
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
		if(comprobarUnidades()) {
			
		}
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
	
}
