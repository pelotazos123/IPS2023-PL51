package giis.demo.logica;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import giis.demo.model.CrearLicencias.servicio.TramitarLicencia;
import giis.demo.ui.VentanaSeleccionTest;
import giis.demo.util.Database;

public class TestsFisiologicos {

	public static final int MUJER = 0;
	public static final int HOMBRE = 1;
	public static final String COOPER = "COOPER";
	public static final String ROCKPORT = "ROCKPORT";

	private Database db;
	private int id;
	private List<LocalDate> fechas;
	private List<Double> resultado;
	private int[] idEntrenados;
	private TramitarLicencia tl;
	private VentanaSeleccionTest vst;

	public TestsFisiologicos(VentanaSeleccionTest vst) {
		this.vst = vst;
		this.db = vst.getVp().getDb();
		this.tl = vst.getVp().getTramitarLicencia();
		asignaId();
		if (esEntrenador())
			this.idEntrenados = getEntrenados();
	}

	private void asignaId() {
		if (tl.esDirectivo())
			this.id = tl.getDirectivo().getId();
		else
			this.id = tl.getSocio().getId();
	}

	private int[] getEntrenados() {
		String SELECT_ENTRENADOS = "select entrenado_id from entrenados where entrenador_id = ? ";
		List<Object[]> entrenados = db.executeQueryArray(SELECT_ENTRENADOS, id);
		int[] ids = new int[entrenados.size()];
		for (int i = 0; i < entrenados.size(); i++) {
			Object[] entrenado = entrenados.get(i);
			ids[i] = Integer.parseInt(entrenado[0].toString());
		}
		return ids;
	}

	public boolean esEntrenador() {
		String ES_ENTRENADOR = "select * from licencias where "
				+ "owner_id = ? and licence_type = 'MONITOR' ";
		List<Object[]> esEntrenador = db.executeQueryArray(ES_ENTRENADOR, id);
		return esEntrenador.size() != 0;
	}
	
	public static boolean esEntrenador(String id, Database db) {
		String ES_ENTRENADOR = "select * from licencias where "
				+ "owner_id = ? and licence_type = 'MONITOR' ";
		List<Object[]> esEntrenador = db.executeQueryArray(ES_ENTRENADOR, id);
		return esEntrenador.size() != 0;
	}

	public Double getTestCooper(double distance) {
		Double resultado = (22.351 * distance) + 11.288;
		Date fecha = vst.getVp().getDcFecha().getDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		LocalDate date = LocalDate.of(year, month, day);
		String INSERTCOOPER = "insert into test(id, fecha, tipo, distance, "
				+ "resultado) values (?,?,'COOPER',?,?)";
		db.executeUpdate(INSERTCOOPER, id, date.toString(), distance, resultado);
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
		Date fecha = vst.getVp().getDcFecha().getDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		LocalDate date = LocalDate.of(year, month, day);
		String INSERTROCKPORT = "insert into test(id, fecha, tipo, peso, "
				+ "edad, sexo, tiempo, pulsaciones, resultado) values (?,?,'ROCKPORT',?,?,?,?,?,?)";
		db.executeUpdate(INSERTROCKPORT, id, date.toString(), peso, edad, sexoAux, tiempo, pulsaciones, resultado);
		return resultado;
	}

	public int cargaEdad(int id) {
		String CARGAEDAD = "select birth_date from socios where id = ?";
		List<Object[]> edad = db.executeQueryArray(CARGAEDAD, id);
		LocalDate birth = LocalDate.parse(edad.get(0)[0].toString());
		Date fecha = vst.getVp().getDcFecha().getDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		LocalDate actual = LocalDate.of(year, month, day);
		return Integer.parseInt(actual.compareTo(birth) + "");
	}

	public String cargaSexo(int id) {
		String CARGASEXO = "select gender from socios where id = ? ";
		List<Object[]> sexo = db.executeQueryArray(CARGASEXO, id);
		return sexo.get(0)[0].toString();
	}

	public int cargaPeso(int id) {
		String CARGAPESO = "select peso from test where id = ? and " + "tipo = 'ROCKPORT'";
		List<Object[]> peso = db.executeQueryArray(CARGAPESO, id);
		return Integer.parseInt(peso.get(peso.size() - 1)[0] + "");
	}

	private void selectValores(int id, String tipo) {
		String SELECTALL = "select fecha, resultado from test where id = ? and tipo = ? ";
		List<Object[]> datos = db.executeQueryArray(SELECTALL, id, tipo);
		int fechaAnterior = -1;
		int añoAnterior = -1;
		int fechaActual = 0;
		int añoActual = 0;
		fechas = new ArrayList<>();
		resultado = new ArrayList<>();
		int i = 0;
		for (Object[] par : datos) {
			fechaActual = LocalDate.parse((CharSequence) par[0]).getDayOfYear();
			añoActual = LocalDate.parse((CharSequence) par[0]).getYear();
			if (fechaActual == fechaAnterior && añoActual == añoAnterior) {
				i--;
				fechas.remove(i);
				fechas.add(LocalDate.parse((CharSequence) par[0]));
				fechaAnterior = fechas.get(i).getDayOfYear();
				añoAnterior = fechas.get(i).getYear();
				resultado.remove(i);
				resultado.add(Double.parseDouble((par[1] + "")));
				i++;
			} else {
				fechas.add(LocalDate.parse((CharSequence) par[0]));
				fechaAnterior = fechas.get(i).getDayOfYear();
				añoAnterior = fechas.get(i).getYear();
				resultado.add((double) par[1]);
				i++;
			}
		}
	}

	public JFreeChart creaGrafica(int id, String tipo) {
		selectValores(id, tipo);
		JFreeChart chart = null;
		if (resultado.size() != 0) {
			TimeSeries series = new TimeSeries("Resultados");

			for (int i = 0; i < resultado.size(); i++) {
				Date fecha = Date.from(fechas.get(i).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
				series.add(new org.jfree.data.time.Day(fecha), resultado.get(i));
			}

			TimeSeriesCollection dcs = new TimeSeriesCollection(series);

			chart = ChartFactory.createTimeSeriesChart("Resultados de test anteriores", "FECHA", "VO2", dcs, false,
					true, false);

			XYPlot plot = (XYPlot) chart.getPlot();

			NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
			yAxis.setRange(30.0, 105.0);
		} else {
			TimeSeriesCollection dcs = new TimeSeriesCollection();

			chart = ChartFactory.createTimeSeriesChart("Resultados de test anteriores", "FECHA", "VO2", dcs, false,
					true, false);

			XYPlot plot = (XYPlot) chart.getPlot();

			NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
			yAxis.setRange(30.0, 105.0);
			
		}
		return chart;
	}

	public int getId() {
		return id;
	}

	public boolean tieneTest(int id) {
		String TIENE_TEST = "select * from test where id = ?";
		List<Object[]> testEntrenado = db.executeQueryArray(TIENE_TEST, id);
		return testEntrenado.size() != 0;
	}

	public boolean tieneTestTipo(int id, String tipo) {
		String TIENE_TEST_TIPO = "select * from test where id = ? and tipo = ? ";
		List<Object[]> testEntrenado = db.executeQueryArray(TIENE_TEST_TIPO, id, tipo);
		return testEntrenado.size() != 0;
	}

	public int[] getIdEntrenados() {
		return idEntrenados;
	}

	public String getNombre(int id) {
		String GETNOMBREWITHID = "select name, surname from socios where id = ? ";
		List<Object[]> name = db.executeQueryArray(GETNOMBREWITHID, id);
		return name.get(0)[0].toString() + " " + name.get(0)[1].toString();
	}
	
}