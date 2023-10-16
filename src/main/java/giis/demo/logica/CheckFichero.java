package giis.demo.logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import giis.demo.util.Database;

public class CheckFichero {

	private static final String SQL_LICENCIA_PAGADO = "update licencias set state = 'PAGADO' where owner_id = ? ";
	private static final String SQL_COMPRUEBA_PAGADO = "select state from licencias where owner_id = ? ";
	
	private Database db = new Database();
	private String id;
	
	public boolean checkFichero(File f) {
		boolean valido = false;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			List<String> lines = new ArrayList<String>();
			
			String aux = reader.readLine(); 
			while(aux != null) {
				lines.add(aux);
				aux = reader.readLine();
			}
			reader.close();
			
			for(int i = 0; i < lines.size(); i++) {
				String[] line = lines.get(i).split(" ");
				valido = validaLinea(line, i);
				if(!valido)
					return false;
			}
			
		} catch (FileNotFoundException e) {
			valido = false;
			e.printStackTrace();
		} catch (Exception e) {
			valido = false;
			e.printStackTrace();
		}
		if(valido)
			estableceLicenciaPagada();
		return valido;
	}

	private void estableceLicenciaPagada() {	
		db.executeUpdate(SQL_LICENCIA_PAGADO, id);
		List<Object[]> resQuery = db.executeQueryArray(SQL_COMPRUEBA_PAGADO, id);
		for(Object[] a: resQuery) {
			System.out.println(a[0].toString());
		}
//		System.out.println(resQuery.get(0).toString());
	}

	private boolean validaLinea(String[] line, int numLinea) {
		if(numLinea == 0) {
			if(line[0].equals("Nombre:")) {
//				String nombre = "";
//				for(int i = 1; i < line.length; i++)
//					nombre += line[i];
			} else 
				return false;
		} else if(numLinea == 1) {
			if(line[0].equals("Apellidos:")) {
//				String apellidos = "";
//				for(int i = 1; i < line.length; i++)
//					apellidos += line[i];

			} else 
				return false;
		} else if(numLinea == 2) {
			if(line[0].equals("DNI:")) {
				
			} else 
				return false;
		} else if(numLinea == 3) {
			if(line[0].equals("ID:")) {
				this.id= line[1];
//				//TODO: Consulta a la base de datos
			} else 
				return false;
		} else if(numLinea == 4) {
			if(line[0].equals("IBAN") && line[1].equals("ORDENANTE:")) {
//				String ordenante = line[2];
//				System.out.println(ordenante);

			} else 
				return false;
		} else if(numLinea == 5) {
			if(line[0].equals("IBAN") && line[1].equals("BENEFICIARIO:")) {
//				String beneficiario = line[2];

			} else 
				return false;
		} else if(numLinea == 6) {
			if(line[0].equals("Importe:")) {
//				String importe = line[1];

			} else 
				return false;
		} else if(numLinea == 7) {
			if(line[0].equals("Fecha:")) {
				
			} else 
				return false;
		} else if(numLinea == 8) {
			if(line[0].equals("Concepto:")) {
				
			} else 
				return false;
		} 
		return true;
	}
	
}