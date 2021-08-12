package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.input.MouseEvent;
import model.Plane;
import model.Plane.Type;

public final class TypeData {
	
	//map of plane types and lists of planes of said type:
	private Map<String, List<String>> planeTypeToNames = new HashMap<String, List<String>>();
	
	//constructor:
	public TypeData() {
		initPlaneTypeToNames(); //initialize type to names
	}
	
	//initialize planeTypeToNames:
	private void initPlaneTypeToNames() {
		
    	for(Type type : Type.values()) {
    		//initialize map with plane type keys, holding empty lists:
    		planeTypeToNames.put(type.toString(), new ArrayList<String>());
    	}
	}
	
	//get list of plane names for given type:
	public List<String> getPlaneNamesForType(String type) {
		return new ArrayList<String>(planeTypeToNames.get(type));
	}

	//return list of pie chart data for given planes:
	public ObservableList<PieChart.Data>getData(List<Plane>planes) {
	
		//list of pie chart data:
    	ObservableList<PieChart.Data>pieChartData = FXCollections.observableArrayList();
    	
    	initPlaneTypeToNames(); //initialize type to names
    	
    	planes.forEach(plane ->{
    		//add plane's name to list of other names with same type:
    		planeTypeToNames.get(plane.getType().toString()).add(plane.getName());
    	});
    	
    	//loop through map's key set:
    	for (String planeType : planeTypeToNames.keySet()) {
    		int planesNum;
    		//if plane type has list entries:
    		if((planesNum = planeTypeToNames.get(planeType).size()) > 0){
    			//Add type and its amount to pie chart data:
        		pieChartData.add(new PieChart.Data(planeType.toString(), planesNum));
    		}
    	}
    	return pieChartData; //return pie chart data
	}
}
