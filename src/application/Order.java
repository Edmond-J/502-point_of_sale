/**
 * 
 */
package application;

public class Order {
	private String Date;
	private String time;
	private Product product;
	private double quantity;
	private double total;
	
	public Order() {
	}

	public Order(String date, String time, Product product, double quantity) {
		super();
		Date = date;
		this.time = time;
		this.product = product;
		this.quantity = quantity;
		this.total=quantity*product.getPrice();
	}

	public String getDate() {
		return Date;
	}

	public String getTime() {
		return time;
	}

	public Product getProduct() {
		return product;
	}

	public double getQuantity() {
		return quantity;
	}

	public double getTotal() {
		return total;
	}
	
	
	
}
