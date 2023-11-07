/**
 * 
 */
package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.AddSKUController;
import application.Product;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

class AddSkuUnitTest {
	AddSKUController controller;
	AnchorPane pane = new AnchorPane();
	ArrayList<Product> mockData;

	@BeforeEach
	public void addbefore() throws IOException {
		mockData = new ArrayList<Product>();
		mockData.add(new Product("apple", 10, 1.99, "KG", "", "Fruit"));
		mockData.add(new Product("banana", 20, 2.99, "PCS", "", "Fruit"));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog_add_product.fxml"));
		pane = loader.load();
		controller = loader.getController();
		System.out.println("pane"+pane.toString());
	}

	@Test
	void testCheckItemCode() {
		Product product = new Product();
		System.out.println(controller.toString());
//		controller.getCode_title().setText("11");
//		assertTrue(controller.checkItemCode(mockData, product));
	}
}
