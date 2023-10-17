package giis.demo.util;

import java.io.*;

public abstract class FileUtil {

	public static String loadFileTickets(String nombreFicheroEntrada) {

		String linea;
		String politicaDeDatos = "";

		try {
			BufferedReader fichero = new BufferedReader(new FileReader(nombreFicheroEntrada));
			while (fichero.ready()) {
				linea = fichero.readLine();
				String[] texto = linea.split(" ");
				int j = 0;
				for(int i = 0; i < texto.length; i++) {
					politicaDeDatos += texto[i]+" ";
					j++;
					if(j == 18) {
						j = 0;
						politicaDeDatos += "\n";
					}
				}
				politicaDeDatos += "\n";
			}
			fichero.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("El archivo no se ha encontrado.");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida.");
		}
		return politicaDeDatos;
	}
}
