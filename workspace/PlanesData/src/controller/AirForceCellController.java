package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.jfoenix.controls.JFXListCell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.AirForce;
import model.Event;
import model.Plane;

public class AirForceCellController extends JFXListCell<AirForce> {
	
	//root fxml element:
	@FXML private AnchorPane rootAP;
	
  	private String airForceName; //air force name
  	private List<Plane>airForcePlanes; //air force planes
  	
  	//constructor:
  	AirForceCellController(BiConsumer<List<Plane>,String> setSpeedsBC) { 
  		
  		//add click event to build speeds bar chart using cell's planes:
  		this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
               
                
                //-------------
                /*
            	XYChart.Series<String, Number> series1 = new Series<String, Number>();
				series1.setName("Plane name here");    
    	        series1.getData().add(new Data<String, Number>("Planes", 80));
    	        //Tooltip.install(series1.getNode(), new Tooltip("Yo dawg!"));
    	        
    	        series1.getData().add(new Data<String, Number>("Planes", 30));
    	        
    	        speedsBC.getData().add(series1);
    	        //speedsBC.getData().setAll(series1);
                //-----------
                 * 
                 * 
    	        
    	      */
                
                /*
                //List<Series>series = new ArrayList<Series>();
                ObservableList<XYChart.Series<String,Number>>series = FXCollections.observableArrayList();
               
                
    	        planes.forEach(plane ->{
    				
		        	 XYChart.Series<String,Number> seriesTEST = new XYChart.Series<String, Number>();
	   				 seriesTEST.setName(plane.getName());
	   				 seriesTEST.getData().add(new Data<String, Number>("",plane.getSpeed()));
	   				 series.add(seriesTEST);
   				 
    	        });
    	        
    	        //speedsBC.getData().clear();
    	        speedsBC.getData().setAll(series);
    	        
    	        */
            	
            	//===================
            	setSpeedsBC.accept(airForcePlanes, airForceName);
            	//=================
                
                
                
                
             }
		});
  	}
	
	//update cell with air force data:
	@Override 
  	protected void updateItem(AirForce airForce, boolean isEmpty) {
  		super.updateItem(airForce, isEmpty);
	  	
		if (isEmpty || airForce == null) {
	        setText(null);
	        setGraphic(null);
	    } else {
	    	airForceName = airForce.getAirForceName(); //get air force name
	    	airForcePlanes = airForce.getAirForcePlanes(); //get air force planes
	    	setText(airForceName); //set text with air force name
	    	setGraphic(null);
	    }
	}
}
