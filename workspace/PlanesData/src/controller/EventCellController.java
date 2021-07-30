package controller;

import com.jfoenix.controls.JFXListCell;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.Event;

public class EventCellController extends JFXListCell<Event> implements Rootable {

    //root fxml element:
    @FXML private AnchorPane rootAP;
  
    //root element for this controller:
  	//private final Parent root = Rootable.getRoot(this, "/view/eventCell.fxml"); //////??????? NEEDED???????
    
  	
	//constructor:
	/*EventCellController() {
		//////////////this.selectEventCtrlr = selectEventCtrlr; //assign selectEvent controller
	}*/
	
	//update cell with event data:
	@Override 
	protected void updateItem(Event event, boolean isEmpty) {
        super.updateItem(event, isEmpty);
        
  		if (isEmpty || event == null) {
  	        setText(null);
  	        setGraphic(null);
  	    } else {
  	    	//set text with name of event:
			setText(event.getName()); 
        }
    }
}
