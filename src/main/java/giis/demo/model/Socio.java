package giis.demo.model;

public class Socio {
	
	private final static String SQL_CARGAR_SOCIO="select * from socios where id = ?" ;
	
	private int id;
	private int tipoCuota;
	private String numeroIban;
	private String nombre;
	private String apellidos;
	private String genero;
	private int edad;
	private String altura;
	private int peso;
	private boolean directivo;
	
	public Socio() {
		cargarDatosSocio();
		
	}

	private void cargarDatosSocio() {
		
		
	}

}
