package giis.demo.model;

public class Instalacion {
	
	private String name;
	private String code;
	private int minReserva;
	private int maxReserva;
	private int minCurso;
	private int maxCurso;	
	
	public Instalacion(String name, String code, int min, int max, int minCurso, int maxCurso) {
		this.name = name;
		this.code = code;
		this.minReserva = min;
		this.maxReserva = max;
		this.minCurso = minCurso;
		this.maxCurso = maxCurso;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCode() {
		return code;
	}
	
	public int getMinReserva() {
		return minReserva;
	}
	
	public int getMaxReserva() {
		return maxReserva;
	}
	
	public int getMinCurso() {
		return minCurso;
	}
	
	public int getMaxCurso() {
		return maxCurso;
	}
	
	public String toString() {
		String str = ""+getName()+" - " + getCode(); 
		return str;
	}

}

