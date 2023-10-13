package giis.demo.model;

import java.util.Date;

import giis.demo.util.Database;

public class Reserva {
	
	private int owner_id;
	private Date date;
	private String reserva;
	private String instalacionId;
	
	private Database db;
	
	private final static String SQL_CREAR_RESERVA= "insert into reservas(owner_id, date, instalation_code) values(?, ?, ?)";
	private final static String SQL_CARGAR_RESERVA = "select owner_id, date, instalation_code from reservas";
	
	public Reserva(int owner_id, Date reserva, String reservaS, Instalacion instalacion, Database db) {
		this.owner_id = owner_id;
		this.reserva = reservaS;
		this.instalacionId = instalacion.getCode();
		this.db = db;
		createReservation();
		comprobarInsertado();
	}
	
	private void createReservation() {
		db.executeUpdate(SQL_CREAR_RESERVA, owner_id, reserva, instalacionId);
	}
	
	private void comprobarInsertado() {
		Object[] result = db.executeQueryArray(SQL_CARGAR_RESERVA, owner_id).get(0);
		int id = (int) result[0];
		String reserva = (String) result[1];
		String instalacionId = (String) result[2];
		
		System.out.println("\n"+"Id del socio: "+id+", reserva fecha: "+reserva+", instalacionId: "+instalacionId);
	}
	
}
