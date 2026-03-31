import java.sql.*;

public class DBConnection {
    public static Connection getConnection() {
        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shuvam_jdbc","root","mastii@2025");
        } catch (Exception e) {
            System.out.println("Connection Error: " + e);
        }

        return con;
    }
}

