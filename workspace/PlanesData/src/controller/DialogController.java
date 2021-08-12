package controller;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class DialogController implements Rootable {
	
	//root fxml element & children:
	@FXML private JFXDialog rootDialog;
    @FXML private JFXDialogLayout contentDL;
    
    @FXML
    void initialize() {
    	//set contentDL as content:
    	rootDialog.setContent(contentDL); 
    }
    
    Parent root = Rootable.getRoot(this, "/view/dialog.fxml"); //root
    
    //constructor:
    DialogController(Pane bodyP){
   		contentDL.setBody(bodyP); //add body pane to content
   	}
    
    //show dialog on given stack pane:
    void show(StackPane stackPane) { rootDialog.show(stackPane); }
}