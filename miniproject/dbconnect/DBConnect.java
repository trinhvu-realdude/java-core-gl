package greatlearning.miniproject.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class: DBConnect
 *
 * Connect with local database
 *
 * Note for examiner: Please use the default sql file I attached and your own username, password
 */
public class DBConnect {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/surabi_billing_system";
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
