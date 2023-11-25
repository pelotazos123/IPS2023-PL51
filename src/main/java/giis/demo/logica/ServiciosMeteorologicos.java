package giis.demo.logica;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import giis.demo.business.ReservationController;
import giis.demo.model.competiciones.servicio.GestionarCompeticiones;
import giis.demo.ui.VentanaPrincipal;
import giis.demo.util.Database;
import giis.demo.util.DbUtil;

public class ServiciosMeteorologicos {

//	 private static final String API_KEY = "8RfHsl5zKwBjPPRndZRuyCbdDZsahX4C";
//	private static final String API_URL = "https://api.tomorrow.io/v4/weather/forecast?"
//			+ "location='Uvieu,Asturies,España'&apikey=8RfHsl5zKwBjPPRndZRuyCbdDZsahX4C";
//	private static final String API_URL = "https://api.tomorrow.io/v4/weather/forecast?"
//	+ "location='Brasilia'&apikey=8RfHsl5zKwBjPPRndZRuyCbdDZsahX4C";	
//	private final String API_URL = "https://api.tomorrow.io/v4/weather/forecast?location='" + location + "'&apikey=8RfHsl5zKwBjPPRndZRuyCbdDZsahX4C";
	
	private static final String CARGAINSTALACIONES = "select code, name from instalaciones";
	
	private static final String TIPO_RESERVA = "RESERVA";
	private static final String TIPO_COMPETICION = "COMPETICION";
	
	private VentanaPrincipal vp;
	private ReservationController rc;
	private Properties prop;
	private Database db;
	
	
	public ServiciosMeteorologicos(VentanaPrincipal vp, Database db) {
		this.vp = vp;
		this.db = db;
		this.rc = new ReservationController(vp.getDb());
		prop = DbUtil.loadProperties(); 
	}

	/**
	 * Comprueba de manera paralela las anulaciones de competiciones
	 */
    public void checkTiempoParallelCompeticiones() {
        List<Object[]> competiciones = GestionarCompeticiones.getCompeticionesNoAnuladas(db);

        if (competiciones.size() > 0) {
			// Crear un pool de hilos
			ExecutorService executorService = Executors.newFixedThreadPool(competiciones.size());
			// Iterar sobre las competiciones y asignar cada una a un hilo
			for (Object[] objects : competiciones) {
				Runnable worker = new WeatherCheckerThread(objects, db, TIPO_COMPETICION, null);
				executorService.execute(worker);
			}
			// Apagar el pool de hilos después de que todos los trabajadores hayan terminado
			executorService.shutdown();
		}
    }
    
    /**
     * Comprueba de manera paralela las anulaciones de reservas
     */
    public void checkTiempoParallelReservas() {
    	List<Object[]> instalaciones = vp.getDb().executeQueryArray(CARGAINSTALACIONES);	
		// Crear un pool de hilos
		ExecutorService executorService = Executors.newFixedThreadPool(instalaciones.size());
		// Iterar sobre las instalaciones y asigna cada una a un hilo
		for(Object[] instalacion: instalaciones) {
			Runnable worker = new WeatherCheckerThread(instalacion, db, TIPO_RESERVA, "Helsinki");
			executorService.execute(worker);
		}
		// Apagar el pool de hilos después de que todos los trabajadores hayan terminado
		executorService.shutdown();
	}
    

    // Clase Runnable para el trabajo concurrente
    private class WeatherCheckerThread implements Runnable {
        private Object[] objects;
        private String tipo;
        private String location;

        public WeatherCheckerThread(Object[] objects, Database db, String tipo, String location) {
            this.objects = objects;
            this.tipo = tipo;
            this.location = location;
        }

        @Override
        public void run() {
        	if (tipo.equals(TIPO_COMPETICION))
        		checkWeatherReservas((String)objects[1], TIPO_COMPETICION, null); // Llamada para competiciones
        	else
        		checkWeatherReservas(location, TIPO_RESERVA, objects[1].toString()); // Llamada para reservas
		}

