package giis.demo.business;

import java.util.List;

import giis.demo.model.Generos;
import giis.demo.model.Socio;
import giis.demo.util.Database;

public abstract class CargaSocios {
	
	private final static String SQL_CARGAR_TODOS_SOCIOS = "select id, name, surname, cuota_type, gender, age from socios";
	
	public static Socio[] cargarSocios(Database db) {
		List<Object[]> res = db.executeQueryArray(SQL_CARGAR_TODOS_SOCIOS);
		Socio[] socios = new Socio[res.size()];
		
		int id = 0;
		String nombre = "";
		String apellidos = "";
		String cuota = "";
		Generos genero;
		int edad = 0;
		
		int counter = 0; 
		
		for (Object[] objects : res) {
			
			id = (int) objects[0];
			nombre = (String)objects[1];
			apellidos = (String)objects[2];
			cuota = (String)objects[3];
			genero = getGenre((String)objects[4]);
			edad = (int)objects[5];
				
			socios[counter] = new Socio(id, nombre, apellidos, cuota, genero, edad);
					
			counter++;
		}
		
		System.out.println(socios[2].toStringList());
		System.out.println(socios[3].toStringList());
		System.out.println(socios[4].toStringList());
		System.out.println(socios[5].toStringList());
		
		for (int i = socios.length; i <= 0 ; i--) {
			System.out.println(socios[i].toStringList());
		}
				
		return socios;
		
	}
	
	private static Generos getGenre(String genero) {
		Generos generoF;
		if(genero.equals("HOMBRE")) {
			generoF = Generos.HOMBRE;
		}else if(genero.equals("MUJER")) {
			generoF = Generos.MUJER;
		}else {
			generoF = Generos.OTRO;
		}
		return generoF;
	}

}
