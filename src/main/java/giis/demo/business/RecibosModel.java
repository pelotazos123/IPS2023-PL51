package giis.demo.business;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import giis.demo.business.entities.CuotaEntity;
import giis.demo.business.entities.ReciboEntity;
import giis.demo.business.entities.SocioEntity;
import giis.demo.ui.VentanaPrincipal;
import giis.demo.util.Database;

public class RecibosModel {
	private Database db = new Database();
	private VentanaPrincipal vp;
	
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
	
	public RecibosModel(Database db, VentanaPrincipal vp) {
		this.db = db;
		this.vp = vp;
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
		//if(!existsRecibo(iban, number,  amount, value_date, charge_date, type_recibo, state))
			db.executeUpdate(SQL_GENERATE_RECIBOS, iban, number,  amount, value_date, charge_date, type_recibo, state);
	}
	
	public void updateCuotas(String id) {
		db.executeUpdate(SQL_UPDATE_CUOTAS, id);
	}
	
	public double getAmount(String id) {
		double amount = 0.0;
		amount += Double.parseDouble(db.executeQueryArray(SQL_GETAMOUNT, id).get(0)[0].toString());
//		amount += calculaAmountCursos(Integer.parseInt(id));
		return amount;
	}
//TODO ELIMINAR
//	private double calculaAmountCursos(int id) {
//		Double amount = 0.0;
//		Date fecha = vp.getDcFecha().getDate();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(fecha);
//		int year = calendar.get(Calendar.YEAR);
//		int month = calendar.get(Calendar.MONTH) + 1;
//		int day = calendar.get(Calendar.DAY_OF_MONTH);
//		LocalDate actual = LocalDate.of(year, month, day);
//		
//		String INSCRITOACURSO = "select id_curso, estado, fecha_eliminacion from inscritos where id_cursante = ?";
//		List<Object[]> cursos = this.db.executeQueryArray(INSCRITOACURSO, id);
//		for(Object[] curso: cursos) {
//			int idCurso = Integer.parseInt(curso[0].toString());
//			String estado = curso[1].toString();
//			String GETAMOUNTCURSO = "select price from cursillos where id = ?";
//			if(estado.equals("BORRADO")) {
//				String fechaEliminacion = curso[2].toString();
//				LocalDate eliminacion = LocalDate.parse(fechaEliminacion);
//				if(eliminacion.isBefore(actual.plusMonths(1))) {
//					amount += Double.parseDouble(db.executeQueryArray(GETAMOUNTCURSO, idCurso).get(0)[0].toString());
//				}
//			} else {
//				String GETFINCURSO = "select fecha_fin, price from cursillos where id = ?";
//				LocalDate fecha_fin = LocalDate.parse(db.executeQueryArray(GETFINCURSO, idCurso).get(0)[0].toString());
//				if(actual.isBefore(fecha_fin))
//					amount += Double.parseDouble(db.executeQueryArray(GETFINCURSO, idCurso).get(0)[1].toString());
//			}
//		}
//		return amount;
//	}

	public int getLastNumber() {
		if(getListaRecibos().isEmpty())
			return 0;
		return (int) db.executeQueryArray(SQL_LASTNUMBER).get(0)[0];
	}

	public void claimRecibo(String number) {
		db.executeUpdate(SQL_CLAIM_RECIBO, number);
	}
}
