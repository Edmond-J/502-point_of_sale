package application;

/**
 * This class is to describe a product
 */
public class Product {
	String name;
	int itemCode;
	double price;
	Unit unit;
	String brand;
	String category;

	public Product() {
	}

	public Product(int barCode, String name, String brand, double price, Unit unit, String category) {
		this.name = name;
		this.itemCode = barCode;
		this.price = price;
		this.unit = unit;
		this.brand = brand;
		this.category = category;
	}

	public void toPrint() {
	}
}
