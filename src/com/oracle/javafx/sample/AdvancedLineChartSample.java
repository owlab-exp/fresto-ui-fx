package com.oracle.javafx.sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class AdvancedLineChartSample extends Application {

	private void init(Stage primaryStage) {
		Group root = new Group();
		primaryStage.setScene(new Scene(root));
		root.getChildren().add(createChart());
	}

	protected LineChart<Number, Number> createChart() {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number,Number> lc = new LineChart<Number,Number>(xAxis,yAxis);
        // setup chart
        lc.setTitle("Basic LineChart");
        xAxis.setLabel("X Axis");
        yAxis.setLabel("Y Axis");
        // add starting data
        XYChart.Series<Number,Number> series = new XYChart.Series<Number,Number>();
        series.setName("Data Series 1");
        series.getData().add(new XYChart.Data<Number,Number>(20d, 50d));
        series.getData().add(new XYChart.Data<Number,Number>(40d, 80d));
        series.getData().add(new XYChart.Data<Number,Number>(50d, 90d));
        series.getData().add(new XYChart.Data<Number,Number>(70d, 30d));
        series.getData().add(new XYChart.Data<Number,Number>(170d, 122d));
        lc.getData().add(series);
        return lc;
    }
 
    @Override public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }
    public static void main(String[] args) { launch(args); }
}

