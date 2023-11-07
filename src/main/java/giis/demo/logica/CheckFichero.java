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
	private static final String SQL_COMPRUEBA_PAGADO = "select * from licencias where owner_id = ? "
			+ "and state = 'PAGADO' ";
	

//	private static final Double IMPORTE = 0.0;
//	private static final String IBAN_BENEFICIARIO = "ES";
	
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
		if(!noPagada())
			return false;
		if(valido)
			estableceLicenciaPagada();
		return valido;
	}

	private boolean noPagada() {
		List<Object[]> resQuery = db.executeQueryArray(SQL_COMPRUEBA_PAGADO, id);
		return resQuery.size() == 0;
	}

	private void estableceLicenciaPagada() {	
		db.executeUpdate(SQL_LICENCIA_PAGADO, id);
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
//				if(beneficiario != IBAN_BENEFICIARIO)
//					return false;
			} else 
				return false;
		} else if(numLinea == 6) {
			if(line[0].equals("Importe:")) {
//				double importe = Double.parseDouble(line[1]);
//				if(importe != IMPORTE) 
//					return false;
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
