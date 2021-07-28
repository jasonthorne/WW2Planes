package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import table.Table;

public interface SelectAll {
	
	static void select(Table table) {
		
		String tableName = table.toString(); //get name of table
		
		try (Connection connection = ConnectDB.getConnection();) { //connect to DB
			
			CallableStatement callableStatement = connection.prepareCall("{CALL select_all(?)}"); //create statement
			callableStatement.setString(1, tableName); //set input with table name
			callableStatement.execute(); //execute statement
			ResultSet resultSet = callableStatement.getResultSet(); //get result set 
			
			System.out.println("\n"+ tableName + ":");
			
			//print column data from result set:
			while(resultSet.next()) {
				for(int i=1,j=resultSet.getMetaData().getColumnCount();i<=j;i++) {
					System.out.print("[" + resultSet.getString(i).toString() + "] ");}
				System.out.print("\n");
			}
			
		 }catch(Exception e) { e.printStackTrace(); }
	}
}