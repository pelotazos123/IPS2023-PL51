package giis.demo.business.entities;

public class SocioEntity {
	
	private String id;
	private String dni;
	private String name;
	private String surname;
	private String email; 
	private String cuota_type;
	private String iban;
	private String height;
	private int weight;
	private String birth_date;
	private String gender;
	private boolean directive;
	
	// Getters
	public String getId() { return id; }
	public String getDni() { return dni; }
	public String getName() { return name; }
	public String getSurname() { return surname; }
	public String getEmail() { return email; }
	public String getCuota_type() { return cuota_type; }
	public String getIban() { return iban; }
	public String getHeight() { return height; }
	public int getWeight() { return weight; }
	public String getBirth_date() { return birth_date; }
	public String getGender() { return gender; }
	public boolean isDirective() { return directive; }
	// Setters
	public void setId(String id) { this.id = id; }
	public void setDni(String dni) { this.dni = dni; }
	public void setName(String name) { this.name = name; }
	public void setSurname(String surname) { this.surname = surname; }
	public void setEmail(String email) { this.email = email; }
	public void setCuota_type(String cuota_type) { this.cuota_type = cuota_type; }
	public void setIban(String iban) { this.iban = iban; }
	public void setHeight(String height) { this.height = height; }
	public void setWeight(int weight) { this.weight = weight; }
	public void setBirth_date(String birth_date) { this.birth_date = birth_date; }
	public void setGender(String gender) { this.gender = gender; }
	public void setDirective(boolean directive) { this.directive = directive; }
	
}
