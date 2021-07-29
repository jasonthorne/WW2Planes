package controller;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PreloaderController implements Rootable {

	//root fxml element & children:
    @FXML private StackPane rootSP;
    @FXML private AnchorPane bodyAP;

    @FXML private Label testLbl;
    @FXML private JFXButton testBtn;

    @FXML
    void initialize() {
    
    }
    
    private final Stage stage = new Stage(); //stage
  	
  	//preloader.fxml controller singleton:
  	private static PreloaderController singlePreloaderCtrlr = null;
  	
  	//private constructor for singleton:
    private PreloaderController(){
    	Scene scene = new Scene(Rootable.getRoot(this, "/view/preloader.fxml")); //add root to scene
    	stage.setScene(scene); //add scene to stage
    }
  	
  	//get preloader controller singleton:
    public static PreloaderController getPreloaderCtrlr() {
    	//create singleton if necessary:
        if (singlePreloaderCtrlr == null) {singlePreloaderCtrlr = new PreloaderController();}
        return singlePreloaderCtrlr; 
    }
  	
    //show stage (if not):
    public void showStage() {
    	if(!stage.isShowing()){stage.showAndWait();}
    }
    
    /*
  	private void moveToLoginView(){ 
  		
  		//fade out transition, adding frame view scene to stage on finish:
  		FadeTransition fadeOut = Fade.getFadeTransition(rootAP, Fade.FadeOption.FADE_OUT, 200);
  		fadeOut.setOnFinished(event -> {
  			
  	  		Parent loginRoot = Rootable.getRoot(loginCtrlr, View.LOGIN.getPath()); //get login root
  			stage.setScene(new Scene(loginRoot)); //add new scene with root to stage
  			Fade.getFadeTransition(loginRoot, Fade.FadeOption.FADE_IN, 200).play(); //fade in view
  			
  		});
  		fadeOut.play(); //play transition
  	}*/
    
    
}
