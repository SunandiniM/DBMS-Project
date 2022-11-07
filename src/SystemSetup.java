import java.util.Scanner;

public class SystemSetup {
    public void AskSystemSetup() {
        System.out.println("Press 1 to upload service general information");
        System.out.println("Press 2 to upload store general information");
        System.out.println("Press 3 to go back to the previous menu");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        String fileName;
        while(option != 3) {
            switch (option){
                case 1:
                    // system setup object initialise
                    in = new Scanner(System.in);
                    System.out.println("Enter file name");
                    fileName = in.nextLine();
                    System.out.println("Successfully uploaded " + fileName);
//                Service service = new Service();
//                service.AskService();
                case 2:
                    // Add a new store
                    in = new Scanner(System.in);
                    System.out.println("Enter file name");
                    fileName = in.nextLine();
                    System.out.println("Successfully uploaded " + fileName);
//                    StoreInfo storeInfo = new StoreInfo();
//                    storeInfo.AskStoreInfo();
                case 3:
                    return;
                default:
                    AskSystemSetup();
            }
            System.out.println("Press 1 to upload service general information");
            System.out.println("Press 2 to upload store general information");
            System.out.println("Press 3 to go back to the previous menu");
            in = new Scanner(System.in);
            option = in.nextInt();
        }
    }
}
