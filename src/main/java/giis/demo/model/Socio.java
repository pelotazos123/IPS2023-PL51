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
	private String genero;
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
		genero = (String) result[8];
		esDirectivo = (int)result[9] == 1;
	}
	
	@Override
	public String toString() {
		return "Nombre: "+nombre+", Apellidos: "+apellidos+", Edad: "+edad+", cuota tipo: "+tipoCuota+", numero iban: "+numeroIban+
				", altura: "+altura+", peso: "+peso+", genero: "+genero+", es directivo :"+esDirectivo;
		
	}

}
