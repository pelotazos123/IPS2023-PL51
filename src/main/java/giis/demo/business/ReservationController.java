package giis.demo.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import giis.demo.model.Reserva;
import giis.demo.util.Database;

public class ReservationController {
	
	private Database db;
	
	private final static int MOCK_ID_OWNER = 100;
	private final static String SQL_CARGAR_RESERVA = "select owner_id, fecha, hora, instalation_code from reservas";
	private final static String SQL_CREAR_RESERVA= "insert into reservas(owner_id, fecha, hora, instalation_code) values(?, ?, ?, ?)";
	
	private List<Object[]> resQuery;
	
	private List<Reserva> listaReservas = new ArrayList<Reserva>();
	
	public ReservationController(Database db) {
		this.db = db;
	}
	
	public void reservar(Date dia, String reserva, String instalacionId) {
		String fecha = reserva.split("-")[0];
		String hora = reserva.split("-")[1];
		createReservation(MOCK_ID_OWNER, fecha, hora, instalacionId);
		getReservas();
	}
	
	public List<Reserva> getReservaL(){
		listaReservas.add(new Reserva(100, "20/10/2023", "23:00", "1341"));
		return listaReservas;
	}
	
	public List<Reserva> getReservas() {
		listaReservas.removeAll(listaReservas);
		resQuery = db.executeQueryArray(SQL_CARGAR_RESERVA);
		
		int id = 0;
		String fecha = "";
		String hora = "";
		String instalacionId = "";
		
		for (Object[] objects : resQuery) {
			id = (int) objects[0];
			fecha = (String) objects[1];
			hora = (String) objects[2];
			instalacionId = (String) objects[3];
			
			listaReservas.add(new Reserva(id, fecha, hora, instalacionId));
		}
		
		for (Reserva reservaTP : listaReservas) {
			System.out.println(reservaTP.toString());
		}
		
		return listaReservas;		
	}
	
	private void createReservation(int mockIdOwner, String fecha, String hora, String instalacionId) {
		db.executeUpdate(SQL_CREAR_RESERVA, mockIdOwner, fecha, hora, instalacionId);
	}

}
