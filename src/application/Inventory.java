package application;

import java.util.Date;

public class Inventory {
	int barCode;
	String name;
	String brand;
	Supplier supplier;
	double price;
	String unit;
	Date expDate;
	double inStrck;
	
	public Inventory() {
	}

	public Inventory(int barCode, String name, String brand, Supplier supplier, double price, String unit, Date expDate,
			double inStrck) {
		this.barCode = barCode;
		this.name = name;
		this.brand = brand;
		this.supplier = supplier;
		this.price = price;
		this.unit = unit;
		this.expDate = expDate;
		this.inStrck = inStrck;
	}
	
}
