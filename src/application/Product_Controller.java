package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class Product_Controller implements Initializable {
	PoS_Main mainController;
	ObservableList<Product> productOBList;
	@FXML
	TextField search_box;
	@FXML
	Text import_result;
	@FXML
	TableView<Product> productsTableView;
	@FXML
	TableColumn<Product, String> col_name;
	@FXML
	TableColumn<Product, Integer> col_itemcode;
	@FXML
	TableColumn<Product, String> col_category;
	@FXML
	TableColumn<Product, Double> col_price;
	@FXML
	TableColumn<Product, String> col_unit;
	@FXML
	TableColumn<Product, String> col_brand;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Product_controller initialize "+col_name);
//		supplierList.add(new Supplier(100, "Green NZ", "14 karori road wellington", 270852547, "greennz@gmail.com"));
//		
	}

	public Product_Controller() {
		System.out.println("Product_controller constructor "+col_name);
	}

	public void setMainController(PoS_Main controller) {
		mainController = controller;
		updateTableView(mainController.productsList);
	}

	@FXML
	private void showImportFileDialog() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog_import.fxml"));
		Parent importDialog = loader.load();
		Stage subStage = new Stage();
		subStage.initModality(Modality.APPLICATION_MODAL);
		subStage.setTitle("Import SKUs");
		subStage.setScene(new Scene(importDialog));
		subStage.setResizable(false);
		subStage.show();
		TextField fileAddress = (TextField)importDialog.lookup("#file_address");
		System.out.println(fileAddress.toString());
		Button browseBtn = (Button)importDialog.lookup("#browse_import");
		browseBtn.setOnMouseClicked(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open File");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"),
					new ExtensionFilter("All Files", "*.*"));
			Window window = browseBtn.getParent().getScene().getWindow();
//			Window window=mainController.sceneProd.getScene().getWindow(); //another way to get the window
			File selectedFile = fileChooser.showOpenDialog(window);
			if (selectedFile != null)
				fileAddress.setText(selectedFile.getPath());
		});
		Button applyBtn = (Button)importDialog.lookup("#apply_import");
		applyBtn.setOnMouseClicked(e -> {
			if (!fileAddress.getText().isEmpty()) {
				loadSKUFromFile(fileAddress.getText());
				updateTableView(mainController.productsList);
				subStage.close();
			}
		});
		Button cancelBtn = (Button)importDialog.lookup("#cancel_import");
		cancelBtn.setOnMouseClicked(e -> {
			subStage.close();
		});
	}

	@FXML
	private void showAddProductDialog() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog_add_product.fxml"));
		Parent addProductDialog = loader.load();
		Stage subStage = new Stage();
		subStage.initModality(Modality.APPLICATION_MODAL);
		subStage.setTitle("Add a SKU");
		subStage.setScene(new Scene(addProductDialog));
		subStage.setResizable(false);
		subStage.show();
		AddProduct_Controller subController = loader.getController();
		Button applyBtn = (Button)addProductDialog.lookup("#apply_add_product");
		applyBtn.setOnMouseClicked(e -> {
			try {
				subController.addProduct(mainController.productsList);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			updateTableView(mainController.productsList);
		});
//		Button cancelBtn = (Button)addProductDialog.lookup("#cancel_add_product");
//		cancelBtn.setOnMouseClicked(e -> {
//		    subStage.close();
//		});
	}

	/**
	 * to update the table with the provided data.
	 * @param allProducts: the list need to be put into the table view
	 * @param tableview: the tableview which all the products need to fillin
	 */
	private void updateTableView(ArrayList<Product> productsList) {
		productOBList = FXCollections.observableArrayList(productsList);
		productsTableView.setItems(productOBList);
		col_name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		col_itemcode.setCellValueFactory(
				cellData -> new SimpleIntegerProperty(cellData.getValue().getItemCode()).asObject());
		col_price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
		col_unit.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnit().toString()));
		col_brand.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBrand()));
		col_category.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
	}

	/**
	 * use the "products.csv" in the default directory as a database.
	 * the save and load need to be in line with each other.
	 * this method can be tested offline.
	 */
	private void loadSKUFromFile(String fileName) {
		try {
			int imported = 0;
			int duplicate = 0;
			Scanner sc = new Scanner(new File(fileName));
			sc.useDelimiter(",");
			sc.nextLine();
			while (sc.hasNextLine()) {
				String name = sc.next();
				boolean isUnique = true;
				for (Product p : mainController.productsList) {
					if (p.getName().equals(name)) {
						sc.nextLine();
						duplicate++;
						isUnique = false;
						break;
					}
				}
				if (isUnique) {
					Product product = new Product(name, sc.nextInt(), sc.nextDouble(), sc.next(), sc.next(), sc.next());
					mainController.productsList.add(product);
					product.saveToFile();
					sc.nextLine();
					imported++;
				}
			}
			import_result.setText(imported+" SKU imported. "+duplicate+" SKU duplicated.");
//			System.out.println(imported+" SKU imported. "+duplicate+" SKU duplicated.");
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), import_result);
			fadeOut.setFromValue(1.0);
			fadeOut.setToValue(0.0);
			fadeOut.play();
			sc.close();
		} catch (IOException e) {
			System.out.println(("Error: "+e));
		}
	}

	@FXML
	private void deleteProduct() {
		TableViewSelectionModel<Product> selected = productsTableView.getSelectionModel();
		ObservableList<Product> selectedItems = selected.getSelectedItems();
		if (!selectedItems.isEmpty()) {
			Product toDelet = selectedItems.get(0);
			mainController.productsList.remove(toDelet);
			mainController.csvBase.delete(toDelet);
			productOBList.remove(toDelet);
		}
	}

	@FXML
	private void searchProduct() {
		if (!search_box.getText().isEmpty()) {
			ArrayList<Product> searchResult = new ArrayList<Product>();
			for (Product p : mainController.productsList) {
				if (p.getName().contains(search_box.getText())) {
					searchResult.add(p);
				}
			}
			productOBList.clear();
			updateTableView(searchResult);
		}
	}
}
