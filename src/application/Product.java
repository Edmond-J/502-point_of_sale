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
	private String name;
	private int itemCode;
	private double price;
	private String unit;
	private String brand;
	private String category;

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

	public void saveToFile() {
		File file = new File("data/db_ products.csv");
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(file, true);
			CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
			if (file.length()==0) {//not using file.exists()as condition, because fileWriter = new FileWriter(file, true) will affect
				csvPrinter.printRecord("NAME", "ITEM CODE", "PRICE", "UNIT", "BRAND", "CATEGORY");
			}
			csvPrinter.printRecord(name, itemCode, price, unit, brand, category);
			csvPrinter.flush();
			csvPrinter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Product) {
			Product p = (Product)obj;
			return (this.name.equals(p.name));
		} else return (this == obj);
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
