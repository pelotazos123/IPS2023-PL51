package giis.demo.model;

public class Reserva {

	private int owner_id;
	private String fecha;
	private String instalacionId;	
	private String hora;
	private boolean extra;
	
	public Reserva(int owner_id, String fecha, String hora, String instalacionId, boolean extra) {
		this.owner_id = owner_id;
		this.fecha = fecha;
		this.hora = hora;
		this.instalacionId = instalacionId;
		this.extra = extra;
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
	
	public boolean hasExtra() {
		return extra;
	}
	
	@Override
	public String toString() {
		return "Reserva: " + owner_id + " - " + fecha + " - " + hora + " - " + instalacionId + " - " + hasExtra();
	}
	
}
