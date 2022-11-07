import java.util.Scanner;

public class StoreInfo {
    public void AskStoreInfo() {
        System.out.println("Press 1 for Add Store");
        System.out.println("Press 2 to go back");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        switch (option){
            case 1:
                // Get store information
                System.out.println("enter store ID:");
                String ID = in.nextLine();
                System.out.println("Enter Address:");
                String address = in.nextLine();
                // GET MANAGER's information
                Manager m = new Manager();
                m.AskManager();
                System.out.println("Get Min wage for mechnics");
                double minWage = in.nextDouble();
                System.out.println("Get Max wage for mechanics");
                double maxWage = in.nextDouble();
                // invoke JDBC function to create the manager first and then create the store
            case 2:
                return;
            default:
                AskStoreInfo();
        }

        return;
    }
}
