package giis.demo.model;

public class Reserva {

	public enum TIPO_RESERVA{
		NORMAL, ANULADA;
	}
	
	private int id;
	private String fecha;
	private String instalacionId;	
	private String hora;
	private boolean extra;
	private TIPO_RESERVA tipo;
	
	public Reserva(int id, String fecha, String hora, String instalacionId, boolean extra, TIPO_RESERVA tipo) {
		this.id = id;
		this.fecha = fecha;
		this.hora = hora;
		this.instalacionId = instalacionId;
		this.extra = extra;
		this.tipo = tipo;
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
		return "Reserva: " + id + " - " + fecha + " - " + hora + " - " + instalacionId + " - " + hasExtra() 
		+ " - " + this.tipo;
	}
	
}
