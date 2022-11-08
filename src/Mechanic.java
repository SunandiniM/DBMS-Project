import java.sql.Statement;
import java.util.Scanner;
import java.sql.*;

public class Mechanic {
    public void LandingPageMenu(LoginContext loginContext) {
        System.out.println("Enter 1 to View Schedule");
        System.out.println("Enter 2 to Request Time Off");
        System.out.println("Enter 3 to Request Swap");
        System.out.println("Enter 4 to Accept or Reject Swap");
        System.out.println("Enter 5 to Logout");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        switch (option) {
            case 1:
                ViewSchedule(loginContext);
                LandingPageMenu(loginContext);
            case 2:
                RequestTimeOff(loginContext);
                LandingPageMenu(loginContext);
            case 3:
                RequestSwap(loginContext);
                LandingPageMenu(loginContext);
            case 4:
                AcceptRejectSwapRequests(loginContext);
                LandingPageMenu(loginContext);
            case 5:
            default:
        }
    }

    public void ViewSchedule(LoginContext loginContext) {
        // list of the time slots busy of the mechnanic
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            Connection conn = dbConn.createConnection();
            String sql1 = "SELECT * FROM HOURLY_EMPLOYEE_SCHEDULE WHERE SCID = " + loginContext.SCID + " AND EMPID = " + loginContext.ID;

//            System.out.println("Query : " + sql1);
            Statement stmt = dbConn.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql1);

            while(rs.next()) {
                // print the order_id, week, starting slot, ending slot
            }


        } catch(Exception e) {
            System.out.println("Failed to print the schedule of the mechanic" + e);

        }
    }

    public void RequestTimeOff(LoginContext loginContext) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the week you want a time off");
        int week = in.nextInt();
        in.nextLine();

        System.out.println("Enter the day you want the time off");
        int day = in.nextInt();
        in.nextLine();

        System.out.println("Enter the slot start of time off");
        int startSlot = in.nextInt();
        in.nextLine();

        System.out.println("Enter the end slot id of the time off");
        int endSlot = in.nextInt();
        in.nextLine();

        System.out.println("Press 1 to make the selection or any other key to go back");
        int option = in.nextInt();
        if (option == 1) {
            // JDBC Call
            try {
                DBConnection dbConn = DBConnection.getDBConnection();
                Connection conn = dbConn.createConnection();
                Statement stmt = dbConn.conn.createStatement();
                String sql1 = "INSERT INTO TIMEOFF_REQUEST VALUES( " + loginContext.SCID + ", " + loginContext.ID + ", " + day + ", " + week + ", " + startSlot + ", " + endSlot + ", " + 0 + ")";
//                System.out.println(sql1);
                stmt.executeUpdate(sql1);
            } catch(Exception e) {
                System.out.println("Met with this exception while committing to the database " + e);
            }
        }else{
            return;
        }
    }

    public void RequestSwap(LoginContext loginContext) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the week you want a swap");
        int week = in.nextInt();
        in.nextLine();

        System.out.println("Enter the day you want swap");
        int day = in.nextInt();
        in.nextLine();

        System.out.println("Enter the slot start of swap");
        int startSlot = in.nextInt();
        in.nextLine();

        System.out.println("Enter the end slot id of the swap");
        int endSlot = in.nextInt();
        in.nextLine();

        System.out.println("Enter the employee ID of the mechanic that is being requested for swap");
        String empID = in.nextLine();

        System.out.println("Enter the time slot range ");
        int st = in.nextInt();
        int end = in.nextInt();

        System.out.println("Press 1 to send the request or any other key to go back");
        int option = in.nextInt();
        if (option == 1) {
            // JDBC Call
            try {
                DBConnection dbConn = DBConnection.getDBConnection();
                Connection conn = dbConn.createConnection();
                String sql1 = "INSERT INTO SWAP_REQUEST(SCID, EMPID, REQUESTED_EMP_ID, DAY, WEEK, START_SLOT, END_SLOT, STATUS) " +
                        "VALUES( " + loginContext.SCID+ ","+ loginContext.ID + ","+  empID + "," + day + "," + week + "," + startSlot + "," + endSlot+ ", 0)";
//                System.out.println(sql1);
                Statement stmt = dbConn.conn.createStatement();
                /*PreparedStatement stmt = conn.prepareStatement("INSERT INTO SWAP_REQUEST(SCID, EMPID, REQUESTED_EMP_ID, DAY, WEEK, START_SLOT, END_SLOT, STATUS) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
                stmt.setString(1, loginContext.SCID);
                stmt.setString(2, loginContext.ID);
                stmt.setString(3, empID);
                stmt.setString(4, day + "");
                stmt.setString(5, week + "");
                stmt.setString(6, startSlot + "");
                stmt.setString(7, endSlot + "");
                stmt.setString(8, 0+ "");

                System.out.println(stmt.toString());*/
                stmt.executeUpdate(sql1);
            } catch(Exception e) {
                System.out.println("Met with this exception while committing to the database " + e);
            }
        }else{
            return;
        }
    }

    public void AcceptRejectSwapRequests(LoginContext loginContext) {

        // Display the swap requests first

        System.out.println("Press 1 to manage swap requests");
        System.out.println("Press 2 to go back");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        if (option == 1) {
            ManageSwapRequests(loginContext);
        }else{
            return;
        }
    }

    public void ManageSwapRequests(LoginContext loginContext) {
        System.out.println("Enter the request ID of the swap request");
        Scanner in = new Scanner(System.in);
        String requestID = in.nextLine();

        System.out.println("Press 1 to Accept the request\n2 to reject it\n3 to go back");
        int option = in.nextInt();
        if (option == 1) {
            // JDBC call to accept the request
            try {
                DBConnection dbConn = DBConnection.getDBConnection();
                Connection conn = dbConn.createConnection();
                PreparedStatement stmt = conn.prepareStatement("UPDATE SWAP_REQUEST SET STATUS=1 WHERE REQUEST_ID=?");
                stmt.setString(1, requestID);
                stmt.executeQuery();

                if(stmt.executeUpdate() == 0)
                    System.out.println("Request to approve swap request failed to submit");
                else
                    System.out.println("Request to approve swap request successfully submitted");
            } catch(Exception e) {
                System.out.println("Met with this exception while committing to the database " + e);
            }
        } else if (option == 2) {
            // JDBC call to reject the request
            try {
                DBConnection dbConn = DBConnection.getDBConnection();
                Connection conn = dbConn.createConnection();
                PreparedStatement stmt = conn.prepareStatement("UPDATE SWAP_REQUEST SET STATUS = 0 WHERE REQUEST_ID=?");
                stmt.setString(1, requestID);
                stmt.executeQuery();

                if(stmt.executeUpdate() == 0)
                    System.out.println("Request to reject swap request failed to submit");
                else
                    System.out.println("Request to reject swap request successfully submitted");
            } catch(Exception e) {
                System.out.println("Met with this exception while committing to the database " + e);
            }
        }else{
            return;
        }

    }
}
