package giis.demo.business;

import java.util.List;

import giis.demo.business.entities.CuotaEntity;
import giis.demo.business.entities.ReciboEntity;
import giis.demo.business.entities.SocioEntity;
import giis.demo.util.Database;

public class RecibosModel {
	private Database db = new Database();
	
	public static final String SQL_LISTA_SOCIOS = 
			"select * from socios";
	public static final String SQL_FIND_SOCIO = 
			"select * from socios where id = ?";
	public static final String SQL_LISTA_CUOTAS = 
			"select * from cuotas where owner_id = ?";
	public static final String SQL_LISTA_CUOTAS_PENDIENTES = 
			"select * from cuotas where owner_id = ? and state = 'Pendiente'";
	public static final String SQL_LISTA_RECIBOS = 
			"select * from recibos";
	public static final String SQL_LISTA_RECIBOS_DEVUELTOS = 
			"select * from recibos where state = 'Devuelto'";
	public static final String SQL_UPDATE_CUOTAS = 
			"update cuotas set state = 'Emitida' where state = 'Pendiente' and owner_id = ?";
	public static final String SQL_GENERATE_RECIBOS = 
			"insert into recibos (owner_iban, number, amount, value_date, charge_date, type_recibo, state) values (?,?,?,?,?,?,?)";
	public static final String SQL_GETAMOUNT = 
			"select sum(price) from cuotas where owner_id = ? and state = 'Pendiente'";
	public static final String SQL_LASTNUMBER = 
			"select max(number) from recibos";
	public static final String SQL_CLAIM_RECIBO = 
			"update recibos set state = 'Reclamado' where state = 'Devuelto' and number = ?";
	
	public RecibosModel(Database db) {
		this.db = db;
	}
	
	public List<SocioEntity> getListaSocios() {
		return db.executeQueryPojo(SocioEntity.class, SQL_LISTA_SOCIOS);
	}
	
	public SocioEntity getSocio(String id) {
		return db.executeQueryPojo(SocioEntity.class, SQL_FIND_SOCIO, id).get(0);
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
	
	public List<ReciboEntity> getListaRecibosDevueltos() {
		return db.executeQueryPojo(ReciboEntity.class, SQL_LISTA_RECIBOS_DEVUELTOS);
	}
	
	public void generateRecibo(String iban, int number, double amount, String value_date, String charge_date, String type_recibo, String state) {
		db.executeUpdate(SQL_GENERATE_RECIBOS, iban, number,  amount, value_date, charge_date, type_recibo, state);
	}
	
	public void updateCuotas(String id) {
		db.executeUpdate(SQL_UPDATE_CUOTAS, id);
	}
	
	public int getAmount(String id) {
		return (int) db.executeQueryArray(SQL_GETAMOUNT, id).get(0)[0];
	}
	
	public int getLastNumber() {
		if(getListaRecibos().isEmpty())
			return 0;
		return (int) db.executeQueryArray(SQL_LASTNUMBER).get(0)[0];
	}

	public void claimRecibo(String number) {
		db.executeUpdate(SQL_CLAIM_RECIBO, number);
	}
}
