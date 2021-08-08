package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private JFXButton loadDataBtn;
    
    @FXML
    private ImageView picIV;

    @FXML
    void initialize() throws URISyntaxException {
    	
    	/*
    	File file = new File("./images/testImg/test.png");
        Image image = new Image(file.toURI().toString());
        picIV.setImage(image);
      */
    	
    	////File file = new File(getClass().getClassLoader().getResource("test.png"));
    	//System.out.println(getClass().getClassLoader().getResource("./images/testImg/test.png"));
    	
    	
    	//////ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("test.png"));
    	//Image image = new Image(file.toURI().toString());
    	//picIV.setImage(new Image(getClass().getResourceAsStream("test.png").toString()));
        
    	/*
    	picIV.setImage(new Image(
    	new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/test.png")))
    	   .lines().collect(Collectors.joining("\n"))));
        */
    	
    	/*String imgLocation = "./images/testImg/test.png";
		URL imageURL = getClass().getResource(imgLocation);
		///////ImageIcon zoomInIcon = new ImageIcon(imageURL);
		/////JButton zoomInButton = new JButton(zoomInIcon);
		Image image = new Image(imageURL.toString());
        picIV.setImage(image);*/
    	
    	
    	
    	//picIV.setImage(new Image ("./images/testImg/test.png"));
    	
    	//set events list view observable events:
		eventsLV.setItems(observEvents); 
		//set events list view cellFactory to create EventControllers:
		eventsLV.setCellFactory(EventCellController -> new EventController());
		
		loadDataBtn.setOnAction(event -> {
			
	    	new Thread(() -> { //fire new thread:
		    	try {
		    		//load events data:
		    		observEvents.addAll(database.SelectAll.selectEvents());
		    	}catch(Exception e) { e.printStackTrace(); }
	    	}).start();
		});
    }
    
    
   
    private final ObservableList<Event>observEvents = FXCollections.observableArrayList();  //events
    
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
