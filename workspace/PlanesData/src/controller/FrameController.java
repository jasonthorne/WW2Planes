package controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.AirForce;
import model.Event;
import model.Plane;

public class FrameController implements Rootable {

	//root fxml element & children:
    @FXML private StackPane rootSP;
    @FXML private AnchorPane bodyAP;
    @FXML private JFXTabPane tabsTP;
    @FXML private Tab availabilitiesTab;
    @FXML private AnchorPane availabilitiesAP;
    @FXML private TableView<Plane>availabilitiesTV;
    @FXML private Tab speedsTab;
    @FXML private AnchorPane speedsAP;
    @FXML private BarChart<?, ?> speedsBC;
    @FXML private JFXListView<Event> eventsLV;

    @FXML
    void initialize() {
   
    }
    
    FrameController(){
    	
    }
    
    //observable list of events:
    private final ObservableList<Event>observEvents = FXCollections.observableArrayList();
    
    //observable list of event's air forces:
    private ObservableList<AirForce>observAirForces;
    
    void loadEventsData(){ //load events data from db
    	
    	if (observEvents.isEmpty()) { //if event data is empty:
    		observEvents.addAll(database.SelectEvents.select()); //load data from database
    		eventsLV.setItems(observEvents); //add observable events to listView
    		//set listView cellFactory to create EventCellControllers:
    		eventsLV.setCellFactory(EventCellController -> new EventCellController());
    	}
    	
    	
    	
    	
    	System.out.println(observEvents);
    }
}
