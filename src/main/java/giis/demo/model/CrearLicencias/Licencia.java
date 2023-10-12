package giis.demo.model.CrearLicencias;

import giis.demo.util.Database;

public class Licencia {
	
	public final static int PRECIO_LICENCIAS = 30;
	
	private final static String SQL_CREAR_LICENCIA = "insert into licencias(owner_id, tutor_name, tutor_surname, tutor_age, state, price, licence_type,"
			+ " facturation_direction,facturation_info) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String SQL_CARGAR_LICENCIA = "select owner_id, tutor_name, tutor_surname, tutor_age, state, price, licence_type,facturation_direction,facturation_info from licencias where owner_id = ?";
	
	private Database db;
	
	private int idPropietario;
	private String nombreTutor;
	private String apellidosTutor;
	private int edadTutor;
	private String estado; //pendiente o pagado
	private int precio;
	private TiposLicencia tipoLicencia;
	private String direccionFacturacion;
	private String infoFacturacion;
	
	public Licencia(int id, String nombre,String apellido, String edad, String direccion, String info, TiposLicencia licencia,Database db) {
		idPropietario = id;
		nombreTutor = nombre;
		apellidosTutor = apellido;
		if(edad == null) {
			edadTutor = -1;
		}else {
			edadTutor = Integer.parseInt(edad);
		}
		estado = "pendiente";
		precio = PRECIO_LICENCIAS;
		direccionFacturacion = direccion;
		infoFacturacion = info;
		tipoLicencia = licencia;
		this.db = db;
		crearLicencia();
		comprobarInsertado();
	}
	
	private void crearLicencia() {
		db.executeUpdate(SQL_CREAR_LICENCIA, idPropietario,nombreTutor,apellidosTutor,edadTutor,estado,precio,tipoLicencia,direccionFacturacion,infoFacturacion);
	}
	
	private void comprobarInsertado() {
		Object[] result = db.executeQueryArray(SQL_CARGAR_LICENCIA, idPropietario).get(0);
		int id = (int) result[0];
		String nombre = (String) result[1];
		String apellido = (String) result[2];
		int edad = (int) result[3];
		String estado = (String) result[4];
		int precio = (int) result[5];
		String licencia = (String) result[6];
		String direccion = (String) result[7];
		String info = (String) result[8];
		System.out.println("\n"+"Id del socio: "+id+", nombre del tutor: "+nombre+", apellidos del tutor: "+apellido+
				", edad del tutor: "+edad+", estado de la licencia: "+estado+", precio de la licencia: "+precio+", tipo de licencia: "+licencia+
				", direccion de facturacion: "+direccion+", informacion de facturacion: "+info);
	}
}
