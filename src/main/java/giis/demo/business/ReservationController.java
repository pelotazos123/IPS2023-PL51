package giis.demo.business;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import giis.demo.model.Instalacion;
import giis.demo.model.Reserva;
import giis.demo.util.Database;
import giis.demo.util.Util;

public class ReservationController {
	
	private Database db;
	
	private final static String SQL_CARGAR_RESERVA = "SELECT id, fecha, instalation_code, extra FROM reservas";
	private final static String SQL_CREAR_RESERVA = "INSERT INTO reservas(fecha, instalation_code, extra) VALUES (?, ?, ?)";
	private final static String SQL_ID_RESERVA = "SELECT seq FROM sqlite_sequence where name='reservas'";
	private final static String SQL_CARGAR_PARTICIPANTES = "SELECT * FROM participante_reserva";
	private final static String SQL_CREAR_PARTICIPANTE = "INSERT INTO participante_reserva (reserva_id, dni) VALUES (?, ?)";
	private final static String SQL_CARGAR_FECHAS_RESERVA = "SELECT DISTINCT fecha FROM reservas, participante_reserva WHERE participante_reserva.dni=? and reservas.instalation_code=?";
	
	private List<Object[]> resQuery;
	
	private List<Reserva> listaReservas = new ArrayList<Reserva>();
	
	public ReservationController(Database db) {
		this.db = db;
	}
	
	public boolean reservar(LocalDateTime dia, String reserva, Instalacion instalacion, List<String> listaParticipantes, boolean extra) {
		if (!checkParticipantsAvailability(dia, instalacion, listaParticipantes))
			return false;
		createReservation(reserva, instalacion.getCode(), extra);
		createQueryParticipants(listaParticipantes);
		getReservas();
		getParticipantes();
		return true;
	}
	
	private boolean checkParticipantsAvailability(LocalDateTime dia, Instalacion instalacion, List<String> participantes) {
		List<Object[]> queryRes = new ArrayList<>();
		List<LocalDateTime> dates = new ArrayList<LocalDateTime>();
		LocalDateTime reserva = null;
		LocalDateTime yesterday = null;
		LocalDateTime tomorrow = null;
				
		for (String dni: participantes) {
			queryRes = db.executeQueryArray(SQL_CARGAR_FECHAS_RESERVA, dni, instalacion.getCode());
			for (Object[] objects : queryRes) {
				reserva = Util.isoStringToDate((String) objects[0]).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				dates.add(reserva);
			}
			
			for (LocalDateTime reservao : dates) {
				yesterday = reservao.minusDays(1);
				tomorrow = reservao.plusDays(1);
				
				if ((dia.isAfter(yesterday) && dia.isBefore(reservao)) || (dia.isAfter(reservao) && dia.isBefore(tomorrow))) {
					JOptionPane.showMessageDialog(null, "El socio de dni: " + dni + " ya está participando en una reserva en\n"
							+ "esta instalación ("+instalacion.getName()+"). \n"
									+ "Espere 24h desde la primera reserva para la poder realizar la siguiente reserva.");
					return false; // No cumple
				}
			}
		}
		
		return true;
	}

	private void createQueryParticipants(List<String> listaParticipantes) {
		int id_reserva = (int) db.executeQueryArray(SQL_ID_RESERVA).get(0)[0];
		
		System.out.println("loco aver: " + id_reserva);
		
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
		List<Object[]> resQuery = db.executeQueryArray(SQL_CARGAR_PARTICIPANTES);
		int id = 0;
		String dni = "";
		
		for (Object[] objects : resQuery) {
			id = (int) objects[0];
			dni = (String) objects[2];
			
			System.out.println(id + " |hola| " + dni);
		}
	}
	
	private void createReservation(String reserva, String instalacionId, boolean extra) {
		db.executeUpdate(SQL_CREAR_RESERVA, reserva, instalacionId, extra);
	}

}
