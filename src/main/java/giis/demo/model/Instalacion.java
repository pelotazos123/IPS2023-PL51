package giis.demo.model;

public class Instalacion {
	
	private String name;
	private String code;
	
	public Instalacion(String name, String code) {
		this.name = name;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCode() {
		return code;
	}
	
	public String toString() {
		String str = ""+getName()+" - " + getCode(); 
		return str;
	}

}
