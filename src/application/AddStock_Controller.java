package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddStock_Controller {
	MainUI_Controller mainController = new MainUI_Controller();
	@FXML
	TextField name, code, price, brand, weight;
	@FXML
	ComboBox<String> supplier;
	@FXML
	TextArea description;
	@FXML
	DatePicker exp_date;

	public AddStock_Controller() {
	}
	
	public void addStock() {
		Inventory inventory = new Inventory();
		mainController.addIventoryToArrayList(inventory);
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
