package giis.demo.business;

import giis.demo.util.Database;

public class RecibosModel {
	private Database db = new Database();
	//TODO
	public static final String SQL_ADD_RECIBO=
			"insert into recibos recibos (number,)"
			+ "values (?,?,?,?,?)";
	
	public void addAsamblea(String type, String announcement, String date1, String date2, String orderOfDay) {
		db.executeUpdate(SQL_ADD_RECIBO, type, announcement, date1, date2, orderOfDay);
		System.out.println("Created asamblea " + type + " date: " + announcement + ", conv1: "+ date1 + ", conv2: "+ date2 + ", order of the day: "+ orderOfDay);
	}
}
