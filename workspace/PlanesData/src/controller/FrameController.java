package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;

import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.AirForce;
import model.Event;
import model.Period;
import model.Plane;
import model.Period.Block;
import model.Plane.Availability;
import table.AvailabilitiesTable;

public class FrameController implements Rootable {

	//root fxml element & children:
    @FXML private StackPane rootSP;
    @FXML private AnchorPane bodyAP;
    @FXML private JFXTabPane tabsTP;
    @FXML private Tab availabilitiesTab;
    @FXML private AnchorPane availabilitiesAP;
    @FXML private ScrollPane planesTablesSP;
    @FXML private VBox planesTablesVB;
    @FXML private Tab speedsTab;
    @FXML private AnchorPane speedsAP;
    @FXML private BarChart<?, ?> speedsBC;
    @FXML private JFXListView<Event> eventsLV;
    
    @FXML
    void initialize() {
    	
    	//add observable events to events list view:
		eventsLV.setItems(observEvents);
		//set list view cellFactory to create EventCellControllers:
		eventsLV.setCellFactory(EventCellController -> new EventCellController());
		
		//add change listener to events list view:
		/**https://stackoverflow.com/questions/12459086/how-to-perform-an-action-by-selecting-an-item-from-listview-in-javafx-2	*/
    	eventsLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Event>() {
    		
    		@Override //override change listener's changed: 
    	    public void changed(ObservableValue<? extends Event> observable, Event oldVal, Event newVal) {
    			
    			List<AirForce>airForces = newVal.getAirForces(); //get selected event's air forces
	        	
    			//make list of planes tables from air forces:
    			List<TableView<Plane>>planesTables = airForces.stream()
    					.map(airForce -> AvailabilitiesTable.getTable(airForce,availabilitiesAP))
    					.collect(Collectors.toList());
    			
    			planesTablesVB.getChildren().setAll(planesTables); //add planes tables to vb
    			
	        	showSpeeds(airForces); //show air force speeds
	        	
	        
    	    }
    	});
    }
    
   
    
    FrameController(){
    	/////this.preloaderCtrlr = preloaderCtrlr;
    	
    }
    
    private final ObservableList<Event>observEvents = FXCollections.observableArrayList(); //observable list of events
    //////////////////////////private ObservableList<AirForce>observAirForces;  //observable list of event's air forces
    
    void loadEventsData(FadeTransition fadeOutPreloader){ //load events data from db
    	
    	if (observEvents.isEmpty()) { //if events data is empty:
    		new Thread(() -> { //fire new thread:
    	    	try {
    	    		observEvents.addAll(database.SelectEvents.select()); //load events data
    	    		fadeOutPreloader.play(); //fade out preloader:
    	    	}catch(Exception e) { e.printStackTrace(); }
        	}).start();
    	}
    }
    
    //++++++++++++++++++THIS SHOULD MAYBE BE OBSERVABLE??????? ++++++++++++++
    //////private List<TableView<Plane>>planesTables = new ArrayList<TableView<Plane>>(); //list of planesTables
   ////////////// private ObservableList<TableView<Plane>>planesTables = FXCollections.observableArrayList();
    //////////////private TableView<Plane> planesTable = new TableView<Plane>();
   
  
    
    
    private void showSpeeds(List<AirForce> airForces) {
    	////////System.out.println("airfoece SPEED : " + airForces);
    }
    
    
    
    
    
}
