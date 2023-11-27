package giis.demo.model;

import java.time.LocalDate;

import giis.demo.util.Database;

public class Socio {
	
	private final static String SQL_CARGAR_SOCIO="select id,dni,name,surname,email,telf,cuota_type,iban,height,weight,birth_date,gender,directive from socios where id = ?" ;
	private final static String SQL_OBTENER_ID="select id from socios where dni = ?";
	private final static String SQL_MODIFICAR_SOCIO= "update socios set dni=?,name=?, surname=?, email=?, telf=?, gender=?, birth_date=? where id = ?";
	private final static String SQL_INSERTAR_SOCIO = "insert into socios(dni,name,surname,email,telf,cuota_type,iban,height,weight,birth_date,gender,directive)"
			+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private Database db;
	
	private int id;
	private String dni;
	private String nombre;
	private String apellidos;
	private Generos genero;
	private double altura;
	private double peso;
	private LocalDate fechaNacimiento;
	private String correo;
	private int telefono;
	private TiposCuotas tipoCuota;
	private String numeroIban;
	private boolean esDirectivo;
	
	public Socio(Database db,int id) {
		this.db = db;
		cargarDatosSocio(id);
		
	}
	
	public Socio(Database db,String dni, String nombre, String apellidos, String correo, int tel, String iban, double altura, double peso, Generos genero, LocalDate fecha) {
		this.db = db;
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.correo = correo;
		this.telefono = tel;
		this.tipoCuota = generarTipoCuota(fecha);
		this.numeroIban = iban;
		this.altura = altura;
		this.peso = peso;
		this.fechaNacimiento = fecha;
		this.genero = genero;
		esDirectivo = false;
	}

	private TiposCuotas generarTipoCuota(LocalDate fecha) {
		LocalDate d = LocalDate.now();
		
		if(esMenorEdad(d,fecha)) {
			return TiposCuotas.SUB18;
		}else if(esVeterano(d,fecha)){
			return TiposCuotas.VETERANO;
		}else {
			return TiposCuotas.SENIOR;
		}
	}

	private boolean esVeterano(LocalDate d, LocalDate fecha) {
		int añoSocio = fecha.getYear();
		int mesSocio = fecha.getMonthValue();
		int diaSocio = fecha.getDayOfMonth();
		
		int añoActual = d.getYear();
		int mesActual = d.getMonthValue();
		int diaActual = d.getDayOfMonth();
		
		if(añoActual - añoSocio > 65) {
			//veterano
			return true;
		}else if(añoActual - añoSocio == 65) {
			//comprobamos mes
			if(mesSocio < mesActual) {
				//veterano
				return true;
			}else if(mesSocio == mesActual) {
				//comprobamos dia
				if(diaSocio < diaActual) {
					//veterano
					return true;
				}else if(diaSocio == diaActual) {
					//veterano
					return true;
				}else {
					//senior
					return false;
				}
			}else {
				//senior
				return false;
			}
		}else {
			//senior
			return false;
		}
	}

	private boolean esMenorEdad(LocalDate d, LocalDate fecha) {
		int añoSocio = fecha.getYear();
		int mesSocio = fecha.getMonthValue();
		int diaSocio = fecha.getDayOfMonth();
		
		int añoActual = d.getYear();
		int mesActual = d.getMonthValue();
		int diaActual = d.getDayOfMonth();
		
		if(añoActual - añoSocio > 18) {
			//mayor de edad
			return false;
		}else if(añoActual - añoSocio == 18) {
			//comprobamos mes
			if(mesSocio < mesActual) {
				//mayor de edad
				return false;
			}else if(mesSocio == mesActual) {
				//comprobamos dia
				if(diaSocio < diaActual) {
					//mayor de edad
					return false;
				}else if(diaSocio == diaActual) {
					//mayor de edad
					return false;
				}else {
					//menor de edad
					return true;
				}
			}else {
				//menor de edad
				return true;
			}
		}else {
			//menor de edad
			return true;
		}
	}

