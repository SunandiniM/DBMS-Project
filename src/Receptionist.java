import java.util.Scanner;

public class Receptionist {
    public void LandingPageMenu() {
        System.out.println("Enter 1 to Add new customer profiles");
        System.out.println("Enter 2 to Find customers with pending invoices");
        System.out.println("Enter 3 to logout");
        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        switch (option){
            case 1:
                AddNewCustomerProfile();
                LandingPageMenu();
            case 2:
                FindCustomersWithPendingInvoices();
                LandingPageMenu();
            case 3:
                Login login = new Login();
                login.AskLogout();
            default:
                LandingPageMenu();
        }
    }

    public void AddNewCustomerProfile() {
        Scanner in = new Scanner(System.in);

        System.out.println("Press 1 to go back to the landing page");
        int option = in.nextInt();

        if (option == 1) {
            return;
        }

        System.out.println("Enter Customer Name");
        String name = in.nextLine();

        System.out.println("Enter Customer Address");
        String address = in.nextLine();

        System.out.println("Enter email address");
        String email = in.nextLine();

        System.out.println("Enter Phone Number");
        long phoneNumber = in.nextLong();

        System.out.println("Enter Username");
        String username = in.nextLine();

        System.out.println("Enter the VIN number");
        String vinNumber = in.nextLine();

        System.out.println("Enter the car manufacturer");
        String carMan = in.nextLine();

        System.out.println("Enter the current mileage");
        double mileage = in.nextDouble();

        System.out.println("Enter the year of the car");
        int year = in.nextInt();

        // JDBC call to save a customer
    }

    public void FindCustomersWithPendingInvoices() {
        Scanner in = new Scanner(System.in);

        System.out.println("Press 1 to go back to the landing page");
        int option = in.nextInt();

        if (option == 1) {
            return;
        }

        System.out.println("Enter Customer ID");
        String custID = in.nextLine();

        System.out.println("Enter Customer Name");
        String custName = in.nextLine();

        System.out.println("Enter Invoice ID");
        String invoiceID = in.nextLine();

        System.out.println("Enter Invoice Date");
        String invoiceDate = in.nextLine();

        System.out.println("Enter Amount");
        double amount = in.nextDouble();

        // JDBC Call to get the invoice
    }
}
