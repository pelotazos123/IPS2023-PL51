package giis.demo.model.competiciones.servicio;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import giis.demo.model.Socio;
import giis.demo.model.TiposCuotas;
import giis.demo.model.TiposDeportes;
import giis.demo.model.competiciones.Competicion;
import giis.demo.util.Database;
import giis.demo.util.FileUtil;

public class GestionarCompeticiones {
	
	private final static String SQL_OBTENER_COMPETICIONES = "select * from competiciones";
	private final static String SQL_OBTENER_DEPORTE_COMPETICION = "select deporte from competiciones where id=?";
	private final static String SQL_OBTENER_FECHA_COMPETICION = "select competition_date from competiciones where id=?";
	private final static String SQL_OBTENER_SOCIOS_INSCRITOS_COMPETICION = "select socio_id from inscripcion_competiciones where competicion_id=?";
	private final static String SQL_OBTENER_SOCIO_INSCRITO = "select * from inscripcion_competiciones where competicion_id=? and socio_id=?";
	private final static String SQL_OBTENER_COMPETICIONES_DE_SOCIO = "select competicion_id from inscripcion_competiciones where socio_id=?";
	private final static String SQL_OBTENER_IDS= "select id from competiciones";
	private final static String SQL_OBTENER_DEPORTES_SOCIO= "select deporte from licencias where owner_id=?";
	
	private final static String SQL_INSCRIBIR_SOCIO = "insert into inscripcion_competiciones (competicion_id, socio_id) values(?, ?)";
	private final static String SQL_AÑADIR_COMPETICIONES = "insert into competiciones (id, name, competition_date, place, categories, deporte) values(?, ?, ?, ?, ?, ?)";
	
	private Database db;
	private List<Competicion> listaCompeticiones;

	public GestionarCompeticiones(Database db) {
		this.db = db;
		cargarCompeticiones();
	}
	
	public void añadirCompeticiones(File archivo) {
		List<Competicion> lista = FileUtil.loadFileCompeticiones(archivo,generarId());
		for (Competicion competicion : lista) {
			String categorias = "";
			for(int i = 0; i < competicion.getCategorias().size(); i++) {
				categorias += competicion.getCategorias().get(i);
				if(i < competicion.getCategorias().size()-1) {
					categorias += "-";
				}
			}
			db.executeUpdate(SQL_AÑADIR_COMPETICIONES, competicion.getId(), competicion.getNombre(), competicion.getFecha().toString(), competicion.getLugar(),categorias, competicion.getDeporte());
		}
	}

	public void cargarCompeticiones() {
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
			
			String[] str = fecha.split("-");
			int año = Integer.parseInt(str[0]);
			int mes = Integer.parseInt(str[1]);
			int dia = Integer.parseInt(str[2]);
			LocalDate fechaCompe = LocalDate.of(año, mes, dia);
			
			TiposDeportes deporteCompeticion = getTipoDeporte(deporte);
			
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
			listaCompeticiones.add(new Competicion(id, nombre, fechaCompe, lugar, listaCategorias, deporteCompeticion));
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
		db.executeUpdate(SQL_INSCRIBIR_SOCIO, idCompeticion,idSocio);
	}

	public boolean comprobarSiSePuedeInscribir(int idCompeticion, int idSocio) {
		List<Object[]> competicionesIDs = db.executeQueryArray(SQL_OBTENER_COMPETICIONES_DE_SOCIO,idSocio);
		
		String fecha = (String) db.executeQueryArray(SQL_OBTENER_FECHA_COMPETICION,idCompeticion).get(0)[0];
		String[] str = fecha.split("-");
		int año = Integer.parseInt(str[0]);
		int mes = Integer.parseInt(str[1]);
		int dia = Integer.parseInt(str[2]);
		LocalDate fechaCompeSolicitada = LocalDate.of(año, mes, dia);
		
		
		for (Object[] competicionId : competicionesIDs) {
			fecha = (String) db.executeQueryArray(SQL_OBTENER_FECHA_COMPETICION,competicionId[0]).get(0)[0];
			str = fecha.split("-");
			año = Integer.parseInt(str[0]);
			mes = Integer.parseInt(str[1]);
			dia = Integer.parseInt(str[2]);
			LocalDate fechaCompe = LocalDate.of(año, mes, dia);
			if(fechaCompe.isEqual(fechaCompeSolicitada) && (int)competicionId[0] != idCompeticion) {
				return false;
			}
		}
		
		
		return true;
	}
	
	public boolean comprobarSiEstaFederado(int idCompeticion, int idSocio) {
		
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

	private TiposDeportes getTipoDeporte(String deporte) {
		if(deporte.equals("FUTBOL")) {
			return TiposDeportes.FUTBOL;
		}else if(deporte.equals("TENIS")) {
			return TiposDeportes.TENIS;
		}else if(deporte.equals("TIRO_CON_ARCO")) {
			return TiposDeportes.TIRO_CON_ARCO;
		}else {
			return TiposDeportes.NATACION;
		}
	}

	public void generarListaSocios(int idCompeticion,File archivo) {
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
		List<Object[]> result = db.executeQueryArray(SQL_OBTENER_IDS);
		return result.size() == 0? 1 : ((int) result.get(result.size()-1)[0])+1;
	}
	
	public boolean comprobarSocioYaInscrito(int idCompeticion, int idSocio) {
		List<Object[]> result = db.executeQueryArray(SQL_OBTENER_SOCIO_INSCRITO,idCompeticion,idSocio);
		return !result.isEmpty();
	}
}
