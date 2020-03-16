package recources;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final Logger log = Logger.getLogger(ConnectionUtil.class);

    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/magazine_shop";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Chervinskaya1997";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            log.error("Can`t connect to DB", e);
        }
        return null;
    }
}
