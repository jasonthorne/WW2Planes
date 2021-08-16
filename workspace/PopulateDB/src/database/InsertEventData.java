package database;

import java.io.FileReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public interface InsertEventData {
	
	static void insert() {
		
		try (Connection connection = ConnectDB.getConnection();) { //connect to DB
			
			//create statement:
			CallableStatement callableStatement = null; 
			
			//read json file to object reference, using json parser: 
			Object object = new JSONParser().parse(new FileReader("resources/json/db_data/events/events.json"));
			JSONArray events = (JSONArray) object; //cast object to json array of events
			Iterator<JSONObject> eventIterator = events.iterator(); //iterate through events
			
			while (eventIterator.hasNext()) {
				JSONObject event = (JSONObject) eventIterator.next().get("event"); //get event from iterator.next()
				//-------------------------------------------------
				//add event to 'events':
				
				String eventName = (String) event.get("name");  //get name of event
				callableStatement = connection.prepareCall("{CALL insert_event(?)}"); //create statement
				callableStatement.setString(1, eventName); //set input with name
				try {
					callableStatement.execute(); //execute statement
				}catch(Exception e) { e.printStackTrace(); }
				//-------------------------------------------------
				//add event, air force & home advantage status to 'event_airforces':
				
				JSONArray airForces = (JSONArray) event.get("airforces"); //get array of air forces
				Iterator<JSONObject> airForceIterator = airForces.iterator(); //iterate through airForces
				while (airForceIterator.hasNext()) {
					JSONObject airForce = (JSONObject) airForceIterator.next().get("airforce"); //get airForce from iterator.next()
					
					String airForceName = (String) airForce.get("name");  //get name of airForce
					callableStatement = connection.prepareCall("{CALL insert_event_airforce(?,?)}"); //create statement
					callableStatement.setString(1, eventName); //set input with event name
					callableStatement.setString(2, airForceName); //set input with air force name
					try {
						callableStatement.execute(); //execute statement
					}catch(Exception e) { e.printStackTrace(); }
				}
				//-------------------------------------------------
				//add event start & end periods to 'event_periods':
				
				JSONObject startPeriod = (JSONObject) event.get("start_period");  //get event starting period
				String startBlock = (String) startPeriod.get("block");  //get block from starting period
				int startYear = Integer.parseInt((String) startPeriod.get("year")); //get year from starting period
				JSONObject endPeriod = (JSONObject) event.get("end_period"); //get event ending period
				String endBlock = (String) endPeriod.get("block");  //get block from ending period
				int endYear = Integer.parseInt((String) endPeriod.get("year")); //get year from ending period
				callableStatement = connection.prepareCall("{CALL insert_event_period(?,?,?,?,?)}"); //create statement
				callableStatement.setString(1, eventName); //set input with event name
				callableStatement.setString(2, startBlock); //set input with starting block
				callableStatement.setInt(3, startYear); //set input with starting year
				callableStatement.setString(4, endBlock); //set input with ending block
				callableStatement.setInt(5, endYear); //set input with ending year
				try {
					callableStatement.execute(); //execute statement
				}catch(Exception e) { e.printStackTrace(); }
				
			}//eventIterator
		}catch(Exception e) { e.printStackTrace(); }
	}
}
