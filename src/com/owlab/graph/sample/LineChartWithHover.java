package com.owlab.graph.sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LineChartWithHover extends Application {
	private static final int MAX_DATA_POINTS = 12;

	@Override
	public void start(Stage stage) throws Exception {
		// defining the axes
		NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();

		xAxis = new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 5);
		xAxis.setForceZeroInRange(false);
		xAxis.setAutoRanging(false);
		xAxis.setLabel("Time");

		// NumberAxis yAxis = new NumberAxis();
		yAxis.setAutoRanging(true);

		stage.setTitle("Line Chart Sample");

		// defining a series
		/*
		 * XYChart.Series series = new XYChart.Series();
		 * series.setName("Hit counts"); // populating the series with data
		 * series.getData().add(plot(1,23,0));
		 * series.getData().add(plot(2,14,23));
		 * series.getData().add(plot(3,15,14));
		 * series.getData().add(plot(4,24,15));
		 * series.getData().add(plot(5,34,24));
		 * series.getData().add(plot(6,36,34));
		 * series.getData().add(plot(7,22,36));
		 * series.getData().add(plot(8,45,22));
		 * series.getData().add(plot(9,43,45));
		 * series.getData().add(plot(10,17,43));
		 * series.getData().add(plot(11,29,17));
		 * series.getData().add(plot(12,25,29));
		 */
		/*
		 * series.getData().add(new XYChart.Data(1, 23));
		 * series.getData().add(new XYChart.Data(2, 14));
		 * series.getData().add(new XYChart.Data(3, 15));
		 * series.getData().add(new XYChart.Data(4, 24));
		 * series.getData().add(new XYChart.Data(5, 34));
		 * series.getData().add(new XYChart.Data(6, 36));
		 * series.getData().add(new XYChart.Data(7, 22));
		 * series.getData().add(new XYChart.Data(8, 45));
		 * series.getData().add(new XYChart.Data(9, 43));
		 * series.getData().add(new XYChart.Data(10, 17));
		 * series.getData().add(new XYChart.Data(11, 29));
		 * series.getData().add(new XYChart.Data(12, 25));
		 */

		// creating the chart
		/*
		 * final LineChart<Number,Number> lineChart = new
		 * LineChart<Number,Number>(xAxis,yAxis);
		 */
		// FXCollections.observableArrayList(series.)
		@SuppressWarnings("unchecked")
		final LineChart chart = new LineChart(xAxis, yAxis) {
			// Override to remove symbols on each data point
			@Override
			protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
			}

		};

		/*
		 * final LineChart chart = new LineChart(new NumberAxis(), new
		 * NumberAxis(), FXCollections.observableArrayList(new XYChart.Series(
		 * "My portfolio", FXCollections .observableArrayList(plot(23, 14, 15,
		 * 24, 34, 36, 22, 45, 43, 17, 29, 25)))));
		 */

		chart.setAnimated(true);
		chart.setId("browserHitCounts");
		chart.setTitle("Hit counts (counts/sec)");
		chart.setCreateSymbols(true);

		/*
		 * lineChart.setTitle("Stock Monitoring, 2010");
		 * lineChart.setCreateSymbols(true);
		 */

		Scene scene = new Scene(chart, 800, 600);
		chart.getData().add(series);

		stage.setScene(scene);
		stage.show();

	}

	/**
	 * @return plotted y values for monotonically increasing integer x values,
	 *         starting from x=1
	 */
	public ObservableList<XYChart.Data<Integer, Integer>> plot(int... y) {
		final ObservableList<XYChart.Data<Integer, Integer>> dataset = FXCollections.observableArrayList();
		int i = 0;
		while (i < y.length) {
			final XYChart.Data<Integer, Integer> data = new XYChart.Data<>(i + 1, y[i]);
			data.setNode(new HoveredThresholdNode((i == 0) ? 0 : y[i - 1], y[i]));

			dataset.add(data);
			i++;
		}

		return dataset;
	}

	public ObservableList<XYChart.Data<Integer, Integer>> plot(int x, int y, int y0) {
		final ObservableList<XYChart.Data<Integer, Integer>> dataset = FXCollections.observableArrayList();
		final XYChart.Data<Integer, Integer> data = new XYChart.Data<>(x, y);
		data.setNode(new HoveredThresholdNode((y0 == 0) ? 0 : y0, y));
		dataset.add(data);
		return dataset;
	}

	/** a node which displays a value on hover, but is otherwise empty */
	class HoveredThresholdNode extends StackPane {
		HoveredThresholdNode(int priorValue, int value) {
			setPrefSize(15, 15);

			final Label label = createDataThresholdLabel(priorValue, value);

			setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					getChildren().setAll(label);
					setCursor(Cursor.NONE);
					toFront();
				}
			});
			setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					getChildren().clear();
					setCursor(Cursor.CROSSHAIR);
				}
			});
		}

		private Label createDataThresholdLabel(int priorValue, int value) {
			final Label label = new Label(value + "");
			label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
			label.setStyle("-fx-font-size: 10; -fx-font-weight: bold;");

			if (priorValue == 0) {
				label.setTextFill(Color.DARKGRAY);
			} else if (value > priorValue) {
				label.setTextFill(Color.FORESTGREEN);
			} else {
				label.setTextFill(Color.FIREBRICK);
			}

			label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
			return label;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