		private void checkWeatherReservas(String location, String tipo, String instalacion) {
			String API = "https://api.tomorrow.io/v4/weather/forecast?location=" + location + "&apikey=cPGU3YHhCPJhZwzPc2Lly68r6dGs7BwL";
			String deporte = null;
    		int id = 0;

			if (tipo.equals(TIPO_COMPETICION)) {
				id = (int) objects[0];
				deporte = (String)objects[2];
				System.out.println("Competiciones");
			} else
				System.out.println("Reservas");
			
			System.out.println(API);
		
			JsonNode jsonNode = getJsonConnection(API);
			
			for(int i = 1; i < 72; i++) {
				WeatherDto dto = new WeatherDto();
				JsonNode jn = jsonNode.get(i);
				String time = jn.get("time").asText();
				LocalDateTime day = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
				dto.hora = day;
				dto.precipitationProbability = jn.path("values")
					.get("precipitationProbability").asDouble();
				dto.temperature = jn.path("values")
					.get("temperature").asDouble();
				dto.temperatureApparent = jn.path("values")
						.get("temperatureApparent").asDouble();
				dto.rainAccumulationLwe = jn.path("values")
						.get("rainAccumulationLwe").asDouble();
				dto.snowAccumulationLwe = jn.path("values")
						.get("snowAccumulationLwe").asDouble();
				
				checkAnulaciones(dto, day, deporte, id, instalacion);
			}
			if (tipo.equals(TIPO_RESERVA))
				rc.getReservas();
		}
		
		private void checkAnulaciones(WeatherDto weather, LocalDateTime day, String deporte, int id, String instalacion) {
			int horaApertura = Integer.parseInt(prop.getProperty("club.horario.apertura"));
			int horaCierre = Integer.parseInt(prop.getProperty("club.horario.cierre"));
			int hour = weather.hora.getHour();
			if(hour >= horaApertura && hour < horaCierre) {
				System.out.println(day + "dia" + " deporte " + deporte);
				if (deporte == null)
					anulacionReservas(weather, day, instalacion);
				else 
					anulacionCompeticiones(weather, day, deporte, id);
					
			}
		}

		private void anulacionCompeticiones(WeatherDto weather, LocalDateTime day, String deporte, int id) {
			switch (deporte) {
				case "TENIS":
					checkAnulaciones(TIPO_COMPETICION, weather, null, day, 
							Double.parseDouble(prop.getProperty("weather.arco.rain")), 
							Double.parseDouble(prop.getProperty("weather.temperature")), 
							Double.parseDouble(prop.getProperty("weather.arco.snow")),
							Double.parseDouble(prop.getProperty("weather.arco.wind")));
					break;
				case "FUTBOL":
					checkAnulaciones(TIPO_COMPETICION, weather, null, day, 
							Double.parseDouble(prop.getProperty("weather.arco.rain")), 
							Double.parseDouble(prop.getProperty("weather.temperature")), 
							Double.parseDouble(prop.getProperty("weather.arco.snow")),
							1000);
					break;
				case "TIRO_CON_ARCO":
					checkAnulaciones(TIPO_COMPETICION, weather, null, day, 
							Double.parseDouble(prop.getProperty("weather.arco.rain")), 
							Double.parseDouble(prop.getProperty("weather.temperature")), 
							Double.parseDouble(prop.getProperty("weather.arco.snow")),
							1000);
					break;
				default:
					break;
			}
			
		}

		private void anulacionReservas(WeatherDto weather, LocalDateTime day, String instalacion) {
			switch (instalacion) {
				case "Tiro con arco":
					checkAnulaciones(TIPO_RESERVA,weather, instalacion, day, 
							Double.parseDouble(prop.getProperty("weather.arco.rain")), 
							Double.parseDouble(prop.getProperty("weather.temperature")), 
							Double.parseDouble(prop.getProperty("weather.arco.snow")),
							Double.parseDouble(prop.getProperty("weather.arco.wind")));
					break;
				case "Pista de tenis":
					checkAnulaciones(TIPO_RESERVA,weather, instalacion, day, 
							Double.parseDouble(prop.getProperty("weather.tenis.rain")), 
							Double.parseDouble(prop.getProperty("weather.temperature")), 
							Double.parseDouble(prop.getProperty("weather.tenis.snow")),
							1000); // Se mete 1000 para que en actividades donde el viento no importa no afecte a la decision
					break;
				case "Campo de fútbol":
					checkAnulaciones(TIPO_RESERVA,weather, instalacion, day, 
							Double.parseDouble(prop.getProperty("weather.futbol.rain")), 
							Double.parseDouble(prop.getProperty("weather.temperature")), 
							Double.parseDouble(prop.getProperty("weather.futbol.snow")),
							1000); // Se mete 1000 para que en actividades donde el viento no importa no afecte a la decision
					break;
				default:
					break;
			}	
		}
		
