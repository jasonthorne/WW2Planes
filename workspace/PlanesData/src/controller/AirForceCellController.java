package controller;

import com.jfoenix.controls.JFXListCell;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.AirForce;

public final class AirForceCellController extends JFXListCell<AirForce> {
	
	//root fxml element:
	@FXML private AnchorPane rootAP;
	
	//update cell with air force data:
	@Override 
  	protected void updateItem(AirForce airForce, boolean isEmpty) {
		
  		super.updateItem(airForce, isEmpty);
	  	
		if (isEmpty || airForce == null) {
	        setText(null);
	        setGraphic(null);
	    } else {
	    	//set text with air force name:
	    	setText(airForce.getAirForceName()); 
	    	setGraphic(null);
	    }
	}
}
