package giis.demo.model.servicio;

import giis.demo.model.Socio;
import giis.demo.util.Database;

public class TramitarLicencia {
	
	private final static int ID_SOCIO_PRUEBAS = 105;
	
	private Database db=new Database();
	private Socio socio;
	
	public TramitarLicencia() {
		db.createDatabase(false);
		db.loadDatabase();
		loggearSocio();
	}
	
	public void loggearSocio() {
		socio = new Socio(db, ID_SOCIO_PRUEBAS);
		System.out.println(socio.toString());
	}
	

}
