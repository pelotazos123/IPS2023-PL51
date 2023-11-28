package giis.demo.business;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import giis.demo.model.Instalacion;
import giis.demo.model.Reserva;
import giis.demo.util.Database;
import giis.demo.util.Util;

public class ReservationController {
	
	private Database db;
	
	public final static int HORA_MAXIMA = 2;
	public final static int HORA_MINIMA = 1;
	
	public final static int HORA_MAXIMA_CURSO = 4;
	public final static int HORA_MINIMA_CURSO = 2;
	
	public final static int HORARIO_INICIO = 9;
	public final static int HORARIO_FIN = 23;
	
	public final static int MAX_ENTRENADORES = 4;
	public final static int EMPTY = 0;
	
	public final static String CURSO_OCUPADO = "RESERVADO PARA CURSO";
	public final static String METEOROLOGIA = "ANULADO METEOROLOGÍA";
	
	public final static String TIPO_CURSO = "CURSO";
	public final static String TIPO_RESERVA = "RESERVA";
	public final static String TIPO_ANULADA = "ANULADA";
	
	// Mapeo de dias para trabajar con los dias en español de el ENUM DaysOfWeek
	public final static Map<String,String> DIAS_SEMANA = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;

		{
			this.put("lunes", "MONDAY");
		    this.put("martes", "TUESDAY");
		    this.put("miércoles", "WEDNESDAY");
		    this.put("jueves", "THURSDAY");
		    this.put("viernes", "FRIDAY");
		    this.put("sábado", "SATURDAY");
		    this.put("domingo", "SUNDAY");
		}
	};
	
	private List<Object[]> resQuery;
	
	private List<Reserva> listaReservas = new ArrayList<Reserva>();
	
	public ReservationController(Database db) {
		this.db = db;
	}
	
	public boolean anular(String hora_Inicio, String instalacionId) {
		createAnulation(hora_Inicio, instalacionId);
//		getReservas();
		return true;
	}

	public boolean reservar(LocalDateTime diaInicio, String reservaInicio, String reservaFin, Instalacion instalacion, List<String> listaParticipantes) {
		if (!checkParticipantsAvailability(diaInicio, instalacion, listaParticipantes))
			return false;
		createReservation(reservaInicio, reservaFin, instalacion.getCode(), TIPO_RESERVA);
		createQueryParticipants(listaParticipantes);
		getReservas();
		getParticipantes();
		return true;
	}
	
	public boolean creacionCurso(String nombreCurso, LocalDate diaInicio, LocalDate diaFin, LocalTime horaInicio, LocalTime horaFin,
			List<String> entrenadores, List<DayOfWeek> dias, Instalacion instalacion, double coste, int numPlazas) {
		
		LocalDateTime inicioCurso = diaInicio.atTime(horaInicio);
		LocalDateTime finalCurso = diaFin.atTime(horaFin);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String inicioCursoStr = inicioCurso.format(dtf);
		String finalCursoStr = finalCurso.format(dtf);
		
		if (!getDisponibilidadDeCursos(instalacion, inicioCursoStr, finalCursoStr)) 
			return false;
		
		// Se crea el curso
		createCurso(nombreCurso, instalacion, coste, inicioCursoStr, finalCursoStr, numPlazas);
		// Añade los entrenadores al curso
		createQueryTrainers(entrenadores, nombreCurso);
		
		// Se reservan los dias que habrá en el rango entre la fecha de inicio y fecha final del curso
		List<LocalDate> fechas = diaInicio.datesUntil(diaFin).toList();		
		for (LocalDate fecha: fechas) {
			if (dias.contains(fecha.getDayOfWeek())) { // Solo si coinciden con los dias de la semana seleccionados para el curso
				createReservation(fecha.atTime(horaInicio).format(dtf), fecha.atTime(horaFin).format(dtf), instalacion.getCode(), TIPO_CURSO);
			}
		}
		return true;
	}

	private void createCurso(String nombreCurso, Instalacion instalacion, double coste, String inicioCursoStr,
			String finalCursoStr, int plazas) {
		String SQL_CREAR_CURSO = "INSERT INTO cursillos(nombre, code_instalacion, fecha_inicio, fecha_fin, price, plazas) VALUES (?, ?, ?, ?, ?, ?)";
		db.executeUpdate(SQL_CREAR_CURSO, nombreCurso, instalacion.getCode(), inicioCursoStr, finalCursoStr, coste, plazas);
	}
	
	/**
	 * Comprueba la disponibilidad de la instalacion pasada por parametro
	 * @param instalacion seleccionada para el curso
	 * @return true si está disponible, false si no
	 */
	private boolean getDisponibilidadDeCursos(Instalacion instalacion, String fechaInicio, String fechaFinal){
		String SQL_CARGAR_CURSOS = "SELECT id FROM cursillos WHERE fecha_inicio <= ? and fecha_fin >= ? and code_instalacion = ?";
		List<Object[]> queryRes = db.executeQueryArray(SQL_CARGAR_CURSOS, fechaInicio, fechaFinal, instalacion.getCode());
		
		if (queryRes.size() != 0) {
			JOptionPane.showMessageDialog(null, "Ya hay un curso en esta instalación.", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	private boolean checkParticipantsAvailability(LocalDateTime dia, Instalacion instalacion, List<String> participantes) {
		List<Object[]> queryRes = new ArrayList<>();
		List<LocalDateTime> dates = new ArrayList<LocalDateTime>();
		LocalDateTime reserva = null;
		LocalDateTime yesterday = null;
		LocalDateTime tomorrow = null;
		String SQL_CARGAR_FECHAS_RESERVA = "SELECT DISTINCT fecha_inicio FROM reservas, participante_reserva WHERE participante_reserva.dni=? and reservas.instalation_code=? and reservas.tipo=?";
				
		for (String dni: participantes) {
			queryRes = db.executeQueryArray(SQL_CARGAR_FECHAS_RESERVA, dni, instalacion.getCode(), "");
			for (Object[] objects : queryRes) {
				reserva = Util.isoStringToDateHour((String) objects[0]).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				dates.add(reserva);
			}
			
			for (LocalDateTime reservao : dates) { // Comprueba que en las 24h siguientes y anteriores ninguno de los participantes está en otra reserva
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
	
	private void createQueryTrainers(List<String> listaEntrenadores, String nameCurso) {
		String SQL_ID_CURSO = "SELECT seq FROM sqlite_sequence where name='cursillos'";
		String SQL_AÑADIR_ENTRENADORES = "INSERT INTO entrenadores_cursillos (id_curso, dni) VALUES (?,?)";
		
		int id_curso = (int) db.executeQueryArray(SQL_ID_CURSO).get(0)[0];
		
		for (String dni: listaEntrenadores) {
			System.out.println("id curso: " + id_curso + " - nombre curso: " + nameCurso + " - dni entrenador: " + dni);
			db.executeUpdate(SQL_AÑADIR_ENTRENADORES, id_curso, dni);
		}
	}

	private void createQueryParticipants(List<String> listaParticipantes) {
		String SQL_ID_RESERVA = "SELECT seq FROM sqlite_sequence where name='reservas'";
		String SQL_CREAR_PARTICIPANTE = "INSERT INTO participante_reserva (reserva_id, dni) VALUES (?, ?)";
		
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
		String SQL_CARGAR_RESERVA = "SELECT id, fecha_inicio, fecha_fin, instalation_code, tipo FROM reservas";
		listaReservas.removeAll(listaReservas);
		resQuery = db.executeQueryArray(SQL_CARGAR_RESERVA);
		
		int id = 0;
		String reservaInicio = "";
		String reservaFin = "";
		String instalacionId = "";
		String fechaInicio = "";
		String horaInicio = "";
		String fechaFin = "";
		String horaFin = "";
		String tipoCurso = "";
		
		for (Object[] objects : resQuery) {
			id = (int) objects[0];
			reservaInicio = (String) objects[1];
			reservaFin = (String) objects[2];
			instalacionId = (String) objects[3];
			tipoCurso = (String) objects[4];
			
			fechaInicio = reservaInicio.split(" ")[0];
			horaInicio = reservaInicio.split(" ")[1];
			fechaFin= reservaFin.split(" ")[0];
			horaFin= reservaFin.split(" ")[1];
			
			listaReservas.add(new Reserva(id, fechaInicio, horaInicio, fechaFin, horaFin, instalacionId, tipoCurso));
		}
		
		for (Reserva reservaS : listaReservas) {
			System.out.println(reservaS);
		}
		
		return listaReservas;		
	}
	
	
	public void getParticipantes() {
		String SQL_CARGAR_PARTICIPANTES = "SELECT * FROM participante_reserva";
		List<Object[]> resQuery = db.executeQueryArray(SQL_CARGAR_PARTICIPANTES);
		int id = 0;
		String dni = "";
		
		for (Object[] objects : resQuery) {
			id = (int) objects[0];
			dni = (String) objects[2];
			
			System.out.println(id + " |hola| " + dni);
		}
	}
	
	private void createReservation(String reservaInicio, String reservaFin, String instalacionId, String tipoReserva) {
		String SQL_CREAR_RESERVA = "INSERT INTO reservas(fecha_inicio, fecha_fin, instalation_code, tipo) VALUES (?, ?, ?, ?)";
		db.executeUpdate(SQL_CREAR_RESERVA, reservaInicio, reservaFin, instalacionId, tipoReserva);
	}
	
	private void createAnulation(String hora_Inicio, String instalacionId) {
//		String SQL_ANULAR = "UPDATE reservas SET tipo='ANULADA' WHERE fecha_inicio=? AND instalation_code=? AND tipo!='ANULADA'";
//		db.executeUpdate(SQL_ANULAR, hora_Inicio, instalacionId);
		String GET_RESERVAS = "select tipo from reservas where fecha_inicio = ? and instalation_code = ?";
		if(db.executeQueryArray(GET_RESERVAS, hora_Inicio, instalacionId).size() == 0){
			String SQL_ANULAR = "insert into reservas(fecha_inicio, fecha_fin, instalation_code, tipo) "
					+ "values (?,?,?,'ANULADA')";
			LocalDateTime horaFin = LocalDateTime.parse(hora_Inicio, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			horaFin.plusHours(1);
			String horaFinReserva = horaFin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			db.executeUpdate(SQL_ANULAR, hora_Inicio, horaFinReserva, instalacionId);
		} else {
			String SQL_ANULAR = "UPDATE reservas SET tipo='ANULADA' WHERE fecha_inicio=? "
					+ "AND instalation_code=? AND tipo!='ANULADA'";
			db.executeUpdate(SQL_ANULAR, hora_Inicio, instalacionId);
		}
	}

	public boolean getReservasNoAnuladasHora(String fechaInicio, String idInst) {
		String SQL_CARGA_NO_ANULADAS = "select fecha_inicio from reservas where tipo == 'NORMAL' "
				+ "and fecha_inicio = ? and instalation_code = ?";
		return !db.executeQueryArray(SQL_CARGA_NO_ANULADAS, fechaInicio, idInst).isEmpty();
	}

	public void borraReserva(String idInst, String horaInicio) {
		String SQL_BORRA_RESERVA = "delete from reservas where fecha_inicio = ? and instalation_code = ?";
		db.executeUpdate(SQL_BORRA_RESERVA, horaInicio, idInst);
	}

	public boolean getReservasAnuladasHora(String horaInicio, String idInst) {
		String SQL_CARGA_ANULADAS = "select fecha_inicio from reservas where tipo == 'ANULADA' "
				+ "and fecha_inicio = ? and instalation_code = ?";
		return !db.executeQueryArray(SQL_CARGA_ANULADAS, horaInicio, idInst).isEmpty();
	}

}

