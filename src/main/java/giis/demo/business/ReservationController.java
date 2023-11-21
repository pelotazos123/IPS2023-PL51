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
	private final static String SQL_CREAR_RESERVA = "INSERT INTO reservas(fecha, instalation_code, extra, tipo) VALUES (?, ?, ?, 'NORMAL')";
	private final static String SQL_ID_RESERVA = "SELECT seq FROM sqlite_sequence where name='reservas'";
	private final static String SQL_CARGAR_PARTICIPANTES = "SELECT * FROM participante_reserva";
	private final static String SQL_CREAR_PARTICIPANTE = "INSERT INTO participante_reserva (reserva_id, dni) VALUES (?, ?)";
	private final static String SQL_CARGAR_FECHAS_RESERVA = "SELECT DISTINCT fecha FROM reservas, participante_reserva WHERE participante_reserva.dni=? and reservas.instalation_code=?";
	private final static String SQL_ANULAR = "INSERT INTO reservas(fecha, instalation_code, extra, tipo) "
			+ "VALUES (?, ?, ?, 'ANULADA')"; 
	private final static String SQL_CARGA_NO_ANULADAS = "select * from reservas where tipo == 'NORMAL' "
			+ "and fecha = ? and instalation_code = ?";
	private final static String SQL_BORRA_RESERVA = "delete from reservas where fecha = ? and instalation_code = ?";
	private final static String SQL_CARGA_ANULADAS = "select * from reservas where tipo == 'ANULADA' "
			+ "and fecha = ? and instalation_code = ?";
	
	public final static int HORA_MAXIMA = 2;
	public final static int HORA_MINIMA = 1;
	
	private List<Object[]> resQuery;
	
	private List<Reserva> listaReservas = new ArrayList<Reserva>();
	
	public ReservationController(Database db) {
		this.db = db;
	}
	
	//TODO NECESARIO? CAMBIAR A VOID?
	public boolean anular(LocalDateTime dia, String reserva, String instalacionId) {
		createAnulation(reserva, instalacionId);
//		getReservas();
		return true;
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
		
		for (String dni: listaParticipantes) {
			System.out.println("id reserva: " + id_reserva + " - dni part: " + dni);
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
			
			listaReservas.add(new Reserva(id, fecha, hora, instalacionId, extra));
		}
		
		for (Reserva reservaS : listaReservas) {
			System.out.println(reservaS);
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
	
	private void createAnulation(String reserva, String instalacionId) {
		// TODO Auto-generated method stub
		db.executeUpdate(SQL_ANULAR, reserva, instalacionId, false);
	}

	public boolean getReservasNoAnuladasHora(String reserva, String idInst) {
		return !db.executeQueryArray(SQL_CARGA_NO_ANULADAS, reserva, idInst).isEmpty();
	}

	public void borraReserva(String idInst, String reserva) {
		System.out.println("Reserva borrada");
		db.executeUpdate(SQL_BORRA_RESERVA, reserva, idInst);
	}

	public boolean getReservasAnuladasHora(String reserva, String idInst) {
		return !db.executeQueryArray(SQL_CARGA_ANULADAS, reserva, idInst).isEmpty();
	}

}
