package giis.demo.business;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import giis.demo.model.Reserva;
import giis.demo.util.Database;

public class ReservationController {
	
	private Database db;
	
	private final static int MOCK_ID_OWNER = 100;
	private final static String SQL_CARGAR_RESERVA = "select owner_id, fecha, instalation_code, extra from reservas";
	private final static String SQL_CREAR_RESERVA= "insert into reservas(owner_id, fecha, instalation_code, extra) values(?, ?, ?, ?)";
	
	private List<Object[]> resQuery;
	
	private List<Reserva> listaReservas = new ArrayList<Reserva>();
	
	public ReservationController(Database db) {
		this.db = db;
	}
	
	public void reservar(LocalDateTime dia, String reserva, String instalacionId, boolean extra) {
		createReservation(MOCK_ID_OWNER, reserva, instalacionId, extra);
		getReservas();
	}
	
	public List<Reserva> getReservaL(){
		listaReservas.add(new Reserva(100, "20/10/2023", "23:00", "13410", false));
		return listaReservas;
	}
	
	public List<Reserva> getReservas() {
		listaReservas.removeAll(listaReservas);
		resQuery = db.executeQueryArray(SQL_CARGAR_RESERVA);
		
		int id = 0;
		String reserva = "";
		String instalacionId = "";
		String fecha = "";
		String hora = "";
		boolean extra = false;
		
		for (Object[] objects : resQuery) {
			id = (int) objects[0];
			reserva = (String) objects[1];
			instalacionId = (String) objects[2];
			extra = ((int) objects[3]) == 1;			
			
			fecha = reserva.split(" ")[0];
			hora = reserva.split(" ")[1];
			
			System.out.println(fecha + " || " + hora);
			
			listaReservas.add(new Reserva(id, fecha, hora, instalacionId, extra));
		}
		
		for (Reserva reservaTP : listaReservas) {
			System.out.println(reservaTP.toString());
		}
		
		return listaReservas;		
	}
	
	private void createReservation(int mockIdOwner, String reserva, String instalacionId, boolean extra) {
		db.executeUpdate(SQL_CREAR_RESERVA, mockIdOwner, reserva, instalacionId, extra);
	}

}
