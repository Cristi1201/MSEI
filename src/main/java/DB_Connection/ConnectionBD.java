package DB_Connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionBD {

    public static Connection getConnection() {
        Connection c = null;
        try {
            String db = "DB_MSEI";
            String user = "root";
            String password = "";
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, user, password);
            if (!c.isClosed())
                System.out.println("Successfuly connected to MySql server");
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        return c;
    }
}

