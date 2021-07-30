package model;

import java.util.ArrayList;
import java.util.List;

public final class Event {
	
	private final String name; //name of event
	private final List<Period>periods; //list of periods covered
	private final List<AirForce>airForces; //list of air forces involved
	
	//constructor:
	public Event(String name, List<Period>periods, List<AirForce>airForces) {
		this.name = name;
		this.periods = new ArrayList<Period>(periods);
		this.airForces = new ArrayList<AirForce>(airForces);
	}
	
	//get event name:
	public String getName() { return name; }
	
	//get periods:
	public List<Period> getPeriods() { return new ArrayList<Period>(periods); }
		
	//get event air forces:
	public List<AirForce> getAirForces() { return new ArrayList<AirForce>(airForces); }
	
	@Override
	public String toString() {
		return "Event [name=" + name + ", periods=" + periods + ", airForces=" + airForces + "]";
	}
	
}