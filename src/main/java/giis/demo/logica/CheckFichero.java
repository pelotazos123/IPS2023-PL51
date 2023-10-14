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
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			List<String> lines = new ArrayList<String>();
			
			while(reader.readLine() != null)
				lines.add(reader.readLine());
			reader.close();
			
			if(!lines.get(0).contains("Nombre:"))
				return false;
			if(!lines.get(1).contains("Apellidos:"))
				return false;
			if(!lines.get(2).contains("DNI:"))
				return false;
			if(!lines.get(3).contains("IBAN ORDENANTE"))
				return false;
			if(!lines.get(4).contains("IBAN BENEFICIARIO:"))
				return false;
			if(!lines.get(5).contains("Importe:"))
				return false;
			if(!lines.get(5).contains("Fecha:"))
				return false;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
}
