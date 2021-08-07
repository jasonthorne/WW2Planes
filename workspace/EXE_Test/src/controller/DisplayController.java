package controller;

import com.jfoenix.controls.JFXListView;

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
       
    }
    
    
    private final Stage stage = new Stage(); //stage
  	
  	//frame.fxml controller:
  	private static DisplayController displayCtrlr = new DisplayController();
  	
  	//private constructor for singleton:
    private DisplayController() {
    	Scene scene = new Scene(Rootable.getRoot(this, "/view/display.fxml")); //add root to scene
    	stage.setScene(scene); //add scene to stage
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
