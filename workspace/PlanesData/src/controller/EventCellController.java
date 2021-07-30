package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListCell;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.Event;

public class EventCellController extends JFXListCell<Event> implements Rootable {

	@FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    
    //root fxml element & children:
    @FXML private AnchorPane rootAP;
    @FXML private Label nameLbl;
	
    @FXML 
    void initialize() {
   
    }
	
    //root element for this controller:
  	private final Parent root = Rootable.getRoot(this, "/view/eventCell.fxml");
    
  	//instance of EventsController:
	////////////////private final EventsController selectEventCtrlr;  //?????????????? needed?????????
	
	//constructor:
	EventCellController(/*EventsController selectEventCtrlr*/) {
		//////////////this.selectEventCtrlr = selectEventCtrlr; //assign selectEvent controller
	}
	
	//update cell with event data:
	@Override 
	protected void updateItem(Event event, boolean isEmpty) {
        super.updateItem(event, isEmpty);
        
  		if (isEmpty || event == null) {
  	        setText(null);
  	        setGraphic(null);
  	    } else {
			//populate cell with data from event:
			nameLbl.setText(event.getName()); 
			 
			setText(null); 
	        setGraphic(rootAP); //set this root element as the graphic	
        }
    }
	
}
