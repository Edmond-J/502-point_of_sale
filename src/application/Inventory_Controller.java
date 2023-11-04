package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Inventory_Controller implements Initializable {
	PoS_Main mainController;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	public Inventory_Controller() {
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
		AddInventroy_Controller subController = loader.getController();
		Button applyBtn = (Button)addstockDialog.lookup("#apply_add_product");
		applyBtn.setOnMouseClicked(e -> {
			subController.addStock(mainController.inventoryList);
		});
	}

	public void setMainController(PoS_Main controller) {
		mainController = controller;
	}
}
