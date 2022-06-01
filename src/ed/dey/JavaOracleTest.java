package ed.dey;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;


public class JavaOracleTest {
    public static void main(String[] args) {
        String dbURL = "jdbc:oracle:thin:@localhost:1521/xepdb1";
        String username = "V_OVERSEER";
        String password = "ovr";


        try {
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected to Oracle database server");
            Application Ap = new Application();
            while (Ap.login(connection)) {
                if(Ap.returning) {
                    Ap.returning = false;
                    while (Ap.displayMenu(connection)) {
                    }
                    if (Ap.returning) break;
                }
                Ap.returning = true;
            }
        }
        catch (SQLException e) {
            System.out.println("Oops, error");
            e.printStackTrace();
        }
    }

}
