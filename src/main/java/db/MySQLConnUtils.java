package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnUtils {

    private static Connection connection;
 
    public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            // Note: Change the connection parameters accordingly.
            String hostName = "localhost";
            String dbName = "companies12341024";
            String userName = "root";
//            String password = "1024";
            String password = "root";
            return getMySQLConnection(hostName, dbName, userName, password);
        }
        return connection;
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
            String userName, String password) throws SQLException, ClassNotFoundException {
        // URL Connection for MySQL
        // Example: jdbc:mysql://localhost:3306/simplehr
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(connectionURL, userName, password);
    }
}