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

    public boolean displayEngineerTableMenu(Connection con) {
        String displayCheck;
        System.out.println("1. WATER_PUMPS");
        System.out.println("2. GENERATORS");
        System.out.println("3. SECTORS");
        System.out.println("4. DAMAGED_GENERATORS");
        System.out.println("5. DAMAGED_PUMPS");
        System.out.println("6. SECTOR_STATUS");
        System.out.println("7. TOOLS");
        System.out.println("8. BEDS");
        System.out.println("0. Exit to menu");
        Scanner sc = new Scanner(System.in);
        displayCheck = sc.nextLine();
        displayCheck = displayCheck.toUpperCase();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        switch (displayCheck) {
            case "1":
                DBTablePrinter.printTable(con, "WATER_PUMPS");
                break;
            case "2":
                DBTablePrinter.printTable(con, "GENERATORS");
                break;
            case "3":
                DBTablePrinter.printTable(con, "SECTORS");
                break;
            case "4":
                DBTablePrinter.printTable(con, "DAMAGED_GENERATORS");
                break;
            case "5":
                DBTablePrinter.printTable(con, "DAMAGED_PUMPS");
                break;
            case "6":
                DBTablePrinter.printTable(con, "SECTOR_STATUS");
                break;
            case "7":
                DBTablePrinter.printTable(con, "TOOLS");
                break;
            case "8":
                DBTablePrinter.printTable(con, "BEDS");
                break;
            case"0":
                return true;
            default:
                return false;
        }
        return true;
    }
    public boolean displayGuardTableMenu(Connection con) {
        String displayCheck;
        System.out.println("1. ARMORY");
        System.out.println("2. ITEMS");
        System.out.println("3. EXPEDITION_LOGS");
        System.out.println("4. EXP_MEMBER_DATA");
        System.out.println("0. Exit to menu");
        Scanner sc = new Scanner(System.in);
        displayCheck = sc.nextLine();
        displayCheck = displayCheck.toUpperCase();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        switch (displayCheck) {
            case "1":
                DBTablePrinter.printTable(con, "ARMORY");
                break;
            case "2":
                DBTablePrinter.printTable(con, "ITEMS");
                break;
            case "3":
                DBTablePrinter.printTable(con, "EXPEDITION_LOGS");
                break;
            case "4":
                DBTablePrinter.printTable(con, "EXP_MEMBER_DATA");
                break;
            case"0":
                return true;
            default:
                return false;
        }
        return true;
    }

    public boolean displayMedicTableMenu(Connection con) {
        String displayCheck;
        System.out.println("1. MEDICAL");
        System.out.println("2. HOSPITAL");
        System.out.println("0. Exit to menu");
        Scanner sc = new Scanner(System.in);
        displayCheck = sc.nextLine();
        displayCheck = displayCheck.toUpperCase();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        switch (displayCheck) {
            case "1":
                DBTablePrinter.printTable(con, "MEDICAL");
                break;
            case "2":
                DBTablePrinter.printTable(con, "HOSPITAL");
                break;
            case"0":
                return true;
            default:
                return false;
        }
        return true;
    }

    public boolean displayDwellerTableMenu(Connection con) {
        String displayCheck;
        System.out.println("1. COMPLAINTS");
        System.out.println("2. ITEMS");
        System.out.println("3. JOBS");
        System.out.println("4. MISSING");
        System.out.println("5. EMPLOYED");
        System.out.println("6. EXPLORERS");
        System.out.println("7. ROOMS_PLACEMENT");
        System.out.println("8. LOGIN_DWELLERS_LIST");
        System.out.println("0. Exit to menu");
        Scanner sc = new Scanner(System.in);
        displayCheck = sc.nextLine();
        displayCheck = displayCheck.toUpperCase();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        switch (displayCheck) {
            case "1":
                DBTablePrinter.printTable(con, "COMPLAINTS");
                break;
            case "2":

                System.out.println("Choose which type of items you want to display:");
                System.out.println("1. ALL ITEMS");
                System.out.println("2. FOOD_ITEMS");
                System.out.println("3. AVAILABLE_ITEMS");
                String foodDisplay = sc.nextLine();
                switch (foodDisplay) {
                    case "1":
                        DBTablePrinter.printTable(con, "ITEMS");
                        break;
                    case "2":
                        DBTablePrinter.printTable(con, "FOOD_ITEMS");
                        break;
                    case "3":
                        DBTablePrinter.printTable(con, "AVAILABLE_ITEMS");
                        break;
                    default:
                        return false;
                }
                break;
            case "3":
                DBTablePrinter.printTable(con, "JOBS");
                break;
            case "4":
                DBTablePrinter.printTable(con, "MISSING");
                break;
            case "5":
                DBTablePrinter.printTable(con, "EMPLOYED");
                break;
            case "6":
                DBTablePrinter.printTable(con, "EXPLORERS");
                break;
            case "7":
                DBTablePrinter.printTable(con, "ROOMS_PLACEMENT");
                break;
            case "8":
                DBTablePrinter.printTable(con, "LOGIN_DWELLERS_LIST");
                break;
            case"0":
                return true;
            default:
                return false;
        }
        return true;
    }

    public boolean displayManagerTableMenu(Connection con) {
        String displayCheck;
        System.out.println("1. BEDS");
        System.out.println("2. DWELLERS");
        System.out.println("3. EXP_MEMBER_DATA");
        System.out.println("4. EXPEDITION_LOGS");
        System.out.println("5. GENERATORS");
        System.out.println("6. ITEMS");
        System.out.println("7. JOBS");
        System.out.println("8. ROOMS");
        System.out.println("9. SECTORS");
        System.out.println("10. WATER_PUMPS");
        System.out.println("11. COMPLAINTS");
        System.out.println("12. E_DWLR_STATUS");
        System.out.println("13. E_EXP_STATUS");
        System.out.println("14. E_GEN_STATUS");
        System.out.println("15. E_VER_STATUS");
        System.out.println("16. LIVING_QUARTERS");
        System.out.println("0. Exit to menu");
        Scanner sc = new Scanner(System.in);
        displayCheck = sc.nextLine();
        displayCheck = displayCheck.toUpperCase();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        switch (displayCheck) {
            case "1":
                DBTablePrinter.printTable(con, "BEDS");
                break;
            case "2":
                DBTablePrinter.printTable(con, "DWELLERS");
                break;
            case "3":
                DBTablePrinter.printTable(con, "EXP_MEMBER_DATA");
                break;
            case "4":
                DBTablePrinter.printTable(con, "EXPEDITION_LOGS");
                break;
            case "5":
                DBTablePrinter.printTable(con, "GENERATORS");
                break;
            case "6":
                DBTablePrinter.printTable(con, "ITEMS");
                break;
            case "7":
                DBTablePrinter.printTable(con, "JOBS");
                break;
            case "8":
                DBTablePrinter.printTable(con, "ROOMS");
                break;
            case "9":
                DBTablePrinter.printTable(con, "SECTORS");
                break;
            case "10":
                DBTablePrinter.printTable(con, "WATER_PUMPS");
                break;
            case "11":
                DBTablePrinter.printTable(con, "COMPLAINTS");
                break;
            case "12":
                DBTablePrinter.printTable(con, "E_DWLR_STATUS");
                break;
            case "13":
                DBTablePrinter.printTable(con, "E_EXP_STATUS");
                break;
            case "14":
                DBTablePrinter.printTable(con, "E_GEN_STATUS");
                break;
            case "15":
                DBTablePrinter.printTable(con, "E_VER_STATUS");
                break;
            case "16":
                DBTablePrinter.printTable(con, "LIVING_QUARTERS");
                break;
            case"0":
                return true;
            default:
                return false;
        }
        return true;
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
                switch (logman.username){
                    case "DWELLER":
                        displayDwellerTableMenu(con);
                        break;
                    case "ENGINEER":
                        displayEngineerTableMenu(con);
                        break;
                    case "GUARD":
                        displayGuardTableMenu(con);
                        break;
                    case "MANAGER":
                        displayManagerTableMenu(con);
                        break;
                    case "MEDIC":
                        displayMedicTableMenu(con);
                        break;
                    default:
                        break;
                }
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
    public void insertDwellerMenu() {
        System.out.println("1. ITEMS");
        System.out.println("0. Exit to Menu");
    }
    public void insertEngineerMenu() {
        System.out.println("1. WATER_PUMPS");
        System.out.println("2. GENERATORS");
        System.out.println("3. SECTORS");
    }

    public void insertMedicMenu() {
        System.out.println("1. BEDS");
    }
    public void insertManagerMenu() {
        System.out.println("1. BEDS");
        System.out.println("2. DWELLERS");
        System.out.println("3. EXP_MEMBER_DATA");
        System.out.println("4. EXPEDITION_LOGS");
        System.out.println("5. GENERATORS");
        System.out.println("6. ITEMS");
        System.out.println("7. JOBS");
        System.out.println("8. ROOMS");
        System.out.println("9. SECTORS");
        System.out.println("10. WATER_PUMPS");
    }
    public void insertMenu(){

    }

    boolean compMenu(){

        clear_console();

        String comp_menu =

                        "=======[VAULT COMPLAINTS]======= \n" +
                        "Type 'check' or 'check my', to list complaints. \n" +
                        "Type 'add' to proceed to complaint add screen. \n" +
                        "Type 'exit' to return to main menu.\n" +
                        "                                            \n" +
                        "Type 'help' anytime to print this message again \n"
                        ;

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

    boolean verScreen(){
        String ver_menu =

                """
                        ====[VERIFICATION SCREEN]====
                        Type 'check' followed by [beds/complaints/generators/items/pumps] to list the data.
                        Type 'verify' or 'deny' followed by the [tablename] and [ID] to modify the data.
                        Type 'verify all' and [tablename] to verify all the pending data in said table.
                        
                        Type 'help' anytime to print this message again.
                        
                        
                        """;
        String command;
        String query = "NULL";
        String[] parse;

        System.out.println(ver_menu);

        while(true){
            System.out.print("VERIFICATION>");
            command = sc.nextLine();
            command = command.toUpperCase();

            if (command.contains("EXIT")) {
                return false;
            }

            if(command.equals("HELP"))
                System.out.println(ver_menu);

            if(command.isEmpty()){
                System.out.println("Please type something");
                continue;
            }

            String tablename;
            int id;

            parse = command.split(" ", 2);

            if(parse[0].equals("CHECK")){
                if(parse.length == 2) {
                    switch(parse[1]) {
                        case"BEDS":
                            tablename = "BEDS";
                            break;
                        case"COMPLAINTS":
                            tablename = "COMPLAINTS";
                            break;
                        case"GENERATORS":
                            tablename = "GENERATORS";
                            break;
                        case"ITEMS":
                            tablename = "ITEMS";
                            break;
                        case"PUMPS":
                            tablename = "PUMPS";
                            break;
                        default:
                            continue;
                    }
                    query = "SELECT FROM " + tablename + " WHERE VER_STATUS IS NOT 'approved'";
                }
                else {
                    System.out.println("Choose a data to check.");
                    continue;
                }
            }

            if(command.equals("VERIFY ALL"))
            {
                query = "UPDATE BEDS, COMPLAINTS, GENERATORS, ITEMS, PUMPS SET VER_STATUS = 'approved' WHERE VER_STATUS IS NOT 'approved'";
            }

            if(parse[0].equals("VERIFY") && parse.length > 2){
                    switch(parse[1]) {
                        case"BEDS":
                            tablename = "BEDS";
                            break;
                        case"COMPLAINTS":
                            tablename = "COMPLAINTS";
                            break;
                        case"GENERATORS":
                            tablename = "GENERATORS";
                            break;
                        case"ITEMS":
                            tablename = "ITEMS";
                            break;
                        case"PUMPS":
                            tablename = "PUMPS";
                            break;
                        default:
                            continue;
                    }

                try{
                    id = Integer.parseInt(parse[2]);
                }catch(NumberFormatException e) {
                    System.out.println("Please provide viable id number.");
                    continue;
                }
                    query = "EXECUTE VERIFICATION("+ tablename +" ," + id + " , 'approved')";
        }
        try(Statement stmt = logman.con.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
            DBTablePrinter.printResultSet(rs);

            System.out.println("SUCCESS!");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }}

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


