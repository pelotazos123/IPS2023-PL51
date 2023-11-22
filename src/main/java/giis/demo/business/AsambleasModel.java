package giis.demo.business;

import java.util.List;

import giis.demo.business.entities.AsambleaEntity;
import giis.demo.util.Database;

public class AsambleasModel {

	private Database db = new Database();
	
	public static final String SQL_ADD_ASAMBLEA =
			"insert into asambleas (type, date, hour_conv1, hour_conv2, orderOfDay, acta)"
			+ "values (?,?,?,?,?,?)";
	
	public static final String SQL_FIND_ASAMBLEAS =
			"select * from asambleas order by date desc";
	
	public static final String SQL_FIND_ASAMBLEAS_DAY =
			"select * from asambleas where type = ? and date = ?";
	
	public static final String SQL_GET_CORREOS =
			"select email from socios";
	
	public static final String SQL_ADD_ACTA = 
			"update asambleas set acta = ? where type = ? and date = ?";
	
	public static final String SQL_GET_LAST_ACTA =
			"select acta from asambleas where type = ? and date = (select max(date) from asambleas where type = ?)";
	
	public AsambleasModel(Database db) {
		this.db = db;
	}
	
	public void addAsamblea(String type, String announcement, String date1, String date2, String orderOfDay, String acta) {
		db.executeUpdate(SQL_ADD_ASAMBLEA, type, announcement, date1, date2, orderOfDay, acta);
		System.out.println("Created asamblea " + type + " date: " + announcement + ", conv1: "+ date1 + ", conv2: "+ date2 + ", order of the day: "+ orderOfDay);
	}
	
	public boolean hasAsamblea(String type, String announcement) {
		List<Object[]> list = db.executeQueryArray(SQL_FIND_ASAMBLEAS_DAY, type, announcement);
		return !list.isEmpty();
	}
	
	public List<Object[]> getCorreos() {
		return db.executeQueryArray(SQL_GET_CORREOS);
	}
	
	public List<AsambleaEntity> getListaAsambleas() {
		return db.executeQueryPojo(AsambleaEntity.class, SQL_FIND_ASAMBLEAS);
	}
	
	public void addActa(String acta, String type, String announcement) {
		db.executeUpdate(SQL_ADD_ACTA, acta, type, announcement);
	}
	
	public String lastActa(String type) {
		return db.executeQueryArray(SQL_GET_LAST_ACTA, type, type).get(0)[0].toString();
	}
	
}
