package giis.demo.model.competiciones.servicio;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import giis.demo.model.Socio;
import giis.demo.model.TiposCuotas;
import giis.demo.model.TiposDeportes;
import giis.demo.model.competiciones.Competicion;
import giis.demo.model.competiciones.EstadoCompeticion;
import giis.demo.model.loggin.Correo;
import giis.demo.util.Database;
import giis.demo.util.FileUtil;

public class GestionarCompeticiones {
	
	private Database db;
	private List<Competicion> listaCompeticiones;

	public GestionarCompeticiones(Database db) {
		this.db = db;
		cargarCompeticiones();
	}
	
	public void añadirCompeticiones(File archivo) {
		String SQL_AÑADIR_COMPETICIONES = "insert into competiciones (id, name, competition_date, place, categories, deporte, estado) values(?, ?, ?, ?, ?, ?, ?)";
		
		List<Competicion> lista = FileUtil.loadFileCompeticiones(archivo,generarId());
		for (Competicion competicion : lista) {
			String categorias = "";
			for(int i = 0; i < competicion.getCategorias().size(); i++) {
				categorias += competicion.getCategorias().get(i);
				if(i < competicion.getCategorias().size()-1) {
					categorias += "-";
				}
			}
			db.executeUpdate(SQL_AÑADIR_COMPETICIONES, competicion.getId(), competicion.getNombre(), competicion.getFecha().toString(), competicion.getLugar(),categorias, competicion.getDeporte(), competicion.getEstado());
		}
	}

	public void cargarCompeticiones() {
		String SQL_OBTENER_COMPETICIONES = "select * from competiciones";
		listaCompeticiones = new ArrayList<Competicion>();
		List<Object[]> result = db.executeQueryArray(SQL_OBTENER_COMPETICIONES);
		for(int i = 0; i < result.size(); i++) {
			Object[] competicion = result.get(i);
			
			int id = (int) competicion[0];
			String nombre = (String) competicion[1];
			String fecha = (String) competicion[2];
			String lugar = (String) competicion[3];
			String categorias = (String) competicion[4];
			String deporte = (String) competicion[5];
			String estado = (String) competicion[6];
			
			String[] str = fecha.split("-");
			int año = Integer.parseInt(str[0]);
			int mes = Integer.parseInt(str[1]);
			int dia = Integer.parseInt(str[2]);
			LocalDate fechaCompe = LocalDate.of(año, mes, dia);
			
			TiposDeportes deporteCompeticion = getTipoDeporte(deporte);
			EstadoCompeticion estadoCompeticion = getEstadoCompeticion(estado);
			
			List<TiposCuotas> listaCategorias = new ArrayList<TiposCuotas>();
			String[] aux= categorias.split("-");
			for(int j = 0; j < aux.length; j++) {
				if(aux[j].equals("SUB18")) {
					listaCategorias.add(TiposCuotas.SUB18);
				}else if(aux[j].equals("SENIOR")) {
					listaCategorias.add(TiposCuotas.SENIOR);
				}else {
					listaCategorias.add(TiposCuotas.VETERANO);
				}
			}
			listaCompeticiones.add(new Competicion(id, nombre, fechaCompe, lugar, listaCategorias, deporteCompeticion, estadoCompeticion));
		}
	}
	
	public List<Competicion> getTodasLasCompeticiones(){
		List<Competicion> lista = new ArrayList<>();
		for (Competicion competicion : listaCompeticiones) {
			lista.add(competicion);
		}
		return lista;
	}
	
	public List<Competicion> getCompeticionesDisponibles(TiposCuotas categoria){
		List<Competicion> lista = new ArrayList<>();
		for (Competicion competicion : listaCompeticiones) {
			if(competicion.getCategorias().contains(categoria)) {
				lista.add(competicion);
			}
		}
		return lista;
	}
	
	public void inscribirSocio(int idCompeticion, int idSocio) {
		String SQL_INSCRIBIR_SOCIO = "insert into inscripcion_competiciones (competicion_id, socio_id) values(?, ?)";
		db.executeUpdate(SQL_INSCRIBIR_SOCIO, idCompeticion,idSocio);
	}

