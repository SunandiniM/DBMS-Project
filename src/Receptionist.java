import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Receptionist {
    public void LandingPageMenu(LoginContext loginContext) {
//        while (1) {
            System.out.println("Enter 1 to Add new customer profiles");
            System.out.println("Enter 2 to Find customers with pending invoices");
            System.out.println("Enter 3 to logout");
            Scanner in = new Scanner(System.in);
            int option = in.nextInt();
            in.nextLine();
            switch (option){
                case 1:
                    AddNewCustomerProfile(loginContext);
                    LandingPageMenu(loginContext);
                case 2:
                    FindCustomersWithPendingInvoices();
                    LandingPageMenu(loginContext);
                case 3:
                    Login login = new Login();
                    login.AskLogout();
                default:
                    LandingPageMenu(loginContext);
            }
    }

    public void AddNewCustomerProfile(LoginContext loginContext) {
        Scanner in = new Scanner(System.in);

        System.out.println("Press 1 to go back to the landing page or any other key to add new profile");
        int option = in.nextInt();

        if (option == 1) {
            return;
        }

        in.nextLine();
        in.useDelimiter(System.lineSeparator());

        System.out.println("Enter Customer First Name");
        String fname = in.nextLine();
        System.out.println("Enter Customer Last Name");
        String lname = in.nextLine();


        System.out.println("Enter Customer Address");
        String address = in.nextLine();

        System.out.println("Enter email address");
        String email = in.nextLine();

        System.out.println("Enter Phone Number");
        long phoneNumber = in.nextLong();
        in.nextLine();

        System.out.println("Enter Username");
        String username = in.nextLine();

        System.out.println("Enter the VIN number");
        String vinNumber = in.nextLine();

        System.out.println("Enter the car manufacturer");
        String carMan = in.nextLine();

        System.out.println("Enter the current mileage");
        int mileage = in.nextInt();
        in.nextLine();

        System.out.println("Enter the year of the car");
        int year = in.nextInt();

        System.out.println("The year is " + year);
//        in.nextLine();



        // JDBC call to save a customer

        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "INSERT INTO CUSTOMER (SCID, FNAME, LNAME, USERNAME, ACC_STATUS, PROFILE_STATUS)" +
                    "VALUES (" + loginContext.SCID + ",'" + fname + "','" + lname + "','" + username + "'," + "0" + "," + "1" +")";
            System.out.println("CUSTOMER SQL " + sql);
            stmt.executeUpdate(sql);
        } catch(Exception e) {
            System.out.println("Failed to add customer profile" + e);
        }

        String CID  = "";

        try{
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "SELECT CID FROM CUSTOMER WHERE FNAME = '" + fname + "'";
            System.out.println("Find cust " + sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CID = rs.getString("CID");
            }
        }catch (Exception e) {
            System.out.println("Failed to get the customer " + e);
        }


        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "INSERT INTO VEHICLE VALUES ('" + vinNumber + "', " + year + ",'" + carMan + "', " + mileage + "," + "'N'" + ")";
            System.out.println("Vehicle in : " + sql);
            stmt.executeUpdate(sql);
        }catch (Exception e) {
            System.out.println("Failed to add vehicle " + e);
        }

        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "INSERT INTO OWNS VALUES (" +loginContext.SCID + ", " + CID + ", '" + vinNumber + "')";
            stmt.executeUpdate(sql);
            System.out.println("Successfully added a new customer profile");
        }catch (Exception e) {
            System.out.println("Failed to add in OWNED_BY" + e);
        }

//        in.close();
    }

    public void FindCustomersWithPendingInvoices() {
        Scanner in = new Scanner(System.in);

        System.out.println("Press 1 to go back to the landing page");
        int option = in.nextInt();

        if (option == 1) {
            return;
        }
        in.nextLine();
        System.out.println("Enter Customer ID");
        String custID = in.nextLine();

        System.out.println("Enter Customer Name");
        String custName = in.nextLine();

        System.out.println("Enter Invoice ID");
        String invoiceID = in.nextLine();

        System.out.println("Enter Invoice Date");
        String invoiceDate = in.nextLine();

        System.out.println("Enter Amount");
        double amount = in.nextDouble();
        in.nextLine();
        // JDBC Call to get the invoice
        try{
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            PreparedStatement statement = dbConn.createConnection().
                    prepareStatement("SELECT ORDER_ID, DATE, BILL FROM INVOICE I, SERVICE_EVENT E WHERE I.status = 0");
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + rs.getString(3) + " " + rs.getString(4));
            }
            System.out.println("Successfully displayed customers with pending invoices");
        } catch(Exception e) {
            System.out.println("Failed to display customers with pending invoices " + e);
        }
//        in.close();
    }
}
