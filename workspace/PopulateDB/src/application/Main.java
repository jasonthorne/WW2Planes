package application;

import database.InsertAirForceData;
import database.InsertEventData;
import database.SelectAll;
import table.Table;

public class Main {

	public static void main(String[] args) {
		
		//add data to database:
		//InsertAirForceData.insert(); //insert air force data
		//InsertEventData.insert(); //insert event data
		
		//pull data from database:
		SelectAll.select(Table.PLANES);
		
		
	}

}
