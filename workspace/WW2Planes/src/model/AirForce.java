package model;

import java.util.ArrayList;
import java.util.List;

public final class AirForce {
	
	private final String name; //name of air force
	private final List<Plane>planes; //planes available to air force
	
	//constructor:
	public AirForce(String name, List<Plane>planes){
		this.name = name;
		this.planes = new ArrayList<Plane>(planes);
	}
	
	public String getAirForceName() { return name; } //get air force name

	public List<Plane> getAirForcePlanes() { //get air force planes
		return new ArrayList<Plane>(planes);
	} 
	
	@Override
	public String toString() {
		return "AirForce [name=" + name + ", planes=" + planes + "]";
	}

}