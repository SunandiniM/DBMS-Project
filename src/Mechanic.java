import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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

        System.out.println("Enter the week you of the time off");
        int week = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the day you of the time off");
        int day = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the slot start of the time off");
        int startSlot = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the end slot of the time off");
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
        System.out.println("Enter the week you want a swap");
        int week = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the day you want a swap");
        int day = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the start slot you want a swap");
        int start_slot = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the end slot you want a swap");
        int end_slot = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the employee ID of the mechanic that is being requested for swap");
        String empID = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter the week you want to request");
        int rweek = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the day you want to request");
        int rday = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the start slot you want to request");
        int rstart_slot = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter the end slot you want to request");
        int rend_slot = in.nextInt();
        in = new Scanner(System.in);

        System.out.println("Press 1 to send the request or 2 to go back");
        int option = in.nextInt();
        if (option == 1) {
            try {
                int selfOrderId = 0;
                int reqOrderId = 0;
                DBConnection dbConn = DBConnection.getDBConnection();
                String sql = "select ORDER_ID, WEEK, DAY, START_SLOT, END_SLOT from HOURLY_EMPLOYEE_SCHEDULE" +
                        " where ORDER_ID!=-1 and EMPID=" + loginContext.ID + " and SCID=" + loginContext.SCID + " order by ORDER_ID";
                Statement stmt = dbConn.conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                boolean selfSlot = false;
                while (rs.next()) {
                    if (rs.getInt("WEEK") == week && rs.getInt("DAY") == day
                            && rs.getInt("START_SLOT") == start_slot && rs.getInt("END_SLOT") == end_slot) {
                        selfSlot = true;
                        selfOrderId = rs.getInt("ORDER_ID");
                        break;
                    }
                }
                sql = "select ORDER_ID, WEEK, DAY, START_SLOT, END_SLOT from HOURLY_EMPLOYEE_SCHEDULE" +
                        " where ORDER_ID<>-1 and EMPID=" + empID + " and SCID=" + loginContext.SCID + " order by ORDER_ID";
                stmt = dbConn.conn.createStatement();
                rs = stmt.executeQuery(sql);
                boolean reqSlot = false;
                while (rs.next()) {
                    if (rs.getInt("WEEK") == rweek && rs.getInt("DAY") == rday
                            && rs.getInt("START_SLOT") == rstart_slot && rs.getInt("END_SLOT") == rend_slot) {
                        reqSlot = true;
                        reqOrderId = rs.getInt("ORDER_ID");
                        break;
                    }
                }
                if (!selfSlot || !reqSlot) {
                    System.out.println("Invalid Swap Request, swap time is invalid");
                    insertSwapRequest(loginContext.SCID, loginContext.ID, empID, week, day, start_slot, end_slot, rweek, rday, rstart_slot, rend_slot, -1);
                    return;
                }
                HashMap<Integer, Integer> selfDurations = new HashMap<>();
                selfDurations.put(1, 0);
                selfDurations.put(2, 0);
                selfDurations.put(3, 0);
                selfDurations.put(4, 0);
                sql = "SELECT * FROM HOURLY_EMPLOYEE_SCHEDULE where SCID=" + loginContext.SCID + " and EMPID=" + loginContext.ID + " and ORDER_ID<>" + selfOrderId;
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    int currDurr = rs.getInt("WEEK") + rs.getInt("END_SLOT") - rs.getInt("START_SLOT");
                    selfDurations.put(rs.getInt("WEEK"), currDurr);
                    if (rs.getInt("WEEK") == rweek && rs.getInt("DAY") == rday) {
                        if ((rs.getInt("START_SLOT") <= rstart_slot && rstart_slot <= rs.getInt("END_SLOT")) ||
                                (rs.getInt("START_SLOT") <= rend_slot && rend_slot <= rs.getInt("END_SLOT"))) {
                            System.out.println("Invalid Swap Request, swap time is not available");
                            insertSwapRequest(loginContext.SCID, loginContext.ID, empID, week, day, start_slot, end_slot, rweek, rday, rstart_slot, rend_slot, -1);
                            return;
                        }
                    }
                }
                HashMap<Integer, Integer> reqDurations = new HashMap<>();
                reqDurations.put(1, 0);
                reqDurations.put(2, 0);
                reqDurations.put(3, 0);
                reqDurations.put(4, 0);
                sql = "SELECT * FROM HOURLY_EMPLOYEE_SCHEDULE where SCID=" + loginContext.SCID + " and EMPID=" + empID + " and ORDER_ID<>" + reqOrderId;
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    int currDurr = rs.getInt("WEEK") + rs.getInt("END_SLOT") - rs.getInt("START_SLOT");
                    reqDurations.put(rs.getInt("WEEK"), currDurr);
                    if (rs.getInt("WEEK") == week && rs.getInt("DAY") == day) {
                        if ((rs.getInt("START_SLOT") <= start_slot && start_slot <= rs.getInt("END_SLOT")) ||
                                (rs.getInt("START_SLOT") <= end_slot && end_slot <= rs.getInt("END_SLOT"))) {
                            System.out.println("Invalid Swap Request, swap time is not available");
                            insertSwapRequest(loginContext.SCID, loginContext.ID, empID, week, day, start_slot, end_slot, rweek, rday, rstart_slot, rend_slot, -1);
                            return;
                        }
                    }
                }
                selfDurations.put(week, selfDurations.get(week) - end_slot + start_slot);
                reqDurations.put(rweek, reqDurations.get(rweek) - rend_slot + rstart_slot);
                if ((selfDurations.get(rweek) + (rend_slot- rstart_slot) > 50) || (reqDurations.get(week) + (end_slot- start_slot) > 50)) {
                    System.out.println("Invalid Swap Request, work hours exceed 50");
                    insertSwapRequest(loginContext.SCID, loginContext.ID, empID, week, day, start_slot, end_slot, rweek, rday, rstart_slot, rend_slot, -1);
                    return;
                }
                insertSwapRequest(loginContext.SCID, loginContext.ID, empID, week, day, start_slot, end_slot, rweek, rday, rstart_slot, rend_slot, 0);
                System.out.println("Successfully added Swap Request");
            } catch(Exception e) {
                System.out.println("Met with this exception while committing to the database " + e);
            }
        }
    }

    public void insertSwapRequest(String SCID, String empid, String req_empid, int week, int day, int start, int stop, int rweek, int rday, int rstart,  int rstop, int status) {
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "INSERT INTO SWAP_REQUEST VALUES('" + SCID + "', '" + empid + "', '" + req_empid
                    + "', '" + day + "', '" + week + "', '" + start + "', '" + stop
                    + "', '" + rday + "', '" + rweek + "', '" + rstart + "', '" + rstop +"')";
            stmt.executeUpdate(sql);
        } catch(Exception e) {
            System.out.println("Failed to insert swap request");
            System.out.println(e);
        }
    }

    public void AcceptRejectSwapRequests(LoginContext loginContext) {

        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "SELECT S.REQUEST_ID, E.FNAME, E.LNAME, S.WEEK, S.DAY, S.START_SLOT, S.END_SLOT," +
                    " S.REQ_WEEK, S.REQ_DAY, S.REQ_START_SLOT, S.REQ_END_SLOT" +
                    " FROM SWAP_REQUEST S, EMPLOYEES E where S.SCID=" + loginContext.SCID + " and REQ_EMP_ID=" + loginContext.ID +
                    " and E.SCID=" + loginContext.SCID + " and E.EMPID=S.EMPID and S.STATUS=0";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("---------------------------------------------------------------------------");
                System.out.println("Request ID: " + rs.getString("REQUEST_ID"));
                System.out.println("Requested by " + rs.getString("FNAME") + " " + rs.getString("LNAME"));
                String timeStr = rs.getString("WEEK") + ", " + rs.getString("DAY") + ", " + rs.getString("START_SLOT") + ", " + rs.getString("END_SLOT");
                System.out.println("Requested for time slot (Week, Day, Start Slot, End Slot): " + timeStr);
                timeStr = rs.getString("REQ_WEEK") + ", " + rs.getString("REQ_DAY") + ", " + rs.getString("REQ_START_SLOT") + ", " + rs.getString("REQ_END_SLOT");
                System.out.println("Swap with time slot (Week, Day, Start Slot, End Slot): " + timeStr);
            }
        } catch(Exception e) {
            System.out.println("Failed to retrieve swap request");
            System.out.println(e);
        }

        System.out.println("Press 1 to manage swap requests");
        System.out.println("Press 2 to go back");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        if (option == 1) {
            ManageSwapRequests(loginContext);
        }
    }

    public void ManageSwapRequests(LoginContext loginContext) {
        System.out.println("Enter the request ID of the swap request");
        Scanner in = new Scanner(System.in);
        String requestID = in.nextLine();

        System.out.println("Press 1 to Accept the request\n2 to reject it\n3 to go back");
        int option = in.nextInt();
        int status = 0;
        int week = 0, day = 0, start_slot = 0,end_slot =0, rweek = 0, rday = 0, rstart_slot=0,rend_slot = 0;
        String empId = "";
        if (option == 1) {
            status = 1;
            try {
                DBConnection dbConn = DBConnection.getDBConnection();
                Statement stmt = dbConn.conn.createStatement();
                String sql = "SELECT * FROM SWAP_REQUEST S where S.REQUEST_ID=" + requestID;
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    week = rs.getInt("WEEK");
                    day = rs.getInt("DAY");
                    start_slot = rs.getInt("START_SLOT");
                    end_slot = rs.getInt("END_SLOT");

                    rweek = rs.getInt("REQ_WEEK");
                    rday = rs.getInt("REQ_DAY");
                    rstart_slot = rs.getInt("REQ_START_SLOT");
                    rend_slot = rs.getInt("REQ_END_SLOT");

                    empId = rs.getString("EMPID");
                }
            } catch(Exception e) {
                System.out.println("Failed to Approve/Reject Swap Request " + e);
                return;
            }
        } else if (option == 2) {
            status = -1;
        }
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            Connection conn = dbConn.createConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE SWAP_REQUEST SET STATUS=? WHERE REQUEST_ID=?");
            stmt.setString(1, String.valueOf(status));
            stmt.setString(2, requestID);
            stmt.executeQuery();
            if(stmt.executeUpdate() == 0)
                System.out.println("Request to approve/reject swap request failed to submit");
        } catch(Exception e) {
            System.out.println("Failed to Approve/Reject Swap Request " + e);
        }
        if (option == 2) {
            System.out.println("Rejected the Swap Request Successfully");
        } else {
            int selfOrderId = 0;
            int reqOrderId = 0;
            DBConnection dbConn = DBConnection.getDBConnection();

            try {
                String sql = "select ORDER_ID from HOURLY_EMPLOYEE_SCHEDULE where WEEK=" + rweek + " and DAY="+rday +" and START_SLOT="+rstart_slot+
                        " and END_SLOT=" + rend_slot + " and SCID=" + loginContext.SCID + " and EMPID=" + loginContext.ID;
                Statement stmt = dbConn.conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    selfOrderId = rs.getInt("ORDER_ID");
                }
            } catch(Exception e) {
                System.out.println("Failed to Fetch order id details to swap the request " + e);
                return;
            }

            try {
                String sql = "select ORDER_ID from HOURLY_EMPLOYEE_SCHEDULE where WEEK=" + week + " and DAY="+day +" and START_SLOT="+start_slot+
                        " and END_SLOT=" + end_slot + " and SCID=" + loginContext.SCID + " and EMPID=" + empId;
                Statement stmt = dbConn.conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    reqOrderId = rs.getInt("ORDER_ID");
                }
            } catch(Exception e) {
                System.out.println("Failed to Fetch order id details to swap the request " + e);
                return;
            }
            try {
                String sql = "delete from HOURLY_EMPLOYEE_SCHEDULE where WEEK=" + rweek + " and DAY="+rday +" and START_SLOT="+rstart_slot+
                        " and ORDER_ID=" + selfOrderId + " and SCID=" + loginContext.SCID + " and EMPID=" + loginContext.ID;
                Statement stmt = dbConn.conn.createStatement();
                stmt.executeUpdate(sql);
                sql = "insert into HOURLY_EMPLOYEE_SCHEDULE values('" + loginContext.SCID + "', '"+ reqOrderId + "', '"+ loginContext.ID + "', '"+ week + "', '" + day + "', '" +start_slot + "', '"+
                        end_slot + "')";
                stmt.executeUpdate(sql);

            } catch(Exception e) {
                System.out.println("Failed to swap time stamp " + e);
                return;
            }

            try {
                String sql = "delete from HOURLY_EMPLOYEE_SCHEDULE where WEEK=" + week + " and DAY="+day +" and START_SLOT="+start_slot+
                        " and ORDER_ID=" + reqOrderId + " and SCID=" + loginContext.SCID + " and EMPID=" + empId;
                Statement stmt = dbConn.conn.createStatement();stmt.executeUpdate(sql);
                sql = "insert into HOURLY_EMPLOYEE_SCHEDULE values('" + loginContext.SCID + "', '"+ selfOrderId + "', '"+ empId + "', '"+ rweek + "', '" + rday + "', '" +rstart_slot + "', '"+
                        rend_slot + "')";
                stmt.executeUpdate(sql);
                System.out.println("Successfully Accepted Swap request");
            } catch(Exception e) {
                System.out.println("Failed to delete swap time stamp " + e);
            }
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
