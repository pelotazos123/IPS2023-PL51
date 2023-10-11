package giis.demo.model.servicio;

import giis.demo.model.Socio;
import giis.demo.util.Database;

public class TramitarLicencia {
	
	private final static int ID_SOCIO_PRUEBAS = 102;
	
	private Database db=new Database();
	private Socio socio;
	
	public TramitarLicencia() {
		db.createDatabase(true);
		db.loadDatabase();
		loggearSocio();
	}
	
	public void loggearSocio() {
		socio = new Socio(db, ID_SOCIO_PRUEBAS);
	}
	

}
