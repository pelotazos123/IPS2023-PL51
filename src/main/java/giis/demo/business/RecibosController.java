package giis.demo.business;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import giis.demo.business.entities.SocioEntity;
import giis.demo.ui.RecibosView;
import giis.demo.util.SwingUtil;
import giis.demo.util.Util;

public class RecibosController {

	private RecibosModel model;
	private RecibosView view;
	
	public RecibosController(RecibosModel m, RecibosView v) {
		this.model = m;
		this.view = v;
	
		this.initView();
	}
	
	public void initController() {
		getListaSocios();
		
		view.getTabSocios().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				SwingUtil.exceptionWrapper(() -> getListaCuotas());
			}
		});
		
		view.getBtnGenerarRecibos().addActionListener(
				e -> SwingUtil.exceptionWrapper(() -> generateRecibos()));
	}
	
	public void initView() {
		view.getFrame().setVisible(true); 
	}
	
	
	public void getListaSocios() {
		TableModel tmodel = SwingUtil.getTableModelFromPojos(model.getListaSocios(), new String[] {"id", "name"});
		view.getTabSocios().setModel(tmodel);
	}
	
	public void getListaCuotas() {
		String lastSelectedKey = SwingUtil.getSelectedKey(view.getTabSocios());
		String id = lastSelectedKey;
		
		TableModel tmodel = SwingUtil.getTableModelFromPojos(model.getListaCuotas(id), new String[] {"cuota_type", "price","state"});
		view.getTabCuotas().setModel(tmodel);
	}
	
	
	public void generateRecibos() {
		boolean generated = false;
		List<SocioEntity> socios = model.getListaSocios();
		
		for(SocioEntity socio : socios) {
			if(!model.getListaCuotasPendientes(socio.getId()).isEmpty()) {
				int amount = model.getAmount(socio.getId());
				
				model.generateRecibo(socio.getIban(), amount);
				
				model.updateCuotas(socio.getId());
				generated = true;
			}
		}
		
		if(generated) {
			saveRecibos();
			JOptionPane.showMessageDialog(null, "Se han generado recibos.", "Recibos", JOptionPane.INFORMATION_MESSAGE);
		}
		else
			JOptionPane.showMessageDialog(null, "Recibos ya generados.", "Recibos", JOptionPane.INFORMATION_MESSAGE);
	}
	private void saveRecibos() {
		String[] fields = {"owner_iban", "number", "amount", "value_date", "charge_date"};
		String csv = Util.pojosToCsv(model.getListaRecibos(), fields);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/files/recibos.csv"))) {
	        writer.write(csv);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
}
