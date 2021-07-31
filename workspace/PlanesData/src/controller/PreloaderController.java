package controller;

import com.jfoenix.controls.JFXSpinner;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PreloaderController implements Rootable {

	//root fxml element & children:
    @FXML private StackPane rootSP;
    @FXML private AnchorPane bodyAP;
    @FXML private Label titleLbl;
    @FXML private JFXSpinner loadingSpinner;

    @FXML
    void initialize() {
    
    }
    
    private final Stage stage = new Stage(); //stage
  	
  	//preloader.fxml controller singleton:
  	private static PreloaderController singlePreloaderCtrlr = null;
  	
  	//frame.fxml controller:
  	private final FrameController frameCtrlr = new FrameController();
  	
  	//private constructor for singleton:
    private PreloaderController() {
    	Scene scene = new Scene(Rootable.getRoot(this, "/view/preloader.fxml")); //add root to scene
    	stage.setScene(scene); //add scene to stage
    }
  	
  	//get preloader controller singleton:
    public static PreloaderController getPreloaderCtrlr() {
    	//create singleton if necessary:
        if (singlePreloaderCtrlr == null) {singlePreloaderCtrlr = new PreloaderController();}
        return singlePreloaderCtrlr; 
    }
  	
    //show stage:
    public void showStage() {
    	
    	if(!stage.isShowing()) { //if stage isn't showing:
    		
    		//create fade in transition for root stack pane:
    		FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), rootSP);
    		fadeIn.setFromValue(0);
    		fadeIn.setToValue(1);
            
            //create fade out transition for root stack pane:
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), rootSP);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            
            //after fade out, change to frame view:
            fadeOut.setOnFinished(event -> {
            	
	  	  		Parent frameRoot = Rootable.getRoot(frameCtrlr, "/view/frame.fxml"); //get frame root
	  			stage.setScene(new Scene(frameRoot)); //add new scene with root to stage
	  			
	  			//create fade in transition for frame root:
	    		FadeTransition fadeInFrame = new FadeTransition(Duration.seconds(1), frameRoot);
	    		fadeInFrame.setFromValue(0);
	    		fadeInFrame.setToValue(1);
	    		fadeInFrame.play();
	  		});
            
            //load events data, passing fade out transition:
        	frameCtrlr.loadEventsData(fadeOut);
            stage.show(); //show stage
            fadeIn.play(); //play fade in
    	}
    }
}
