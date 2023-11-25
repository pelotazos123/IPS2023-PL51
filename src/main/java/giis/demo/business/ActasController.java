package giis.demo.business;

import javax.swing.JOptionPane;
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
		
		view.getBtnAñadirActa().addActionListener(
				e -> SwingUtil.exceptionWrapper(() -> addActa()));
	}

	private void addActa() {
		if(view.getTabAsambleas().getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Selecciona la asamblea a la que quieras añadir el acta.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String type = view.getTabAsambleas().getValueAt(view.getTabAsambleas().getSelectedRow(), 0).toString();
		String announcement = view.getTabAsambleas().getValueAt(view.getTabAsambleas().getSelectedRow(), 1).toString();
		String acta = view.getTxtActa().getText();
		if(acta.isBlank()) {
			JOptionPane.showMessageDialog(null, "Acta necesaria.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		model.addActa(acta, type, announcement);
		getListaAsambleas();
		JOptionPane.showMessageDialog(null, "Acta añadida correctamente.", "Correcto", JOptionPane.INFORMATION_MESSAGE);
	}

	private void getListaAsambleas() {
		TableModel tmodel = SwingUtil.getTableModelFromPojos(model.getListaAsambleas(),
				new String[] { "type", "date", "hour_conv1", "hour_conv2", "orderOfDay", "acta" });
		
		((DefaultTableModel)tmodel).setColumnIdentifiers(new String[] { "Tipo", "Fecha", "Hora 1º convocatoria", "Hora 2º convocatoria", "Orden del día", "Acta" });
		view.getTabAsambleas().setModel(tmodel);
	}
}
