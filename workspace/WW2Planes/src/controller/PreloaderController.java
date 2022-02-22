package controller;

import com.jfoenix.controls.JFXSpinner;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public final class PreloaderController implements Rootable {

	//root fxml element & children:
	@FXML private StackPane rootSP;
	@FXML private AnchorPane bodyAP;
	@FXML private Label titleLbl;
	@FXML private JFXSpinner loadingSpinner;
	@FXML private Label loadingLbl;
	
	@FXML
	void initialize() {
		System.out.println("yo");
		///titleLbl.setFont(Font.loadFont(getClass().getResourceAsStream("/font/raf-ww2.ttf"), 64.0)); //.loadFont("file:resources/fonts/TenaliRamakrishna-Regular.ttf", 45));
		//titleLbl.setFont(Font.loadFont("/resources/font/raf-ww2.ttf", 64.0)); //.loadFont("file:resources/fonts/TenaliRamakrishna-Regular.ttf", 45));
		
	}
	
	private final Stage stage; //stage
	
	//frame.fxml controller:
	private final FrameController frameCtrlr = new FrameController();
	
	//constructor:
	public PreloaderController(Stage stage) {
		
		/*
		Font font = Font.loadFont("file:resources/fonts/TenaliRamakrishna-Regular.ttf", 45);
	      //Setting the font
	      text.setFont(font);
	      
	      getClass().getResourceAsStream("/img/spitfire.png")));
	      
	      
		 */
		
		//=======================
		//titleLbl.setFont(Font.loadFont("/resources/font/raf-ww2.ttf", 64.0)); //.loadFont("file:resources/fonts/TenaliRamakrishna-Regular.ttf", 45));
		/////titleLbl.setFont(Font.loadFont(getClass().getResourceAsStream("/font/raf-ww2.ttf"), 64.0)); //.loadFont("file:resources/fon
		
		
		
		//=====================
		this.stage = stage; //set stage
		Scene scene = new Scene(Rootable.getRoot(this, "/view/preloader.fxml")); //add root to scene
		
		//--------
		////////scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm()); //add styles
		//--------
		this.stage.setScene(scene); //add scene to stage
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
				Scene scene = new Scene(frameRoot); //create new scene with root
				scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm()); //add styles
				stage.setScene(scene); //add scene to stage
				
				//create fade in transition for frame root:
				FadeTransition fadeInFrame = new FadeTransition(Duration.seconds(1), frameRoot);
				fadeInFrame.setFromValue(0);
				fadeInFrame.setToValue(1);
				fadeInFrame.play();
			});
			
			//after fade in, load data, passing fade out transition:
			fadeIn.setOnFinished(event -> {frameCtrlr.loadEventsData(fadeOut);});
			
			stage.show(); //show stage
			fadeIn.play(); //play fade in
		}
	}
}
