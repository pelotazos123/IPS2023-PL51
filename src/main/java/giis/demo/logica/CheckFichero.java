package giis.demo.logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckFichero {

	public static boolean checkFichero(File f) {
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
		
		return valido;
	}

	private static boolean validaLinea(String[] line, int numLinea) {
		if(numLinea == 0) {
			if(line[0].equals("Nombre:")) {
				String nombre = "";
				for(int i = 1; i < line.length; i++)
					nombre += line[i];
				//TODO: Consulta a la base de datos
			} else 
				return false;
		} else if(numLinea == 1) {
			if(line[0].equals("Apellidos:")) {
				String apellidos = "";
				for(int i = 1; i < line.length; i++)
					apellidos += line[i];
				//TODO: Consulta a la base de datos
			} else 
				return false;
		} else if(numLinea == 2) {
			if(line[0].equals("DNI:")) {
				String dni = line[1];
				//TODO: Consulta a la base de datos
			} else 
				return false;
		} else if(numLinea == 3) {
			if(line[0].equals("IBAN") && line[1].equals("ORDENANTE:")) {
				String ordenante = line[2];
				System.out.println(ordenante);
				//TODO: Consulta a la base de datos
			} else 
				return false;
		} else if(numLinea == 4) {
			if(line[0].equals("IBAN") && line[1].equals("BENEFICIARIO:")) {
				String beneficiario = line[2];
				//TODO: Consulta a la base de datos
			} else 
				return false;
		} else if(numLinea == 5) {
			if(line[0].equals("Importe:")) {
//				String importe = line[1];
				//TODO: Consulta a la base de datos
			} else 
				return false;
		} else if(numLinea == 6) {
			if(line[0].equals("Fecha:")) {
				//TODO: Consulta a la base de datos
			} else 
				return false;
		} else if(numLinea == 7) {
			if(line[0].equals("Concepto:")) {
				//TODO: Consulta a la base de datos
			} else 
				return false;
		} 
		return true;
	}
	
}
