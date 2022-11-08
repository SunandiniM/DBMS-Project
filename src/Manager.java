import java.sql.Statement;
import java.util.Scanner;

public class Manager {

    public void LandingPageMenu() {
        Scanner in = new Scanner(System.in);

        System.out.println("Press 1 to Setup Store");
        System.out.println("Press 2 to Add New Employee");
        System.out.println("Press 3 to log out");

        int option = in.nextInt();
        switch (option) {
            case 1:
                SetupStore();
                LandingPageMenu();
            case 2:
                AddEmployees();
                LandingPageMenu();
            case 3:
                break;
            default:
                LandingPageMenu();
        }
    }

    public void SetupStore() {
        System.out.println("Press 1 to add employees");
        System.out.println("Press 2 to setup operational hours");
        System.out.println("Press 3 to setup service prices");
        System.out.println("Press 4 to go back");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        switch (option) {
            case 1:
                AddEmployees();
                break;
            case 2:
                SetupOperationalHours();
                break;
            case 3:
                SetupRepairServicePrices();
                break;
            case 4:
                return;
            default:
                SetupStore();
        }
    }

    public void AddEmployees() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Name");
        String name = in.nextLine();
        System.out.println("Enter address");
        String address = in.nextLine();
        System.out.println("Enter email address");
        String email = in.nextLine();
        System.out.println("Enter Phone number");
        long phone = in.nextLong();
        in.nextLine();
        System.out.println("Enter the role");
        String role = in.nextLine();
        System.out.println("Enter the starting date");
        String startDate = in.nextLine();
        System.out.println("Enter the compensation");
        int compensation = in.nextInt();

        // JDBC CALL to save

    }

    public void SetupServicePrices() {
        System.out.println("Press 1 Setup Maintainance Service prices");
        System.out.println("Press 2 Setup Repair Service prices");
        System.out.println("Press 3 to go back");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        switch (option) {
            case 1:
                SetupMaintainanceServicePrices();
            case 2:
                SetupRepairServicePrices();
            case 3:
            default:
        }
    }

    public void SetupMaintainanceServicePrices() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Schedule A price");
        double aPrice = in.nextDouble();
        in.nextLine();
        System.out.println("Enter Schedule B price");
        double bPrice = in.nextDouble();
        in.nextLine();
        System.out.println("Enter Schedule C price");
        double cPrice = in.nextDouble();
        in.nextLine();

        // UPDATE JDBC
    }

    public void SetupRepairServicePrices() {
        // Load all the repair services from the db and find a way to setup the price for them
    }

    public void SetupOperationalHours() {
        Scanner in = new Scanner(System.in);
        System.out.println("Is the store open on a saturday? 1 for yes and anything else for no");
        int option = in.nextInt();
        in.nextLine();
        System.out.println("Press 1 to setup operational hours");
        int op = in.nextInt();
        in.nextLine();

    }

    public boolean AskManager(long storeId, DBConnection dbConn) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Manager's id");
        long empID = Long.parseLong(in.nextLine());
        in = new Scanner(System.in);
        System.out.println("Enter Manager's First Name");
        String fname = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter Manager's Last name");
        String lname = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter Manager's Address");
        String address = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter Manager's Email");
        String email = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter Manager's Phone Number");
        long phoneNum = Long.parseLong(in.nextLine());
        in = new Scanner(System.in);
        System.out.println("Enter Manager's Salary");
        int salary = in.nextInt();
        boolean returnFlag = true;
        try {
            Statement stmt = dbConn.conn.createStatement();
            String sql = "INSERT INTO EMPLOYEES VALUES ('" + storeId + "', '" + empID + "', '" + fname + "', '" + lname + "', '" +
                    address + "', '" + email + "', '" + phoneNum + "', '" + "MANAGER" + "', '" + fname + lname + "')";
            stmt.executeUpdate(sql);
            try {
                sql = "INSERT INTO WORKSIN VALUES ('" + storeId + "', '" + empID + "', '" + salary + "')";
                stmt.executeUpdate(sql);
                System.out.println("Successfully added store manager");
            } catch (Exception e) {
                System.out.println("Failed to Create store manager");
                System.out.println(e);
                returnFlag = false;
            }
            dbConn.closeConnection();
        } catch(Exception e) {
            System.out.println("Failed to create manager");
            System.out.println(e);
            returnFlag = false;
        }
        return returnFlag;
    }
}
