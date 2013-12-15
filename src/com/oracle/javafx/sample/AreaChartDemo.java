package com.oracle.javafx.sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class AreaChartDemo extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Group root = new Group();

		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();

		xAxis.setLabel("Month");
		yAxis.setLabel("Value");

		final AreaChart<String, Number> areaChart = new AreaChart<String, Number>(
				xAxis, yAxis);

		areaChart.setTitle("AreaChart");
		XYChart.Series series = new XYChart.Series();
		series.setName("XYChart.Series");

		series.getData().add(new XYChart.Data("January", 100));
		series.getData().add(new XYChart.Data("February", 200));
		series.getData().add(new XYChart.Data("March", 50));
		series.getData().add(new XYChart.Data("April", 150));
		
		series.getData().remove(0);

		areaChart.getData().add(series);

		root.getChildren().add(areaChart);

		primaryStage.setScene(new Scene(root, 500, 400));
		primaryStage.show();
	}
}
