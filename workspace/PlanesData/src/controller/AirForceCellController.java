package controller;

import java.util.ArrayList;
import java.util.List;

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
	
	//air force planes:
  	private List<Plane>planes;
  	
  	//constructor:
  	AirForceCellController(String TEST, BarChart<String,Number> speedsBC) { //#####################PASS GRAPH IN HERE!! AND 
  		//add click event to build graph using cell's planes:
  		this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                System.out.println(TEST + " " + planes);
                
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
                
                
                //List<Series>series = new ArrayList<Series>();
                ObservableList<XYChart.Series<String,Number>>series = FXCollections.observableArrayList();
               
                
    	        planes.forEach(plane ->{
    				
	        	 XYChart.Series<String,Number> seriesTEST = new XYChart.Series<String, Number>();
   				 seriesTEST.setName(plane.getName());
   				 seriesTEST.getData().add(new Data<String, Number>("",plane.getSpeed()));
   				 series.add(seriesTEST);
   				 
   			});
    	        
    	        speedsBC.getData().clear();
    	        speedsBC.getData().setAll(series);
    	        
    	        
                
                
                
                
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
	    	setText(airForce.getAirForceName()); //set text with air force name
	    	planes = airForce.getAirForcePlanes(); //set air force planes
	    	setGraphic(null);
	    }
	}
}
