package Wolf;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	
	private static String URL;
	private static String username;
	private static String password;

	public static Connection getConnection(String URL1,String username1, String password1) throws SQLException{
		// TODO Auto-generated method stub
		
		URL = URL1;
		username = username1;
		password = password1;
		
		Connection conn = null;
			
		conn = DriverManager.getConnection(URL, username, password);
		
		return conn;

	}
	
	public static Connection getConnection() throws SQLException{
		// TODO Auto-generated method stub
		Connection conn = null;
			
		conn = DriverManager.getConnection(URL, username, password);
		
		return conn;

	}

}