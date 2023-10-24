package giis.demo.model.CrearLicencias;

import java.util.Calendar;
import java.util.List;

import giis.demo.model.Generos;
import giis.demo.util.Database;

public class Licencia {
	
	public final static int PRECIO_LICENCIAS = 30;
	
	private final static String SQL_CREAR_LICENCIA = "insert into licencias(owner_id, tutor_name, tutor_surname, tutor_age, tutor_gender, state, price, licence_type,"
			+ " facturation_direction,facturation_info) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String SQL_CARGAR_LICENCIA = "select owner_id, tutor_name, tutor_surname, tutor_age, tutor_gender, state, price, licence_type,facturation_direction,facturation_info from licencias where owner_id = ? and licence_type = ?";
	private final static String SQL_MODIFICAR_LICENCIA = "update licencias set tutor_name=?, tutor_surname=?, tutor_age=?, tutor_gender=?,"
			+ "state=?, price=?, licence_type=?,facturation_direction=?,facturation_info=? where owner_id = ?";
	
	private Database db;
	
	private int idPropietario;
	private String nombreTutor;
	private String apellidosTutor;
	private Calendar fechaNacimiento;
	private Generos generoTutor;
	private EstadosLicencia estado;
	private int precio;
	private TiposLicencia tipoLicencia;
	private String direccionFacturacion;
	private String infoFacturacion;
	
	public Licencia(int id, Database db) {
		idPropietario = id;
		this.db = db;
	}

	public void crearLicencia(String nombre,String apellido, Calendar fecha, Generos genero, String direccion, String info, TiposLicencia licencia) {
		nombreTutor = nombre;
		apellidosTutor = apellido;
		
		fechaNacimiento = fecha;
		String fechaTutor = "noTutor";
		if(fecha != null) {
			fechaTutor =""+fecha.get(Calendar.DAY_OF_MONTH)+"-"+fecha.get(Calendar.MONTH)+"-"+fecha.get(Calendar.YEAR);
		}
		
		generoTutor = genero;
		
		estado = EstadosLicencia.PENDIENTE;
		precio = PRECIO_LICENCIAS;
		direccionFacturacion = direccion;
		infoFacturacion = info;
		tipoLicencia = licencia;
		
		db.executeUpdate(SQL_CREAR_LICENCIA, idPropietario,nombreTutor,apellidosTutor,fechaTutor,generoTutor,estado,precio,tipoLicencia,direccionFacturacion,infoFacturacion);
		comprobarInsertado();
	}
	
	public void cargarLicencia(TiposLicencia tipo) {
		Object[] result = db.executeQueryArray(SQL_CARGAR_LICENCIA, idPropietario,tipo).get(0);
		idPropietario = (int) result[0];
		nombreTutor = (String) result[1];
		apellidosTutor = (String) result[2];
		precio = (int) result[6];
		direccionFacturacion = (String) result[8];
		infoFacturacion = (String) result[9];
		
		String genero = (String) result[4];
		if(genero == null) {
			generoTutor = Generos.OTRO;
		}else if(genero.equals("HOMBRE")) {
			generoTutor = Generos.HOMBRE;
		}else if(genero.equals("MUJER")) {
			generoTutor = Generos.MUJER;
		}else {
			generoTutor = Generos.OTRO;
		}
		
		String estado = (String) result[5];
		if(estado.equals("PENDIENTE")) {
			this.estado = EstadosLicencia.PENDIENTE;
		}else {
			this.estado = EstadosLicencia.PAGADO;
		}
		
		String licencia = (String) result[7];
		if(licencia.equals("MONITOR")) {
			tipoLicencia = TiposLicencia.MONITOR;
		}else if(licencia.equals("DEPORTISTA")) {
			tipoLicencia = TiposLicencia.DEPORTISTA;
		}else {
			tipoLicencia = TiposLicencia.JUEZ;
		}
		
		String edadTutor = (String) result[3];
		if(edadTutor.equals("noTutor")) {
			fechaNacimiento = null;
		}else {
			String[] str = edadTutor.split("-");
			int dia = Integer.parseInt(str[0]);
			int mes = Integer.parseInt(str[1]);
			int año = Integer.parseInt(str[2]);
			fechaNacimiento = Calendar.getInstance();
			fechaNacimiento.set(año, mes, dia);
		}
	}
	
