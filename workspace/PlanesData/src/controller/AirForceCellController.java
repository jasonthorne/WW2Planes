package controller;

import java.util.List;

import com.jfoenix.controls.JFXListCell;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.AirForce;
import model.Plane;

public class AirForceCellController extends JFXListCell<AirForce> implements Rootable {
	
	//root fxml element:
	@FXML private AnchorPane rootAP;
	
	//air force planes:
  	private List<Plane>planes;
  	
  	private String TEST;
  	
    @FXML
    void initialize() {
    	this.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent event) {
                       
                        System.out.println(TEST);
                     }
                });
    }
  	
  	//root element for this controller:
  	private final Parent root = Rootable.getRoot(this, "/view/airForceCell.fxml");
  	
  	AirForceCellController(){
  		/*
  		this.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                       
                        System.out.println(TEST);
                     }
                });
    	*/
  	}
	
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
	    	
	    	TEST = airForce.getAirForceName();
	    	
	    	
	    	
	    	
	    }
	}

}
