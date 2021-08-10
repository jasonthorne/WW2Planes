package data;

import model.AirForce;
import model.Event;

/** for hash map keys holding an event and air force */
/** https://stackoverflow.com/questions/14677993/how-to-create-a-hashmap-with-two-keys-key-pair-value */

public class EventAirForceKey {
	
	private final Event event;
	private final AirForce airForce;
	
	//constructor:
	EventAirForceKey(Event event, AirForce airForce){
		this.event = event;
		this.airForce = airForce;
	}

	//for unique HashMap insertion:
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airForce == null) ? 0 : airForce.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		return result;
	}

	//for unique HashMap insertion:
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventAirForceKey other = (EventAirForceKey) obj;
		if (airForce == null) {
			if (other.airForce != null)
				return false;
		} else if (!airForce.equals(other.airForce))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		return true;
	}
}
