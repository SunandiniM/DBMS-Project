import java.sql.*;
import java.util.Scanner;

public class Login {
    // return the role in string
    public LoginContext AskLogin(Scanner in) {
        LoginContext loginContext = new LoginContext();
        System.out.println("Enter your username:");
        String username = in.nextLine();
        System.out.println("Enter your password:");
        String password = in.nextLine();
        System.out.println("Enter your service center id:");
        String centerId = in.nextLine();
        if (username.equals("admin") && password.equals("admin")) {
            loginContext.role = "ADMIN";
            return loginContext;
        }

        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql1 = "SELECT * FROM CUSTOMER WHERE CID=" + username + " AND SCID=" + centerId + " AND LNAME='" + password + "'";
            ResultSet rs1 = stmt.executeQuery(sql1);
            if (rs1 != null && rs1.isBeforeFirst()) {
                loginContext.SCID = centerId;
                loginContext.ID = username;
                loginContext.role = "CUSTOMER";
            } else {
                String sql2 = "SELECT * FROM EMPLOYEES WHERE EMPID=" + username + " AND SCID=" + centerId + " AND LNAME='" + password + "'";
                ResultSet rs2 = stmt.executeQuery(sql2);
                if (rs2 != null && rs2.isBeforeFirst()) {
                    loginContext.SCID = centerId;
                    loginContext.ID = username;
                    while (rs2.next()) {
                        loginContext.role = rs2.getString("EROLE");
                    }
                    System.out.println("Role : " + loginContext.role);
                } else {
                    System.out.println("Failed to Login. Please enter valid details.");
//                    dbConn.closeConnection();
                    return null;
                }
            }
//            dbConn.closeConnection();
            System.out.println("Successfully logged in");
        } catch (Exception e) {
            System.out.println("Failed to login");
        }
        // check from JDBC if the user exists and return the role
        // based on the role create the landing page
        return loginContext;
    }

    public void AskLogout() {
        System.out.println("Do you want to logout?");
    }
}
