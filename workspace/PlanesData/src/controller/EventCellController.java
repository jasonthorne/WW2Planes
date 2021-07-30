package controller;

import com.jfoenix.controls.JFXListCell;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.Event;

public class EventCellController extends JFXListCell<Event> {

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
        }
    }
}