package application;

import database.InsertAirForceData;
import database.InsertEventData;
import database.SelectAll;
import table.Table;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("adding data to database");
		InsertAirForceData.insert(); //insert air force data
		InsertEventData.insert(); //insert event data
		
		//pull data from database:
		System.out.println("\ndata added:");
		SelectAll.select(Table.YEARS);
		SelectAll.select(Table.PERIODS);
		SelectAll.select(Table.EVENTS);
		SelectAll.select(Table.AIRFORCES);
		SelectAll.select(Table.PLANES);
		SelectAll.select(Table.AIRFORCE_PLANES);
		SelectAll.select(Table.EVENT_PERIODS);
		SelectAll.select(Table.EVENT_AIRFORCES);
		SelectAll.select(Table.PLANE_AVAILABILITIES);
	}
	
}