		private void checkAnulaciones(String tipoAnulacion, WeatherDto weather, String idInst, LocalDateTime day, double rain, double temperature, double snow, double wind) {		
			if(weather.rainAccumulationLwe >= rain || weather.temperatureApparent >= temperature
					|| weather.snowAccumulationLwe >= snow || weather.windSpeed >= wind) {
				if (tipoAnulacion.equals(TIPO_COMPETICION)) {
					DateTimeFormatter dtfComp = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String horaInicioComp = day.format(dtfComp);
					GestionarCompeticiones.createAnulation(horaInicioComp, db);				
				} else if (tipoAnulacion.equals(TIPO_RESERVA)) {
					DateTimeFormatter dtfReserva = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					String horaInicioReserva = day.format(dtfReserva);
					rc.anular(horaInicioReserva, idInst);
				}
			} else {
				DateTimeFormatter dtfReserva = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				String horaInicioReserva = day.format(dtfReserva);
				if(rc.getReservasAnuladasHora(horaInicioReserva, idInst))
					rc.borraReserva(idInst, horaInicioReserva);
			}
		}
        
	    private JsonNode getJsonConnection(String API) {
	    	JsonNode jsonNode = null;
	    	HttpURLConnection connection = null;
			try {
				URL url = new URL(API);
				connection = (HttpURLConnection) url.openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						connection.getInputStream())); 
				
				String line = reader.readLine(); 
				reader.close();
				String[] linesAux = line.split("hourly");
				linesAux[0] = linesAux[1];
				
				line = linesAux[0];
				line = line.substring(2, line.length());
				ObjectMapper om = new ObjectMapper();
				jsonNode = om.readTree(line);
			}  catch (Exception e/*IOException e*/) {
				e.printStackTrace();
				e.toString();
				System.out.println("Localización no encontrada");
			} finally {
				connection.disconnect();
			}
			return jsonNode;
	    }
    }

    
//	public static void writeToTxt(String[] array, String filePath) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//            // Iterar sobre el array y escribir cada elemento en una línea del archivo
//            for (String element : array) {
//                writer.write(element);
//                writer.newLine(); // Agregar un salto de línea después de cada elemento
//            }
//            System.out.println("Datos escritos en el archivo correctamente.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
	
//	private void checkInstalationes(WeatherDto weather, LocalDateTime day) {		
//		int horaApertura = Integer.parseInt(prop.getProperty("club.horario.apertura"));
//		int horaCierre = Integer.parseInt(prop.getProperty("club.horario.cierre"));
//		int hour = weather.hora.getHour();
//		if(hour >= horaApertura && hour < horaCierre) {
//			anulacionReservas(weather, day);
//		}
//	}

	
	
	/*
	 * SI HAY CONDICIONES ADVERSAS if(HAY RESERVA Y NO ES ANULADA) BORRA DE RESERVAS
	 * RESERVA PARA ANULAR SI NO HAY CONDICIONES ADVERSAS Y HAY RESERVA ANULADA
	 * if(HAY RESERVA ANULADA) BORRA RESERVA
	 */


	class WeatherDto {
		LocalDateTime hora;
		double temperature;
		double precipitationProbability;
		double windSpeed;			
		double rainIntensity;
		double rainAccumulationLwe;
		double temperatureApparent;			
		double humidity;
		double iceAccumulationLwe;
		double snowAccumulationLwe;
	}

}
    

