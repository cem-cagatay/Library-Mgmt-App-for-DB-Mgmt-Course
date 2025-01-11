package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static String url = "jdbc:mysql://library-db.cfk044iesems.eu-north-1.rds.amazonaws.com/LibApp";
    private static String user = "admin";
    private static String password = "bHji5tA#$z2";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
