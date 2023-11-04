package application;

/**
 * This class is to describe a product
 */
public class Product {
	String name;
	int itemCode;
	double price;
	String unit;
	String brand;
	String category;

	public Product() {
	}

	public Product(String name, int itemCode, double price, String unit, String brand, String category) {
		this.name = name;
		this.itemCode = itemCode;
		this.price = price;
		this.unit = unit;
		this.brand = brand;
		this.category = category;
	}

	public void toPrint() {
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setItemCode(int itemCode) {
		this.itemCode = itemCode;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public int getItemCode() {
		return itemCode;
	}

	public double getPrice() {
		return price;
	}

	public String getUnit() {
		return unit;
	}

	public String getBrand() {
		return brand;
	}

	public String getCategory() {
		return category;
	}
}
