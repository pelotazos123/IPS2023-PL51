package giis.demo.business;

import java.util.List;

import giis.demo.model.Instalacion;
import giis.demo.util.Database;

public class InstalacionController {
	
	private final static String SQL_CARGAR_INSTALACION = "select code, name from instalaciones";	
	
	public static Instalacion[] getInstalaciones(Database db){
		List<Object[]> resQuery = db.executeQueryArray(SQL_CARGAR_INSTALACION);
		Instalacion[] listInstalacion = new Instalacion[resQuery.size()];
		
		String code = "";
		String name = "";
		
		int contador = 0;
		
		for (Object[] objects : resQuery) {
			code = (String) objects[0];
			name = (String) objects[1];
			
			listInstalacion[contador] = new Instalacion(name, code);
			
			contador++;
		}
		
		return listInstalacion;
	}
	
}
