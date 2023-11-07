package testing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.AddSKUController;
import application.Product;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Testing {
	Product product;
	AddSKUController controller;
	ArrayList<Product> mockData;
	
	public Testing() {
//		File file = new File("data/db_ products.csv");
//		System.out.println(file.exists());
		testCheckItemCode();
//		testProductSave();
	}
	
	public void  testProductSave() throws IOException{
		product=new Product("name",1,1.0,"KG","null","F");
		product.saveToFile();
	}
	
	public void name() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog_add_product.fxml"));
		controller = loader.getController();
	}
	
	void testCheckItemCode() {
		AnchorPane pane;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog_add_product.fxml"));
		System.out.println(loader.toString());
//		System.out.println(controller.toString());
		try {
//			System.out.println("pane"+pane.toString());
			pane=loader.load();
			System.out.println("pane"+pane.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
//		Product product=new Product();
		controller = loader.getController();
		System.out.println(controller.toString());
//		controller.getCode_title().setText("11");
//		assertTrue(controller.checkItemCode(mockData, product));
	}
	
	public static void main(String[] args) throws IOException {
		new Testing();
	}
}
