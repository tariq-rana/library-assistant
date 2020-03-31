package library.assistant.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DbHandler {

    private static Connection conn = null;

    public static Connection getConnection() {
        if(conn == null){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "test", "test");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Error :" + e.getMessage());
            }
        }    
        return conn;
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error :" + e.getMessage());
        }

    }
}
