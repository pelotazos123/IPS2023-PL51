package giis.demo.business;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import giis.demo.business.entities.CuotaEntity;
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
		List<CuotaEntity> cuotas = new ArrayList<>();
		
		int[] seleccionados = view.getTabSocios().getSelectedRows();
		for(int i=0; i<seleccionados.length; i++) {
			cuotas.addAll(model.getListaCuotas((String) view.getTabSocios().getValueAt(seleccionados[i], 0)));
		}
		TableModel tmodel = SwingUtil.getTableModelFromPojos(cuotas, new String[] {"cuota_type", "price","state"});
		
		view.getTabCuotas().setModel(tmodel);
	}
	
	
	public void generateRecibos() {
		boolean generated = false;
		List<SocioEntity> socios = new ArrayList<>();
		
		int[] seleccionados = view.getTabSocios().getSelectedRows();
		for(int i=0; i<seleccionados.length; i++) {
			socios.add(model.getSocio((String) view.getTabSocios().getValueAt(seleccionados[i], 0)));
		}
		
		Calendar v_date = Calendar.getInstance();
		v_date.set(Calendar.YEAR, view.getDate().get(Calendar.YEAR));
		v_date.set(Calendar.MONTH, view.getDate().get(Calendar.MONTH));
		v_date.set(Calendar.DAY_OF_MONTH, 1);
		String value_date = new SimpleDateFormat("yyyy-MM-dd").format(v_date.getTime());

		Calendar c_date = Calendar.getInstance();
		c_date.set(Calendar.YEAR, Integer.parseInt(view.getCbYear().getItemAt(view.getCbYear().getSelectedIndex())));
		c_date.set(Calendar.MONTH, view.getCbMonth().getSelectedIndex());
		c_date.set(Calendar.DAY_OF_MONTH, 15);
		String charge_date = new SimpleDateFormat("yyyy-MM-dd").format(c_date.getTime());
		
		if(c_date.before(v_date) || c_date.before(view.getDate())) {
			JOptionPane.showMessageDialog(null, "Fecha de cargo no válida", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else {
			for(SocioEntity socio : socios) {
				if(!model.getListaCuotasPendientes(socio.getId()).isEmpty()) {
					double amount = model.getAmount(socio.getId());
					
					model.generateRecibo(socio.getIban(), model.getLastNumber()+1, amount, value_date, charge_date, "Cuota", "Domiciliado", "Pendiente");
						
					model.updateCuotas(socio.getId());
						
					generated = true;
				}
			}
			
			if(generated) {
				getListaCuotas();
				saveRecibos();
				JOptionPane.showMessageDialog(null, "Se han generado recibos.", "Recibos", JOptionPane.INFORMATION_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(null, "Recibos ya generados.", "Recibos", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	private void saveRecibos() {
		String[] fields = {"owner_iban", "number", "amount", "value_date", "charge_date", "concept", "type_recibo", "state"};
		String csv = Util.pojosToCsv(model.getListaRecibos(), fields);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/files/recibos.csv"))) {
	        writer.write(csv);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
}
