import java.util.Scanner;

public class Mechanic {
    public void LandingPageMenu() {
        System.out.println("Enter 1 to View Schedule");
        System.out.println("Enter 2 to Request Time Off");
        System.out.println("Enter 3 to Request Swap");
        System.out.println("Enter 4 to Accept or Reject Swap");
        System.out.println("Enter 5 to Logout");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        switch (option) {
            case 1:
                ViewSchedule();
                LandingPageMenu();
            case 2:
                RequestTimeOff();
                LandingPageMenu();
            case 3:
            case 4:
            case 5:
            default:
        }
    }

    public void ViewSchedule() {
        // list of the time slots busy of the mechnaic
    }

    public void RequestTimeOff() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the week you want a time off");
        int week = in.nextInt();
        in.nextLine();

        System.out.println("Enter the day you want the time off");
        int day = in.nextInt();
        in.nextLine();

        System.out.println("Enter the slot start of time off");
        int startSlot = in.nextInt();
        in.nextLine();

        System.out.println("Enter the end slot id of the time off");
        int endSlot = in.nextInt();
        in.nextLine();

        System.out.println("Press 1 to make the selection or any other key to go back");
        int option = in.nextInt();
        if (option == 1) {
            // JDBC Call
        }else{
            return;
        }
    }

    public void RequestSwap() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the week you want a swap");
        int week = in.nextInt();
        in.nextLine();

        System.out.println("Enter the day you want swap");
        int day = in.nextInt();
        in.nextLine();

        System.out.println("Enter the slot start of swap");
        int startSlot = in.nextInt();
        in.nextLine();

        System.out.println("Enter the end slot id of the swap");
        int endSlot = in.nextInt();
        in.nextLine();

        System.out.println("Enter the employee ID of the mechanic that is being requested for swap");
        String empID = in.nextLine();

        System.out.println("Enter the time slot range ");
        int st = in.nextInt();
        int end = in.nextInt();

        System.out.println("Press 1 to send the request or any other key to go back");
        int option = in.nextInt();
        if (option == 1) {
            // JDBC Call
        }else{
            return;
        }
    }

    public void AcceptRejectSwapRequests() {

        // Display the swap requests first

        System.out.println("Press 1 to manage swap requests");
        System.out.println("Press 2 to go back");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        if (option == 1) {

        }else{
            return;
        }
    }

    public void ManageSwapRequests() {
        System.out.println("Enter the request ID of the swap request");
        Scanner in = new Scanner(System.in);
        String requestID = in.nextLine();

        System.out.println("Press 1 to Accept the request\n2 to reject it\n3 to go back");
        int option = in.nextInt();
        if (option == 1) {
            // JDBC call to accept the request
        } else if (option == 2) {
            // JDBC call to reject the request
        }else{
            return;
        }

    }
}
