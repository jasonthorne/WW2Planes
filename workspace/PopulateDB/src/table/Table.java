package table;

public enum Table {
	
	//database tables:
	YEARS("years"),
	PERIODS("periods"),
	EVENTS("events"),
	AIRFORCES("airforces"),
	PLANES("planes"),
	IMAGES("images"),
	AIRFORCE_IMAGES("airforce_images"),
	AIRFORCE_PLANES("airforce_planes"),
	EVENT_PERIODS("event_periods"),
	EVENT_AIRFORCES("event_airforces"),
	PLANE_AVAILABILITIES("plane_availabilities");
	
	private final String table; //name of chosen table
	private Table(String table) { this.table = table; } //constructor sets name of table
	@Override public String toString() { return table; } //return chosen table
}