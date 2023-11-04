package giis.demo.logica;

import java.sql.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import giis.demo.util.Database;

public class TestsFisiologicos {

	public static final int MUJER = 0;
	public static final int HOMBRE = 1;

	private static final String CARGAVALORES = "select edad, peso, sexo from test where id = ?";
	private static final String INSERTROCKPORT = "insert into test(id, fecha, tipo, peso, edad, sexo, tiempo, pulsaciones, resultado) values (?,?,'ROCKPORT',?,?,?,?,?)";
	private static final String INSERTCOOPER = "insert into test(id, fecha, tipo, distancia, resultado) values (?,?,'COOPER',?,?)";
	private static final String SELECTALL = "select fecha, resultado from test where id = ?" ;

	private Database db;
	private int id;

	public TestsFisiologicos(Database db) {
		this.db = db;
	}

	public Double getTestCooper(double distance) {
		Double resultado = (22.351 * distance) + 11.288;
		db.executeUpdate(INSERTCOOPER, /* TODO insertar id, fecha, */distance, resultado);
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
		db.executeUpdate(INSERTROCKPORT, /* TODO insertar id,fecha, */peso, edad, sexoAux, tiempo, pulsaciones,
				resultado);
		return resultado;
	}

	public String[] cargaValores() {
		String[] valores = new String[2];
		List<Object[]> valoresDevueltos = db.executeQueryArray(CARGAVALORES, "A"); // TODO Añadir id socio
		int i = 0;
		for (Object[] valor : valoresDevueltos) {
			valores[i] = valor.toString();
			i++;
		}
		return valores;
	}

	private void selectValores() {
		List<Object[]> datos = db.executeQueryArray(SELECTALL, "a"/* TODO Insertar ID */);
		Date[] fechas = new Date[datos.size()];
		double[] resultado = new double[datos.size()];
		for(int i = 0; i < datos.size(); i++) {
			Object[] par = datos.get(i);
//			fechas =  par[0]; 
			resultado[i] = (double) par[1];
		}
		
	}

	private void creaGrafica() {
		DefaultCategoryDataset dcs = new DefaultCategoryDataset();

		JFreeChart chart = ChartFactory.createLineChart("Resultados de test anteriores", "FECHA", "VO2", dcs,
				PlotOrientation.VERTICAL, true, true, false);
	}

}
