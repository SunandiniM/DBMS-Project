import java.util.Scanner;

public class AdminLandingPage {
    public void AskAdmin() {
        System.out.println("Press 1 for System Setup");
        System.out.println("Press 2 to Add new store");
        System.out.println("Press 3 to Add new service");
        System.out.println("Press 4 for Logout");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        switch (option){
            case 1:
                // system setup object initialise
                SystemSetup sysObj = new SystemSetup();
                sysObj.AskSystemSetup();
            case 2:
                // Add a new store
            case 3:
                // Add a new service
            case 4:
                // Logout
            default:
                AskAdmin();
        }
    }
}
