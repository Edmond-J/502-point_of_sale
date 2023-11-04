package application;

import java.io.IOException;
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
	ComboBox<String> unit, category;
	@FXML
	TextArea description, error;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		unit.getItems().addAll("BAG", "BOX", "KG", "PCS", "TRAY");
		category.getItems().addAll("Fruits", "Vegetables", "Groceries");
	}

	public AddProduct_Controller() {
	}

	public void addProduct(ArrayList<Product> productsList) throws IOException {
		error.clear();
//		Scene scene = (Scene)name.getScene();
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		boolean valid = true;
		Product product = new Product();
		if (!code.getText().isEmpty()) {
			if (!code.getText().matches("\\d*")) {
				code_title.setStyle("-fx-fill: red;");
				error.appendText("error: 'code' can only contain numbers\n");
				valid = false;
			} else {
				Boolean redundant = false;
				for (Product p : productsList) {
					if (Integer.parseInt(code.getText()) == p.getItemCode()) {
						code_title.setStyle("-fx-fill: red;");
						error.appendText("error: duplicated product code\n");
						redundant = true;
						valid = false;
						break;
					}
				}
				if (!redundant) {
					product.setItemCode(Integer.parseInt(code.getText()));
					code_title.setStyle("-fx-fill: white;");
				}
			}
		} else code_title.setStyle("-fx-fill: white;");
		if (name.getText().isEmpty()) {
//			System.out.print(name_title.toString());
			name_title.setStyle("-fx-fill: red;");
			valid = false;
			error.appendText("error: 'name' is mandatory fields\n");
		} else {
			Boolean redundant = false;
			for (Product p : productsList) {
				if (name.getText().equals(p.getName())) {
					name_title.setStyle("-fx-fill: red;");
					error.appendText("error: duplicated product name\n");
					redundant = true;
					valid = false;
					break;
				}
			}
			if (!redundant) {
				product.setName(name.getText());
				name_title.setStyle("-fx-fill: white;");
			}
		}
		if (price.getText().isEmpty()) {
			price_title.setStyle("-fx-fill: red;");
			valid = false;
			error.appendText("error: 'price' is mandatory fields\n");
		} else if (price.getText().matches("[^0-9.]*")) {
			price_title.setStyle("-fx-fill: red;");
			valid = false;
			error.appendText("error: only number and '.' are allowed in 'price'\n");
		} else {
			product.setPrice(Double.parseDouble(price.getText()));
			price_title.setStyle("-fx-fill: white;");
		}
		if (unit.getValue() == null) {
			unit_title.setStyle("-fx-fill: red;");
			valid = false;
			error.appendText("error: 'unit' is mandatory fields\n");
		} else {
			product.setUnit(unit.getValue());
			unit_title.setStyle("-fx-fill: white;");
		}
		if (category.getValue() == null) {
			category_title.setStyle("-fx-fill: red;");
			valid = false;
			error.appendText("error: 'category' is mandatory fields\n");
		} else {
			product.setCategory(category.getValue());
			category_title.setStyle("-fx-fill: white;");
		}
		if (valid) {
			productsList.add(product);
			product.saveToFile();
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
//	public boolean checkValidity() {
//		if (price.getText().contains("a")) {
//			return false;
//		}
//		return true;
//	}
}
