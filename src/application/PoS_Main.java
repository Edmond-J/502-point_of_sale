package application;

import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class PoS_Main extends Application implements Initializable {
	@FXML
	private AnchorPane subpage;
	@FXML
	private Text page_title, text_dashboard, text_product, text_inventory;
	private Parent sceneDash, sceneProd, sceneIven;
	private DashboardController dashCon;
	private InventoryController inveCon;
	private ProductController prodCon;
	private Text currentTab;
	private Database csvBase;
	private ArrayList<Product> productsList = new ArrayList<Product>();
	private ArrayList<Inventory> inventoryList = new ArrayList<Inventory>();
	private ArrayList<Supplier> supplierList = new ArrayList<Supplier>();
	private ArrayList<Order> orderList = new ArrayList<Order>();
//	public PoS_Main() {
//		System.out.println("main constructor");// why the constructor is executed twice?
//	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		csvBase = new Database("data/");
		FXMLLoader loader1 = new FXMLLoader(getClass().getResource("tab_dashboard.fxml"));
		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("tab_inventory.fxml"));
		FXMLLoader loader3 = new FXMLLoader(getClass().getResource("tab_product.fxml"));
		try {
			productsList = loadProductData("data/db_products.csv");
			orderList = loadOrderData("data/db_orders.csv");
			sceneDash = loader1.load();
			sceneIven = loader2.load();
			sceneProd = loader3.load();
			sceneIven.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			sceneProd.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			dashCon = loader1.getController();
			inveCon = loader2.getController();
			prodCon = loader3.getController();
			dashCon.setMainController(this);
			inveCon.setMainController(this);
			prodCon.setMainController(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentTab = text_dashboard;
		switchToDashboard();
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("main_frame.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("PoS System");
			primaryStage.getIcons().add(new Image("img/point-of-service.png"));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void switchToDashboard() {
		dashCon.refreshChart();
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

	public ArrayList<Product> loadProductData(String dataPath) {
		ArrayList<Product> PDList = new ArrayList<Product>();
		File db = new File(dataPath);
		if (!db.exists())
			return PDList;
		try (FileReader fileReader = new FileReader(db);
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
				PDList.add(product);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return PDList;
	}

	public ArrayList<Order> loadOrderData(String dataPath) {
		ArrayList<Order> orders = new ArrayList<Order>();
		File db = new File(dataPath);
		if (!db.exists())
			return orders;
		try (FileReader fileReader = new FileReader(db);
				CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
			for (CSVRecord csvRecord : csvParser) {
				// Get values from CSV columns
				String date = csvRecord.get("DATE");
				String time = csvRecord.get("TIME");
				String productName = csvRecord.get("PRODUCT");
				double quantity = Double.parseDouble(csvRecord.get("QUANTITY"));
				Product product = new Product();
				for (Product p : productsList) {
					if (productName.equals(p.getName()))
						product = p;
				}
				Order order = new Order(date, time, product, quantity);
				orders.add(order);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return orders;
	}

	public Database getCsvBase() {
		return csvBase;
	}

	public ArrayList<Inventory> getInventoryList() {
		return inventoryList;
	}

	public void setInventoryList(ArrayList<Inventory> inventoryList) {
		this.inventoryList = inventoryList;
	}

	public ArrayList<Supplier> getSupplierList() {
		return supplierList;
	}

	public void setSupplierList(ArrayList<Supplier> supplierList) {
		this.supplierList = supplierList;
	}

	public ArrayList<Product> getProductsList() {
		return productsList;
	}

	public void setProductsList(ArrayList<Product> productsList) {
		this.productsList = productsList;
	}

	public ArrayList<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(ArrayList<Order> orderList) {
		this.orderList = orderList;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
