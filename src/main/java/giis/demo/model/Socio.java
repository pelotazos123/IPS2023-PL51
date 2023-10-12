package giis.demo.model;

import giis.demo.util.Database;

public class Socio {
	
	private final static String SQL_CARGAR_SOCIO="select id,name,surname,cuota_type,iban,height,weight,age,gender,directive from socios where id = ?" ;
	
	private Database db;
	
	private int id;
	private String tipoCuota;
	private String numeroIban;
	private String nombre;
	private String apellidos;
	private Generos genero;
	private int edad;
	private String altura;
	private int peso;
	private boolean esDirectivo;
	
	public Socio(Database db,int id) {
		this.db = db;
		cargarDatosSocio(id);
		
	}

	private void cargarDatosSocio(int id) {
		Object[] result = db.executeQueryArray(SQL_CARGAR_SOCIO, id).get(0);
		
		this.id = (int) result[0];
		nombre = (String) result[1];
		apellidos = (String) result[2];
		tipoCuota = (String) result[3];
		numeroIban = (String) result[4];
		altura = (String) result[5];
		peso = (int) result[6];
		edad = (int) result[7];
		String genero = (String)result[8];
		if(genero.equals("HOMBRE")) {
			this.genero = Generos.HOMBRE;
		}else if(genero.equals("MUJER")) {
			this.genero = Generos.MUJER;
		}else {
			this.genero = Generos.OTRO;
		}
		esDirectivo = (int)result[9] == 1;
	}
	
	public int getId() {
		return id;
	}

	public String getTipoCuota() {
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

	public int getEdad() {
		return edad;
	}

	public String getAltura() {
		return altura;
	}

	public int getPeso() {
		return peso;
	}

	public boolean isEsDirectivo() {
		return esDirectivo;
	}

	@Override
	public String toString() {
		return "Nombre: "+nombre+", Apellidos: "+apellidos+", Edad: "+edad+", cuota tipo: "+tipoCuota+", numero iban: "+numeroIban+
				", altura: "+altura+", peso: "+peso+", genero: "+genero+", es directivo :"+esDirectivo;
		
	}

}
