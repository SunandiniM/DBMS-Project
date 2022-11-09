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
            case 4:
                // logout code
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
            String sql = "SELECT I.ORDER_ID, S.SNAME, H.START_SLOT, H.END_SLOT, E.FNAME, E.LNAME, O.PRICE, I.BILL, " +
                    "I.STATUS FROM SERVICE_EVENT SE, SERVICE S, OFFERS O, HOURLY_EMPLOYEE_SCHEDULE H, EMPLOYEES E, " +
                    "OWNS OW, INVOICE I WHERE I.ORDER_ID = 4 AND SE.ORDER_ID = I.ORDER_ID AND H.ORDER_ID = SE.ORDER_ID " +
                    "AND I.CID = "+ loginContext.ID + " AND I.SCID = " + loginContext.SCID + " AND SE.CID = I.CID AND " +
                    "SE.SID = S.SID AND S.SID = O.SID AND S.SID = O.SID AND H.EMPID = E.EMPID AND OW.SCID = " + loginContext.SCID +" AND OW.CID = " + loginContext.ID;
            System.out.println("QUERY : " + sql);
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("\n SERVICE HISTORY :");
            System.out.println("INVOICE_ID, SNAME, START_SLOT, END_SLOT, MECHANIC NAME , PRICE, INVOICE BILL, INVOICE STTAUS");
            while (rs.next()) {
                System.out.println(rs.getString(0) + " " + rs.getString(1) + " " + rs.getString(2) + " " +
                        rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) +  " " +
                        rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9));
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
                String sql = "SELECT SE.ORDER_ID, S.SNAME, H.START_SLOT, H.END_SLOT, E.FNAME, E.LNAME," +
                        " O.PRICE FROM SERVICE_EVENT SE, SERVICE S, OFFERS O, HOURLY_EMPLOYEE_SCHEDULE H, " +
                        "EMPLOYEES E WHERE E.EMPID = H.EMPID AND S.SID = SE.SID AND S.SID = O.SID AND " +
                        "H.ORDER_ID = SE.ORDER_ID AND SE.CID = " + loginContext.ID + " AND SE.SCID = " + loginContext.SCID;
                System.out.println("QUERY : " + sql);
                ResultSet rs = stmt.executeQuery(sql);
                System.out.println("\n SERVICE HISTORY :");
                System.out.println("INVOICE_ID, SNAME, START_SLOT, END_SLOT, MECHANIC NAME , PRICE");
                while (rs.next()) {
                    System.out.println(rs.getString(0) + " " + rs.getString(1) + " " + rs.getString(2) + " " +
                            rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) +  " " +
                            rs.getString(6) + " " + rs.getString(7));
                }
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
