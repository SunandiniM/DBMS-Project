import java.util.Scanner;

public class Service {
    public void AskService() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter existing service category");
        String serviceCategory = in.nextLine();
        // call JDBC function to check if this exists -> raise error if it does not

        System.out.println("Enter the name of the service");
        String serviceName = in.nextLine();

        System.out.println("Enter the duration of the service");
        int duration = in.nextInt();

        // Call JDBC function
    }

    //
    // should have customer id, and service center id as input to this
    public void AskToShowServiceHistory(String CustomerID, String SCID) {
        System.out.println("Press 1 to see service history or anything else to go back");
        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        if (option == 1) {
            System.out.println("Enter the CAR's VIN number");
            String vinNumber = in.nextLine();
            // make a JDBC call to get the service history
        }else{
            return;
        }
    }
}
