package giis.demo.model.CrearLicencias;

import java.time.LocalDate;
import java.util.List;

import giis.demo.model.Generos;
import giis.demo.model.TiposDeportes;
import giis.demo.util.Database;

public class Licencia {
	
	public final static int PRECIO_LICENCIA_MONITOR = 30;
	public final static int PRECIO_LICENCIA_DEPORTISTA = 30;
	public final static int PRECIO_LICENCIA_JUEZ = 30;
	
	private final static String SQL_CREAR_LICENCIA = "insert into licencias(owner_id, tutor_dni, tutor_name, tutor_surname, tutor_email, tutor_telf, tutor_birth_date, tutor_gender, state, price, licence_type,"
			+ " facturation_direction,facturation_info, deporte) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String SQL_CARGAR_LICENCIA = "select owner_id, tutor_dni, tutor_name, tutor_surname, tutor_email, tutor_telf, tutor_birth_date, tutor_gender, state, price, licence_type,facturation_direction,facturation_info, deporte from licencias where owner_id = ? and licence_type = ?";
	private final static String SQL_MODIFICAR_LICENCIA = "update licencias set tutor_dni=?, tutor_name=?, tutor_surname=?, tutor_email=?, tutor_telf=?, tutor_birth_date=?, tutor_gender=?,"
			+ "state=?, price=?,facturation_direction=?,facturation_info=?, deporte=? where owner_id = ? and licence_type = ?";
	
	private Database db;
	
	private int idPropietario;
	private String dniTutor;
	private String nombreTutor;
	private String apellidosTutor;
	private String correoTutor;
	private int telefonoTutor;
	private LocalDate fechaNacimiento;
	private Generos generoTutor;
	private EstadosLicencia estado;
	private int precio;
	private TiposLicencia tipoLicencia;
	private String direccionFacturacion;
	private String infoFacturacion;
	private TiposDeportes deporte;
	
	public Licencia(int id, Database db) {
		idPropietario = id;
		this.db = db;
	}

	public void crearLicencia(String dni,String nombre,String apellido, String correo, int telf, LocalDate fecha, Generos genero, String direccion, String info, TiposLicencia licencia, TiposDeportes deporte) {
		nombreTutor = nombre;
		apellidosTutor = apellido;
		dniTutor = dni;
		correoTutor = correo;
		telefonoTutor = telf;
		
		fechaNacimiento = fecha;
		String fechaTutor = null;
		if(fecha != null) {
			fechaTutor =""+fecha.getYear()+"-"+fecha.getMonthValue()+"-"+fecha.getDayOfMonth();
		}
		
		generoTutor = genero;
		
		estado = EstadosLicencia.PENDIENTE;
		
		if(licencia.equals(TiposLicencia.MONITOR)) {
			precio = PRECIO_LICENCIA_MONITOR;
		}else if(licencia.equals(TiposLicencia.JUEZ)) {
			precio = PRECIO_LICENCIA_JUEZ;
		}else {
			precio = PRECIO_LICENCIA_DEPORTISTA;
		}
		
		direccionFacturacion = direccion;
		infoFacturacion = info;
		tipoLicencia = licencia;
		this.deporte = deporte;
		
		db.executeUpdate(SQL_CREAR_LICENCIA, idPropietario,dniTutor,nombreTutor,apellidosTutor,correoTutor,telefonoTutor,fechaTutor,generoTutor,estado,precio,tipoLicencia,direccionFacturacion,infoFacturacion,this.deporte);
		comprobarInsertado();
	}
	
