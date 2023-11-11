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
	private PoS_Main mainController;
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

	/**
	 * Clear the existing data in each chart and reload the order data.
	 */
	public void refreshChart() {
		pie1.getData().clear();
		bar1.getData().clear();
		line1.getData().clear();
		showBarChart();
		showPieChart();
		showLineChart();
	}

	/**
	 * The input value of this chart is still hard coded, it should calculate from the db_order.csv, but not yet.
	 */
	private void showBarChart() {
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

	/**
	 * Automatically calculate from the db_order.csv
	 */
	private void showPieChart() {
		HashMap<String, Double> skuSaleMap = new HashMap<String, Double>();
		for (Order order : mainController.getOrderList())
			skuSaleMap.merge(order.getProduct().getName(), order.getTotal(), (oldValue, newValue) -> oldValue+newValue);
		// use merge, to add up all the value related with the same key, to avoid the
		// later record over write the previous record.
		List<String> keys = new ArrayList<>(skuSaleMap.keySet());
		Collections.sort(keys);// sort the keys to let the chart display in order
		for (String s : keys) {
			PieChart.Data slice = new PieChart.Data(s, skuSaleMap.get(s));
			pie1.getData().add(slice);// create new slice and give the slice to the pie chart
		}
	}

	/**
	 * Automatically calculate from the db_order.csv
	 */
	private void showLineChart() {
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