	private void comprobarInsertado() {
		Object[] result = db.executeQueryArray(SQL_CARGAR_LICENCIA, idPropietario,tipoLicencia).get(0);
		int id = (int) result[0];
		String nombre = (String) result[1];
		String apellido = (String) result[2];
		String edadTutor = (String) result[3];
		if(edadTutor.equals("noTutor")) {
			fechaNacimiento = null;
		}else {
			String[] str = edadTutor.split("-");
			int dia = Integer.parseInt(str[0]);
			int mes = Integer.parseInt(str[1]);
			int año = Integer.parseInt(str[2]);
			fechaNacimiento = Calendar.getInstance();
			fechaNacimiento.set(año, mes, dia);
		}
		String fechaTutor = "noTutor";
		if(fechaNacimiento != null) {
			fechaTutor =""+fechaNacimiento.get(Calendar.DAY_OF_MONTH)+"-"+fechaNacimiento.get(Calendar.MONTH)+"-"+fechaNacimiento.get(Calendar.YEAR);
		}
		
		String genero = (String) result[4];
		String estado = (String) result[5];
		int precio = (int) result[6];
		String licencia = (String) result[7];
		String direccion = (String) result[8];
		String info = (String) result[9];
		System.out.println("\n"+"Id del socio: "+id+", nombre del tutor: "+nombre+", apellidos del tutor: "+apellido+
				", edad del tutor: "+fechaTutor+" ,genero del tutor: "+genero+", estado de la licencia: "+estado+", precio de la licencia: "+precio+", tipo de licencia: "+licencia+
				", direccion de facturacion: "+direccion+", informacion de facturacion: "+info);
	}
	
	public void modificarDatos(String nombre, String apellido, Calendar fecha, Generos genero, String direccion,
			String info) {
		nombreTutor = nombre;
		apellidosTutor = apellido;
		
		fechaNacimiento = fecha;
		generoTutor = genero;
		precio = PRECIO_LICENCIAS;
		direccionFacturacion = direccion;
		infoFacturacion = info;
	}
	
	public void guardarDatos() {
		String fechaTutor = "noTutor";
		if(fechaNacimiento != null) {
			fechaTutor =""+fechaNacimiento.get(Calendar.DAY_OF_MONTH)+"-"+fechaNacimiento.get(Calendar.MONTH)+"-"+fechaNacimiento.get(Calendar.YEAR);
		}
		estado = EstadosLicencia.PENDIENTE;
		db.executeUpdate(SQL_MODIFICAR_LICENCIA,nombreTutor, apellidosTutor, fechaTutor, generoTutor, estado, precio, tipoLicencia, direccionFacturacion
				,infoFacturacion, idPropietario);
		System.out.println("Datos Licencia modificados:\n"+getDatosLicencia());
	}
	
	public boolean existe(TiposLicencia tipo) {
		List<Object[]> result = db.executeQueryArray(SQL_CARGAR_LICENCIA, idPropietario,tipo);
		return result.size() > 0;
	}
	
	@Override
	public String toString() {
		return ""+tipoLicencia;
	}
	
	public String getDatosLicencia() {
		String fecha = ""+fechaNacimiento.get(Calendar.DAY_OF_MONTH)+"-"+fechaNacimiento.get(Calendar.MONTH)+"-"+fechaNacimiento.get(Calendar.YEAR);
		return "Id del socio: "+idPropietario+", nombre del tutor: "+nombreTutor+", apellidos del tutor: "+apellidosTutor+
				", edad del tutor: "+fecha+" ,genero del tutor: "+generoTutor+", estado de la licencia: "+estado+", precio de la licencia: "
				+precio+", tipo de licencia: "+tipoLicencia+", direccion de facturacion: "+direccionFacturacion+", informacion de facturacion: "
				+infoFacturacion;
	}

	public int getIdPropietario() {
		return idPropietario;
	}

	public String getNombreTutor() {
		return nombreTutor;
	}

	public String getApellidosTutor() {
		return apellidosTutor;
	}

	public Calendar getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public Generos getGeneroTutor() {
		return generoTutor;
	}

	public EstadosLicencia getEstado() {
		return estado;
	}

	public int getPrecio() {
		return precio;
	}

	public TiposLicencia getTipoLicencia() {
		return tipoLicencia;
	}

	public String getDireccionFacturacion() {
		return direccionFacturacion;
	}

	public String getInfoFacturacion() {
		return infoFacturacion;
	}
}
