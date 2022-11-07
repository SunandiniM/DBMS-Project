import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DBConnection dbConn = DBConnection.getDBConnection();
        dbConn.createConnection();
        dbConn.testrun();
        dbConn.closeConnection();
        System.out.println("Press 1 to Log In");
        System.out.println("Press 2 to Exit");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        while (option != 2) {

            in = new Scanner(System.in);
            Login loginObj = new Login();
            String role = loginObj.AskLogin(in);
            // based on role give the landing pages

            if (role == "admin") {
                AdminLandingPage adminLandingPageObj = new AdminLandingPage();
                adminLandingPageObj.AskAdmin();
            } else if (role == "customer") {

            } else if (role == "receptionist") {
                Receptionist receptionist = new Receptionist();
                receptionist.LandingPageMenu();
            } else if (role == "manager") {
                Manager manager = new Manager();
                manager.LandingPageMenu();
            } else if (role == "mechanic") {

            }
            System.out.println("Press 1 to Log In");
            System.out.println("Press 2 to Exit");
            in = new Scanner(System.in);
            option = in.nextInt();
        }

    }
}