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

public class AddStock_Controller implements Initializable {
	@FXML
	TextField name, code, price, brand, weight;
	@FXML
	ComboBox<String> supplier;
	@FXML
	TextArea description;
	@FXML
	DatePicker exp_date;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		supplier.getItems().add("supplier1");
		System.out.println();
//		for (Supplier s : mainController.getSupplierList()) {
//			supplier.getItems().add(s.name);
//		}
	}

	public AddStock_Controller() {
	}

	public void addStock(ArrayList<Inventory> inventoryList) {
		Inventory inventory = new Inventory();
		if (checkInvalidation()) {
			// show message;
		} else {
			// add data
			closeDialog();
		}
	}

	public void closeDialog() {
		Stage stage = (Stage)name.getScene().getWindow();
		// get the object of the current windows from the elements in it.
		stage.close();
	}

	public boolean checkInvalidation() {
		return false;
	}
}
