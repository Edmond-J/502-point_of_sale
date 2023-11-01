package application;

import java.util.Date;

/**
 * This class is to describe a product
 */
public class Product {
	int barCode;
	String name;
	String brand;
	double price;
	Unit unit;
	Date expDate;
	double inStrck;
	
	public Product(int barCode, String name, String brand, double price, Unit unit, Date expDate, double inStrck) {
		this.barCode = barCode;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.unit = unit;
		this.expDate = expDate;
		this.inStrck = inStrck;
	}

	public void toPrint(){
		
	}
}
