package ed.dey;
import java.lang.invoke.ConstantCallSite;
import java.sql.*;
import java.text.ParseException;
import java.util.*;

public class Application {
    public boolean returning = true;
    public int columnAmount;
    LoginManager logman = new LoginManager();
    public static ArrayList<String> columnName = new ArrayList<>();
    public static ArrayList<String>  dataType = new ArrayList<>();
    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                if (true) {
                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " + ((SQLException)e).getSQLState());
                    System.err.println("Error Code: " + ((SQLException)e).getErrorCode());
                    System.err.println("Message: " + e.getMessage());
                    Throwable t = ex.getCause();
                    while (t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
    }

    public void run() {
            while(true){
                try {
                    System.out.println();
                    while (logman.login()) {
                            while (displayMenu(logman.con));
                    }
                } catch (ParseException | SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
    }

    public static void findColumns (Connection con, String tableName) throws SQLException{
        String query =
                "SELECT column_name, data_type " +
                        "FROM USER_TAB_COLUMNS " +
                        "WHERE table_name = '" + tableName.toUpperCase() + "'";
        int columnAmount = 0;
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                columnName.add(rs.getString("Column_Name".toUpperCase()));
                dataType.add(rs.getString("Data_Type".toUpperCase()));
                System.out.println(
                        columnName.get(columnAmount) + ", " + dataType.get(columnAmount));
                columnAmount++;
            }
            if(rs.equals(0)) {
                System.out.println("\t No records returned.\n");
            }
        }
        catch(SQLException e){
                printSQLException(e);
            }
    }



    public static void viewTable(Connection con, String tableName) throws SQLException {
        Statement stmt = null;
        String query = "SELECT * FROM "+ tableName;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String coffeeName = rs.getString("");
                int supplierID = rs.getInt("SUP_ID");
                float price = rs.getFloat("PRICE");
                int sales = rs.getInt("SALES");
                int total = rs.getInt("TOTAL");
                System.out.println(
                        coffeeName + ", " + supplierID + ", " + price + ", " + sales + ", " + total);
            }

        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }




    public boolean displayMenu(Connection con){
        String displayCheck;
        System.out.println("1. Display Table");
        System.out.println("2. Display View");
        System.out.println("3. Other activity");
        System.out.println("(Type 'Logout' or 'Quit' to do accordingly)\n");
        System.out.println("Type the number of the activity that you want to do:");
        Scanner sc = new Scanner(System.in);
        displayCheck = sc.nextLine();
        displayCheck= displayCheck.toUpperCase();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        switch (displayCheck) {
            case "1":
                //display table
                System.out.println("Display Table");

                String tabName = sc.nextLine();
                //tabName = tabName.toUpperCase();

                DBTablePrinter.printTable(con, tabName);
                break;
            case "2":
                //display view
                System.out.println("Display view");
                break;
            case "3":
                //other activity
                System.out.println("Other activity");
                System.out.println("Enter table: ");
                String tableName = sc.nextLine();

                try {
                    findColumns(con,tableName);
                } catch (SQLException e) {
                    printSQLException(e);
                }

                break;
            case "LOGOUT":
                System.out.println("Exit to logging");         //exit to login
                return false;
            case "QUIT":
                System.out.println("Exiting the base");
                exit_success();
            default:
                System.err.println("I can't understand you, please retry");
                System.out.print("\033[H\033[2J");
                System.out.flush();
                break;
        }
        return true;
    }


    public static void exit_success(){
        System.out.println("\nExiting Vault's database\n\n\t Have a splendid day ;)");
        System.exit(0);
    }

}
