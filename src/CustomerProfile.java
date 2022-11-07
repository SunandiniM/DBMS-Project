import java.util.Scanner;

public class CustomerProfile {
    public void AskForProfile(String customerId, String serviceCenterID) {
        System.out.println("Enter 1 to View Profile");
        System.out.println("Enter 2 to Add a car to profile");
        System.out.println("Enter 3 to Delete a car from profile");
        System.out.println("Enter 4 to go back");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        switch (option) {
            case 1:
                ShowCustomerProfile();
            case 2:
                AskToAddCar();
            case 3:
                AskToRemoveCar();
            case 4:
                return;
        }

    }

    public void ShowCustomerProfile() {
        // call jdbc function to show the customer profile
    }

    public void AskToRemoveCar() {
        // display all the cars

        Scanner in = new Scanner(System.in);
        System.out.println("Press 1 to delete a car from the profile or any other key to go back");
        int option = in.nextInt();
        if (option == 1) {
            System.out.println("Enter the vin number of the car you want to delete");
            String vin = in.nextLine();
            // call jdbc function to delete
        }else {
            return;
        }
    }

    public void AskToAddCar() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the VIN Number of CAR");
        String vin = in.nextLine();

        System.out.println("Enter the car manufacturer name");
        String manName = in.nextLine();

        System.out.println("Enter the mileage");
        double mileage = in.nextDouble();

        System.out.println("Enter the year");
        int year = in.nextInt();

        System.out.println("Press 1 to save it or 2 to cancel and go back");
        int option = in.nextInt();

        if (option == 1) {
            // call the JDBC function to save a car
        }else{
            return;
        }
    }
}
