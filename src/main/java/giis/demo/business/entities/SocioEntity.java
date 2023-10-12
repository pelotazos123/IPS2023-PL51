package giis.demo.business.entities;

public class SocioEntity {
	
	private String id;
	private String name; 
	private int cuota_type;
	private String iban;
	private String height;
	private int weight;
	private int age;
	private String gender;
	private boolean directive;
	
	// Getters
	public String getId() { return id; }
	public String getName() { return name; }
	public int getCuota_type() { return cuota_type; }
	public String getIban() { return iban; }
	public String getHeight() { return height; }
	public int getWeight() { return weight; }
	public int getAge() { return age; }
	public String getGender() { return gender; }
	public boolean isDirective() { return directive; }
	// Setters
	public void setId(String id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setCuota_type(int cuota_type) { this.cuota_type = cuota_type; }
	public void setIban(String iban) { this.iban = iban; }
	public void setHeight(String height) { this.height = height; }
	public void setWeight(int weight) { this.weight = weight; }
	public void setAge(int age) { this.age = age; }
	public void setGender(String gender) { this.gender = gender; }
	public void setDirective(boolean directive) { this.directive = directive; }
	
}
