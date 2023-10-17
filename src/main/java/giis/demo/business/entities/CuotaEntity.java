package giis.demo.business.entities;

public class CuotaEntity {
	private String owner_id;
	private String cuota_type;
	private String state;
	private String price;
	
	
	public String getOwner_id() { return owner_id; }
	public String getCuota_type() { return cuota_type; }
	public String getState() {	return state; }
	public String getPrice() { return price; }

	public void setOwner_id(String owner_id) { this.owner_id = owner_id; }
	public void setCuota_type(String cuota_type) { this.cuota_type = cuota_type; }
	public void setState(String state) { this.state = state; }
	public void setPrice(String price) { this.price = price; }
	
}
