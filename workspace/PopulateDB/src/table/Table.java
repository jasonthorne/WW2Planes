package table;

public enum Table {
	
	//database tables:
	AIRFORCES("airforces"),
	IMAGES("images"),
	EVENTS("events"),
	YEARS("years"),
	PERIODS("periods"),
	PLANES("planes"),
	AIRFORCE_IMAGES("airforce_images"),
	AIRFORCE_PLANES("airforce_planes"),
	EVENT_AIRFORCES("event_airforces"),
	EVENT_PERIODS("event_periods"),
	PLANE_AVAILABILITIES("plane_availabilities");
	
	private final String table; //name of chosen table
	private Table(String table) { this.table = table; } //constructor sets name of table
	@Override public String toString() { return table; } //return chosen table
}