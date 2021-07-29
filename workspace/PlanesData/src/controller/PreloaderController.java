package controller;

import com.jfoenix.controls.JFXButton;
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
    @FXML private Label loadingLbl;

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
    		FadeTransition fadeInThisRoot = new FadeTransition(Duration.seconds(2), rootSP);
    		fadeInThisRoot.setFromValue(0);
    		fadeInThisRoot.setToValue(1);
            
            //create fade out transition for root stack pane:
            FadeTransition fadeOutThisRoot = new FadeTransition(Duration.seconds(1), rootSP);
            fadeOutThisRoot.setFromValue(1);
            fadeOutThisRoot.setToValue(0);
            
            //after root stack pane fade out:
            fadeOutThisRoot.setOnFinished((event) -> {
	  			
	  	  		Parent frameRoot = Rootable.getRoot(frameCtrlr, "/view/frame.fxml"); //get frame root
	  			stage.setScene(new Scene(frameRoot)); //add new scene with root to stage
	  			
	  			//create fade in transition for frame root:
	    		FadeTransition fadeInFrameRoot = new FadeTransition(Duration.seconds(1), frameRoot);
	    		fadeInFrameRoot.setFromValue(0);
	    		fadeInFrameRoot.setToValue(1);
	    		fadeInFrameRoot.play();
	  			
	  		});
            
            //after this root has faded in:
            fadeInThisRoot.setOnFinished((event) -> {
            	
            	new Thread(() -> { //fire off new thread
            		frameCtrlr.loadData(); //loading data to frame controller
            		fadeOutThisRoot.play(); //then fading out this root
            	}).start();
            });
            
            stage.show(); //show stage
            fadeInThisRoot.play(); //play fade in
    	}
    }
}
