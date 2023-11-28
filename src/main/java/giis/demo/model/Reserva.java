package giis.demo.model;

public class Reserva {
	
	private int id;
	private String fechaInicio;
	private String instalationCode;
	private String horaInicio;
	private String fechaFin;
	private String horaFin;
	private String tipo_curso;
	
	public Reserva(int id, String fechaInicio, String horaInicio, String fechaFin, String horaFin, String instalationCode, String tipo_curso) {
		this.id = id;
		this.fechaInicio = fechaInicio;
		this.horaInicio = horaInicio;
		this.instalationCode = instalationCode;
		this.fechaFin = fechaFin;
		this.horaFin = horaFin;
		this.tipo_curso = tipo_curso;
	}
	
	public String getFechaInicio() {
		return fechaInicio;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public int getId() {
		return id;
	}

	public String getInstalationCode() {
		return instalationCode;
	}
	
	public String getTipoCurso() {
		return tipo_curso;
	}

	@Override
	public String toString() {
		return "Reserva: " + id + " - " + fechaInicio + " - " + horaInicio + " - " + instalationCode;
	}
	
}
