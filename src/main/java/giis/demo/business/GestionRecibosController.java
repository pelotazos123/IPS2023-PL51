package giis.demo.business;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import giis.demo.ui.GestionRecibosView;
import giis.demo.util.SwingUtil;
import giis.demo.util.Util;

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

		view.getBtnCargarRecibos().addActionListener(e -> SwingUtil.exceptionWrapper(() -> chargeRecibos()));

		view.getBtnReclamar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> { claimRecibos(); getListaRecibos(); saveRecibos(); } ));
	}

	private void getListaRecibos() {
		TableModel tmodel = SwingUtil.getTableModelFromPojos(model.getListaRecibos(),
				new String[] { "owner_iban", "number", "amount", "value_date", "charge_date", "type_recibo", "state" });
		
		((DefaultTableModel)tmodel).setColumnIdentifiers(new String[] { "IBAN", "Número", "Cantidad", "Fecha de valor", "Fecha de emision", "Tipo", "Estado" });
		view.getTabRecibos().setModel(tmodel);
		view.getTabRecibos().getColumnModel().getColumn(0).setPreferredWidth(170);
	}

	private void chargeRecibos() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
		fileChooser.setFileFilter(filter);

		int returnValue = fileChooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			FileReader fileReader = null;
			BufferedReader bufferedReader = null;

			try {
				fileReader = new FileReader(selectedFile);
				bufferedReader = new BufferedReader(fileReader);

				String line;
				while ((line = bufferedReader.readLine()) != null) {
					String[] recibo = line.split(",");

					model.generateRecibo(recibo[0], Integer.parseInt(recibo[1]), Double.parseDouble(recibo[2]), recibo[3],
							recibo[4], recibo[5], recibo[6]);
					getListaRecibos();
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					if (bufferedReader != null) {
						bufferedReader.close();
					}
					if (fileReader != null) {
						fileReader.close();
					}
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	private void claimRecibos() {
		int[] seleccionados = view.getTabRecibos().getSelectedRows();
		for (int i = 0; i < seleccionados.length; i++) {
			if(view.getTabRecibos().getValueAt(seleccionados[i], 6).toString().equals("Devuelto")) {
				model.claimRecibo(view.getTabRecibos().getValueAt(seleccionados[i], 1).toString());
				
				String iban = view.getTabRecibos().getValueAt(seleccionados[i], 0).toString();
				int number = model.getLastNumber()+1;
				double amount = Double.parseDouble(view.getTabRecibos().getValueAt(seleccionados[i], 2).toString()) + Double.parseDouble(view.getTabRecibos().getValueAt(seleccionados[i], 2).toString()) * 0.15;
				String value_date = view.getTabRecibos().getValueAt(seleccionados[i], 3).toString();
				
				Calendar date = Calendar.getInstance();
				if(date.get(Calendar.DAY_OF_MONTH) >= 15)
					date.add(Calendar.MONTH, 1);
				date.set(Calendar.DAY_OF_MONTH, 15);
				String charge_date = new SimpleDateFormat("dd-MM-yyyy").format(date.getTime());
				
				model.generateRecibo(iban, number, amount, value_date, charge_date, "Reliquidado", "Pendiente");
			}
		}
	}
	
	private void saveRecibos() {
		String[] fields = {"owner_iban", "number", "amount", "value_date", "charge_date", "type_recibo", "state"};
		String csv = Util.pojosToCsv(model.getListaRecibos(), fields);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/files/recibos.csv"))) {
	        writer.write(csv);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
