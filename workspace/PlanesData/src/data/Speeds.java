package data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import model.Event;
import model.Plane;

public final class Speeds {
	
	//observable lists of bar chart series for event air forces:
	private final Map<EventAirForceKey, ObservableList<XYChart.Series<String,Number>>>
	eventAirForceToPlaneSeries = new HashMap<EventAirForceKey, ObservableList<XYChart.Series<String,Number>>>();
	
	

}
