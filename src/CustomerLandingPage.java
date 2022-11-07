import java.util.Scanner;

public class CustomerLandingPage {
    public void AskCustomer(String CustomerID, String SCID) {
        System.out.println("Enter 1 to View and Update profile");
        System.out.println("Enter 2 to View and Schedule service");
        System.out.println("Enter 3 to go to Invoices");
        System.out.println("Enter 4 to logout");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        switch (option) {
            case 1:
                CustomerProfile customerProfileObj = new CustomerProfile();
                customerProfileObj.AskForProfile(CustomerID, SCID);
                break;
        }
    }

    public void AskToViewAndScheduleService(String CustomerID, String SCID) {
        // menu
        System.out.println("Enter 1 to View Service History");
        System.out.println("Enter 2 to Schedule Service");
        System.out.println("Enter anything to go back");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        switch (option) {
            case 1:
                // go to View Service History Function
                Service service = new Service();
                service.AskToShowServiceHistory(CustomerID, SCID);
                break;
            case 2:
                // go on to schedule a service
                break;
            case 3:
                return;
        }

        return;
    }
}
