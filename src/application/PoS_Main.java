package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
	Text page_title, text_dashboard, text_product, text_inventory;
	Parent sceneDash, sceneProd, sceneIven;
	Text currentTab;
	Dashboard_Controller dashCon;
	Product_Controller prodCon;
	Inventory_Controller inveCon;
	ArrayList<Product> productsList = new ArrayList<Product>();
	ArrayList<Inventory> inventoryList = new ArrayList<Inventory>();
	ArrayList<Supplier> supplierList = new ArrayList<Supplier>();
    HBox hbox; 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FXMLLoader loader1 = new FXMLLoader(getClass().getResource("tab_dashboard.fxml"));
		FXMLLoader loader2 = new FXMLLoader(getClass().getResource("tab_product.fxml"));
		FXMLLoader loader3 = new FXMLLoader(getClass().getResource("tab_inventory.fxml"));
		try {
			sceneDash = loader1.load();
			sceneProd = loader2.load();
			sceneProd.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			sceneIven = loader3.load();
			dashCon = loader1.getController();
			prodCon = loader2.getController();
			inveCon = loader3.getController();
			prodCon.setMainController(this);
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
		currentTab = text;
		currentTab.setStyle("-fx-fill:#dad873;");
	}
   
	public static void main(String[] args) {
		launch(args);
	}
}
