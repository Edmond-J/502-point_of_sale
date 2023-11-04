package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

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

	public void saveToFile() throws IOException {
		File file = new File("data/db_ products.csv");
		System.out.println(file.exists());
		FileWriter fileWriter = new FileWriter(file, true);
		CSVPrinter csvPrinter;
		if (!file.exists()) {
			csvPrinter = new CSVPrinter(fileWriter,
					CSVFormat.DEFAULT.withHeader("NAME", "ITEM", "CODE", "PRICE", "UNIT", "BRAND", "CATEGORY"));
		} else {
			csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
		}
		csvPrinter.printRecord(name, itemCode, price, unit, brand, category);
		csvPrinter.flush();
		csvPrinter.close();
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
