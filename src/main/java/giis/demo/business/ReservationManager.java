package giis.demo.business;

import java.util.Date;

import giis.demo.model.Instalacion;
import giis.demo.model.Reserva;
import giis.demo.util.Database;

public class ReservationManager {
	
	private Database db;
	
	private final static int MOCK_ID_OWNER = 100;
	
	public ReservationManager(Database db) {
		this.db = db;
	}
	
	public void reservar(Date dia, String reserva, Instalacion instalacion) {
		String fecha = reserva.split("-")[0];
		String hora = reserva.split("-")[1];
		Reserva reservaFinal = new Reserva(MOCK_ID_OWNER, dia, reserva, instalacion, db);
	}

}
