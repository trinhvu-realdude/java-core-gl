package greatlearning.week4.gradedproject.dbconnect;

import java.sql.*;

/**
 * Class: DBConnect
 *
 * Connect with local database
 *
 * Note for examiner: Please use the default sql file I attached and your own username, password
 */
public class DBConnect {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/magic_of_books";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    /**
     * Function: getConnection
     * @return connection
     */
    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
