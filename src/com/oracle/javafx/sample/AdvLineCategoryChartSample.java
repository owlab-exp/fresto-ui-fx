package com.oracle.javafx.sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class AdvLineCategoryChartSample extends Application {
	private static final String[] CATEGORIES = { "Alpha", "Beta", "RC1", "RC2", "1.0", "1.1" };

	private void init(Stage primaryStage) {
		Group root = new Group();

		primaryStage.setScene(new Scene(root));

		root.getChildren().add(createChart());

	}

	protected LineChart<String, Number> createChart() {

		final CategoryAxis xAxis = new CategoryAxis();

		final NumberAxis yAxis = new NumberAxis();

		final LineChart<String, Number> lc = new LineChart<String, Number>(xAxis, yAxis);

		// setup chart

		lc.setTitle("LineChart with Category Axis");

		xAxis.setLabel("X Axis");

		yAxis.setLabel("Y Axis");

		// add starting data

		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();

		series.setName("Data Series 1");

		series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[0], 50d));

		series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[1], 80d));

		series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[2], 90d));

		series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[3], 30d));

		series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[4], 122d));

		series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[5], 10d));

		lc.getData().add(series);

		return lc;

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		init(primaryStage);

		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
