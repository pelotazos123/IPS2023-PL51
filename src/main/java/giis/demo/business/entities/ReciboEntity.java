package giis.demo.business.entities;

public class ReciboEntity {

	private String owner_iban;
	private int number;
	private double amount;
	private String value_date;
	private String charge_date;
	private String type_recibo;
	private String state;
	
	public String getOwner_iban() { return owner_iban; }
	public int getNumber() { return number; }
	public double getAmount() { return amount; }
	public String getValue_date() { return value_date; }
	public String getCharge_date() { return charge_date; }
	public String getType_recibo() { return type_recibo; }
	public String getState() { return state; }
	
	public void setOwner_iban(String owner_iban) { this.owner_iban = owner_iban; }
	public void setNumber(int number) { this.number = number; }
	public void setAmount(double amount) { this.amount = amount; }
	public void setValue_date(String value_date) { this.value_date = value_date; }
	public void setCharge_date(String charge_date) { this.charge_date = charge_date; }
	public void setType_recibo(String type_recibo) { this.type_recibo = type_recibo; }
	public void setState(String state) { this.state = state; }
	
	
}
