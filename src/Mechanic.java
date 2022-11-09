import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

class Pair {
    int x;
    int y;

    // Constructor
    public Pair(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}

public class Mechanic {
    public void LandingPageMenu(LoginContext loginContext) {
        while (true) {
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
                    break;
                case 2:
                    RequestTimeOff(loginContext);
                    break;
                case 3:
                    RequestSwap(loginContext);
                    break;
                case 4:
                    AcceptRejectSwapRequests(loginContext);
                    break;
                case 5:
                    return;
                default:
                    break;
            }
        }
    }

    public void ViewSchedule(LoginContext loginContext) {
        // list of the time slots busy of the mechnanic
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            Connection conn = dbConn.createConnection();

//            String sql = "select ORDER_ID,  min(WEEK) as start_week, min(DAY) as start_day, min(START_SLOT) as start_slot," +
//                    " max(WEEK) as end_week, max(DAY) as end_day, max(END_SLOT) as end_slot from HOURLY_EMPLOYEE_SCHEDULE" +
//                    " where EMPID=" + loginContext.ID + " and SCID=" + loginContext.SCID + " group by ORDER_ID";

            String sql = "select ORDER_ID,  WEEK, DAY, START_SLOT, END_SLOT from HOURLY_EMPLOYEE_SCHEDULE" +
                    " where EMPID=" + loginContext.ID + " and SCID=" + loginContext.SCID + " order by ORDER_ID";

            Statement stmt = dbConn.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Below is the Employee Schedule");
            while(rs.next()) {
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Order Id: " + rs.getString("ORDER_ID"));
                String timeStr = rs.getString("WEEK") + ", " + rs.getString("DAY") + ", " + rs.getString("START_SLOT") + ", " + rs.getString("END_SLOT");
                System.out.println("Time Slot(Week, Day, Start Slot, End Slot): " + timeStr);
            }
        } catch(Exception e) {
            System.out.println("Failed to print the schedule of the mechanic" + e);
        }
    }

    public void RequestTimeOff(LoginContext loginContext) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the week you want a time off");
        int week = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the day you want the time off");
        int day = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the slot start of time off");
        int startSlot = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the end slot id of the time off");
        int endSlot = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Press 1 to apply for time off and 2 to go back");
        int option = in.nextInt();
        if (option == 1) {
            try {
                DBConnection dbConn = DBConnection.getDBConnection();
                Statement stmt = dbConn.conn.createStatement();
                String sql = "SELECT * from HOURLY_EMPLOYEE_SCHEDULE where SCID=" + loginContext.SCID + " and EMPID=" + loginContext.ID +
                        " and WEEK=" + week + " and DAY=" + day;
                ResultSet rs = stmt.executeQuery(sql);
                int status = 1;
                while(rs.next()) {
                    if (rs.getInt("START_SLOT") <= startSlot && startSlot <= rs.getInt("END_SLOT") ) {
                        status = 0;
                    }
                    if (rs.getInt("START_SLOT") <= endSlot && endSlot <= rs.getInt("END_SLOT") ) {
                        status = 0;
                    }

                }
                sql = "INSERT INTO TIMEOFF_REQUEST VALUES( " + loginContext.SCID + ", " + loginContext.ID + ", " + day + ", " + week + ", " + startSlot + ", " + endSlot + ", " + status + ")";
                stmt.executeUpdate(sql);
                if (status == 0) {
                    System.out.println("Time off request rejected");
                } else {
                    sql = "INSERT INTO HOURLY_EMPLOYEE_SCHEDULE VALUES(" + loginContext.SCID + ", -1, " + loginContext.ID + ", " + week + ", " + day + ", " + startSlot + ", " + endSlot + ")";
                    stmt.executeUpdate(sql);
                    System.out.println("Time off request accepted");
                }
            } catch(Exception e) {
                System.out.println("Failed to apply for TimeOff" + e);
            }
        }
    }

    public void RequestSwap(LoginContext loginContext) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the start date week you want a swap");
        int start_week = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the start date day you want swap");
        int start_day = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the start date start slot of swap");
        int start_Slot = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the end date week you want a swap");
        int end_week = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the end date day you want swap");
        int end_day = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the end date end slot id of the swap");
        int end_Slot = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the employee ID of the mechanic that is being requested for swap");
        String empID = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter the start date week of requested employee");
        int rstart_week = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the start date day of requested employee");
        int rstart_day = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the start date start slot of requested employee");
        int rstart_Slot = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the end date week of requested employee");
        int rend_week = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the end date day of requested employee");
        int rend_day = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the end date end slot of requested employee");
        int rend_Slot = in.nextInt();
        in = new Scanner(System.in);

        System.out.println("Press 1 to send the request or 2 to go back");
        int option = in.nextInt();
        if (option == 1) {
            // JDBC Call
            try {
                DBConnection dbConn = DBConnection.getDBConnection();
                int status = 0;
                Connection conn = dbConn.createConnection();
                String sql = "select ORDER_ID,  min(WEEK) as start_week, min(DAY) as start_day, min(START_SLOT) as start_slot," +
                        " max(WEEK) as end_week, max(DAY) as end_day, max(END_SLOT) as end_slot from HOURLY_EMPLOYEE_SCHEDULE" +
                        " where ORDER_ID<>-1 and EMPID=" + loginContext.ID + " and SCID=" + loginContext.SCID + " group by ORDER_ID";
                Statement stmt = dbConn.conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                boolean selfSlot = false;
                while (rs.next()) {
                    if (rs.getInt("start_week") == start_week && rs.getInt("start_day") == start_day && rs.getInt("start_slot") == start_Slot
                    && rs.getInt("end_week") == end_week && rs.getInt("end_day") == end_day && rs.getInt("end_slot") == end_Slot) {
                        selfSlot = true;
                    }
                }
                sql = "select ORDER_ID,  min(WEEK) as start_week, min(DAY) as start_day, min(START_SLOT) as start_slot," +
                        " max(WEEK) as end_week, max(DAY) as end_day, max(END_SLOT) as end_slot from HOURLY_EMPLOYEE_SCHEDULE" +
                        " where ORDER_ID<>-1 and EMPID=" + empID + " and SCID=" + loginContext.SCID + " group by ORDER_ID";
                stmt = dbConn.conn.createStatement();
                rs = stmt.executeQuery(sql);
                boolean reqSlot = false;
                while (rs.next()) {
                    if (rs.getInt("start_week") == rstart_week && rs.getInt("start_day") == rstart_day && rs.getInt("start_slot") == rstart_Slot
                            && rs.getInt("end_week") == rend_week && rs.getInt("end_day") == rend_day && rs.getInt("end_slot") == rend_Slot) {
                        reqSlot = true;
                    }
                }
                if (!selfSlot || !reqSlot) {
                    System.out.println("Invalid Swap Request");
                    return;
                }
                sql = "SELECT * FROM HOURLY_EMPLOYEE_SCHEDULE where SCID=" + loginContext.SCID + " and EMPID=" + loginContext.ID;
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    if (rs.getInt("WEEK") == rstart_week && rs.getInt("DAY") == rstart_day && rs.getInt("START_SLOT") <= rstart_Slot && rstart_Slot <= rs.getInt("END_SLOT")) {
                        status = -1;
                        break;
                    } else if (rs.getInt("WEEK") == rend_week && rs.getInt("DAY") == rend_day && rs.getInt("START_SLOT") <= rend_Slot && rend_Slot <= rs.getInt("END_SLOT")) {
                        status = -1;
                        break;
                    }
                }
                if (status == -1) {
                    System.out.println("Invalid Swap Request");
                    return;
                }
                sql = "SELECT * FROM HOURLY_EMPLOYEE_SCHEDULE where SCID=" + empID + " and EMPID=" + loginContext.ID;
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    if (rs.getInt("WEEK") == start_week && rs.getInt("DAY") == start_day && rs.getInt("START_SLOT") <= start_Slot && start_Slot <= rs.getInt("END_SLOT")) {
                        status = -1;
                        break;
                    } else if (rs.getInt("WEEK") == end_week && rs.getInt("DAY") == end_day && rs.getInt("START_SLOT") <= end_Slot && end_Slot <= rs.getInt("END_SLOT")) {
                        status = -1;
                        break;
                    }
                }
                if (status == -1) {
                    System.out.println("Invalid Swap Request");
                    return;
                }

            } catch(Exception e) {
                System.out.println("Met with this exception while committing to the database " + e);
            }
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

    public ArrayList<MechanicFreeSlot> getSlots(LoginContext loginContext, Cart cart) {
        return getFreeSlot(loginContext, cart.getTotalDuration());
    }

    public ArrayList<MechanicFreeSlot> getFreeSlot(LoginContext loginContext, int duration) {
        MechanicFreeSlot result = new MechanicFreeSlot();
        ArrayList<String> mechanics = new ArrayList<String>();
        ArrayList<MechanicFreeSlot> resultSlots = new ArrayList<>();
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();

            String sql = "SELECT EMPID FROM EMPLOYEE WHERE SCID = " + loginContext.SCID + " AND EROLE = 'MECHANIC'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                mechanics.add(Integer.toString(rs.getInt(0)));
            }
        }catch (Exception e) {
            System.out.println("Cannot query mechanics from the service center" + e);
        }

        /*
        1. For each mechanic, get the schedule from the SCHEDULE table
        2. For each day and each week construct a list of busy slots -> and then convert it to free slots with a condition that any of the slots have
        duration greater than the specified duration
        3. Add it to a list of pairs which is the result.
         */
        // iterating over all the weeks

        for(String empid : mechanics) {
            ArrayList<Pair> schedule = new ArrayList<>();
            for (int i = 1; i < 5; i++) {
                for (int j = 1; j < 6; j++) {
                    // make a sql query to find out the records
                    // make an array list of Pairs
                    resultSlots = getFreeSlots(schedule, duration, resultSlots, i, j, empid);
                }

                if (resultSlots.size() > 3) {
                    break;
                }
            }
        }

        return resultSlots;
    }

    ArrayList<MechanicFreeSlot> getFreeSlots(ArrayList<Pair> schedule, int duration, ArrayList<MechanicFreeSlot> returnResult, int week, int day, String EMPID) {
        for(int i = 0; i < schedule.size(); i++) {

            if (i == schedule.size() - 1) {
                Pair last = schedule.get(i);
                if (12 - last.y >= duration) {
                    returnResult.add(new MechanicFreeSlot(11, last.y, week, day, EMPID));
                }
            }

            Pair pair1 = schedule.get(i);
            Pair pair2 = schedule.get(i + 1);

            if (pair2.x - pair1.y >= duration) {
                returnResult.add(new MechanicFreeSlot(pair1.y, pair2.x, week, day, EMPID));
            }
        }

        return returnResult;
    }
}
