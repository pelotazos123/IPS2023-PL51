package giis.demo.business;

import java.util.ArrayList;
import java.util.List;

import giis.demo.model.Instalacion;
import giis.demo.util.Database;

public class InstalacionController {	
	
	public static List<Instalacion> getInstalaciones(Database db){
		String SQL_CARGAR_INSTALACION = "select code, name, min_reserva, max_reserva, min_curso, max_curso from instalaciones";
		List<Object[]> resQuery = db.executeQueryArray(SQL_CARGAR_INSTALACION);
		List<Instalacion> instalaciones = new ArrayList<Instalacion>();
		
		String code = "";
		String name = "";
		int min = 0;
		int max = 0;
		int minC = 0;
		int maxC = 0;
		
		for (Object[] objects : resQuery) {
			code = (String) objects[0];
			name = (String) objects[1];
			min = (int) objects[2];
			max = (int) objects[3];
			minC = (int) objects[4];
			maxC = (int) objects[5];
			
			instalaciones.add(new Instalacion(name, code, min, max, minC, maxC));
		}
		
		return instalaciones;
	}
	
}
