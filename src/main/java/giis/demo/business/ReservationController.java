package giis.demo.business;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import giis.demo.model.Instalacion;
import giis.demo.model.Reserva;
import giis.demo.util.Database;

public class ReservationController {
	
	private Database db;
	
	private final static String SQL_CARGAR_RESERVA = "select id, fecha, instalation_code, extra from reservas";
	private final static String SQL_CREAR_RESERVA = "insert into reservas(fecha, instalation_code, extra) values (?, ?, ?)";
	private final static String SQL_ID_RESERVA = "SELECT LAST_INSERT_ROWID() FROM reservas";
	private final static String SQL_CREAR_PARTICIPANTE = "INSERT INTO participante_reserva (reserva_id, dni) VALUES (?, ?)";
	
	private List<Object[]> resQuery;
	
	private List<Reserva> listaReservas = new ArrayList<Reserva>();
	
	public ReservationController(Database db) {
		this.db = db;
	}
	
	public void reservar(LocalDateTime dia, String reserva, Instalacion instalacionId, List<String> listaParticipantes, boolean extra) {
		createReservation(reserva, instalacionId.getCode(), extra);
		createQueryParticipants(listaParticipantes);
		getReservas();
		getParticipantes();
	}
	
	private void createQueryParticipants(List<String> listaParticipantes) {
		int id_reserva = (int) db.executeQueryArray(SQL_ID_RESERVA).get(0)[0];
		
		for (String dni: listaParticipantes) {
			System.out.println(id_reserva + " - " + dni);
			db.executeUpdate(SQL_CREAR_PARTICIPANTE, id_reserva, dni);
		}
	}

	public List<Reserva> getReservaL(){
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
			System.out.println(reservaTP.getId());
		}
		
		return listaReservas;		
	}
	
	public void getParticipantes() {
		List<Object[]> resQuery = db.executeQueryArray(SQL_CARGAR_RESERVA);
		int id = 0;
		String dni = "";
		
		for (Object[] objects : resQuery) {
			id = (int) objects[0];
			dni = (String) objects[1];
			
			System.out.println(id + " |hola| " + dni);
		}
	}
	
	private void createReservation(String reserva, String instalacionId, boolean extra) {
		db.executeUpdate(SQL_CREAR_RESERVA, reserva, instalacionId, extra);
	}

}
