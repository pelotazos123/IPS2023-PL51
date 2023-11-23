package giis.demo.logica;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import giis.demo.business.ReservationController;
import giis.demo.ui.VentanaPrincipal;
import giis.demo.util.DbUtil;

public class ServiciosMeteorologicos {

//	 private static final String API_KEY = "8RfHsl5zKwBjPPRndZRuyCbdDZsahX4C";
//	private static final String API_URL = "https://api.tomorrow.io/v4/weather/forecast?"
//			+ "location='Uvieu,Asturies,España'&apikey=8RfHsl5zKwBjPPRndZRuyCbdDZsahX4C";
//	private static final String API_URL = "https://api.tomorrow.io/v4/weather/forecast?"
//	+ "location='Brasilia'&apikey=8RfHsl5zKwBjPPRndZRuyCbdDZsahX4C";
	private static final String API_URL = "https://api.tomorrow.io/v4/weather/forecast?"
			+ "location='Helsinki'&apikey=8RfHsl5zKwBjPPRndZRuyCbdDZsahX4C";
	
	private static final String CARGAINSTALACIONES = "select code, name from instalaciones";
	
	
	private VentanaPrincipal vp;
	private ReservationController rc;
	private Properties prop;
	
	public ServiciosMeteorologicos(VentanaPrincipal vp) {
		this.vp = vp;
		this.rc = new ReservationController(vp.getDb());
		prop = DbUtil.loadProperties(); 
	}

	public void checkTiempo() {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(API_URL);
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
			JsonNode jsonNode = om.readTree(line);
			
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
				checkInstalationes(dto, day);
			}
			rc.getReservas();
			
		} catch (Exception e/*IOException e*/) {
			e.printStackTrace();
			e.toString();
			System.out.println("Localización no encontrada");
		} finally {
			connection.disconnect();
		}
	}

	public static void writeToTxt(String[] array, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Iterar sobre el array y escribir cada elemento en una línea del archivo
            for (String element : array) {
                writer.write(element);
                writer.newLine(); // Agregar un salto de línea después de cada elemento
            }
//            System.out.println("Datos escritos en el archivo correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private void checkInstalationes(WeatherDto weather, LocalDateTime day) {		
		int horaApertura = Integer.parseInt(prop.getProperty("club.horario.apertura"));
		int horaCierre = Integer.parseInt(prop.getProperty("club.horario.cierre"));
		int hour = weather.hora.getHour();
		if(hour >= horaApertura && hour < horaCierre) {
			anulacionReservas(weather, day);
			anulacionCompeticiones(weather, day);
		}
	}

	private void anulacionCompeticiones(WeatherDto weather, LocalDateTime day) {
		// TODO Auto-generated method stub
		
	}

	private void anulacionReservas(WeatherDto weather, LocalDateTime day) {
		List<Object[]> instalaciones = vp.getDb().executeQueryArray(CARGAINSTALACIONES);
		for(Object[] instalacion: instalaciones) {
			switch (instalacion[1].toString()) {
			case "Tiro con arco":
				checkAnulaciones(weather, instalacion[0].toString(), day, 
						Double.parseDouble(prop.getProperty("weather.arco.rain")), 
						Double.parseDouble(prop.getProperty("weather.temperature")), 
						Double.parseDouble(prop.getProperty("weather.arco.snow")),
						Double.parseDouble(prop.getProperty("weather.arco.wind")));
				break;
			case "Pista de tenis":
				checkAnulaciones(weather, instalacion[0].toString(), day, 
						Double.parseDouble(prop.getProperty("weather.tenis.rain")), 
						Double.parseDouble(prop.getProperty("weather.temperature")), 
						Double.parseDouble(prop.getProperty("weather.tenis.snow")),
						1000); // Se mete 1000 para que en actividades donde el viento no importa no afecte a la decision
				break;
			case "Campo de fútbol":
				checkAnulaciones(weather, instalacion[0].toString(), day, 
						Double.parseDouble(prop.getProperty("weather.futbol.rain")), 
						Double.parseDouble(prop.getProperty("weather.temperature")), 
						Double.parseDouble(prop.getProperty("weather.futbol.snow")),
						1000); // Se mete 1000 para que en actividades donde el viento no importa no afecte a la decision
				break;
			default:
				break;
			}
		}
	}
	
	private void checkAnulaciones(WeatherDto weather, String idInst, LocalDateTime day, double rain, double temperature, double snow, double wind) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String horaInicio = day.format(dtf);
		
		if(weather.rainAccumulationLwe >= rain || weather.temperatureApparent >= temperature
				|| weather.snowAccumulationLwe >= snow || weather.windSpeed >= wind) {
			rc.anular(horaInicio, idInst);
		} else {
			if(rc.getReservasAnuladasHora(horaInicio, idInst))
				rc.borraReserva(idInst, horaInicio);
		}
	}
	
	/*
	 * SI HAY CONDICIONES ADVERSAS if(HAY RESERVA Y NO ES ANULADA) BORRA DE RESERVAS
	 * RESERVA PARA ANULAR SI NO HAY CONDICIONES ADVERSAS Y HAY RESERVA ANULADA
	 * if(HAY RESERVA ANULADA) BORRA RESERVA
	 */
//	private void checkTiroConArco(WeatherDto weather, String idInst, LocalDateTime day) {
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//		String horaInicio = day.format(dtf);
//		String horaFin = day.plusHours(1).format(dtf);
//		double rain = Double.parseDouble(prop.getProperty("weather.arco.rain"));
//		double temperature = Double.parseDouble(prop.getProperty("weather.temperature"));
//		double snow = Double.parseDouble(prop.getProperty("weather.arco.snow"));
//		double wind = Double.parseDouble(prop.getProperty("weather.arco.wind"));
//		if(weather.rainAccumulationLwe >= rain || weather.temperatureApparent >= temperature
//				|| weather.snowAccumulationLwe >= snow || weather.windSpeed >= wind) {
//			if(rc.getReservasNoAnuladasHora(horaInicio, idInst))
//				rc.borraReserva(idInst, horaInicio);
//			rc.anular(horaInicio, horaFin, idInst);
//		} else {
//			if(rc.getReservasAnuladasHora(horaInicio, idInst))
//				rc.borraReserva(idInst, horaInicio);
//		}
//	}

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

