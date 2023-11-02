package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddProduct_Controller implements Initializable {
	@FXML
	TextField name, code, price, brand;
	@FXML
	Text name_title, code_title, price_title, unit_title, brand_title, category_title;
	@FXML
	ComboBox<String> unit;
	@FXML
	ComboBox<String> category;
	@FXML
	TextArea description;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		unit.getItems().addAll("BAG", "BOX", "KG", "PCS", "TRAY");
		category.getItems().addAll("Fruits", "Vegetables", "Groceries");
	}

	public AddProduct_Controller() {
	}

	public void addProduct(ArrayList<Product> productsList) {
//		Scene scene = (Scene)name.getScene();
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		boolean valid = true;
		Product product = new Product();
		if (name.getText().isEmpty()) {
//			System.out.print(name_title.toString());
			name_title.setStyle("-fx-fill: red;");
			valid = false;
		} else {
			product.setName(name.getText());
			name_title.setStyle("-fx-fill: black;");
		}
		if (price.getText().isEmpty() || price.getText().matches(".*\\D.*")) {
			price_title.setStyle("-fx-fill: red;");
			valid = false;
		} else {
			product.setPrice(Double.parseDouble(price.getText()));
			price_title.setStyle("-fx-fill: black;");
		}
		if (unit.getValue() == null) {
			unit_title.setStyle("-fx-fill: red;");
			valid = false;
		} else {
			product.setUnit(unit.getValue());
			unit_title.setStyle("-fx-fill: black;");
		}
		if (category.getValue() == null) {
			category_title.setStyle("-fx-fill: red;");
			valid = false;
		} else {
			product.setCategory(category.getValue());
			category_title.setStyle("-fx-fill: black;");
		}
		if(valid){
			productsList.add(product);
			closeDialog();
		}
	}

	public void closeDialog() {
		Stage stage = (Stage)name.getScene().getWindow();
		// get the object of the current windows from the elements in it.
		stage.close();
	}

	/**
	 * check the validity of all input fields.Set the invalid fields with red text.
	 * @return True if every fields is valid. False if any of the fields is invalid.
	 */
	public boolean checkValidity() {
		if (price.getText().contains("a")) {
			return false;
		}
		return true;
	}
}
