import java.sql.*;

public class DB {
    public static Connection conn;

    public static void Connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db"); // TODO: Pull name from .env
            
            // Creates tables if they do not exist

            // Inserts sample test data on first init

        } catch ( ClassNotFoundException | SQLException e ) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1); // 1 indicates error
        }

        System.out.println("Opened database successfully");
    }
}