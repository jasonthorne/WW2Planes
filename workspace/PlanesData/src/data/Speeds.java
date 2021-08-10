package data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import model.Plane;

public final class Speeds {
	
	//observable lists of bar chart series for event air forces:
	private final Map<EventAirForceKey, ObservableList<XYChart.Series<String,Number>>>
	eventAirForceToPlaneSeries = new HashMap<EventAirForceKey, ObservableList<XYChart.Series<String,Number>>>();
	
	//get observable lists of bar chart series:
	public ObservableList<XYChart.Series<String,Number>>getSeries(EventAirForceKey eventAirForceKey, List<Plane>planes){
		
		ObservableList<XYChart.Series<String,Number>>seriesCheck = null;
		
		//return series list if already present in map:
		if((seriesCheck = eventAirForceToPlaneSeries.get(eventAirForceKey))!= null) {
			return FXCollections.observableArrayList(seriesCheck);}//???????????????????????????
		
		//add built series list to map:
		eventAirForceToPlaneSeries.put(eventAirForceKey, buildSeries(planes));
		//return series:
		return FXCollections.observableArrayList(
				eventAirForceToPlaneSeries.get(eventAirForceKey));
	}
	
	//build series list:
	private ObservableList<XYChart.Series<String,Number>>buildSeries(List<Plane>planes) {
		
		ObservableList<XYChart.Series<String,Number>>
		planeSeries = FXCollections.observableArrayList(); //list of series
		
		planes.forEach(plane ->{
			XYChart.Series<String,Number> series = new XYChart.Series<String, Number>(); //create series
			series.setName(plane.getName()); //add plane name
			series.getData().add(new Data<String, Number>("Planes",plane.getSpeed())); //add planes speed
			planeSeries.add(series); //add series to list
		});
		
		return planeSeries;
	}
}
