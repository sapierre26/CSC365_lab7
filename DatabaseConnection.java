import java.sql.*;

public class DatabaseConnection {
    private static final String URL = System.getenv("HP_JDBC_URL");
    private static final String USER = System.getenv("HP_JDBC_USER");
    private static final String PASSWORD = System.getenv("HP_JDBC_PW");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}