package model;

import java.util.HashMap;
import java.util.Map;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;

public final class Plane extends RecursiveTreeObject<Plane>{
	
	public enum Availability { //a plane's availability (in relation to a period of history)
		
		UNAVAILABLE("Unavailable"), LIMITED("Limited"), COMMON("Common");
		
		private final String availability; //availability
		//constructor:
		private Availability(String availability) { this.availability = availability; } 
		@Override public String toString() { return availability; } //return availability
	}
	
	int i = 0; //++++++++++++++++++++++
	
	private final SimpleStringProperty name; //name of plane
	private final String type; //===================================change to enum! 
	private final int speed; //speed of plane
	private final Map<Period, Availability>periodToAvailability; //periods and their corresponding availability
	
	//constructor:
	public Plane(String name, int speed, Map<Period, Availability>periodToAvailability) {
		this.name = new SimpleStringProperty(name);
		this.type = "type" + i++; //++++++++++++++
		this.speed = speed;
		this.periodToAvailability = new HashMap<Period, Availability>(periodToAvailability);
		
	}
	
	//get plane name:
	public String getName() { return name.get(); }
	
	//get type:
	public String getType() { return type; }
	
	//get plane speed:
	public int getSpeed() { return speed; }
	
	//get plane availabilities:
	public HashMap<Period, Availability> getAvailabilities() { 
		return new HashMap<Period, Availability>(periodToAvailability); 
	}

	@Override
	public String toString() {
		return "Plane [name=" + name.get() + ", speed=" + speed + ", periodToAvailability=" + periodToAvailability + "]";
	}

}
