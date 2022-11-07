import java.util.Scanner;

public class ServiceScheduler {
    public void Menu() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter VIN Number");
        String vinNumber = in.nextLine();
        System.out.println("Enter the current mileage");
        Double mileage = in.nextDouble();

        System.out.println("Enter 1 to Add Schedule Maintainance");
        System.out.println("Enter 2 to Add Schedule Repair");
        System.out.println("Enter 3 to View cart and select schedule time");
        System.out.println("Enter anything else to go back");
        int option = in.nextInt();
        Cart cartObj = new Cart();

        switch (option){
            case 1:
                cartObj = ScheduleMaintainance(vinNumber, cartObj);
                if (cartObj == null) {
                    Menu();
                }
            case 2:
            case 3:
            default:

        }
    }


    public Cart ScheduleMaintainance(String vin, Cart cart) {
        // Display which next maintainance is the customer eligible for:

        String NextMaintainance = "A";
        Scanner in = new Scanner(System.in);
        System.out.println("Press 1 to add this maintainance schedule in the cart");
        System.out.println("Press 2 to go back");
        int option = in.nextInt();
        if (option == 1){
            cart.Maintainance = NextMaintainance;
        }else{
            return null;
        }

        return cart;
    }

    public Cart ScheduleRepairService(String vin, Cart cart) {
        String[] listOfCategories = {"Engine Services", "Exhaust Services", "Electrical Services",
                "Transmission Services", "Tire Services", "Heating and AC Services"};

        for (int i = 0; i < listOfCategories.length; i++) {
            System.out.println("Enter " + (i + 1) + " to pick " + listOfCategories[i]);
        }

        System.out.println("Enter anything else to go back");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        if (option > listOfCategories.length) {
            return null;
        }

        return cart;
    }

    // IMPLEMENT THIS ONCE JDBC IS IMPLEMENTED
    public Cart getServicesOfCategory(String category, Cart cart) {
        // call JDBC to get all the repair services

        return cart;
    }
}
