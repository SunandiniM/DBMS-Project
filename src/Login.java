import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {
    // return the role in string
    public LoginContext AskLogin(Scanner in) {
        LoginContext loginContext = new LoginContext();
        System.out.println("Enter your username:");
        String username = in.nextLine();
        System.out.println("Enter your password:");
        String password = in.nextLine();
        if (username.equals("admin") && password.equals("admin")) {
            loginContext.role = "ADMIN";
            return loginContext;
        }

        ResultSet rs;
        String role = "";


        loginContext.SCID = "1001";
        loginContext.ID = "2001";
        loginContext.role = "MECHANIC";
        // check from JDBC if the user exists and return the role
        // based on the role create the landing page
        return loginContext;
    }




    public void AskLogout() {
        System.out.println("Do you want to logout?");
    }
}
