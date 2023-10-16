package giis.demo.model;

public class Reserva {

	private int owner_id;
	private String fecha;
	private String hora;
	private String instalacionId;	
	
	public Reserva(int owner_id, String fecha, String hora, String instalacionId) {
		this.owner_id = owner_id;
		this.fecha = fecha;
		this.hora = hora;
		this.instalacionId = instalacionId;
	}
	
	public int getOwner_id() {
		return owner_id;
	}

	public String getFecha() {
		return fecha;
	}

	public String getHora() {
		return hora;
	}

	public String getInstalacionId() {
		return instalacionId;
	}
	
	@Override
	public String toString() {
		return "Reserva: " + owner_id + " - " + fecha + " - " + hora + "- " + instalacionId;
	}
	
}
