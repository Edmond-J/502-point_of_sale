package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddProduct_Controller {
	MainUI_Controller mainController = new MainUI_Controller();
	@FXML
	TextField name, code, price, brand;
	@FXML
	ComboBox<Unit> unit;
	@FXML
	ComboBox<String> category;
	@FXML
	TextArea description;


	public AddProduct_Controller() {
	}


	public void addProduct() {
		Product product = new Product();
		mainController.addProductToArrayList(product);
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
