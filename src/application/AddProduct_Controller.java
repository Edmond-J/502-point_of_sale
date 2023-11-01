package application;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProduct_Controller {
	MainUI_Controller mainController;
	@FXML
	TextField name, code, price, brand, supplier;
	@FXML
	ComboBox<Unit> unit;
	@FXML
	ComboBox<String> category;
	@FXML
	DatePicker mtf_date, exp_date;

	public AddProduct_Controller() {
	}

	public void addProduct() {
		Product product=new Product();
		mainController.addProductToArrayList(product);
	}

	public void closeDialog() {
		Stage stage = (Stage) unit.getScene().getWindow();
        stage.close();
	}
}
