package giis.demo.model.CrearLicencias.servicio;

import giis.demo.model.Socio;
import giis.demo.model.CrearLicencias.Licencia;
import giis.demo.model.CrearLicencias.TiposLicencia;
import giis.demo.util.Database;

public class TramitarLicencia {
	
	private final static int ID_SOCIO_PRUEBAS = 105;
	private final static int ID_SOCIO_MENOR_EDAD_PRUEBAS = 103;
	private final static int ID_DIRECTIVO_PRUEBAS = 100;
	
	
	private Database db=new Database();
	private Socio socio;
	private Licencia licencia;
	
	public TramitarLicencia(boolean esDirectivo) {
		db.createDatabase(false);
		db.loadDatabase();
		loggearSocio(esDirectivo);
	}
	
	public void loggearSocio(boolean esDirectivo) {
		if(esDirectivo) {
			socio = new Socio(db, ID_DIRECTIVO_PRUEBAS);
		}else {
			socio = new Socio(db, ID_SOCIO_PRUEBAS);
		}
		System.out.println(socio.toString());
	}
	
	public void crearLicencia(String nombre,String apellido, String edad, String direccion, String info, TiposLicencia licencia) {
		this.licencia = new Licencia(socio.getId(), nombre,apellido, edad, direccion, info, licencia, db);
	}
	
	public Socio getSocio() {
		return socio;
	}
	

}
