package controller;

import java.io.IOException;
import java.util.List;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DialogController implements Rootable {
	
	//root fxml element & children:
	@FXML private JFXDialog rootDialog;
    @FXML private JFXDialogLayout contentDL;
    @FXML private Text headingText;
    @FXML private VBox planeNamesVB;
    
    @FXML
    void initialize() {
    	rootDialog.setContent(contentDL); //set contentDL as content
    	
    }
    
    ////Parent root = Rootable.getRoot(this, "/view/dialog.fxml"); //root
   Parent root = Rootable.getRoot(this, "/view/dialog.fxml"); //root
    
    //FXMLLoader loader;
  // Parent root; 
	////////
    
    //constructor:
    DialogController(List<String>planeNames, String planeType){
    	
    	
       // loader.setController(rootable); //set this class as the controller
       // Parent root = loader.setController(rootable);
    	
        
		try {
			
			
			/*
			loader = FXMLLoader.load(getClass().getResource("/view/dialog.fxml"));
			 root = loader.load();
			loader.setController(root);*/
			/*
			FXMLLoader fxmlLoader = new FXMLLoader("/view/dialog.fxml");
			fxmlLoader.setRoot(rootDialog);
			fxmlLoader.setController(this);
			fxmlLoader.load();*/
	       
		} catch (Exception e) {
			
			e.printStackTrace();
		}
        
    	
    	//set heading text with plane type and amount:
    	headingText.setText(String.valueOf(planeNames.size()) + " " + planeType);
    	contentDL.setHeading(headingText); //add heading text to heading
  
    	//add plane names to vertical box:
    	planeNames.forEach(planeName-> planeNamesVB.getChildren().add(new Label(planeName)));
    	contentDL.setBody(planeNamesVB); //add vertical box to body
   	}
    
    //show dialog on given stack pane:
    void show(StackPane stackPane) { 
    	System.out.println("yo");
    	
    	rootDialog.show(stackPane); 
    	/*
    	new Thread(new Runnable() {
    	    @Override public void run() {
		    	Platform.runLater(new Runnable() {
		            @Override public void run() {
		            	rootDialog.show(stackPane); 
		            }
		        });
    	    }
	    }).start();	*/	
    	
    	
    	}
}