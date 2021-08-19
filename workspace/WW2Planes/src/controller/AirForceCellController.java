package controller;

import java.util.List;
import java.util.function.BiConsumer;

import com.jfoenix.controls.JFXListCell;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.AirForce;
import model.Plane;

public final class AirForceCellController extends JFXListCell<AirForce> {
	
	private String name; //air force name
	private List<Plane>planes; //air force planes
	
	//constructor:
	AirForceCellController(BiConsumer<String, List<Plane>>showChartData) {
		
		this.setId("airforce-cell"); //give id for style sheet
		
		//add press event to build charts using cell's planes:
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent event) {
				//give name and planes to invoked bi consumer:
				showChartData.accept(name, planes);
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
			name = airForce.getAirForceName(); //get air force name
			planes = airForce.getAirForcePlanes(); //get air force planes
			setText(name); //set text with air force name
		}
	}
}
