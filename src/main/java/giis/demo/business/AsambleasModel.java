package giis.demo.business;

import java.util.List;

import giis.demo.util.Database;

public class AsambleasModel {

	private Database db = new Database();
	
	public static final String SQL_ADD_ASAMBLEA =
			"insert into asambleas (type, announcement, date_announcement1, date_announcement2, orderOfDay)"
			+ "values (?,?,?,?,?)";
	
	public static final String SQL_FIND_ASAMBLEAS =
			"select * from asambleas where type = ? and announcement = ?";
	
	public void addAsamblea(String type, String announcement, String date1, String date2, String orderOfDay) {
		db.executeUpdate(SQL_ADD_ASAMBLEA, type, announcement, date1, date2, orderOfDay);
		System.out.println("Created asamblea " + type + " date: " + announcement + ", conv1: "+ date1 + ", conv2: "+ date2 + ", order of the day: "+ orderOfDay);
	}
	
	public boolean hasAsamblea(String type, String announcement) {
		List<Object[]> list = db.executeQueryArray(SQL_FIND_ASAMBLEAS, type, announcement);
		return !list.isEmpty();
	}
	
}
