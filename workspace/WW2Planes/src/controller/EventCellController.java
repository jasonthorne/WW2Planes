package controller;

import com.jfoenix.controls.JFXListCell;

import model.Event;

public final class EventCellController extends JFXListCell<Event> {
	
	//constructor:
	EventCellController(){
		this.setId("event-cell"); //give id for css sheet
	}
	
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