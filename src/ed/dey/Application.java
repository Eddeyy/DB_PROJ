package ed.dey;
import java.lang.invoke.ConstantCallSite;
import java.sql.*;
import java.text.ParseException;
import java.util.*;

public class Application {
    public boolean returning = true;
    public int columnAmount;
    LoginManager logman = new LoginManager();
    Scanner sc = new Scanner(System.in);
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
        System.out.println("1. Complaints menu");
        System.out.println("2. Display View");
        System.out.println("3. Other activity");
        System.out.println("(Type 'Logout' or 'Quit' to do accordingly)\n");
        System.out.println("Type the number of the activity that you want to do:");
        Scanner sc = new Scanner(System.in);
        displayCheck = sc.nextLine();
        displayCheck= displayCheck.toUpperCase();
        clear_console();
        switch (displayCheck) {
            case "1":
                compMenu();
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
                return false;
            case "QUIT":
                exit_success();
            default:
                System.err.println("I can't understand you, please retry");
                System.out.print("\033[H\033[2J");
                System.out.flush();
                break;
        }
        return true;
    }

    boolean compMenu(){

        clear_console();

        String comp_menu =
                """
                        =======[VAULT COMPLAINTS]=======
                        Type "check" or "check my", to list complaints.
                        Type "add" to proceed to complaint add screen.
                        Type "exit" to return to main menu.

                        Type "help" anytime to print this message again
                        """;

        String command;
        String query;

        System.out.println(comp_menu);
        while(true) {

            System.out.print("COMPLAINTS>");
            command = sc.nextLine();
            command = command.toUpperCase();

            if (command.contains("EXIT")) {
                return false;
            }

            if(command.equals("HELP"))
                System.out.println(comp_menu);

            if(command.isEmpty()){
                System.out.println("Please type something");
                continue;
            }

            String where = "";
            String tail = "";

            if(command.contains("CHECK")){
                if(command.equals("CHECK MY")) {
                    where = " WHERE";
                    tail = " COMP_AUTHOR = " + logman.u_id;
                }

                System.out.println("\n1.All\n2.Approved\n3.Not approved\n4.Pending\n0.Exit");
                String op = sc.nextLine();
                    switch(op) {
                        case "1":
                            query = "SELECT COMP_ID AS \"ID\", COMP_AUTHOR AS \"AUTHOR\", COMP_SUBJ AS \"SUBJECT\", DESCR AS \"DESCRIPTION\" FROM " +
                            " COMPLAINTS"+ where + tail;
                            break;
                        case "2":
                            query = "SELECT COMP_ID AS \"ID\", COMP_AUTHOR AS \"AUTHOR\", COMP_SUBJ AS \"SUBJECT\", DESCR AS \"DESCRIPTION\" FROM " +
                                    " V_OVERSEER.APPROVED_COMPLAINTS"+ where + tail;
                            break;
                        case "3":
                            query = "SELECT COMP_ID AS \"ID\", COMP_AUTHOR AS \"AUTHOR\", COMP_SUBJ AS \"SUBJECT\", DESCR AS \"DESCRIPTION\" FROM " +
                                    " NOT_APPROVED_COMPLAINTS"+ where + tail;
                            break;
                        case "4":
                            where = " WHERE";
                            if(tail!="")
                                tail += " AND";
                            query = "SELECT COMP_ID AS \"ID\", COMP_AUTHOR AS \"AUTHOR\", COMP_SUBJ AS \"SUBJECT\", DESCR AS \"DESCRIPTION\" FROM " +
                                    " COMPLAINTS"+ where + tail + " VER_STATUS = 'pending'";
                            break;
                        case "0":
                            continue;
                        default:
                            System.out.println("\nNothing selected");
                            continue;
                    }
                try{
                    Statement stmt = logman.con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                            DBTablePrinter.printResultSet(rs, 128);
                }catch(SQLException e)
                {
                    e.printStackTrace();
                }
                }
                if(command.equals("ADD"))
                {
                    String subj;
                    StringJoiner desc = new StringJoiner(" ");
                    String buff = "";

                    System.out.print("ENTER COMPLAINT SUBJECT>");
                    subj = sc.nextLine();

                    System.out.print("ENTER COMPLAINT DESCRIPTION (type :q to commit)>");

                    while(!Objects.equals(buff = sc.next(), ":q")){
                        desc.add(buff);
                    }

                    String decision = "";
                    System.out.println("SUBJECT : " + subj);
                    System.out.println("DESCRIPTION : " + desc);

                    while(!decision.equals("y")) {
                        System.out.print("\n\nConfirm? [y/n] : ");
                        decision = sc.nextLine();

                        if(decision.equals("n"))
                            break;
                        else if(decision.equals("y"))
                            try {
                                addComp(subj, desc.toString());
                            }catch(SQLException e){
                                printSQLException(e);
                            }
                    }
                }

            }
        }


    void addComp(String subj, String desc) throws SQLException{
        Statement stmt = logman.con.createStatement();
        try {
            stmt.execute("SAVEPOINT COMP_INSERTION");
            String insertion = "INSERT INTO COMPLAINTS (COMP_AUTHOR, COMP_SUBJ, DESCR) VALUES (" + logman.u_id + " ,'" + subj + "' ,'" + desc + "')";
            stmt.execute(insertion);
            System.out.println("Successfuly added complaint!");
            stmt.execute("COMMIT");

        } catch (SQLException e) {
            stmt.executeQuery("ROLLBACK TO COMP_INSERTION");
            e.printStackTrace();
        }
    }

    void printQueryResult(String query){
        try{
            Statement stmt = logman.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            DBTablePrinter.printResultSet(rs);
        }catch(SQLException e)
        {
            printSQLException(e);
        }
    }

    public static void exit_success(){
        System.out.println("\nExiting Vault's database\n\n\t Have a splendid day ;)");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        System.exit(0);
    }

    public static void clear_console(){
        try{
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system

            if(operatingSystem.contains("Windows")){
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}


