import java.util.Scanner;

public class Login {
    // return the role in string
    public String AskLogin(Scanner in) {
        System.out.println("Enter your username:");
        String username = in.nextLine();
        System.out.println("Enter your password:");
        String password = in.nextLine();
        // JDBC ->
        String role = "manager";
        // check from JDBC if the user exists and return the role
        // based on the role create the landing page
        return role;
    }


    public void AskLogout() {
        System.out.println("Do you want to logout?");
    }
}
