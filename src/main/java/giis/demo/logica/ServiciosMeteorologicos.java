package giis.demo.logica;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import giis.demo.business.ReservationController;
import giis.demo.ui.VentanaPrincipal;

public class ServiciosMeteorologicos {

//	 private static final String API_KEY = "8RfHsl5zKwBjPPRndZRuyCbdDZsahX4C";
	private static final String API_URL = "https://api.tomorrow.io/v4/weather/forecast?"
			+ "location='Uvieu,Asturies,España'&apikey=8RfHsl5zKwBjPPRndZRuyCbdDZsahX4C";
//	private static final String API_URL = "https://api.tomorrow.io/v4/weather/forecast?"
//	+ "location='Brasilia'&apikey=8RfHsl5zKwBjPPRndZRuyCbdDZsahX4C";
	
	private static final String CARGAINSTALACIONES = "select code, name from instalaciones";
	
	private VentanaPrincipal vp;
	private ReservationController rc;
	
	public ServiciosMeteorologicos(VentanaPrincipal vp) {
		this.vp = vp;
		this.rc = new ReservationController(vp.getDb());
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

	private void checkInstalationes(WeatherDto weather, LocalDateTime day) {
		int hour = weather.hora.getHour();
		if(hour > 8 && hour < 23) {
			List<Object[]> instalaciones = vp.getDb().executeQueryArray(CARGAINSTALACIONES);
			for(Object[] instalacion: instalaciones) {
				switch (instalacion[1].toString()) {
				case "Tiro con arco":
					checkTiroConArco(weather, instalacion[0].toString(), day);
					break;
				case "Pista de tenis":
					checkTenis(weather, instalacion[0].toString(), day);
					break;
				case "Campo de fútbol":
					checkFutbol(weather, instalacion[0].toString(), day);
					break;
				default:
					break;
				}
			}
		}
	}
	
	
	/*
	 * SI HAY CONDICIONES ADVERSAS if(HAY RESERVA Y NO ES ANULADA) BORRA DE RESERVAS
	 * RESERVA PARA ANULAR SI NO HAY CONDICIONES ADVERSAS Y HAY RESERVA ANULADA
	 * if(HAY RESERVA ANULADA) BORRA RESERVA
	 */
	private void checkTenis(WeatherDto weather, String idInst, LocalDateTime day) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String reserva = day.format(dtf);
		if(weather.rainAccumulationLwe >= 0.10 || weather.temperatureApparent >= 40.0 
				|| weather.snowAccumulationLwe >= 0.1) {
			if(rc.getReservasNoAnuladasHora(reserva, idInst)) {
				rc.borraReserva(idInst, reserva);
			}
			rc.anular(day, reserva, idInst);
		} else {
			if(rc.getReservasAnuladasHora(reserva, idInst))
				rc.borraReserva(idInst, reserva);
		}
			
	}

	private void checkFutbol(WeatherDto weather, String idInst, LocalDateTime day) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String reserva = day.format(dtf);
		if(weather.rainAccumulationLwe >= 1.0 || weather.temperatureApparent >= 40.0 
				|| weather.snowAccumulationLwe >= 0.5) {
			if(rc.getReservasNoAnuladasHora(reserva, idInst)) 
				rc.borraReserva(idInst, reserva);
			rc.anular(day, reserva, idInst);
		} else {
			if(rc.getReservasAnuladasHora(reserva, idInst))
				rc.borraReserva(idInst, reserva);
		}
	}

	private void checkTiroConArco(WeatherDto weather, String idInst, LocalDateTime day) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String reserva = day.format(dtf);
		if(weather.rainAccumulationLwe >= 0.05 || weather.temperatureApparent >= 40.0 
				|| weather.snowAccumulationLwe >= 0.05 || weather.windSpeed >= 7) {
			if(rc.getReservasNoAnuladasHora(reserva, idInst))
				rc.borraReserva(idInst, reserva);
			rc.anular(day, reserva, idInst);
		} else {
			if(rc.getReservasAnuladasHora(reserva, idInst))
				rc.borraReserva(idInst, reserva);
		}
	}

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

