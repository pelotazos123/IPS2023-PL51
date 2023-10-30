package giis.demo.business.entities;

public class SocioEntity {
	
	private String id;
	private String dni;
	private String name;
	private String surname;
	private String email; 
	private String telf;
	private String cuota_type;
	private String iban;
	private double height;
	private double weight;
	private String birth_date;
	private String gender;
	private boolean directive;
	
	// Getters
	public String getId() { return id; }
	public String getDni() { return dni; }
	public String getName() { return name; }
	public String getSurname() { return surname; }
	public String getEmail() { return email; }
	public String getTelf() { return telf; }
	public String getCuota_type() { return cuota_type; }
	public String getIban() { return iban; }
	public double getHeight() { return height; }
	public double getWeight() { return weight; }
	public String getBirth_date() { return birth_date; }
	public String getGender() { return gender; }
	public boolean isDirective() { return directive; }
	// Setters
	public void setId(String id) { this.id = id; }
	public void setDni(String dni) { this.dni = dni; }
	public void setName(String name) { this.name = name; }
	public void setSurname(String surname) { this.surname = surname; }
	public void setEmail(String email) { this.email = email; }
	public void setTelf(String telf) { this.telf = telf; }
	public void setCuota_type(String cuota_type) { this.cuota_type = cuota_type; }
	public void setIban(String iban) { this.iban = iban; }
	public void setHeight(double height) { this.height = height; }
	public void setWeight(double weight) { this.weight = weight; }
	public void setBirth_date(String birth_date) { this.birth_date = birth_date; }
	public void setGender(String gender) { this.gender = gender; }
	public void setDirective(boolean directive) { this.directive = directive; }
	
}

