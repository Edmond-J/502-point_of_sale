package application;


public class Supplier {
	int ID;
	String name;
	String address;
	int phone;
	String email;

	public Supplier() {
	}

	public Supplier(int iD, String name, String address, int phone, String email) {
		ID = iD;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}
}
