package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class MainUI_Controller {
	@FXML
	AnchorPane subpageContainer;
	@FXML
	Text text_iventory, text_product;
	
	Parent product;
	
	
	public MainUI_Controller() {
		// TODO Auto-generated constructor stub
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
        loadSubpage("Product.fxml");
    }
    @FXML
    private void loadInventoryPage() {
        loadSubpage("Inventory.fxml");
    }
    @FXML
    private void loadDashBoardPage() {
    	loadSubpage("DashBoard.fxml");
    }
}
