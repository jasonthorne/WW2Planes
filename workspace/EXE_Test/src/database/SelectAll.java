package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Event;

public interface SelectAll {
	
	static List<Event> selectEvents() {
		
		
		List<Event>events = new ArrayList<Event>();
		
		try (Connection connection = ConnectDB.getConnection();) { //connect to DB
			
			CallableStatement callableStatement = connection.prepareCall("{CALL select_all(?)}"); //create statement
			callableStatement.setString(1, "events"); //set input with table name
			callableStatement.execute(); //execute statement
			ResultSet resultSet = callableStatement.getResultSet(); //get result set 
			
			//print column data from result set:
			while(resultSet.next()) {
				for(int i=1,j=resultSet.getMetaData().getColumnCount();i<=j;i++) {
					System.out.print("[" + resultSet.getString(i) + "] ");
					events.add(new Event( resultSet.getString(i)));
				}
				
				
			}
			
		 }catch(Exception e) { e.printStackTrace(); }
		
		return events;
	}
}