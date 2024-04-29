package Kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static ConnectionManager instance;
    private static final String URL = "YOUR_URL";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private ConnectionManager() {
    }

    //public static method to get singleton instance
    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    // Connect to db
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}