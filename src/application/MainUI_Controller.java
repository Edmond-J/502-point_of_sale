package application;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class MainUI_Controller {
	@FXML
	AnchorPane subpageContainer;
	@FXML
	Text text_iventory, text_product;
	@FXML
	TableView productsTableView;
	AddProduct_Controller addProduct;
	ArrayList<Product>productsList=new ArrayList<Product>();
	
	
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
    private void showLoadFileDialog(){
    	
    }
    
    @FXML
    private void showAddProductDialog(){
    	
    } 
    /**
     * to update the table with the provided data.
     * @param allProducts: the list need to be put into the table view
     * @param tableview: the tableview which all the products need to fillin
     */
    public void updateTableView(ArrayList<Product> allProducts, TableView<Product> tableview){
    	
    }
    
    /**
     * use the "SKU.txt" in the default directory as a database.
     * the save and load need to be in line with each other.
     * this method can be tested offline.
     */
    public void loadInventroyFromeFile(){
    	
    }
    
    public void saveInventoryToFile(){
    	
    }
    
    public void searchProduct(String userInput){
    	updateTableView(productsList,  productsTableView);
    }
}
