package giis.demo.business;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import giis.demo.ui.ActasView;
import giis.demo.util.SwingUtil;

public class ActasController {
	
	private AsambleasModel model;
	private ActasView view;

	public ActasController(AsambleasModel model, ActasView view) {
		this.model = model;
		this.view = view;
		this.initView();
	}

	public void initView() {
		view.getFrame().setVisible(true);
	}
	
	public void initController() {
		getListaAsambleas();
		
		
	}

	private void getListaAsambleas() {
		TableModel tmodel = SwingUtil.getTableModelFromPojos(model.getListaAsambleas(),
				new String[] { "type", "date", "hour_conv1", "hour_conv2", "orderOfDay" });
		
		((DefaultTableModel)tmodel).setColumnIdentifiers(new String[] { "Tipo", "Fecha", "Hora 1º convocatoria", "Hora 2º convocatoria", "Orden del día" });
		view.getTabAsambleas().setModel(tmodel);
	}
}
