import sun.rmi.runtime.Log;

import java.sql.Statement;
import java.util.Scanner;

public class Manager {
    LoginContext loginContext;

    Manager() {
        loginContext = null;
    }

    Manager(LoginContext lc) {
        loginContext = lc;
    }

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
                return;
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
        System.out.println("Enter Employee id");
        long empID = Long.parseLong(in.nextLine());
        in = new Scanner(System.in);
        System.out.println("Enter Employee's First Name");
        String fname = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter Employee's Last name");
        String lname = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter Employee's Address");
        String address = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter Employee's Email");
        String email = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter Employee's Phone Number");
        long phoneNum = Long.parseLong(in.nextLine());
        in = new Scanner(System.in);
        System.out.println("Select Employee Role\n 1. Receptionist\n 2. Mechanic");
        int role = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter Employee's Salary");
        int salary = in.nextInt();
        boolean returnFlag = true;
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "INSERT INTO EMPLOYEES VALUES ('" + loginContext.SCID + "', '" + empID + "', '" + fname + "', '" + lname + "', '" +
                    address + "', '" + email + "', '" + phoneNum + "', '" + role + "', '" + fname + lname + "')";
            stmt.executeUpdate(sql);
            try {
                sql = "INSERT INTO WORKSIN VALUES ('" + loginContext.SCID + "', '" + empID + "', '" + salary + "')";
                stmt.executeUpdate(sql);
                System.out.println("Successfully added store employee");
            } catch (Exception e) {
                System.out.println("Failed to Create store employee");
                System.out.println(e);
                returnFlag = false;
            }
            if (!returnFlag) {
                sql = "DELETE FROM EMPLOYEES WHERE SCID=" + loginContext.SCID + " AND EMPID=" + empID;
                stmt.executeUpdate(sql);
                System.out.println("Removed Employee entry");
            }
        } catch(Exception e) {
            System.out.println("Failed to add employee");
            System.out.println(e);
        }
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
        System.out.println("Is the store open on a saturday? 1 for yes and 0 for no");
        int option = in.nextInt();
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "UPDATE TABLE SERVICE_CENTER SET SERVICE_CENTER.OPEN_SATURDAY='" + option + "' where SERVICE_CENTER.SCID=" + loginContext.SCID;
            stmt.executeUpdate(sql);
            System.out.println("Successfully updated store operation hours");
        } catch(Exception e) {
            System.out.println("Failed to update store operation hours");
            System.out.println(e);
        }
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
        } catch(Exception e) {
            System.out.println("Failed to create manager");
            System.out.println(e);
            returnFlag = false;
        }
        return returnFlag;
    }
}
