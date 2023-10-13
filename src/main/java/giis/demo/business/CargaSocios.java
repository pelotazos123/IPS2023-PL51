package giis.demo.business;

import giis.demo.util.Database;

public class CargaSocios {
	
	private Database db;
	
	private final static String SQL_CARGAR_TODOS_SOCIOS = "select id, name, cuota_type, gender from socios";
	
	public CargaSocios(Database db) {
		this.db = db;
	}

}