	public void cargarLicencia(TiposLicencia tipo) {
		Object[] result = db.executeQueryArray(SQL_CARGAR_LICENCIA, idPropietario,tipo).get(0);
		
		idPropietario = (int) result[0];
		dniTutor = (String) result[1];
		nombreTutor = (String) result[2];
		apellidosTutor = (String) result[3];
		correoTutor = (String) result[4];
		telefonoTutor =  result[5] == null? -1:(int)result[5];
		String edadTutor = (String) result[6];
		String genero = (String) result[7];
		String estado = (String) result[8];
		precio = (int) result[9];
		String licencia = (String) result[10];
		direccionFacturacion = (String) result[11];
		infoFacturacion = (String) result[12];
		String tipoDeporte = (String) result[13];
		
		if(genero == null) {
			generoTutor = Generos.OTRO;
		}else if(genero.equals("HOMBRE")) {
			generoTutor = Generos.HOMBRE;
		}else if(genero.equals("MUJER")) {
			generoTutor = Generos.MUJER;
		}else {
			generoTutor = Generos.OTRO;
		}
		
		if(tipoDeporte.equals("FUTBOL")) {
			deporte = TiposDeportes.FUTBOL;
		}else if(tipoDeporte.equals("NATACION")) {
			deporte = TiposDeportes.NATACION;
		}else if(tipoDeporte.equals("TENNIS")) {
			deporte = TiposDeportes.TENNIS;
		}else if(tipoDeporte.equals("TIRO_CON_ARCO")) {
			deporte = TiposDeportes.TIRO_CON_ARCO;
		}
		
		
		if(estado.equals("PENDIENTE")) {
			this.estado = EstadosLicencia.PENDIENTE;
		}else {
			this.estado = EstadosLicencia.PAGADO;
		}
		
		
		if(licencia.equals("MONITOR")) {
			tipoLicencia = TiposLicencia.MONITOR;
		}else if(licencia.equals("DEPORTISTA")) {
			tipoLicencia = TiposLicencia.DEPORTISTA;
		}else {
			tipoLicencia = TiposLicencia.JUEZ;
		}
		
		
		if(edadTutor == null) {
			fechaNacimiento = null;
		}else {
			String[] str = edadTutor.split("-");
			int año = Integer.parseInt(str[0]);
			int mes = Integer.parseInt(str[1]);
			int dia = Integer.parseInt(str[2]);
			fechaNacimiento = LocalDate.of(año, mes, dia);
		}
	}
	
	private void comprobarInsertado() {
		Object[] result = db.executeQueryArray(SQL_CARGAR_LICENCIA, idPropietario,tipoLicencia).get(0);
		
		int id = (int) result[0];
		String dni = (String) result[1];
		String nombre = (String) result[2];
		String apellido = (String) result[3];
		String correo = (String) result[4];
		int telefono = (int) result[5];
		String fechaTutor = (String) result[6];
		String genero = (String) result[7];
		String estado = (String) result[8];
		int precio = (int) result[9];
		String licencia = (String) result[10];
		String direccion = (String) result[11];
		String info = (String) result[12];
		String deporte = (String) result[13];
		
		System.out.println("\n"+"Id del socio: "+id+", dni del tutor: "+dni+", nombre del tutor: "+nombre+", apellidos del tutor: "+apellido+
				", correo del tutor: "+correo+", telefono del tutor: "+telefono+", edad del tutor: "+fechaTutor+" ,genero del tutor: "+genero+
				", estado de la licencia: "+estado+", precio de la licencia: "+precio+", tipo de licencia: "+licencia+
				", direccion de facturacion: "+direccion+", informacion de facturacion: "+info+", deporte:"+deporte);
	}
	
	public void modificarDatos(String dni, String nombre, String apellido, String correo, int telf, LocalDate fecha, Generos genero, String direccion,
			String info, TiposDeportes deporte) {
		dniTutor = dni;
		nombreTutor = nombre;
		apellidosTutor = apellido;
		correoTutor = correo;
		telefonoTutor = telf;
		
		fechaNacimiento = fecha;
		generoTutor = genero;
		
		direccionFacturacion = direccion;
		infoFacturacion = info;
		this.deporte = deporte;
	}
	
	public void guardarDatos() {
		String fechaTutor = null;
		if(fechaNacimiento != null) {
			fechaTutor =""+fechaNacimiento.getYear()+"-"+fechaNacimiento.getMonthValue()+"-"+fechaNacimiento.getDayOfMonth();
		}
		estado = EstadosLicencia.PENDIENTE;
		db.executeUpdate(SQL_MODIFICAR_LICENCIA,dniTutor, nombreTutor, apellidosTutor, correoTutor, telefonoTutor, fechaTutor, generoTutor, estado, precio, direccionFacturacion
				,infoFacturacion, idPropietario, tipoLicencia,deporte);
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
		return "Id del socio: "+idPropietario+", nombre del tutor: "+nombreTutor+", apellidos del tutor: "+apellidosTutor+
				", edad del tutor: "+fechaNacimiento+" ,genero del tutor: "+generoTutor+", estado de la licencia: "+estado+", precio de la licencia: "
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

	public LocalDate getFechaNacimiento() {
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

	public String getDniTutor() {
		return dniTutor;
	}

	public String getCorreoTutor() {
		return correoTutor;
	}

	public int getTelefonoTutor() {
		return telefonoTutor;
	}

	public TiposDeportes getDeporte() {
		return deporte;
	}
}
