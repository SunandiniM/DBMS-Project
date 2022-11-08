import java.util.Scanner;
import java.sql.*;

public class AdminLandingPage {
    public void AskAdmin() throws SQLException {
        System.out.println("Press 1 for System Setup");
        System.out.println("Press 2 to Add new store");
        System.out.println("Press 3 to Add new service");
        System.out.println("Press 4 for Logout");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        while (option != 4) {
            switch (option){
                case 1:
                    // system setup object initialise
                    SystemSetup sysObj = new SystemSetup();
                    sysObj.AskSystemSetup();
                    break;
                case 2:
                    // Add a new store
                    break;
                case 3:
                    Service serviceObj = new Service();
                    serviceObj.AskService();
                    break;
                case 4:
                    // Logout
                    return;
                default:
                    AskAdmin();
            }
            System.out.println("Press 1 for System Setup");
            System.out.println("Press 2 to Add new store");
            System.out.println("Press 3 to Add new service");
            System.out.println("Press 4 for Logout");

            in = new Scanner(System.in);
            option = in.nextInt();
        }
    }
}
