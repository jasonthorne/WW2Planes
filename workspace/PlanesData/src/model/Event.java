package model;

import java.util.ArrayList;
import java.util.List;

public final class Event {
	
	private final String name; //name of event
	/////private final int maxTurns; //number of turns //////////////////////??????????
	private final List<Period>periods; //list of periods covered
	private final List<AirForce>airForces; //list of air forces involved
	
	//constructor:
	public Event(String name, List<Period>periods, List<AirForce>airForces) {
		this.name = name;
		/////////this.maxTurns = setMaxTurns(periods.size());
		this.periods = new ArrayList<Period>(periods);
		this.airForces = new ArrayList<AirForce>(airForces);
	}
	
	
	//get event name:
	public String getName() { return name; }
	
	//get start period:
	/*public Period getStartPeriod() {
		return periods.get(0);//////////startPeriod; ///////////new Period(startPeriod.getBlock(), startPeriod.getYear()); //+++++++++++++++too strong??????
	}*/
	
	//get end period:
	/*public Period getEndPeriod() {
		return periods.get(periods.size()-1);//////////endPeriod; /////////new Period(endPeriod.getBlock(), endPeriod.getYear()); //+++++++++++++++too strong??????
	}*/
	
	//get max turns:
	///////////public int getMaxTurns() { return maxTurns; }
	
	//get periods:
	public List<Period> getPeriods() { return new ArrayList<Period>(periods); }
		
	//get event air forces:
	public List<AirForce> getAirForces() { return new ArrayList<AirForce>(airForces); }
	
	@Override
	public String toString() {
		return "Event [name=" + name + ", periods=" + periods + ", airForces=" + airForces + "]";
	}
	

}