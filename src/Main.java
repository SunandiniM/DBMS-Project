import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Press 1 to Log In");
        System.out.println("Press 2 to Exit");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        while (option != 2) {
            in = new Scanner(System.in);
            Login loginObj = new Login();
            LoginContext loginContext = loginObj.AskLogin();
            if (loginContext != null) {
                if (loginContext.role.equals("ADMIN")) {
                    AdminLandingPage adminLandingPageObj = new AdminLandingPage();
                    adminLandingPageObj.AskAdmin();
                } else if (loginContext.role.equals("CUSTOMER")) {
                    CustomerLandingPage customerLandingPage = new CustomerLandingPage();
                    customerLandingPage.AskCustomer(loginContext);
                } else if (loginContext.role.equals("RECEPTIONIST")) {
                    Receptionist receptionist = new Receptionist();
                    receptionist.LandingPageMenu(loginContext);
                } else if (loginContext.role.equals("MANAGER")) {
                    Manager manager = new Manager(loginContext);
                    manager.LandingPageMenu();
                } else if (loginContext.role.equals("MECHANIC")) {
                    Mechanic mechanic = new Mechanic();
                    mechanic.LandingPageMenu(loginContext);
                }
            }
            System.out.println("Press 1 to Log In");
            System.out.println("Press 2 to Exit");
            option = in.nextInt();
        }

    }
}