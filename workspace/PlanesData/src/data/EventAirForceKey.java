package data;

/** for hash map keys holding the name of an event and air force */
/** https://stackoverflow.com/questions/14677993/how-to-create-a-hashmap-with-two-keys-key-pair-value */

public class EventAirForceKey {
	
	private final String eventName;
	private final String airForceName;
	
	//constructor:
	EventAirForceKey(String eventName, String airForceName){
		this.eventName = eventName;
		this.airForceName = airForceName;
	}

	//for unique HashMap insertion:
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airForceName == null) ? 0 : airForceName.hashCode());
		result = prime * result + ((eventName == null) ? 0 : eventName.hashCode());
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
		if (airForceName == null) {
			if (other.airForceName != null)
				return false;
		} else if (!airForceName.equals(other.airForceName))
			return false;
		if (eventName == null) {
			if (other.eventName != null)
				return false;
		} else if (!eventName.equals(other.eventName))
			return false;
		return true;
	}
}
