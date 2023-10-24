package giis.demo.business;

import java.util.ArrayList;
import java.util.List;

import giis.demo.model.Generos;
import giis.demo.model.Socio;
import giis.demo.util.Database;

public abstract class SociosController {
	
	private final static String SQL_CARGAR_TODOS_SOCIOS = "select id, name, surname, cuota_type, gender, age from socios ";
	
	public static final String WHERE = "WHERE";
	public static final String AND = "AND";
	public static final String OR = "OR";
	
	public static List<Socio> cargarSocios(Database db, String filter, String order) {
		
		String query = "";
		
		query = buildFinalQuery(filter, order);
		
		List<Object[]> res = db.executeQueryArray(query);
		List<Socio> socios = new ArrayList<>();
		
		int id = 0;
		String nombre = "";
		String apellidos = "";
		String cuota = "";
		Generos genero;
		int edad = 0;
		
		for (Object[] objects : res) {
			id = (int) objects[0];
			nombre = (String)objects[1];
			apellidos = (String)objects[2];
			cuota = (String)objects[3];
			genero = getGenre((String)objects[4]);
			edad = (int)objects[5];
			//adaptar a nuevo formato de edad	
			//socios.add(new Socio(id, nombre, apellidos, cuota, genero, edad));
		}
				
		return socios;
		
	}

	private static String buildFinalQuery(String filter, String order) {
		String query;

		if (!filter.isEmpty() && !order.isEmpty()) {
			query = SQL_CARGAR_TODOS_SOCIOS + filter + order;
		} else if (!filter.isEmpty() && order.isEmpty()) {
			query = SQL_CARGAR_TODOS_SOCIOS + filter;
		} else if (filter.isEmpty() && !order.isEmpty()) {
			query = SQL_CARGAR_TODOS_SOCIOS + order;
		} else {
			query = SQL_CARGAR_TODOS_SOCIOS;
		}
		
		return query;
	}
	
	public static String checkOrden(String opt) {
		switch (opt) {
			case "A-Z":
				opt = "ORDER BY name ASC";
				break;
			case "Z-A":
				opt = "ORDER BY name DESC";
				break;
			case "ID ↑":
				opt = "ORDER BY id ASC";
				break;
			case "ID ↓":
				opt = "ORDER BY id DESC";
				break;
			case "":
				opt = "";
				break;
			default:
				break;
		}
		return opt;
	}
	
	private static Generos getGenre(String genero) {
		Generos generoF;
		if(genero.equals("HOMBRE")) {
			generoF = Generos.HOMBRE;
		}else if(genero.equals("MUJER")) {
			generoF = Generos.MUJER;
		}else {
			generoF = Generos.OTRO;
		}
		return generoF;
	}

}
