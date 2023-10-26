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
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

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
	private final static int ID_DIRECTIVO_PRUEBAS = 100;
	
	
	private Database db=new Database();
	private Socio socio;
	private Licencia licenciaSeleccionada;
	private List<Licencia> licenciasDelSocio = new ArrayList<Licencia>();
	
	public TramitarLicencia(Database db) {
		this.db = db;
//		db.createDatabase(false);
//		db.loadDatabase();
	}
	
	public void loggearSocio(boolean esDirectivo) {
		if(esDirectivo) {
			cargarSocio(ID_DIRECTIVO_PRUEBAS);
		}else {
			cargarSocio(ID_SOCIO_CON_LICENCIA_MENOR_EDAD_PRUEBAS);
		}
		System.out.println(socio.toString());
	}
	
	public void crearLicencia(String nombre,String apellido, LocalDate fecha, Generos genero, String direccion, String info, TiposLicencia licencia) {
		licenciaSeleccionada = new Licencia(socio.getId(), db);
		this.licenciaSeleccionada.crearLicencia( nombre,apellido, fecha, genero, direccion, info, licencia);
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
	
	private void cargarSocio(int id) {
		socio = new Socio(db, id);
		cargarLicenciasDelSocio();
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
	

	
	public void modificarDatosLicencia(String nombre,String apellido, LocalDate fecha, Generos genero, String direccion, String info) {
		this.licenciaSeleccionada.modificarDatos(nombre, apellido, fecha, genero, direccion, info);
	}
	
	public void modificarDatosSocio(String nombre, String apellido, Generos genero, LocalDate fecha) {
		socio.modificarDatos(nombre,apellido,genero,fecha);
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
}
