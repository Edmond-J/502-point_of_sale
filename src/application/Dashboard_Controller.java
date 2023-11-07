package application;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class Dashboard_Controller implements Initializable {
	PoS_Main mainController;
	@FXML
	private BarChart<String, Number> bar1;
	@FXML
	private LineChart<String,Integer> line1;
	@FXML
	private PieChart pie1;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	public Dashboard_Controller() {
	}
	
	public void showBarChart() {
		//Add data to bar chart
		final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Orders");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Fruit");
        series1.getData().add(new XYChart.Data("01-11-2023",50));
        series1.getData().add(new XYChart.Data("02-11-2023",100));
        series1.getData().add(new XYChart.Data("03-11-2023",100));
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Vegetable");
        series2.getData().add(new XYChart.Data("01-11-2023",60 ));
        series2.getData().add(new XYChart.Data("02-11-2023",70));
        series2.getData().add(new XYChart.Data("03-11-2023",70));
        bar1.getData().addAll(series1, series2);
        
        //Add data to pie chart
        PieChart.Data slice1 = new PieChart.Data("Apple" , 36);
        PieChart.Data slice2 = new PieChart.Data("Kiwi" , 40);
        PieChart.Data slice3 = new PieChart.Data("Banana" , 16);
        PieChart.Data slice4 = new PieChart.Data("EggPlant" , 6);
        pie1.getData().addAll(slice1,slice2,slice3,slice4);
        
        //Add data to line chart
        final NumberAxis xAxis1 = new NumberAxis();
        final NumberAxis yAxis1 = new NumberAxis();
        xAxis1.setLabel("Date");
        yAxis1.setLabel("Revenue");
        XYChart.Series<String,Integer> series = new XYChart.Series();
        series.setName("Revenue");
        series.getData().add(new XYChart.Data("01/11/2023", 23));
        series.getData().add(new XYChart.Data("02/11/2023", 14));
        series.getData().add(new XYChart.Data("03/11/2023", 15));
        series.getData().add(new XYChart.Data("04/11/2023", 24));
        series.getData().add(new XYChart.Data("05/11/2023", 34));
        line1.setTitle("Revenue Trend");
        line1.getData().add(series);

	}


	public void setMainController(PoS_Main controller) {
		mainController = controller;
		showBarChart();
	}
}
