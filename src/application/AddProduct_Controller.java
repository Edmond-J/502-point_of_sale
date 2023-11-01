package application;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProduct_Controller {
	MainUI_Controller mainController=new MainUI_Controller();
	@FXML
	TextField name, code, price, brand;
	@FXML
	ComboBox<Unit> unit;
	@FXML
	ComboBox<Supplier> supplier;
	@FXML
	ComboBox<String> category;
	@FXML
	DatePicker mtf_date, exp_date;

	public AddProduct_Controller() {
	}

	public void addProduct() {
		Product product=new Product();
		mainController.addProductToArrayList(product);
		closeDialog();
	}

	public void closeDialog() {
		Stage stage = (Stage) name.getScene().getWindow();
		//get the object of the current windows from the elements in it. 
        stage.close();
	}
}
