package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class DashboardController implements Initializable {
	PoS_Main mainController;
	@FXML
	private BarChart<String, Number> bar1;
	@FXML
	private LineChart<String, Integer> line1;
	@FXML
	private PieChart pie1;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void setMainController(PoS_Main controller) {
		mainController = controller;
	}

	public void refreshChart() {
		pie1.getData().clear();
		bar1.getData().clear();
		line1.getData().clear();
		showBarChart();
		showPieChart();
		showLineChart();
	}

	public void showBarChart() {
		// Add data to bar chart
		final NumberAxis yAxis = new NumberAxis();
		final CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Date");
		yAxis.setLabel("Orders");
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Fruit");
		series1.getData().add(new XYChart.Data("01-11-2023", 50));
		series1.getData().add(new XYChart.Data("02-11-2023", 100));
		series1.getData().add(new XYChart.Data("03-11-2023", 100));
		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Vegetable");
		series2.getData().add(new XYChart.Data("01-11-2023", 60));
		series2.getData().add(new XYChart.Data("02-11-2023", 70));
		series2.getData().add(new XYChart.Data("03-11-2023", 70));
		bar1.getData().addAll(series1, series2);
	}

	public void showPieChart() {
		HashMap<String, Double> skuSaleMap = new HashMap<String, Double>();
		for (Order order : mainController.getOrderList())
//			skuSaleMap.put(order.getProduct().getName(), order.getTotal());
			skuSaleMap.merge(order.getProduct().getName(), order.getTotal(), (oldValue, newValue) -> oldValue+newValue);
		List<String> keys = new ArrayList<>(skuSaleMap.keySet());
		Collections.sort(keys);
		for (String s : keys) {
			PieChart.Data slice = new PieChart.Data(s, skuSaleMap.get(s));
			pie1.getData().add(slice);
		}
	}

	public void showLineChart() {
		HashMap<String, Double> skuSaleMap = new HashMap<String, Double>();
		for (Order order : mainController.getOrderList()) {
			skuSaleMap.merge(order.getDate(), order.getTotal(), (oldValue, newValue) -> oldValue+newValue);
		}
		List<String> keys = new ArrayList<>(skuSaleMap.keySet());
		Collections.sort(keys);
		final NumberAxis xAxis1 = new NumberAxis();
		final NumberAxis yAxis1 = new NumberAxis();
		xAxis1.setLabel("Date");
		yAxis1.setLabel("Revenue");
		XYChart.Series<String, Integer> series = new XYChart.Series();
		series.setName("Revenue");
		for (String s : keys) {
			series.getData().add(new XYChart.Data(s, skuSaleMap.get(s)));
		}
		line1.setTitle("Revenue Trend");
		line1.getData().add(series);
	}
}
