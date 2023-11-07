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

public class ProductController implements Initializable {
	private PoS_Main mainController;
	private ObservableList<Product> productOBList;
	@FXML
	private TextField search_box;
	@FXML
	private Text import_result;
	@FXML
	private TableView<Product> productsTableView;
	@FXML
	private TableColumn<Product, String> col_name;
	@FXML
	private TableColumn<Product, Integer> col_itemcode;
	@FXML
	private TableColumn<Product, String> col_category;
	@FXML
	private TableColumn<Product, Double> col_price;
	@FXML
	private TableColumn<Product, String> col_unit;
	@FXML
	private TableColumn<Product, String> col_brand;
//
//	private ProductController() {
//	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		supplierList.add(new Supplier(100, "Green NZ", "14 karori road wellington", 270852547, "greennz@gmail.com"));
	}

	public void setMainController(PoS_Main controller) {
		mainController = controller;
		updateTableView(mainController.getProductsList());
	}

	@FXML
		private void showAddProductDialog() throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog_add_product.fxml"));
			Parent addProductDialog = loader.load();
			AddSKUController subController = loader.getController();
			Stage subStage = new Stage();
			subStage.initModality(Modality.APPLICATION_MODAL);
			subStage.setTitle("Add a SKU");
			subStage.setScene(new Scene(addProductDialog));
			subStage.setResizable(false);
			subStage.show();
			Button applyBtn = (Button)addProductDialog.lookup("#apply_add_product");
			applyBtn.setOnMouseClicked(e -> {
				try {
					subController.addProduct(mainController.getProductsList());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				updateTableView(mainController.getProductsList());
			});
	//		Button cancelBtn = (Button)addProductDialog.lookup("#cancel_add_product");
	//		cancelBtn.setOnMouseClicked(e -> {
	//		    subStage.close();
	//		});
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
				updateTableView(mainController.getProductsList());
				subStage.close();
			}
		});
		Button cancelBtn = (Button)importDialog.lookup("#cancel_import");
		cancelBtn.setOnMouseClicked(e -> {
			subStage.close();
		});
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
				for (Product p : mainController.getProductsList()) {
					if (p.getName().equals(name)) {
						sc.nextLine();
						duplicate++;
						isUnique = false;
						break;
					}
				}
				if (isUnique) {
					Product product = new Product(name, sc.nextInt(), sc.nextDouble(), sc.next(), sc.next(), sc.next());
					mainController.getProductsList().add(product);
					product.saveToFile();
					sc.nextLine();
					imported++;
				}
			}
			setPopupMessage(imported+" SKU imported. "+duplicate+" SKU duplicated.");
			sc.close();
		} catch (IOException e) {
			System.out.println(("Error: "+e));
		}
	}

	public void setPopupMessage(String message) {
		import_result.setText(message);
//		System.out.println(imported+" SKU imported. "+duplicate+" SKU duplicated.");
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), import_result);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.play();
	}

	@FXML
	private void syncDatabase() {
		File file = new File("data/db_ products.csv");
		ArrayList<Product> productsInDB = mainController.loadProductData(file.getPath());
		int difference = productsInDB.size()-mainController.getProductsList().size();
		if (file.exists()) {
			for (Product p : productsInDB) {
				if (!mainController.getProductsList().contains(p))
					mainController.getProductsList().add(p);
			}
			productOBList.clear();
			updateTableView(mainController.getProductsList());
			file.delete();
			if (difference > 0)
				setPopupMessage(difference+" items loaded from database");
			else setPopupMessage((-difference)+" items saved to database");
		}else setPopupMessage((-difference)+" items saved to database");
		
		for (Product p : mainController.getProductsList())
			p.saveToFile();
	}

	@FXML
	private void deleteProduct() {
		TableViewSelectionModel<Product> selected = productsTableView.getSelectionModel();
		ObservableList<Product> selectedItems = selected.getSelectedItems();
		if (!selectedItems.isEmpty()) {
			Product toDelet = selectedItems.get(0);
			mainController.getProductsList().remove(toDelet);
			mainController.getCsvBase().delete(toDelet);
			productOBList.remove(toDelet);
		}
	}

	@FXML
	private void searchProduct() {
		if (!search_box.getText().isEmpty()) {
			ArrayList<Product> searchResult = new ArrayList<Product>();
			for (Product p : mainController.getProductsList()) {
				if (p.getName().contains(search_box.getText())) {
					searchResult.add(p);
				}
			}
			productOBList.clear();
			updateTableView(searchResult);
		} else updateTableView(mainController.getProductsList());
	}
}
