package application;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class PoS_Main extends Application implements Initializable {
	@FXML
	AnchorPane subpage;
	@FXML
	HBox dashboard, inventory, product;
	@FXML
	Text page_title, text_dashboard, text_product, text_inventory;
	Parent sceneDash, sceneProd, sceneIven;
	Text currentTab;
	Dashboard_Controller dashCon;
	Product_Controller prodCon;
	Inventory_Controller inveCon;
	Database csvBase;
	ArrayList<Product> productsList = new ArrayList<Product>();
	ArrayList<Inventory> inventoryList = new ArrayList<Inventory>();
	ArrayList<Supplier> supplierList = new ArrayList<Supplier>();

	public PoS_Main() {
		System.out.println("main constructor");//why the constructor is executed twice?
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			csvBase=new Database("data/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FXMLLoader loader1 = new FXMLLoader(getClass().getResource("tab_dashboard.fxml"));
		System.out.println("Main init: FXMLLoader loader1 "+loader1.toString());
		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("tab_product.fxml"));
		FXMLLoader loader3 = new FXMLLoader(getClass().getResource("tab_inventory.fxml"));
		try {
			loadData();
			sceneDash = loader1.load();
			System.out.println("Main init:sceneDash "+sceneDash.toString());
			sceneProd = loader2.load();
			sceneProd.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			sceneIven = loader3.load();
			dashCon = loader1.getController();
			System.out.println("Main init: dashCon "+dashCon.toString());
			prodCon = loader2.getController();
			inveCon = loader3.getController();
			prodCon.setMainController(this);
			System.out.println("Main init: set main controller");
			inveCon.setMainController(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentTab = text_dashboard;
		currentTab.setStyle("-fx-fill:#dad873;");
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("main_frame.fxml"));
			System.out.println("Main start:root "+root.toString());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("PoS System");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void switchToDashboard() {
		subpage.getChildren().clear();
		subpage.getChildren().add(sceneDash);
		page_title.setText("Dashboard");
//		Text text = (Text)root.lookup("#text_dashboard");
		textHighlight(text_dashboard);
	}

	public void switchToInventory() {
		subpage.getChildren().clear();
		subpage.getChildren().add(sceneIven);
		page_title.setText("Inventory");
		textHighlight(text_inventory);
	}

	public void switchToProduct() {
		subpage.getChildren().clear();
		subpage.getChildren().add(sceneProd);
		page_title.setText("Product");
		textHighlight(text_product);
	}

	private void textHighlight(Text text) {
		currentTab.setStyle("-fx-fill:white;");
		currentTab.getParent().setStyle("");
		currentTab = text;
		currentTab.setStyle("-fx-fill:#dad873;");
		currentTab.getParent().setStyle("-fx-background-color:#454d66");
	}

	private void loadData() {
		try (FileReader fileReader = new FileReader("data/db_ products.csv");
				CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
			for (CSVRecord csvRecord : csvParser) {
				// Get values from CSV columns
				String name = csvRecord.get("NAME");
				int itemCode = Integer.parseInt(csvRecord.get("ITEM CODE"));
				double price = Double.parseDouble(csvRecord.get("PRICE"));
				String unit = csvRecord.get("UNIT");
				String brand = csvRecord.get("BRAND");
				String category = csvRecord.get("CATEGORY");
				Product product = new Product(name, itemCode, price, unit, brand, category);
				productsList.add(product);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
