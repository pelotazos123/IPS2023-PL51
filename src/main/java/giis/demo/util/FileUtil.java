package giis.demo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import giis.demo.model.Socio;
import giis.demo.model.TiposCuotas;
import giis.demo.model.competiciones.Competicion;

public abstract class FileUtil {

	public static String loadFilePoliticaDatos(String nombreFicheroEntrada) {

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
	
	public static void saveToFileSociosInscritos(File archivo, List<Socio> socios) {
		FileWriter escribir;
		try {
			escribir = new FileWriter(archivo,true);
			String texto="";
			for(int i = 0; i < socios.size(); i++) {
				Socio socio = socios.get(i);
				texto += socio.getNombre()+";"+socio.getApellidos()+";"+socio.getCorreo()+";"+socio.getTelefono()+";"+socio.getFechaNacimiento().toString()+";"+socio.getTipoCuota()+"\n";
			}
			escribir.write(texto);
			escribir.close();
		}

		catch (FileNotFoundException fnfe) {
			System.out.println("El archivo no se ha podido guardar");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida");
		}
	}
	
	public static List<Competicion> loadFileCompeticiones(File archivo,int ultimoId) {

		String linea;
		List<Competicion> lista = new ArrayList<>();

		try {
			BufferedReader fichero = new BufferedReader(new FileReader(archivo.getAbsolutePath()));
			while (fichero.ready()) {
				linea = fichero.readLine();
				String[] texto = linea.split(";");
				lista.add(crearCompeticion(texto,ultimoId++));
			}
			fichero.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("El archivo no se ha encontrado.");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida.");
		}
		return lista;
	}

	private static Competicion crearCompeticion(String[] competicion, int nuevoId) {
		int id = nuevoId;
		String nombre = competicion[0];
		String fecha = competicion[1];
		String lugar = competicion[2];
		String categorias = competicion[3];
		
		String[] aux = fecha.split("-");
		int año = Integer.parseInt(aux[0]);
		int mes= Integer.parseInt(aux[1]);
		int dia = Integer.parseInt(aux[2]);
		LocalDate fechaCompe = LocalDate.of(año, mes, dia);
		
		aux = categorias.split("-");
		List<TiposCuotas> listaCategorias = new ArrayList<>();
		for(int j = 0; j < aux.length; j++) {
			if(aux[j].equals("SUB18")) {
				listaCategorias.add(TiposCuotas.SUB18);
			}else if(aux[j].equals("SENIOR")) {
				listaCategorias.add(TiposCuotas.SENIOR);
			}else {
				listaCategorias.add(TiposCuotas.VETERANO);
			}
		}
		return new Competicion(id, nombre, fechaCompe, lugar, listaCategorias);
	}
}
