package model;

import java.util.HashMap;
import java.util.Map;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;

public final class Plane extends RecursiveTreeObject<Plane>{
	
	public enum Availability { 
		//plane's availability status:
		UNAVAILABLE("Unavailable"), LIMITED("Limited"), COMMON("Common");
		private final String availability; //availability
		private Availability(String availability) { this.availability = availability; } 
		@Override public String toString() { return availability; } //return availability
	}
	
	public enum Type { 
		//type of plane:
		FIGHTER("Fighter"), BOMBER("Bomber"), FIGHTER_BOMBER("Fighter-bomber");
		private final String type; //type
		private Type(String type) { this.type = type; } 
		@Override public String toString() { return type; } //return type
	}
	
	private final SimpleStringProperty name; //name of plane
	private final Type type; //type of plane
	private final int speed; //speed of plane
	private final Map<Period, Availability>periodToAvailability; //periods and their corresponding availability
	
	//constructor:
	public Plane(String name, Type type, int speed, Map<Period, Availability>periodToAvailability) {
		this.name = new SimpleStringProperty(name);
		this.type = type;
		this.speed = speed;
		this.periodToAvailability = new HashMap<Period, Availability>(periodToAvailability);
	}
	
	//get plane name:
	public String getName() { return name.get(); }
	
	//get plane type:
	public Type getType() { return type; }
	
	//get plane speed:
	public int getSpeed() { return speed; }
	
	//get plane availabilities:
	public HashMap<Period, Availability> getAvailabilities() { 
		return new HashMap<Period, Availability>(periodToAvailability); 
	}

	@Override
	public String toString() {
		return "Plane [name=" + name.get() + ", type=" + type + ", speed=" + speed + ", periodToAvailability=" 
				+ periodToAvailability + "]";
	}
}