	public boolean comprobarSiSePuedeInscribir(int idCompeticion, int idSocio) {
		String SQL_OBTENER_COMPETICIONES_DE_SOCIO = "select competicion_id from inscripcion_competiciones where socio_id=?";
		String SQL_OBTENER_FECHA_COMPETICION = "select competition_date from competiciones where id=?";
		
		List<Object[]> competicionesIDs = db.executeQueryArray(SQL_OBTENER_COMPETICIONES_DE_SOCIO,idSocio);
		
		String fecha = (String) db.executeQueryArray(SQL_OBTENER_FECHA_COMPETICION,idCompeticion).get(0)[0];
		LocalDate fechaCompeSolicitada = LocalDate.parse(fecha);
		
		for (Object[] competicionId : competicionesIDs) {
			fecha = (String) db.executeQueryArray(SQL_OBTENER_FECHA_COMPETICION,competicionId[0]).get(0)[0];
			LocalDate fechaCompe = LocalDate.parse(fecha);
			if(fechaCompe.isEqual(fechaCompeSolicitada) && (int)competicionId[0] != idCompeticion) {
				return false;
			}
		}
		
		
		return true;
	}
	
	public static List<Object[]> getCompeticionesNoAnuladas(Database db) {
		String SQL_OBTENER_COMPETICIONES_NO_ANULADAS = "SELECT id, place, deporte FROM competiciones WHERE estado='ABIERTA'";
		
		List<Object[]> competiciones = db.executeQueryArray(SQL_OBTENER_COMPETICIONES_NO_ANULADAS);
		return competiciones;
	}
	
