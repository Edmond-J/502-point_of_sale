package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class controls the pop up window of adding a SKU
 */
public class AddSKUController implements Initializable {
	@FXML
	private TextField name, code, price, brand;
	@FXML
	private Text name_title, code_title, price_title, unit_title, brand_title, category_title;
	@FXML
	private ComboBox<String> unit, category;
	@FXML
	private TextArea description, error;
	@FXML
	private Button cancel_add_product, apply_add_product;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		unit.getItems().addAll("BAG", "BOX", "KG", "PCS", "TRAY");
		category.getItems().addAll("Fruits", "Vegetables", "Groceries");
//		apply_add_product.setDefaultButton(true);//可以在scenebuilder里勾选
//		cancel_add_product.setOnKeyPressed(e -> {
//			if (e.getCode() == KeyCode.ESCAPE) {
//				closeDialog();
//			}
//		});
	}

	public void addProduct(ArrayList<Product> productsList) {
		error.clear();
		Product product = new Product();
//		System.out.println(name.getText());
		boolean valid1 = checkItemCode(productsList, product);
		boolean valid2 = checkName(productsList, product);
		boolean valid3 = checkPrice(product);
		boolean valid4 = checkUnit(product);
		boolean valid5 = checkCategory(product);
		if (valid1 && valid2 && valid3 && valid4 && valid5) {
			product.setBrand(brand.getText());
			productsList.add(product);
			product.saveToFile();
			closeDialog();
		}
	}

	/**
	 * 
	 * @param productsList This method need to check the redundancy of the item code within the existing product list
	 * @param product If the validity check pass, the input value(item code) will be given to this product.
	 * @return True if the input item code is valid(blank, or number only).
	 */
	private boolean checkItemCode(ArrayList<Product> productsList, Product product) {
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
					// ↑ need to set the label back to white if it's changed to valid value.
				}
			}
		} else code_title.setStyle("-fx-fill: white;");// blank is valid
		return valid;
	}

	private boolean checkName(ArrayList<Product> productsList, Product product) {
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

	/**
	 * 
	 * @param product
	 * @return True if the input price is valid("1.0","2","0.99"). False in the other case("d",".1","2.2.","3..2","-2","15d")
	 */
	private boolean checkPrice(Product product) {
		boolean valid = true;
		if (price.getText().isEmpty()) {
			price_title.setStyle("-fx-fill: red;");
			valid = false;
			error.appendText("error: 'price' is mandatory fields\n");
		} else if (!price.getText().matches("\\d+(.?)\\d+") && !price.getText().matches("\\d")) {// regular expression
			price_title.setStyle("-fx-fill: red;");
			valid = false;
			error.appendText("error: only number and '.' are allowed in 'price'\n");
		} else {
			product.setPrice(Double.parseDouble(price.getText()));
			price_title.setStyle("-fx-fill: white;");
		}
		return valid;
	}

	private boolean checkUnit(Product product) {
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

	private boolean checkCategory(Product product) {
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

	@FXML
	private void closeDialog() {
		Stage stage = (Stage)name.getScene().getWindow();
		// get the object of the current windows from the elements in it.
		stage.close();
	}
//	public Text getCode_title() {
//		return code_title;
//	}
//	public void setCode_title(Text code_title) {
//		this.code_title = code_title;
//	}
}
