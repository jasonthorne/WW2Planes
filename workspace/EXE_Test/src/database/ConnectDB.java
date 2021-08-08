package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Paths;
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
			
			/*
			URL url = new URL("url");
			InputStream in = url.openStream();
			Reader reader = new InputStreamReader(in, "UTF-8"); // for example
			 
			Properties prop = new Properties();
			try {
			    prop.load(reader);
			} finally {
			    reader.close();
			}
			System.out.println( prop.getProperty("key"));
			*/
		
			///////ConnectDB.class.getResource("./configs/mysql/db_configs/db_configs.properties") ;
			/////properties.load(new FileInputStream("./configs/mysql/db_configs/db_configs.properties"));
			
			//--------------------------------------------------------------------------------
			//URL url = new URL("/db_configs.properties");
			//InputStream in = url.openStream();
			//////////Reader reader = new InputStreamReader(in, "UTF-8"); // for example
			 
			////////////////Properties prop = new Properties();
			
			/*
			try {
				properties.load(reader);
			} finally {
			    reader.close();
			    
			}*/
			properties.load(ConnectDB.class.getResourceAsStream("db_configs.properties"));
			////////////File file = new File("./configs/mysql/db_configs/db_configs.properties");
			////////////////properties.load(new FileInputStream(file));
			/*
			URL url = new URL("url");
			InputStream in = url.openStream();

			Properties prop = new Properties();
			prop.load(in);

			System.out.println( prop.getProperty("key"));
			*/
			////InputStream is = ConnectDB.class.getResourceAsStream("/EXE_Test/configs/mysql/db_configs/db_configs.properties");
			///properties.load(is);
			//File file = Paths.get(url.toURI()).toFile();
			/////////File file = new File("./configs/mysql/db_configs/db_configs.properties");
			////////////properties.load(new FileInputStream(file));
			
			///System.out.println("yo");
			
			//Properties properties = new Properties();
			
			/*try (InputStream propStream = getClass().getResourceAsStream("./configs/mysql/db_configs/db_configs.properties")) {
				properties.load(propStream);
			}*/
			
			//URL url = ClassLoader.getSystemResource("/SelectAll.java");
			//System.out.println(url);
			
			
			//ConnectDB.class.getResourceAsStream("configs/mysql/db_configs/db_configs.properties");
			
			//FileInputStream in = new FileInputStream("./configs/mysql/db_configs/db_configs.properties");
			//properties.load(in);
			//in.close();
			
			//InputStream is = this.getClass().getResourceAsStream("/configs/mysql/db_configs/db_configs.properties");
			
			//properties.load(is);
			
			    
			//InputStream is = getClass().getResourceAsStream("/configs/mysql/db_configs/db_configs.properties");
			//properties.load(
				//	ConnectDB.class.getClassLoader().getResourceAsStream("/db_configs.properties"));
			
			
			
			//properties.load(new FileInputStream("./configs/mysql/db_configs/db_configs.properties"));
			
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