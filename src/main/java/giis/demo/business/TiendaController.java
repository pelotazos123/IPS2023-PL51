package giis.demo.business;

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
		
		view.getBtnComprar().addActionListener(
				e -> SwingUtil.exceptionWrapper(() -> comprar()));
	}

	private void getPrices() {
		view.getLblPrecioEq().setText(model.getPrice("equipacion") + "€");
		view.getLblPrecioChd().setText(model.getPrice("chandal") + "€");
		view.getLblPrecioChq().setText(model.getPrice("chaqueta") + "€");
	}
	
	private Object comprar() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
