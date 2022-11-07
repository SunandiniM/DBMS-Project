import java.util.Scanner;

public class Manager {
    public void AskManager() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Manager's First Name");
        String fname = in.nextLine();
        System.out.println("Enter Manager's Last name");
        String lname = in.nextLine();
        System.out.println("Enter the username");
        String username = in.nextLine();
        System.out.println("Enter Password");
        String password = in.nextLine();
        System.out.println("Enter Salary");
        double salary = in.nextDouble();
        System.out.println("Enter the employee id");
        String empID = in.nextLine();

        // Call the JDBC function to add the manager

        // return empid of the manager?
    }
}
