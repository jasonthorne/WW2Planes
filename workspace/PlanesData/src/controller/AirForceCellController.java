package controller;

import java.util.List;

import com.jfoenix.controls.JFXListCell;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.AirForce;
import model.Plane;

public class AirForceCellController extends JFXListCell<AirForce> {
	
	//root fxml element:
	@FXML private AnchorPane rootAP;
	
	//air force planes:
  	private List<Plane>planes;
  	
  	//constructor:
  	AirForceCellController(String TEST) { //#####################PASS GRAPH IN HERE!! AND 
  		//add click event to build graph using cell's planes:
  		this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                System.out.println(TEST + " " + planes);
             }
		});
  	}
	
	//update cell with air force data:
	@Override 
  	protected void updateItem(AirForce airForce, boolean isEmpty) {
  		super.updateItem(airForce, isEmpty);
	  	
		if (isEmpty || airForce == null) {
	        setText(null);
	        setGraphic(null);
	    } else {
	    	setText(airForce.getAirForceName()); //set text with air force name
	    	planes = airForce.getAirForcePlanes(); //set air force planes
	    	setGraphic(null);
	    }
	}
}
