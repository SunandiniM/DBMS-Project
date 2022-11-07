import java.util.Scanner;

public class SystemSetup {
    public void AskSystemSetup() {
        System.out.println("Press 1 to upload service general information");
        System.out.println("Press 2 to upload store general information");
        System.out.println("Press 3 to go back to the previous menu");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        switch (option){
            case 1:
                // system setup object initialise
            case 2:
                // Add a new store
            case 3:
                // Add a new service
            default:
                AskSystemSetup();
        }
    }
}
