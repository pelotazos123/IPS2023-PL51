package giis.demo.model;

public class Instalacion {
	
	private String name;
	private String code;
	private int min;
	private int max;
	
	public Instalacion(String name, String code, int min, int max) {
		this.name = name;
		this.code = code;
		this.min = min;
		this.max = max;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCode() {
		return code;
	}
	
	public int getMin() {
		return min;
	}
	
	public int getMax() {
		return max;
	}
	
	public String toString() {
		String str = ""+getName()+" - " + getCode(); 
		return str;
	}

}
