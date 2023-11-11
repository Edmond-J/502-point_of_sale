package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		supplier.getItems().add("supplier1");
	}

	public void addStock(ArrayList<Inventory> inventoryList) {
	}

	public void closeDialog() {
		Stage stage = (Stage)name.getScene().getWindow();
		// get the object of the current windows from the elements in it.
		stage.close();
	}
}
