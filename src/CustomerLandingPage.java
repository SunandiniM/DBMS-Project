import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class CustomerLandingPage {
    public void AskCustomer(LoginContext loginContext) {
        System.out.println("Enter 1 to View and Update profile");
        System.out.println("Enter 2 to View and Schedule service");
        System.out.println("Enter 3 to go to Invoices");
        System.out.println("Enter 4 to logout");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        switch (option) {
            case 1:
                CustomerProfile customerProfileObj = new CustomerProfile();
                customerProfileObj.AskForProfile(loginContext);
                AskCustomer(loginContext);
                break;
            case 2:
                AskToViewAndScheduleService(loginContext);
                AskCustomer(loginContext);
                break;
            case 3:
                InvoiceHandler(loginContext);
                AskCustomer(loginContext);
                break;
            case 4:
                Login login = new Login();
                login.AskLogout();
            default:
                return;
        }
    }

    public void InvoiceHandler(LoginContext loginContext) {
        System.out.println("Press 1 to see Invoice Details");
        System.out.println("Press 2 to pay invoice");
        System.out.println("Press 3 or anything to go back");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        switch (option) {
            case 1:
                ShowInvoices(loginContext);
                break;
            case 2:
                PayInvoice(loginContext);
                break;
            case 3:
                break;
        }
    }

    public void PayInvoice(LoginContext loginContext) {
        System.out.println("Enter the Invoice ID");
        Scanner in = new Scanner(System.in);
        String invoiceID = in.nextLine();

        System.out.println("Enter 1 to pay else anything to go back");
        int op = in.nextInt();

        if (op != 1) {
            return;
        }

        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "UPDATE INVOICE SET STATUS = 1 WHERE ORDER_ID = " + invoiceID;
            stmt.executeUpdate(sql);
        }catch (Exception e) {
            System.out.println("Failed to add in INVOICE " + e);
        }
    }

    public void ShowInvoices(LoginContext loginContext) {
        System.out.println("Enter the invoice ID");
        Scanner in = new Scanner(System.in);
        String orderID = in.nextLine();

        System.out.println("Press 1 to see Invoice details\npress anything else to go back");
        int option = in.nextInt();
        in.nextLine();

        switch (option) {
            case 1:
                InvoiceDetails(loginContext, orderID);
            default:
                return;
        }
    }

    public void InvoiceDetails(LoginContext loginContext, String order_id) {
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "SELECT I.ORDER_ID, S.SNAME, O.PRICE, E.FNAME, E.LNAME, H.WEEK, H.DAY, H.START_SLOT, H.END_SLOT, I.BILL, I.STATUS FROM INVOICE I, SERVICE_EVENT SE, " +
                    "SERVICE S, OFFERS O, VEHICLE V, EMPLOYEES E, HOURLY_EMPLOYEE_SCHEDULE H WHERE I.SCID = " + loginContext.SCID+ " AND " +
                    "I.ORDER_ID = " + order_id +" AND I.ORDER_ID = SE.ORDER_ID AND I.CID = SE.CID AND I.SCID = SE.SCID AND SE.SID = S.SID " +
                    "AND O.SCID = SE.SCID AND O.SID = S.SID AND V.VIN_NO = SE.VIN_NO AND V.MFG = O.MFG AND H.ORDER_ID = I.ORDER_ID " +
                    "AND H.SCID = I.SCID AND H.EMPID = E.EMPID";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("\n SERVICE HISTORY :");
            System.out.println("INVOICE_ID, SNAME, PRICE, MECHANIC_NAME, WEEK, DAY, START_SLOT, END_SLOT, INVOICE BILL, INVOICE STATUS");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " +
                        rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) +  " " +
                        rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " +
                        rs.getString(9) + " " + rs.getString(10) + " " + rs.getString(11));
            }
        } catch(Exception e) {
            System.out.println("Failed to get service history");
            System.out.println(e);
        }
    }

    public void AskToViewAndScheduleService(LoginContext loginContext) {
        // menu
        System.out.println("Enter 1 to View Service History");
        System.out.println("Enter 2 to Schedule Service");
        System.out.println("Enter 3 to go back");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        switch (option) {
            case 1:
                // go to View Service History Function
                AskToShowServiceHistory(loginContext);
                break;
            case 2:
                // go on to schedule a service
                ServiceScheduler serviceScheduler = new ServiceScheduler();
                serviceScheduler.Menu(loginContext);
                break;
            case 3:
                return;
        }

        return;
    }

    public void AskToShowServiceHistory(LoginContext loginContext) {
        System.out.println("Press 1 to see service history or anything else to go back");
        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        in.nextLine();
        if (option == 1) {
            System.out.println("Enter the CAR's VIN number");
            String vinNumber = in.nextLine();
            // make a JDBC call to get the service history
            try {
                DBConnection dbConn = DBConnection.getDBConnection();
                dbConn.createConnection();
                Statement stmt = dbConn.conn.createStatement();
                String sql = "SELECT SE.SID, S.SNAME, SE.VIN_NO, O.PRICE, E.FNAME, E.LNAME, H.WEEK, H.DAY, H.START_SLOT, " +
                        "H.END_SLOT FROM SERVICE_EVENT SE, SERVICE S, OFFERS O, VEHICLE V, EMPLOYEES E, " +
                        "HOURLY_EMPLOYEE_SCHEDULE H WHERE SE.CID = " + loginContext.ID + " AND SE.SCID = " + loginContext.SCID +" AND SE.SID = O.SID " +
                        "AND SE.SCID = O.SCID AND SE.SID = S.SID AND S.SID = O.SID AND V.VIN_NO = SE.VIN_NO AND " +
                        "O.MFG = V.MFG AND H.SCID = SE.SCID AND H.ORDER_ID = SE.ORDER_ID AND E.EMPID = H.EMPID AND H.SCID = E.SCID";
                ResultSet rs = stmt.executeQuery(sql);
                System.out.println("\n SERVICE HISTORY :");
                System.out.println("SERVICE ID, SNAME, VIN_NO, PRICE, MECHANIC_NAME, WEEK, DAY, START_SLOT, END_SLOT");
                while (rs.next()) {
                    System.out.println(rs.getString(1) + " " + rs.getString(2) + " " +
                            rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) +  " " +
                            rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) +
                            rs.getString(9) + rs.getString(10));
                }
                System.out.println("\n");
            } catch(Exception e) {
                System.out.println("Failed to get service history");
                System.out.println(e);
            }
        }else{
            return;
        }
    }

    public void ScheduleService(LoginContext loginContext) {
        System.out.println("Press 1 to add schedule maintainance");
        System.out.println("Press 2 to add schedule repair");
        System.out.println("Press 3 to view cart and select schedule");
        System.out.println("Press 4 to go back");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        in.nextLine();

        switch (option)  {
            case 1:
            case 2:
            case 3:
            case 4:
                return;
        }
    }

    public void AddScheduleMaintainance() {

    }

}
