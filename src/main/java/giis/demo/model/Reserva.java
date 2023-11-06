package giis.demo.model;

public class Reserva {

	private int id;
	private String fecha;
	private String instalacionId;	
	private String hora;
	private boolean extra;
	
	public Reserva(int id, String fecha, String hora, String instalacionId, boolean extra) {
		this.id = id;
		this.fecha = fecha;
		this.hora = hora;
		this.instalacionId = instalacionId;
		this.extra = extra;
	}
	
	public int getId() {
		return id;
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
		return "Reserva: " + id + " - " + fecha + " - " + hora + " - " + instalacionId + " - " + hasExtra();
	}
	
}
