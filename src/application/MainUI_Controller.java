package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainUI_Controller implements Initializable {
	@FXML
	AnchorPane subpageContainer;
	@FXML
	TextField search_box;
//	@FXML
//	Text text_iventory, text_product;
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
	ArrayList<Product> productsList = new ArrayList<Product>();
	ArrayList<Inventory> inventoryList = new ArrayList<Inventory>();
	ArrayList<Supplier> supplierList = new ArrayList<Supplier>();
	ObservableList<Product> productOBList;
//	AddProduct_Controller addProduct;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		supplierList.add(new Supplier(100, "Green NZ", "14 karori road wellington", 270852547, "greennz@gmail.com"));
//		updateTableView(productsList);
	}

	public MainUI_Controller() {
	}

	private void loadSubpage(String fxmlFileName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
			AnchorPane subpage = loader.load();
			subpageContainer.getChildren().setAll(subpage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void loadProductPage() {
		loadSubpage("tab_product.fxml");
	}

	@FXML
	private void loadInventoryPage() {
		loadSubpage("tab_inventory.fxml");
	}

	@FXML
	private void loadDashBoardPage() {
		loadSubpage("tab_dashboard.fxml");
	}

	@FXML
	private void showimportFileDialog() throws IOException {
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
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.csv"),
					new ExtensionFilter("All Files", "*.*"));
			Window window = subpageContainer.getScene().getWindow();
			File selectedFile = fileChooser.showOpenDialog(window);
//			System.out.println(selectedFile.getPath());
			fileAddress.setText(selectedFile.getPath());
		});
		Button applyBtn = (Button)importDialog.lookup("#apply_import");
		applyBtn.setOnMouseClicked(e -> {
			if (!fileAddress.getText().isEmpty()) {
//				loadSKUFromFile(fileAddress.getText());
				loadSKUFromFile("products.csv");
				updateTableView(productsList);
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
			subController.addProduct(productsList);
			updateTableView(productsList);
		});
//		Button cancelBtn = (Button)addProductDialog.lookup("#cancel_add_product");
//		cancelBtn.setOnMouseClicked(e -> {
//		    subStage.close();
//		});
	}

	@FXML
	private void showAddStockDialog() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog_add_stock.fxml"));
		Parent addstockDialog = loader.load();
		Stage subStage = new Stage();
		subStage.initModality(Modality.APPLICATION_MODAL);
		subStage.setTitle("Add a Stock");
		subStage.setScene(new Scene(addstockDialog));
		subStage.setResizable(false);
		subStage.show();
		AddStock_Controller subController = loader.getController();
		Button applyBtn = (Button)addstockDialog.lookup("#apply_add_product");
		applyBtn.setOnMouseClicked(e -> {
			subController.addStock(inventoryList);
		});
	}

	/**
	 * to update the table with the provided data.
	 * @param allProducts: the list need to be put into the table view
	 * @param tableview: the tableview which all the products need to fillin
	 */
	public void updateTableView(ArrayList<Product> productsList) {
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
	public void loadSKUFromFile(String fileName) {
		try {
			Scanner sc = new Scanner(new File("products.csv"));
			sc.useDelimiter(",");
			sc.nextLine();
			while (sc.hasNextLine()) {
				productsList
						.add(new Product(sc.next(), sc.nextInt(), sc.nextDouble(), sc.next(), sc.next(), sc.next()));
				sc.nextLine();
			}
			sc.close();
		} catch (IOException e) {
			System.out.println(("Error: "+e));
		}
	}

	public void loadProductsFromeDatabase(String fileName) {
	}

	public void saveProductsToDatabase(String fileName) {
	}

	public void searchProduct() {
		ArrayList<Product>searchResult=new ArrayList<Product>();
		for(Product p:productsList){
			if(p.getName().contains(search_box.getText())){
				searchResult.add(p);
			}
		}
		productOBList.clear();
		updateTableView(searchResult);
	}
}
