package database;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

final class ConnectDB {
	
	private Connection connection = null; //holds an established connection
	
	//constructor connects to DB using properties file:
	private ConnectDB() { 
		
		try {
			//load properties:
			Properties properties = new Properties();
			///////ConnectDB.class.getResource("./configs/mysql/db_configs/db_configs.properties") ;
			//properties.load(new FileInputStream("./configs/mysql/db_configs/db_configs.properties"));
			//URL url = ConnectDB.class.getResource("./configs/mysql/db_configs/db_configs.properties");
			
			File file = new File("./configs/mysql/db_configs/db_configs.properties");
			properties.load(new FileInputStream(file));
			
			//get connection using properties: 
			this.connection = DriverManager.getConnection(
					properties.getProperty("db_url"),
					properties.getProperty("db_user"), 	
					properties.getProperty("db_password"));
			
		}catch(Exception e) { e.printStackTrace(); }
	}
	
	//return a newly established connection:
	static Connection getConnection() { return new ConnectDB().connection; }
}