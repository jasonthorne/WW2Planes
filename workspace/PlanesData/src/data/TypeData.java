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

public final class TypeData {
	
	
	
	
	Map<Plane.Type, List<String>> planeTypeToNames = new HashMap<Plane.Type, List<String>>();
	
	
	
	
	//return list of pie chart data for given planes:
	public /*static*/ ObservableList<PieChart.Data>getData(List<Plane>planes) {
		
		
		//list of pie chart data:
    	ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    	
    	//===========================MADE GLOBAL
    	//map of plane types and lists of planes of said type:
    	//////////////Map<Plane.Type, List<String>> planeTypeToNames = new HashMap<Plane.Type, List<String>>();
    	
    	//initialize map with enum keys, holding empty lists: //++++++PUT IN GLOBAL METHOD WHICH IS THEN CALLED HERE??
    	for (Plane.Type planeType : Plane.Type.values()) {
    		//add type key with empty list value to map:
    		planeTypeToNames.put(planeType, new ArrayList<String>());
    	}
    	
    	//======================
    	
    	//loop through planes:
    	planes.forEach(plane ->{
    		//add plane's name to list of names with same type:
    		planeTypeToNames.get(plane.getType()).add(plane.getName());
    	});
    	
    	
    	//loop through map's key set:
    	for (Plane.Type planeType : planeTypeToNames.keySet()) {
    		//++++++++++++++++CHECK THAT NUMBER IS GREATER THAN 1
    		int planesNum;
    		//if plane type has list entries:
    		if((planesNum = planeTypeToNames.get(planeType).size()) > 0){
    			
    			//+++++++++TEST FOR STORING data  for retreival later +++++++++++
    			 PieChart.Data test = new PieChart.Data(
        				 planeType.toString(),
        				 planeTypeToNames.get(planeType).size());
    			 
    			//Add type and its amount to pie chart:
        		pieChartData.add(new PieChart.Data(planeType.toString(), planesNum));
    		}
    	}
    	
    	
    	//=============================
    	/*
    	for (final PieChart.Data data : pieChartData.) {
    	    data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,
    	        new EventHandler<MouseEvent>() {
    	            @Override public void handle(MouseEvent e) {
    	            
    	                System.out.println(planeTypeToNames.get(Plane.Type.valueOf(
    	                				data.getName().toUpperCase().replace('-', '_'))));
    	             }
    	        });
    	}
    	*/
    	
    	
    	//pieChartData.forEach(data -> {
    		
    		
    		/*
    		data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,
        	        new EventHandler<MouseEvent>() {
        	            @Override public void handle(MouseEvent e) {
        	            
        	            	
        	                //System.out.println(planeTypeToNames.get(Plane.Type.valueOf(
        	                		//		data.getName().toUpperCase().replace('-', '_'))));
        	            	
        	            	System.out.println("yo");
        	             }
        	        });*/
    	//});*/
    	
    	/*
    	pieChartData.forEach(data -> {
    		
    		System.out.println("=================");
    		System.out.println(data.getPieValue());
    		System.out.println("=================");
    		
    	});*/
    	
    	//============================
		
    	return pieChartData;
		
		/*
		ObservableList<XYChart.Series<String,Number>>
		planeSeries = FXCollections.observableArrayList(); //list of series
		
		planes.forEach(plane ->{
			XYChart.Series<String,Number> series = new XYChart.Series<String, Number>(); //create series
			series.setName(plane.getName()); //add plane name
			series.getData().add(new Data<String, Number>("Planes",plane.getSpeed())); //add planes speed
			planeSeries.add(series); //add series to list
		});
		
		return planeSeries;
		*/
	}

}
