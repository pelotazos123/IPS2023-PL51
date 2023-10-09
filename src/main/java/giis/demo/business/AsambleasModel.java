package giis.demo.business;

import giis.demo.util.Database;

public class AsambleasModel {

	private Database db = new Database();
	
	public static final String SQL_ADD_ASAMBLEA=
			"insert into asambleas (type, announcement, date_announcement1, date_announcement2)"
			+ "values (?,?,?,?)";
	
	public void addAsamblea(String type, String announcement, String date1, String date2) {
		db.executeUpdate(SQL_ADD_ASAMBLEA, type, announcement, date1, date2);
		System.out.println("Created asamblea " + type + " date: " + announcement + ", conv1: "+ date1 + ", conv2: "+ date2);
	}
}
