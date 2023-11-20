package giis.demo.business;

import giis.demo.ui.TiendaView;

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
		// TODO Auto-generated method stub
		
	}
	
}
