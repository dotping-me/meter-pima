import java.sql.*;

import db.DB;

import gui.*;

public class Main {
    public static void main(String[] args) {
        try {

            // 1. Connect to database
            // 2. Feed application with data from DB
            DB.Connect();

            // 3. Launch GUI
            javax.swing.SwingUtilities.invokeLater(() -> {
                new AuthFrame().setVisible(true);
            });

        // Handle error(s)
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}