	private void cargarDatosSocio(int id) {
		Object[] result = db.executeQueryArray(SQL_CARGAR_SOCIO, id).get(0);
		
		this.id = (int) result[0];
		this.dni = (String) result[1];
		nombre = (String) result[2];
		apellidos = (String) result[3];
		correo = (String) result[4];;
		telefono = (int) result[5];;
		String cuota = (String) result[6];
		numeroIban = (String) result[7];
		altura = (double) result[8];
		peso = (double) result[9];
		String edad = (String) result[10];
		String genero = (String)result[11];
		
		if(genero.equals("HOMBRE")) {
			this.genero = Generos.HOMBRE;
		}else if(genero.equals("MUJER")) {
			this.genero = Generos.MUJER;
		}else {
			this.genero = Generos.OTRO;
		}
		
		if(cuota.equals("SUB18")) {
			tipoCuota = TiposCuotas.SUB18;
		}else if(cuota.equals("SENIOR")) {
			tipoCuota = TiposCuotas.SENIOR;
		}else {
			tipoCuota = TiposCuotas.VETERANO;
		}
		
		esDirectivo = (int)result[12] == 1;
		
		String[] str = edad.split("-");
		int año = Integer.parseInt(str[0]);
		int mes = Integer.parseInt(str[1]);
		int dia = Integer.parseInt(str[2]);
		fechaNacimiento = LocalDate.of(año, mes, dia);
		//fechaNacimiento.set(año, mes, dia);
		
		toString();
	}
	
	public void modificarDatos(String nombre, String apellido, String dni, int telf, String correo, Generos genero, LocalDate fecha) {
		this.nombre = nombre;
		this.apellidos = apellido;
		this.genero = genero;
		fechaNacimiento = fecha;
		this.dni = dni;
		this.telefono = telf;
		this.correo = correo;
	}
	
	public void guardarDatos() {
		String fecha = ""+fechaNacimiento.getYear()+"-"+fechaNacimiento.getMonthValue()+"-"+fechaNacimiento.getDayOfMonth();
		db.executeUpdate(SQL_MODIFICAR_SOCIO,dni,nombre,apellidos,correo,telefono,genero,fecha,id);
		System.out.println("Datos Socio modificados:\n"+toString());
	}
	
	public void insertarSocio() {
		db.executeUpdate(SQL_INSERTAR_SOCIO,dni,nombre,apellidos,correo,telefono,tipoCuota,numeroIban,altura,peso,fechaNacimiento,genero,esDirectivo);
		this.id = (int) db.executeQueryArray(SQL_OBTENER_ID, dni).get(0)[0];
		System.out.println("Socio creado:\n"+toString());
	}
	
	public int getId() {
		return id;
	}

	public TiposCuotas getTipoCuota() {
		return tipoCuota;
	}

	public String getNumeroIban() {
		return numeroIban;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public Generos getGenero() {
		return genero;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public double getAltura() {
		return altura;
	}

	public double getPeso() {
		return peso;
	}

	public boolean isEsDirectivo() {
		return esDirectivo;
	}

	public String getDni() {
		return dni;
	}

	public String getCorreo() {
		return correo;
	}

	public int getTelefono() {
		return telefono;
	}

	@Override
	public String toString() {
		String fecha = ""+fechaNacimiento.getYear()+"-"+fechaNacimiento.getMonthValue()+"-"+fechaNacimiento.getDayOfMonth();
		return "Nombre: "+nombre+", Apellidos: "+apellidos+", Fecha de nacimiento: "+fecha+", cuota tipo: "+tipoCuota+", numero iban: "+numeroIban+
				", altura: "+altura+", peso: "+peso+", genero: "+genero+", es directivo :"+esDirectivo;
		
	}
	
	public String toStringList() {
		String fecha = ""+fechaNacimiento.getYear()+"-"+fechaNacimiento.getMonthValue()+"-"+fechaNacimiento.getDayOfMonth();
		return id + " - " + nombre +" - " + apellidos + " - " + fecha + " - " + tipoCuota + " - " + genero;
		
	}

}
