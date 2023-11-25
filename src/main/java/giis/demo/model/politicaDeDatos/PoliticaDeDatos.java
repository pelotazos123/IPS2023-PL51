package giis.demo.model.politicaDeDatos;

import java.util.List;

import giis.demo.model.Socio;
import giis.demo.util.Database;

public class PoliticaDeDatos {
	
	private final static String SQL_ENVIAR_SOLICITUD_DATOS = "insert into solicitudesModificacionDatos (dni, name, surname, modificacion) values(?, ?, ?, ?)";
	private final static String SQL_OBTENER_SOLICITUDES_DE_MODIFICACION = "select * from solicitudesModificacionDatos";
	private final static String SQL_FINALIZAR_SOLICITUD = "delete from solicitudesModificacionDatos where id = ?";
	private final static String SQL_BORRAR_LOGGIN = "delete from loggin where dni_socio = ?";
	private final static String SQL_DARSE_DE_BAJA = "update socios set name='Socio-dado-de-baja', dni= NULL, surname= NULL, email= NULL, telf= NULL"
			+ ", cuota_type= NULL, iban= NULL, height= NULL, weight= NULL, birth_date= NULL, gender= NULL where id = ?";
	
	private Database db;
	
	public PoliticaDeDatos(Database db) {
		this.db = db;
	}
	
	public void enviarSolicitudDeModificacionDeDatos(Socio s, String modificacion) {
		db.executeUpdate(SQL_ENVIAR_SOLICITUD_DATOS, s.getDni(), s.getNombre(), s.getApellidos(), modificacion);
	}
	
	public List<Object[]> cargarSolicitudesDeModificacionDeDatos() {
		return db.executeQueryArray(SQL_OBTENER_SOLICITUDES_DE_MODIFICACION);
	}
	
	public void finalizarSolicitud(int id) {
		db.executeUpdate(SQL_FINALIZAR_SOLICITUD, id);
	}
	
	public void darseDeBaja(Socio s) {
		db.executeUpdate(SQL_BORRAR_LOGGIN, s.getDni());
		db.executeUpdate(SQL_DARSE_DE_BAJA, s.getId());
	}
}
