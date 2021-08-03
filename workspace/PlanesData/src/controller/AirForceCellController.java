package controller;

import java.util.List;

import com.jfoenix.controls.JFXListCell;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import model.AirForce;
import model.Plane;

public class AirForceCellController extends JFXListCell<AirForce> {
	
	//root fxml element:
	@FXML private HBox rootAP;
	
	//air force planes:
  	private List<Plane>planes;
	
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
	    	
	    	//set air force planes:
	    	planes = airForce.getAirForcePlanes(); 
	    	
	    	/*
			//populate cell with data from air force:
	    	airForceNameLbl.setText(airForce.getAirForceName()); 
			hasHomeAdvLbl.setText(Boolean.toString(airForce.getHasHomeAdv()));
			
			planes = airForce.getAirForcePlanes(); //get air force planes
	
			setText(null); 
			setGraphic(rootAP); //set this root element as the graphic	
			*/
	    	
	    	
	    	/** +++++++++++++++++LOOK AT CAMPAIGN CELL CONTROLLER ++++++++++++++++ */
	    	/////////////////create cliock event here that calls bar chart creation using selected airforces planes
	    	setGraphic(null);
	    }
	}

}
