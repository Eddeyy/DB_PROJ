package ed.dey;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class LoginManager {
    Connection con;
    Scanner sc = new Scanner(System.in);
    int u_id;
    String dbURL = "jdbc:oracle:thin:@localhost:1521/xepdb1";
    String username = "DWELLER";
    String password = "dwlr";

    LoginManager(){
        String welcome =
                """ 
                        ]]> VAULT DB v1.0.0
                        ]]> © W&B VAULTS LLC.
                        
                        Welcome to the vault's database system user interface.
                        
                        """;
        System.out.println(welcome);
        establishConnection();
    }

    void establishConnection()
    {
        try {
            con = DriverManager.getConnection(dbURL, username, password);
            con.setAutoCommit(false);
            System.out.println("Connected to Oracle database server as " + username + " successfully");
        }
            catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void adjustRole(){
        String job_querry = "SELECT JOB_ID FROM LOGIN_DWELLERS_LIST WHERE DWELLER_ID = " + u_id;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(job_querry);
            rs.next();
            int job = Integer.parseInt(rs.getString("JOB_ID"));

            switch(job){
                case 1:
                    con.close();
                    username = "V_OVERSEER";
                    password = "ovr";
                    break;
                case 2:
                    con.close();
                    username = "ENGINEER";
                    password = "eng";
                    break;
                case 3:
                    con.close();
                    username = "MANAGER";
                    password = "mgr";
                    break;
                case 4:
                    con.close();
                    username = "GUARD";
                    password = "grd";
                    break;
                case 6:
                    con.close();
                    username = "MEDIC";
                    password = "mdc";
                    break;
                default:
                    con.close();
                    username = "DWELLER";
                    password = "dwlr";
            }
            establishConnection();
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public boolean login() throws SQLException, ParseException {
        boolean success = false;
        String[] parse;

        System.out.print("Enter your NAME, SURNAME, and ID in a \"N S I\" format: ");

        String command = sc.nextLine();

        if(command.equalsIgnoreCase("QUIT")) {
            Application.exit_success();
        }

        if(command.isEmpty()) {
            throw new ParseException("Please type something.", 0);
        }

        parse = command.split(" ");

        if(parse.length > 3) {
            throw new ParseException("Provided too much information", command.indexOf(" ",command.indexOf(" ",command.indexOf(" "))));
        }

        if(parse.length < 3) {
            throw new ParseException("Provided too little information", command.indexOf(" ",command.indexOf(" ",command.indexOf(" "))));
        }

        try{
            u_id = Integer.parseInt(parse[2]);
        }catch(NumberFormatException e){
            throw new ParseException("Please make sure, the ID is a number", command.lastIndexOf(" "));
        }

        try {
            return checkingName(con, parse[0], parse[1], u_id);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkingName(Connection con, String name, String surname, int id) throws SQLException {
        String query =
                "SELECT * FROM V_OVERSEER.LOGIN_DWELLERS_LIST WHERE NAME = '"+ name +"' AND SURNAME = '" + surname +"'";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int idCheck = rs.getInt("dweller_ID");
            String nameCheck = rs.getString("NAME");
            String surnameCheck = rs.getString("surname");
            if(idCheck == id && nameCheck.equals(name) && surnameCheck.equals(surname)){
                adjustRole();
                return true;
            }
            else {
                return false;
            }

        } catch (SQLException e) {

            if(e.getErrorCode() == 17289)
                System.err.println("No such dweller within Vault.");
            else
                Application.printSQLException(e);

            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
}