	public boolean comprobarSiEstaFederado(int idCompeticion, int idSocio) {
		String SQL_OBTENER_DEPORTE_COMPETICION = "select deporte from competiciones where id=?";
		String SQL_OBTENER_DEPORTES_SOCIO= "select deporte from licencias where owner_id=?";
		
		TiposDeportes deporteCompeticion = getTipoDeporte((String) db.executeQueryArray(SQL_OBTENER_DEPORTE_COMPETICION,idCompeticion).get(0)[0]);
		
		List<Object[]> licenciasSocio =  db.executeQueryArray(SQL_OBTENER_DEPORTES_SOCIO,idSocio);
		
		for (Object[] licencia : licenciasSocio) {
			TiposDeportes deporteSocio =  getTipoDeporte((String) licencia[0]);
			if(deporteCompeticion.equals(deporteSocio)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Anula la competicion si cumple con las condiciones
	 * @param day en el que hace mal tiempo
	 * @param db 
	 * @param idCompeticion
	 */
	public static void createAnulation(String day, Database db, int idCompeticion) {
		System.out.println(day + "dia?");
		if (!checkDates(day, db, idCompeticion)) return;
			
		
		if (!checkOnlyOneCall(db, idCompeticion, day)) return;
		Competicion competi = getCompeticion(idCompeticion, db);
		
		String fecha = competi.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String cuerpoHTML = getBodyMailCancelation(competi, fecha);
		
		System.out.println(cuerpoHTML);
		
		List<String> correosSocios = getCorreosFromInscritos(idCompeticion, db);
		avisarAnulacion(correosSocios, idCompeticion, cuerpoHTML);
	}

	/**
	 * Comprueba que las fechas tienen sentido a la hora de hacer las cancelaciones
	 * @param day
	 * @param db
	 * @param idCompeticion
	 * @return
	 */
	private static boolean checkDates(String day, Database db, int idCompeticion) {
		String SQL_GET_DATE_FROM_ID = "SELECT competition_date FROM competiciones WHERE id=?";
		
		String res = ((String)db.executeQueryArray(SQL_GET_DATE_FROM_ID, idCompeticion).get(0)[0]);
	
		LocalDate fechaCompe = LocalDate.parse(res);
		LocalDate fechaCall = LocalDate.parse(day);
		
		// Solo se comprueba el tiempo con 4 dias de prevision como mucho
		if (!fechaCall.equals(fechaCompe) || fechaCall.isBefore(LocalDate.now()) ||
				fechaCall.isAfter(LocalDate.now().plusDays(4))) {
			System.out.println("Dia de competicion inválido");
			return false;
		}
		return true;
	}

	private static boolean checkOnlyOneCall(Database db, int idCompeticion, String dia) {
		String SQL_ANULAR = "UPDATE competiciones SET estado='CANCELADA' WHERE estado='ABIERTA' AND id=?";
		String SQL_GET_STATE = "SELECT estado FROM competiciones WHERE id=?";
		String prevState = ((String)db.executeQueryArray(SQL_GET_STATE, idCompeticion).get(0)[0]);
		
		db.executeUpdate(SQL_ANULAR, idCompeticion); //Actualiza el estado de la competicion a ANULADA
		
		String postState = ((String)db.executeQueryArray(SQL_GET_STATE, idCompeticion).get(0)[0]);
		
		if (prevState.equals(postState)) { // Se comprueba si el estado previo y posterior de la competicion son iguales
			System.out.println("llamada repetida"); // para no mandar correos repetidos debido a las multiples llamadas concurrentes
			return false;
		}
		return true;
	}

	private static String getBodyMailCancelation(Competicion competi, String fecha) {
		return " <meta charset=\"UTF-8\"><html>" +
                "<body>" +
                "<p style=\"font-size: 16px;\">Estimado socio,</p>" +
                "<p style=\"font-weight: bold; color: red;\"><strong>¡Importante!</strong> La competición en la que iba a participar ha sido cancelada debido a las condiciones climatológicas.</p>" +
                "<br />" + 
                "<p>Detalles de la competición:</p>" +
                "<ul>" +
                "<li><strong>Nombre de la Competición:</strong> " + competi.getNombre() + "</li>" +
                "<li><strong>Fecha:</strong> " + fecha + "</li>" +
                "<li><strong>Lugar:</strong> " + competi.getLugar() + "</li>" +
                "<li><strong>Deporte:</strong> " + competi.getDeporte() + "</li>" +
                "</ul>" +
                "<p>Le pedimos disculpas por cualquier inconveniente que esto pueda causar.</p>" +
                "<p>Atentamente,</p>" +
                "<p>El Club Deportivo</p>" +
                "</body>" +
                "</html>";
	}
	
	private static void avisarAnulacion(List<String> correosSocios, int idCompeticion, String cuerpoCorreo) {
		
		System.out.println("Anulacion correcta");
		 // Crear un pool de hilos
        ExecutorService executorService = null;
		try {
			executorService = Executors.newFixedThreadPool(correosSocios.size());
			
			// Crear instancias de Correo y ejecutarlas en hilos separados
	        for (String correoSocio : correosSocios) {
	        	System.out.println("Correo enviado");
	            Correo correo = new Correo(correoSocio, "Cancelación de su competición", cuerpoCorreo, Correo.TIPO_TXT_MAIL_HTML);
	            executorService.submit(correo);
	        }

		} catch (Exception e) {
			System.err.println("Error con algun hilo");
		} finally {
	        // Apagar el pool de hilos después de que todos los trabajadores hayan terminado
			executorService.shutdown();
		}

	}
	
	private static Competicion getCompeticion(int idCompeticion, Database db) {
		String SQL_CARGAR_COMPETICION_POR_ID = "SELECT name, competition_date, place, deporte FROM competiciones WHERE id=?";
		Object[] competicion = db.executeQueryArray(SQL_CARGAR_COMPETICION_POR_ID, idCompeticion).get(0);
		LocalDate fechaCompe = LocalDate.parse((String)competicion[1]);
		
		return new Competicion(idCompeticion, (String)competicion[0], fechaCompe, (String)competicion[2], getTipoDeporte((String)competicion[3]));
	}

	private static List<String> getCorreosFromInscritos(int idCompeticion, Database db) {
		String SQL_OBTENER_SOCIOS_INSCRITOS_COMPETICION = "select socio_id from inscripcion_competiciones where competicion_id=?";
		String SQL_OBETENER_CORREO_SOCIOS = "SELECT email FROM socios WHERE id=?";
		
		List<Object[]> queryRes = db.executeQueryArray(SQL_OBTENER_SOCIOS_INSCRITOS_COMPETICION, idCompeticion);
		List<Integer> idsInscritos = new ArrayList<Integer>();
		for (Object[] id : queryRes) {
			idsInscritos.add((int)id[0]);
		}
		
		List<String> correos = new ArrayList<String>();
		for (Integer idSocio : idsInscritos) {
			correos.add((String)db.executeQueryArray(SQL_OBETENER_CORREO_SOCIOS, idSocio).get(0)[0]);;
		}
		
		return correos;
	}
	
	private static TiposDeportes getTipoDeporte(String deporte) {
		TiposDeportes deporteFinal = null;
		switch (deporte) {
			case "FUTBOL":
				deporteFinal = TiposDeportes.FUTBOL;
				break;
			case "TENIS":
				deporteFinal = TiposDeportes.TENIS;
				break;
			case "TIRO_CON_ARCO":
				deporteFinal = TiposDeportes.TIRO_CON_ARCO;
				break;
			case "NATACION":
				deporteFinal = TiposDeportes.NATACION;
				break;
		}
		return deporteFinal;
	}
	
	public static EstadoCompeticion getEstadoCompeticion(String state) {
		EstadoCompeticion estado;
		switch (state) {
			case "ABIERTA":
				estado = EstadoCompeticion.ABIERTA;
				break;
			case "CANCELADA":
				estado = EstadoCompeticion.CANCELADA;
				break;
			default:
				estado = EstadoCompeticion.ABIERTA;
				break;
		}
		
		return estado;
	}

	public void generarListaSocios(int idCompeticion,File archivo) {
		String SQL_OBTENER_SOCIOS_INSCRITOS_COMPETICION = "select socio_id from inscripcion_competiciones where competicion_id=?";
		
		List<Object[]> result = db.executeQueryArray(SQL_OBTENER_SOCIOS_INSCRITOS_COMPETICION,idCompeticion);
		
		List<Socio> sociosOrdenadosPorCategoria = new ArrayList<>();
		
		for(TiposCuotas categoria : TiposCuotas.values()) {
			for(int i = 0; i < result.size(); i++) {
				int socioId = (int) result.get(i)[0];
				Socio socio = new Socio(db, socioId);
				if(socio.getTipoCuota().equals(categoria) && !sociosOrdenadosPorCategoria.contains(socio)) {
					sociosOrdenadosPorCategoria.add(socio);
				}
			}
		}
		FileUtil.saveToFileSociosInscritos(archivo, sociosOrdenadosPorCategoria);
	}
	
	public List<Competicion> getCompeticionesFiltradas(boolean sub18, boolean senior, boolean veterano) {
		List<Competicion> lista = new ArrayList<>();
		for (Competicion competicion : listaCompeticiones) {
			List<TiposCuotas> categorias = competicion.getCategorias();
			if (sub18 && categorias.contains(TiposCuotas.SUB18)) {
				lista.add(competicion);
			}else if(senior && categorias.contains(TiposCuotas.SENIOR)) {
				lista.add(competicion);
			}else if(veterano && categorias.contains(TiposCuotas.VETERANO)) {
				lista.add(competicion);
			}else if(!sub18 && !senior && !veterano){
				lista.add(competicion);
			}
		}
		return lista;

	}
	
	private int generarId() {
		String SQL_OBTENER_IDS= "select id from competiciones";
		List<Object[]> result = db.executeQueryArray(SQL_OBTENER_IDS);
		return result.size() == 0? 1 : ((int) result.get(result.size()-1)[0])+1;
	}
	
	public boolean comprobarSocioYaInscrito(int idCompeticion, int idSocio) {
		String SQL_OBTENER_SOCIO_INSCRITO = "select * from inscripcion_competiciones where competicion_id=? and socio_id=?";
		List<Object[]> result = db.executeQueryArray(SQL_OBTENER_SOCIO_INSCRITO,idCompeticion,idSocio);
		return !result.isEmpty();
	}
}
