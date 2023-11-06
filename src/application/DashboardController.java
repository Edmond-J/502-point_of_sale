package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class DashboardController implements Initializable {
	PoS_Main mainController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

//	private Dashboard_Controller() {
//	}

	public void setMainController(PoS_Main controller) {
		mainController = controller;
	}
}
