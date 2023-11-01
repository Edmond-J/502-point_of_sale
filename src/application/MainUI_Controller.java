package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainUI_Controller {
	@FXML
	AnchorPane subpageContainer;
//	@FXML
//	Text text_iventory, text_product;
	@FXML
	TableView<Product> productsTableView;
	ArrayList<Product> productsList = new ArrayList<Product>();
	//supplier arraylist to be implemented
	
//	AddProduct_Controller addProduct;

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
		Pane importDialog = loader.load();
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
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
					new ExtensionFilter("All Files", "*.*"));
			Window window = subpageContainer.getScene().getWindow();
			File selectedFile = fileChooser.showOpenDialog(window);
			System.out.println(selectedFile.getPath());
			fileAddress.setText(selectedFile.getPath());
		});
		Button applyBtn = (Button)importDialog.lookup("#apply_import");
		applyBtn.setOnMouseClicked(e -> {
			if (fileAddress.getText() != null)
				loadSKUFromFile(fileAddress.getText());
			subStage.close();
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
		
		Button cancelBtn = (Button)addProductDialog.lookup("#cancel_add_product");
		cancelBtn.setOnMouseClicked(e -> {
			subStage.close();
		});
		Button applyBtn = (Button)addProductDialog.lookup("#apply_add_product");
		applyBtn.setOnMouseClicked(e -> {
			subStage.close();
		});
	}

	/**
	 * to update the table with the provided data.
	 * @param allProducts: the list need to be put into the table view
	 * @param tableview: the tableview which all the products need to fillin
	 */
	public void updateTableView(ArrayList<Product> allProducts, TableView<Product> tableview) {
	}

	/**
	 * use the "SKU.txt" in the default directory as a database.
	 * the save and load need to be in line with each other.
	 * this method can be tested offline.
	 */
	public void loadSKUFromFile(String fileName) {
	}

	public void loadInventroyFromeFile(String fileName) {
	}

	public void saveInventoryToFile(String fileName) {
	}

	public void searchProduct(String userInput) {
		updateTableView(productsList, productsTableView);
	}
	
	public void addProductToArrayList(Product product){
		productsList.add(product);
	}
}
