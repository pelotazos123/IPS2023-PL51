package giis.demo.model.CrearLicencias.servicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import giis.demo.model.Generos;
import giis.demo.model.Socio;
import giis.demo.model.CrearLicencias.EstadosLicencia;
import giis.demo.model.CrearLicencias.Licencia;
import giis.demo.model.CrearLicencias.TiposLicencia;
import giis.demo.util.Database;


public class TramitarLicencia {
	
	@SuppressWarnings("unused")
	private final static int ID_SOCIO_SIN_LICENCIA_PRUEBAS = 102;
	@SuppressWarnings("unused")
	private final static int ID_SOCIO_SIN_LICENCIA_MENOR_EDAD_PRUEBAS = 103;
	@SuppressWarnings("unused")
	private final static int ID_SOCIO_CON_LICENCIA_PRUEBAS = 104;
	@SuppressWarnings("unused")
	private final static int ID_SOCIO_CON_LICENCIA_MENOR_EDAD_PRUEBAS = 106;
	@SuppressWarnings("unused")
	private final static int ID_SOCIO_CON_DOS_LICENCIAS_PRUEBAS = 105;
	@SuppressWarnings("unused")
	private final static int ID_SOCIO_CON_TRES_LICENCIAS_PRUEBAS = 101;
	@SuppressWarnings("unused")
	private final static int ID_DIRECTIVO_PRUEBAS = 100;
	
	
	private final static String SQL_OBTENER_IDS= "select id from socios";
	private final static String SQL_OBTENER_SI_ES_DIRECTIVO = "select directive from socios where dni=?";
	private final static String SQL_OBTENER_ID_SOCIO = "select id from socios where dni=?";
	
	
	private Database db;
	private Socio directivo;
	private Socio socio;
	private Licencia licenciaSeleccionada;
	private List<Licencia> licenciasDelSocio = new ArrayList<Licencia>();
	
	public TramitarLicencia(Database db) {
		this.db = db;
	}
	
	public void loggearSocio(String dniSocio) {
		if(esDirectivo(dniSocio)) {
			cargarDirectivo(dniSocio);
			System.out.println(directivo.toString());
		}else {
			cargarSocio(dniSocio);
			System.out.println(socio.toString());
		}
	}
	
	private boolean esDirectivo(String dniSocio) {
		Object[] result = db.executeQueryArray(SQL_OBTENER_SI_ES_DIRECTIVO,dniSocio).get(0);
		return (int)result[0] == 1;
	}

	public void crearSocio(String dni, String nombre, String apellidos, String correo, int telf, Generos genero, LocalDate fecha) {
		socio = new Socio(db,generarId(),dni,nombre, apellidos, correo, telf,null,-1,-1,genero,fecha);
		socio.insertarSocio();
	}
	
	public void crearLicencia(String nombre,String apellido,String dni,int telf,String correo, LocalDate fecha, Generos genero, String direccion, String info, TiposLicencia licencia) {
		licenciaSeleccionada = new Licencia(socio.getId(), db);
		this.licenciaSeleccionada.crearLicencia(dni, nombre, apellido, correo, telf, fecha, genero, direccion, info, licencia);
		licenciasDelSocio.add(licenciaSeleccionada);
	}
	
	public Licencia[] getLicenciasPagadas() {
		List<Licencia> licenciasPagadas = new ArrayList<Licencia>();
		for (Licencia licencia : licenciasDelSocio) {
			if(licencia.getEstado().equals(EstadosLicencia.PAGADO)) {
				licenciasPagadas.add(licencia);
			}
		}
		return licenciasPagadas.toArray(new Licencia[licenciasPagadas.size()]);
	}
	
	public TiposLicencia[] getLicenciasDisponibles(boolean mayor) {
		List<TiposLicencia> tiposDisponibles = new ArrayList<TiposLicencia>();
		for(int i = 0; i < TiposLicencia.values().length; i++) {
			TiposLicencia tipo = TiposLicencia.values()[i];
			boolean tipoDisponible = true;
			
			for (Licencia licencia : licenciasDelSocio) {
				if(licencia.getTipoLicencia().equals(tipo)) {
					tipoDisponible = false;
				}
			}
			
			if(tipoDisponible) {
				if(!mayor) {
					if(tipo.equals(TiposLicencia.DEPORTISTA)) {
						tiposDisponibles.add(tipo);
					}
				}else {
					tiposDisponibles.add(tipo);
				}
			}
		}
		return tiposDisponibles.toArray(new TiposLicencia[tiposDisponibles.size()]);
	}
	
	private void cargarSocio(String dniSocio) {
		int id = (int) db.executeQueryArray(SQL_OBTENER_ID_SOCIO,dniSocio).get(0)[0];
		socio = new Socio(db, id);
		cargarLicenciasDelSocio();
	}
	
