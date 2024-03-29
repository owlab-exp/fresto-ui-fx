package com.owlab.graph.chart;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import com.oracle.javafx.sample.AreaChartSample;
import com.owlab.graph.query.JsonHttpPostQuery;

public class TimeLineCountsChart extends Application {
	private static final int MAX_DATA_POINTS = 50;

	private Series series;
	private int xSeriesData = 0;
	private ConcurrentLinkedQueue<Number> dataQ = new ConcurrentLinkedQueue<Number>();
	private ExecutorService executor;
	private AddToQueue addToQueue;
	// private Timeline timeline2;
	private NumberAxis xAxis;
	public static JsonHttpPostQuery query;

	@SuppressWarnings("unchecked")
	private void init(Stage primaryStage) {

		xAxis = new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 5);
		xAxis.setForceZeroInRange(false);
		xAxis.setAutoRanging(false);
		xAxis.setLabel("Time");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setAutoRanging(true);

		// -- Chart
		/*
		 * final AreaChart<Number, Number> sc = new AreaChart<Number, Number>(
		 * xAxis, yAxis) { // Override to remove symbols on each data point
		 * 
		 * @Override protected void dataItemAdded(Series<Number, Number> series,
		 * int itemIndex, Data<Number, Number> item) { } };
		 */
		final LineChart<Number, Number> chart = new LineChart<Number, Number>(xAxis, yAxis) {
			// Override to remove symbols on each data point
			@Override
			protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
			}

		};

		chart.setAnimated(true);
		chart.setId("browserHitCounts");
		chart.setTitle("Hit counts (counts/sec)");
		chart.setCreateSymbols(true);

		// sc.setCursor(Cursor.CROSSHAIR);

		// -- Chart Series
		// series = new LineChart.Series<Number, Number>();
		series = new XYChart.Series<Number, Number>();
		series.setName("Hit counts");
		chart.getData().add(series);

		primaryStage.setScene(new Scene(chart));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		init(primaryStage);
		primaryStage.show();

		// -- Prepare Executor Services
		executor = Executors.newCachedThreadPool();
		addToQueue = new AddToQueue();
		executor.execute(addToQueue);
		// -- Prepare Timeline
		prepareTimeline();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private class AddToQueue implements Runnable {
		public void run() {
			try {
				// add a item of random data to queue
				dataQ.add(Math.random());
				
				// Query owlab response time
				/*long elapseTime = 0L;
				try {
					elapseTime = JsonHttpPostQuery.getInstance().queryResponseTime();
				} catch (Exception e) {
					e.printStackTrace();
				}
				dataQ.add(elapseTime);*/
				Thread.sleep(1000);
				executor.execute(this);
			} catch (InterruptedException ex) {
				Logger.getLogger(AreaChartSample.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	// -- Timeline gets called in the JavaFX Main thread
	private void prepareTimeline() {
		// Every frame to take any data from queue and add to chart
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				addDataToSeries();
			}
		}.start();
	}

	@SuppressWarnings("unchecked")
	private void addDataToSeries() {
		for (int i = 0; i < 20; i++) { // -- add 20 numbers to the plot+
			if (dataQ.isEmpty())
				break;
			//series.getData().add(new LineChart.Data(xSeriesData++, dataQ.remove()));
			series.getData().add(new LineChart.Data(xSeriesData++, dataQ.remove()));
		}
		// remove points to keep us at no more than MAX_DATA_POINTS
		if (series.getData().size() > MAX_DATA_POINTS) {
			series.getData().remove(0, series.getData().size() - MAX_DATA_POINTS);
		}
		// update
		xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
		xAxis.setUpperBound(xSeriesData - 1);
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
}
