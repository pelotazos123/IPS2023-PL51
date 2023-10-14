package giis.demo.business;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import giis.demo.business.entities.CuotaEntity;
import giis.demo.business.entities.ReciboEntity;
import giis.demo.business.entities.SocioEntity;
import giis.demo.util.Database;

public class RecibosModel {
	private Database db = new Database();
	
	public static final String SQL_LISTA_SOCIOS = 
			"select * from socios";
	public static final String SQL_LISTA_CUOTAS = 
			"select * from cuotas where owner_id = ?";
	public static final String SQL_LISTA_CUOTAS_PENDIENTES = 
			"select * from cuotas where owner_id = ? and state = 'Pendiente'";
	public static final String SQL_LISTA_RECIBOS = 
			"select * from recibos";
	public static final String SQL_UPDATE_CUOTAS = 
			"update cuotas set state = 'Emitida' where state = 'Pendiente' and owner_id = ?";
	public static final String SQL_GENERATE_RECIBOS = 
			"insert into recibos (owner_iban, number, amount, value_date, charge_date) values (?,?,?,?,?)";
	public static final String SQL_GETAMOUNT = 
			"select sum(price) from cuotas where owner_id = ? and state = 'Pendiente'";
	
	
	public List<SocioEntity> getListaSocios() {
		return db.executeQueryPojo(SocioEntity.class, SQL_LISTA_SOCIOS);
	}
	
	public List<CuotaEntity> getListaCuotas(String id) {
		return db.executeQueryPojo(CuotaEntity.class, SQL_LISTA_CUOTAS, id);
	}
	
	public List<CuotaEntity> getListaCuotasPendientes(String id) {
		return db.executeQueryPojo(CuotaEntity.class, SQL_LISTA_CUOTAS_PENDIENTES, id);
	}
	
	public List<ReciboEntity> getListaRecibos() {
		return db.executeQueryPojo(ReciboEntity.class, SQL_LISTA_RECIBOS);
	}
	
	public void generateRecibo(String iban, int amount) {
		int number = new Random().nextInt();
		Calendar today = Calendar.getInstance();
		today.add(Calendar.MONTH, 1);
		today.set(Calendar.DAY_OF_MONTH, 1);
		String value_date = new SimpleDateFormat("dd-MM-yyyy").format(today.getTime());
		today.set(Calendar.DAY_OF_MONTH, 15);
		String charge_date = new SimpleDateFormat("dd-MM-yyyy").format(today.getTime());
		
		db.executeUpdate(SQL_GENERATE_RECIBOS, iban, number,  amount, value_date, charge_date);
	}
	
	public void updateCuotas(String id) {
		db.executeUpdate(SQL_UPDATE_CUOTAS, id);
	}
	
	public int getAmount(String id) {
		return (int) db.executeQueryArray(SQL_GETAMOUNT, id).get(0)[0];
	}
}
