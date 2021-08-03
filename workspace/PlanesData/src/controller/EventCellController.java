package controller;

import com.jfoenix.controls.JFXListCell;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.Event;

public class EventCellController extends JFXListCell<Event> implements Rootable{

    //root fxml element:
    @FXML private AnchorPane rootAP;
   
    //update cell with event name:
	@Override  
	protected void updateItem(Event event, boolean isEmpty) {
		
        super.updateItem(event, isEmpty);
        
  		if (isEmpty || event == null) {
  	        setText(null);
  	        setGraphic(null);
  	    } else {
  	    	//set text with event name:
  	    	setText(event.getName());
  	    	setGraphic(null);
        }
    }
}