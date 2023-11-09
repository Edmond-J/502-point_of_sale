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

public class AddSKUController implements Initializable {
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

	public void addProduct(ArrayList<Product> productsList) {
//		System.out.println(name.getText());
//		System.out.println(code.getText());
//		System.out.println(price.getText());
//		System.out.println(brand.getText());
		error.clear();
		Product product = new Product();
//		System.out.println(name.getText());
		boolean valid1 = checkItemCode(productsList, product);
		boolean valid2 = checkName(productsList, product);
		boolean valid3 = checkPrice(productsList, product);
		boolean valid4 = checkUnit(productsList, product);
		boolean valid5 = checkCategory(productsList, product);
		if (valid1 && valid2 && valid3 && valid4 && valid5) {
			product.setBrand(brand.getText());
			productsList.add(product);
			product.saveToFile();
			closeDialog();
		}
	}

	public boolean checkItemCode(ArrayList<Product> productsList, Product product) {
		boolean valid = true;
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
		return valid;
	}

	public boolean checkName(ArrayList<Product> productsList, Product product) {
		boolean valid = true;
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
		return valid;
	}

	public boolean checkPrice(ArrayList<Product> productsList, Product product) {
		boolean valid = true;
		if (price.getText().isEmpty()) {
			price_title.setStyle("-fx-fill: red;");
			valid = false;
			error.appendText("error: 'price' is mandatory fields\n");
		} else if (!price.getText().matches("[0-9](.?)[0-9]*")) {
			price_title.setStyle("-fx-fill: red;");
			valid = false;
			error.appendText("error: only number and '.' are allowed in 'price'\n");
		} else {
			product.setPrice(Double.parseDouble(price.getText()));
			price_title.setStyle("-fx-fill: white;");
		}
		return valid;
	}

	public boolean checkUnit(ArrayList<Product> productsList, Product product) {
		boolean valid = true;
		if (unit.getValue() == null) {
			unit_title.setStyle("-fx-fill: red;");
			valid = false;
			error.appendText("error: 'unit' is mandatory fields\n");
		} else {
			product.setUnit(unit.getValue());
			unit_title.setStyle("-fx-fill: white;");
		}
		return valid;
	}

	public boolean checkCategory(ArrayList<Product> productsList, Product product) {
		boolean valid = true;
		if (category.getValue() == null) {
			category_title.setStyle("-fx-fill: red;");
			valid = false;
			error.appendText("error: 'category' is mandatory fields\n");
		} else {
			product.setCategory(category.getValue());
			category_title.setStyle("-fx-fill: white;");
		}
		return valid;
	}

	public void closeDialog() {
		Stage stage = (Stage)name.getScene().getWindow();
		// get the object of the current windows from the elements in it.
		stage.close();
	}

	public Text getCode_title() {
		return code_title;
	}

	public void setCode_title(Text code_title) {
		this.code_title = code_title;
	}
}