	private void cargarDirectivo(String dniSocio) {
		int id = (int) db.executeQueryArray(SQL_OBTENER_ID_SOCIO,dniSocio).get(0)[0];
		directivo = new Socio(db, id);
	} 
	
	public void restaurarSocio(int id) {
		socio = new Socio(db, id);
		licenciaSeleccionada = null;
	}
	
	private void cargarLicenciasDelSocio() {
		Licencia licencia = null;
		for(int i = 0; i < TiposLicencia.values().length; i++) {
			licencia = new Licencia(socio.getId(), db);
			TiposLicencia tipo = TiposLicencia.values()[i]; 
			if(licencia.existe(tipo)) {
				licencia.cargarLicencia(tipo);
				licenciasDelSocio.add(licencia);
			}
		}
	}
	
	public boolean socioConAlgunaLicenciaDisponible(boolean mayor) {
		return getLicenciasDisponibles(mayor).length > 0;
	}
	
	public boolean socioConLicenciasPagadas() {
		for (Licencia licencia : licenciasDelSocio) {
			if (licencia.getEstado().equals(EstadosLicencia.PAGADO)) {
				return true;
			}
		}
		return false;
	}
	
	public Socio getSocio() {
		return socio;
	}
	
	public Socio getDirectivo() {
		return directivo;
	}
	
	public boolean esDirectivo() {
		return directivo != null;
	}
	

	
	public void modificarDatosLicencia(String dni, String nombre,String apellido, String correo, int telf, LocalDate fecha, Generos genero, String direccion, String info) {
		this.licenciaSeleccionada.modificarDatos(dni, nombre, apellido, correo, telf, fecha, genero, direccion, info);
	}
	
	public void modificarDatosSocio(String dni, String nombre, String apellido, Generos genero, int telfSocio, String correoSocio, LocalDate fecha) {
		socio.modificarDatos(nombre,apellido,dni,telfSocio,correoSocio,genero,fecha);
	}
	
	public void guardarDatosModificadosLicencia() {
		licenciaSeleccionada.guardarDatos();
	}
	
	public void guardarDatosModificadosSocio() {
		socio.guardarDatos();
	}
	
	public Licencia getLicenciaSeleccionada() {
		return licenciaSeleccionada;
	}
	
	public void setLicenciaSeleccionada(Licencia licencia) {
		licenciaSeleccionada = licencia;
	}

	public boolean comprobarSocioMayorEdad() {
		int añoSocio = socio.getFechaNacimiento().getYear();
		int mesSocio = socio.getFechaNacimiento().getMonthValue();
		int diaSocio = socio.getFechaNacimiento().getDayOfMonth();
		
		LocalDate d = LocalDate.now();
		int añoActual = d.getYear();
		int mesActual = d.getMonthValue();
		int diaActual = d.getDayOfMonth();
		
		if(añoActual - añoSocio > 18) {
			//mayor de edad
			return true;
		}else if(añoActual - añoSocio == 18) {
			//comprobamos mes
			if(mesSocio < mesActual) {
				//mayor de edad
				return true;
			}else if(mesSocio == mesActual) {
				//comprobamos dia
				if(diaSocio < diaActual) {
					//mayor de edad
					return true;
				}else if(diaSocio == diaActual) {
					//mayor de edad
					return true;
				}else {
					//menor de edad
					return false;
				}
			}else {
				//menor de edad
				return false;
			}
		}else {
			//menor de edad
			return false;
		}
	}
	
	public boolean comprobarMayorEdad(int dia, int mes, int año) {
		int añoSocio = año;
		int mesSocio = mes;
		int diaSocio = dia;
		
		LocalDate d = LocalDate.now();
		int añoActual = d.getYear();
		int mesActual = d.getMonthValue();
		int diaActual = d.getDayOfMonth();
		
		if(añoActual - añoSocio > 18) {
			//mayor de edad
			return true;
		}else if(añoActual - añoSocio == 18) {
			//comprobamos mes
			if(mesSocio < mesActual) {
				//mayor de edad
				return true;
			}else if(mesSocio == mesActual) {
				//comprobamos dia
				if(diaSocio < diaActual) {
					//mayor de edad
					return true;
				}else if(diaSocio == diaActual) {
					//mayor de edad
					return true;
				}else {
					//menor de edad
					return false;
				}
			}else {
				//menor de edad
				return false;
			}
		}else {
			//menor de edad
			return false;
		}
	}
	
	private int generarId() {
		List<Object[]> result = db.executeQueryArray(SQL_OBTENER_IDS);
		return ((int) result.get(result.size()-1)[0])+1;
	}
	
	public Socio getUsuario() {
		return esDirectivo()? getDirectivo():getSocio();
	}
}
