package giis.demo.business;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.table.TableModel;

import giis.demo.ui.GestionRecibosView;
import giis.demo.util.SwingUtil;

public class GestionRecibosController {

	private RecibosModel model;
	private GestionRecibosView view;

	public GestionRecibosController(RecibosModel m, GestionRecibosView v) {
		this.view = v;
		this.model = m;
		
		this.initView();
	}
	
	public void initView() {
		view.getFrame().setVisible(true); 
	}
	
	public void initController() {
		getListaRecibos();
		
//		view.getBtnGenerarRecibos().addActionListener(
//				e -> SwingUtil.exceptionWrapper(() -> generateRecibos()));
	}

	private void getListaRecibos() {
		TableModel tmodel = SwingUtil.getTableModelFromPojos(model.getListaRecibos(), new String[] {"owner_iban", "number", "amount", "value_date", "charge_date", "type_recibo", "state"});
		view.getTabRecibos().setModel(tmodel);
	}
}
