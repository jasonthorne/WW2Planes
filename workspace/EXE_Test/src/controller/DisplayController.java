package controller;

import com.jfoenix.controls.JFXListView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Event;

public class DisplayController implements Rootable{
	
	@FXML
    private StackPane rootSP;

    @FXML
    private AnchorPane bosyAP;

    @FXML
    private JFXListView<Event> eventsLV;

    @FXML
    private Label eventsLbl;

    @FXML
    void initialize() {
    	
    	//set events list view observable events:
		eventsLV.setItems(observEvents); 
		//set events list view cellFactory to create EventControllers:
		eventsLV.setCellFactory(EventCellController -> new EventController());
    }
    
    private final ObservableList<Event>observEvents = FXCollections.observableArrayList();  //events
    
    private final Stage stage = new Stage(); //stage
  	
  	//frame.fxml controller:
  	private static DisplayController displayCtrlr = new DisplayController();
  	
  	//private constructor for singleton:
    private DisplayController() {
    	
    	Scene scene = new Scene(Rootable.getRoot(this, "/view/display.fxml")); //add root to scene
    	stage.setScene(scene); //add scene to stage

    	new Thread(() -> { //fire new thread:
	    	try {
	    		//load events data:
	    		observEvents.addAll(database.SelectAll.selectEvents());
	    	}catch(Exception e) { e.printStackTrace(); }
    	}).start();
    }
  	
  	//get controller singleton:
    public static DisplayController getDisplayCtrlr() {
    	//create singleton if necessary:
        if (displayCtrlr == null) {displayCtrlr = new DisplayController();}
        return displayCtrlr; 
    }
    
    //show stage:
    public void showStage() {
    	 stage.show(); //show stage
    }
    

}
