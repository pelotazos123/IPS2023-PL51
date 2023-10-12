package giis.demo.model.CrearLicencias.servicio;

import giis.demo.model.Socio;
import giis.demo.model.CrearLicencias.Licencia;
import giis.demo.model.CrearLicencias.TiposLicencia;
import giis.demo.util.Database;

public class TramitarLicencia {
	
	private final static int ID_SOCIO_PRUEBAS = 100;
	
	private Database db=new Database();
	private Socio socio;
	private Licencia licencia;
	
	public TramitarLicencia() {
		db.createDatabase(false);
		db.loadDatabase();
		loggearSocio();
	}
	
	public void loggearSocio() {
		socio = new Socio(db, ID_SOCIO_PRUEBAS);
		System.out.println(socio.toString());
	}
	
	public void crearLicencia(String nombre,String apellido, String edad, String direccion, String info, TiposLicencia licencia) {
		this.licencia = new Licencia(socio.getId(), nombre,apellido, edad, direccion, info, licencia, db);
	}
	
	public Socio getSocio() {
		return socio;
	}
	

}
