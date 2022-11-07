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
}
