import java.sql.*;

public class TestingConnection {
    public static void main(String[] args) {
        String dbUrl = System.getenv("HP_JDBC_URL");
        String dbUser = System.getenv("HP_JDBC_USER");
        String dbPassword = System.getenv("HP_JDBC_PW");

        try {
            if (dbUrl == null || dbUser == null || dbPassword == null) {
                System.out.println("Environment variables not set.");
                return;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            if (conn != null) {
                System.out.println("Successfully connected to the database!");
                conn.close();
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        }
    }
}