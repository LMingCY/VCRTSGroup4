package MySQL;

import java.sql.*;

public class Driver {
    Connection connection;
    public void connection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("localhost:3306", "leon", "11111111");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
