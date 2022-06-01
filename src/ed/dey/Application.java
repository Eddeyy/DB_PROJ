package ed.dey;
import java.lang.invoke.ConstantCallSite;
import java.sql.*;
import java.util.*;

public class Application {
    public boolean returning = true;
    public int columnAmount;
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

    public static boolean checkingName(Connection con, String name, String surname, int id) throws SQLException {
        String query =
                "SELECT " +
                "dweller_ID, name, surname " +
                "FROM V_OVERSEER.DWELLERS" +
                " WHERE dweller_ID = " + id + "";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
                rs.next();
                int idCheck = rs.getInt("dweller_ID");
                String nameCheck = rs.getString("name");
                String surnameCheck = rs.getString("surname");
                System.out.println(idCheck + ", " + nameCheck + ", " + surnameCheck);
                System.out.println(id + ", " + name + ", " + surname);
                if(idCheck == id && nameCheck.equals(name) && surnameCheck.equals(surname)){
                    System.out.println("Brawo debilu");
                    return true;
                }
                else {
                    System.out.println("Jestes chujem");
                    return false;
                }


        } catch (SQLException e) {
            System.out.println("Oops, chuju");
            printSQLException(e);
        }
        return false;
    }
    public boolean login(Connection con) throws SQLException {
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = sc.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("Enter surname: ");
        String surname = sc.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("Enter ID: ");
        int id = sc.nextInt();

        System.out.print("\033[H\033[2J");
        System.out.flush();
        try {
            if(checkingName(con, name, surname, id)) return true;
            else{
                System.out.println("Try logging again or leave you cunt ");
                System.out.println("1. Login again");
                System.out.println("2. Exit");
                int ask = sc.nextInt();
                switch (ask){
                    case 1:
                        returning = false;
                        return true;
                    case 2:
                        return false;
                    default:
                        System.out.println("Try logging again or leave you cunt ");
                        return true;
                }

            }
        }
        catch (SQLException e) {
             System.out.println("Oops, error");
             return false;
        }

    }

    public boolean displayMenu(Connection con){
        int displayCheck;
        System.out.println("1. Display Table");
        System.out.println("2. Display View");
        System.out.println("3. Other activity");
        System.out.println("0. Exit to login");
        System.out.println("99. Exit the App");
        System.out.println("Input number next to the activity that you want to do:");
        Scanner sc = new Scanner(System.in);
        displayCheck = sc.nextInt();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        switch (displayCheck) {
            case 1:
                //display table
                System.out.println("Display Table");
                break;
            case 2:
                //display view
                System.out.println("Display view");
                break;
            case 3:
                //other activity
                System.out.println("Other activity");
                sc.nextLine();
                System.out.println("Enter table: ");
                String tableName = sc.nextLine();

                try {
                    findColumns(con,tableName);
                } catch (SQLException e) {
                    printSQLException(e);
                }
                break;
            case 0:
                System.out.println("Exit to logging");         //exit to login
                return false;
            case 99:
                System.out.println("Exit the base");
                returning = true;
                return false;
            default:
                System.out.println("Bad number you idiot");
                System.out.print("\033[H\033[2J");
                System.out.flush();
                break;
        }
        return true;
    }


}
