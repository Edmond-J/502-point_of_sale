package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * This function is not implemented, but very similar to AddSKUController
 */
public class AddStockController implements Initializable {
	@FXML
	private TextField name, code, price, brand, weight;
	@FXML
	private ComboBox<String> supplier;
	@FXML
	private TextArea description;
	@FXML
	private DatePicker exp_date;
	@FXML
	private Button cancel_add_stock, apply_add_stock;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		supplier.getItems().add("supplier1");
//		apply_add_stock.setDefaultButton(true);
//		cancel_add_stock.setOnKeyPressed(e -> {
//			if (e.getCode() == KeyCode.ESCAPE) {
//				closeDialog();
//			}
//		});
	}

	public void addStock(ArrayList<Inventory> inventoryList) {
		System.out.println("apply pressed");
	}

	@FXML
	private void closeDialog() {
		Stage stage = (Stage)name.getScene().getWindow();
		// get the object of the current windows from the elements in it.
		stage.close();
	}
}
