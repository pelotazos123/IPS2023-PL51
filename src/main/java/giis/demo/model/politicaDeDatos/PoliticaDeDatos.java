package giis.demo.model.politicaDeDatos;

import java.util.List;

import giis.demo.model.Socio;
import giis.demo.util.Database;

public class PoliticaDeDatos {
	
	private final static String SQL_ENVIAR_SOLICITUD_DATOS = "insert into solicitudesModificacionDatos (dni, name, surname, email) values(?, ?, ?, ?)";
	private final static String SQL_OBTENER_SOLICITUDES_DE_MODIFICACION = "select * from solicitudesModificacionDatos";
	private final static String SQL_FINALIZAR_SOLICITUD = "delete from solicitudesModificacionDatos where id = ?";
	
	private Database db;
	
	public PoliticaDeDatos(Database db) {
		this.db = db;
	}
	
	public void enviarSolicitudDeModificacionDeDatos(Socio s) {
		db.executeUpdate(SQL_ENVIAR_SOLICITUD_DATOS, s.getDni(), s.getNombre(), s.getApellidos(), s.getCorreo());
	}
	
	public List<Object[]> cargarSolicitudesDeModificacionDeDatos() {
		return db.executeQueryArray(SQL_OBTENER_SOLICITUDES_DE_MODIFICACION);
	}
	
	public void finalizarSolicitud(int id) {
		db.executeUpdate(SQL_FINALIZAR_SOLICITUD, id);
	}
}
