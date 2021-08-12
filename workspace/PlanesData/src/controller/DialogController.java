package controller;

import java.util.List;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.fxml.FXML;
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
    
    Parent root = Rootable.getRoot(this, "/view/dialog.fxml"); //root
    
    //constructor:
    DialogController(List<String>planeNames, String planeType){
    	
    	//set heading text with plane type and amount:
    	headingText.setText(String.valueOf(planeNames.size()) + " " + planeType);
    	contentDL.setHeading(headingText); //add heading text to heading
  
    	//add plane names to vertical box:
    	planeNames.forEach(planeName-> planeNamesVB.getChildren().add(new Label(planeName)));
    	contentDL.setBody(planeNamesVB); //add vertical box to body
   	}
    
    //show dialog on given stack pane:
    void show(StackPane stackPane) { rootDialog.show(stackPane); }
}