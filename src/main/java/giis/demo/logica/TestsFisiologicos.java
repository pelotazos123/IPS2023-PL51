package giis.demo.logica;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import giis.demo.ui.VentanaSeleccionTest;
import giis.demo.util.Database;

public class TestsFisiologicos {

	public static final int MUJER = 0;
	public static final int HOMBRE = 1;

	private static final String CARGAVALORES = "select edad, peso, sexo from test where id = ?";
	private static final String INSERTROCKPORT = "insert into test(id, fecha, tipo, peso, "
			+ "edad, sexo, tiempo, pulsaciones, resultado) values (?,?,'ROCKPORT',?,?,?,?,?,?)";
	private static final String INSERTCOOPER = "insert into test(id, fecha, tipo, distance, "
			+ "resultado) values (?,?,'COOPER',?,?)";
	private static final String SELECTALL = "select fecha, resultado from test where id = ?";
	private static final String ES_ENTRENADOR = "select * from licencias where "
			+ "owner_id = ? and licence_type = 'MONITOR' ";
	
	
	private Database db;
	private int id;
	private LocalDate[] fechas;
	private double[] resultado;
	private LocalDate[] fechasEntrenados;
	private double[] resultadoEntrenados;
	private int idEntrenado;

	public TestsFisiologicos(VentanaSeleccionTest vst) {
		this.db = vst.getVp().getDb();
//		this.id = tl.getSocio().getId();
		this.id = 102;
		this.idEntrenado = 104;
	}
	
	public boolean esEntrenador() {
		List<Object[]> esEntrenador = db.executeQueryArray(ES_ENTRENADOR, id);
		return esEntrenador.size() != 0;
	}

	public Double getTestCooper(double distance) {
		Double resultado = (22.351 * distance) + 11.288;
		db.executeUpdate(INSERTCOOPER, id, LocalDate.now().toString(), distance, resultado);
		return resultado;
	}

	public Double getTestRockport(int peso, int edad, int sexo, double tiempo, int pulsaciones) {
		Double resultado = 132.7 - (0.17 * peso) - (0.39 * edad) + (6.31 * sexo) - (3.27 * tiempo)
				- (0.156 * pulsaciones);
		String sexoAux;
		if (sexo == 1)
			sexoAux = "Hombre";
		else
			sexoAux = "Mujer";
		db.executeUpdate(INSERTROCKPORT, id, LocalDate.now().toString(), peso, edad, sexoAux, tiempo, pulsaciones,
				resultado);
		return resultado;
	}

	public String[] cargaValores() {
		String[] valores = new String[2];
		List<Object[]> valoresDevueltos = db.executeQueryArray(CARGAVALORES, id);
		int i = 0;
		for (Object[] valor : valoresDevueltos) {
			valores[i] = valor.toString();
			i++;
		}
		return valores;
	}

	private void selectValores() {
		List<Object[]> datos = db.executeQueryArray(SELECTALL, id);
		fechas = new LocalDate[datos.size()];
		resultado = new double[datos.size()];
		for (int i = 0; i < datos.size(); i++) {
			Object[] par = datos.get(i);
			fechas[i] = LocalDate.parse((CharSequence) par[0]);
			resultado[i] = (double) par[1];
		}
	}

	public JFreeChart creaGrafica() {
		selectValores();

		TimeSeries series = new TimeSeries("Resultados");
		
		for (int i = 0; i < resultado.length; i++) {
			Date fecha = Date.from(fechas[i].atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			series.add(new org.jfree.data.time.Day(fecha), resultado[i]);
		}

		TimeSeriesCollection dcs = new TimeSeriesCollection(series);
		
		JFreeChart chart = ChartFactory.createTimeSeriesChart("Resultados de test anteriores", 
				"FECHA","VO2", dcs, false, true, false);
		
		XYPlot plot = (XYPlot) chart.getPlot();
	    
	    NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
	    yAxis.setRange(resultado[0] - 10.0, resultado[0] + 10.0); 
		
		return chart;
	}

	private void selectValoresEntrenados() {
		List<Object[]> datos = db.executeQueryArray(SELECTALL, idEntrenado);
		fechasEntrenados = new LocalDate[datos.size()];
		resultadoEntrenados = new double[datos.size()];
		for (int i = 0; i < datos.size(); i++) {
			Object[] par = datos.get(i);
			fechasEntrenados[i] = LocalDate.parse((CharSequence) par[0]);
			resultadoEntrenados[i] = (double) par[1];
		}
	}

	public JFreeChart creaGraficaEntrenados() {
		selectValoresEntrenados();

		TimeSeries series = new TimeSeries("Resultados");
		
		for (int i = 0; i < resultadoEntrenados.length; i++) {
			Date fecha = Date.from(fechasEntrenados[i].atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			series.add(new org.jfree.data.time.Day(fecha), resultadoEntrenados[i]);
		}

		TimeSeriesCollection dcs = new TimeSeriesCollection(series);
		
		JFreeChart chart = ChartFactory.createTimeSeriesChart("Resultados de test anteriores", 
				"FECHA","VO2", dcs, false, true, false);
		
		XYPlot plot = (XYPlot) chart.getPlot();
	    
	    NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
	    yAxis.setRange(resultadoEntrenados[0] - 25.0, resultadoEntrenados[0] + 25.0); 
		
		return chart;
	}
	
}
