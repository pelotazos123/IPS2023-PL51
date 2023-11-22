package giis.demo.business;

import giis.demo.util.Database;

public class TiendaModel {
	
	private Database db = new Database();
	
	public static final String SQL_GET_PRICE =
			"select price from articulos where name = ?";
	
	public TiendaModel(Database db) {
		this.db = db;
	}
	
	public String getPrice(String name) {
		return db.executeQueryArray(SQL_GET_PRICE, name).get(0)[0].toString();
	}

}
