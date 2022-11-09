import java.sql.*;
import java.util.*;

public class ServiceScheduler {
    public void Menu(LoginContext loginContext) {
        ResultSet rs;
        DBConnection dbConn;
        String sql;
        Statement stmt;
        Scanner in;
        String vinNumber;
        Double mileage;
        while (true) {
            in = new Scanner(System.in);
            System.out.println("Enter VIN Number");
            vinNumber = in.nextLine();
            System.out.println("Enter the current mileage");
            mileage = in.nextDouble();
            try {
                dbConn = DBConnection.getDBConnection();
                dbConn.createConnection();
                stmt = dbConn.conn.createStatement();
                sql = "select * from OWNS where SCID=" + loginContext.SCID + " and CID=" + loginContext.ID + " and VIN_NO='" + vinNumber + "'";
                rs = stmt.executeQuery(sql);
                if (rs != null && rs.isBeforeFirst()) {
                    break;
                } else {
                    System.out.println("No Car with this Vin number is owned by the Customer");
                }
            } catch(Exception e) {
                System.out.println("No Car with this Vin number is owned by the Customer");
                System.out.println(e);
            }
        }

        try {
            dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            stmt = dbConn.conn.createStatement();
            sql = "SELECT COUNT(*) FROM EMPLOYEES where SCID=" + loginContext.SCID + " and EROLE= 'MECHANIC'";
            rs = stmt.executeQuery(sql);
            int num = 4;
            while (rs.next()) {
                num = rs.getInt(1);
            }
            System.out.println( "Number of mechanics " + num);
            if (num < 3) {
                return;
            }

        } catch(Exception e) {
            System.out.println(e);
        }

        Cart cartObj = new Cart();
        while (true) {
            System.out.println("Enter 1 to Add Schedule Maintenance");
            System.out.println("Enter 2 to Add Schedule Repair");
            System.out.println("Enter 3 to View cart and select schedule time");
            System.out.println("Enter 4 to go back");
            int option = in.nextInt();
            switch (option){
                case 1:
                    cartObj = ScheduleMaintainance(vinNumber, cartObj, loginContext);
                    break;
                case 2:
                    cartObj = ScheduleRepairService(vinNumber, cartObj, loginContext);
                    break;
                case 3:
                    ViewCartAndSelectScheduleTime(loginContext, cartObj);
                    break;
                case 4:
                    return;
                default:
                    break;
            }
        }
    }

    public void ViewCartAndSelectScheduleTime(LoginContext loginContext, Cart cart) {

        viewCart(cart);

        System.out.println("Press 1 to continue with Scheduling");
        System.out.println("Press 2 to go back");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        switch (option) {
            case 1:
                ChooseSlots(loginContext, cart);
            case 2:
                return;
            default:
                ViewCartAndSelectScheduleTime(loginContext, cart);
        }
    }

    public void ChooseSlots(LoginContext loginContext, Cart cart) {
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "select * from HOURLY_EMPLOYEE_SCHEDULE H1 where H1.END_SLOT in (select MAX(H2.END_SLOT) " +
                    "from HOURLY_EMPLOYEE_SCHEDULE H2 where H2.SCID=" + loginContext.SCID + " and H2.EMPID=H1.EMPID and DAY in " +
                "(select MAX(H3.DAY) from HOURLY_EMPLOYEE_SCHEDULE H3 where H3.SCID=" + loginContext.SCID + " and H3.EMPID=H1.EMPID and DAY in " +
                    "(select MAX(H4.WEEK) from HOURLY_EMPLOYEE_SCHEDULE H4 where H4.SCID=" + loginContext.SCID + " and H4.EMPID=H1.EMPID))) and " +
                    "H1.EMPID in (select EMPID from EMPLOYEES E where E.SCID=" + loginContext.SCID + " and E.EMPID=H1.EMPID and E.EROLE='MECHANIC')";
            ResultSet rs = stmt.executeQuery(sql);
            List<String> empIdList = new ArrayList<>();
            List<List<Integer>> currSlotDetails = new ArrayList<>();
            while (rs.next()) {
                empIdList.add(rs.getString("EMPID"));
                currSlotDetails.add(Arrays.asList(rs.getInt("WEEK"), rs.getInt("DAY"), rs.getInt("END_SLOT")));
            }
            if (empIdList.size() > 0) {
                sql = "select EMPID from EMPLOYEES E where E.SCID=" + loginContext.SCID + " and E.EROLE='MECHANIC' AND empid not in (" + String.join(", ", empIdList) + ")";
            } else {
                sql = "select EMPID from EMPLOYEES E where E.SCID=" + loginContext.SCID + " and E.EROLE='MECHANIC'";
            }

            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                empIdList.add(rs.getString("EMPID"));
                currSlotDetails.add(Arrays.asList(1, 1, 0));
            }
            int numDays = 5;
            try {
                sql = "select OPEN_SATURDAY from SERVICE_CENTER where SCID=" + loginContext.SCID;
                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    if (rs.getInt("OPEN_SATURDAY") == 1) {
                        numDays += 1;
                    }
                }
            } catch(Exception e) {
                numDays = 5;
            }
            int duration = cart.getTotalDuration();
            System.out.println("Total duration Value: " + duration);
            SelectedSlots nextStartSlots = getStartSlots(empIdList, currSlotDetails, numDays, duration);
            SelectedSlots nextEndSlots = getEndSlots(nextStartSlots, numDays, duration);
            System.out.println("Enter the time slot number of your choice");
            for (int i=0;i<nextEndSlots.selectedIds.size();i++) {
                System.out.println("--------------------------------------------------------------------------");
                String timeStr = nextStartSlots.selectedSlots.get(i).get(0) + ", " + nextStartSlots.selectedSlots.get(i).get(1) + ", " + nextStartSlots.selectedSlots.get(i).get(2);
                System.out.println("Time Slot " + (i + 1));
                System.out.println("Mechanic ID: " + nextEndSlots.selectedIds.get(i));
                System.out.println("Start week, day, hour: " + timeStr);
                timeStr = nextEndSlots.selectedSlots.get(i).get(0) + ", " + nextEndSlots.selectedSlots.get(i).get(1) + ", " + nextEndSlots.selectedSlots.get(i).get(2);
                System.out.println("End week, day, hour: " + timeStr);
            }
            Scanner in = new Scanner(System.in);
            int option = in.nextInt();
            System.out.println("Selected timeslot is " + option);
            SubmitOrder(loginContext, cart, nextStartSlots, nextEndSlots, option, numDays);
            System.out.println("Booked Slot and Invoice generated");
        } catch(Exception e) {
            System.out.println("Failed to fetch slots");
            System.out.println(e);
        }
    }

    public SelectedSlots getStartSlots(List<String> empIds, List<List<Integer>> slots, int numDays, int duration) {
        List<List<Integer>> nextSlots = new ArrayList<>();
        List<String> selectedIds = new ArrayList<>();
        for (int i=0;i<slots.size();i++) {
            int weekVal = slots.get(i).get(0);
            int dayVal = slots.get(i).get(1);
            int currSlotVal = slots.get(i).get(2);
            currSlotVal += 1;
            if (currSlotVal > 11 || (11 - currSlotVal) < duration) {
                currSlotVal = 1;
                dayVal += 1;
                if (dayVal > numDays) {
                    dayVal = 1;
                    weekVal += 1;
                    if (weekVal > 4) {
                        continue;
                    }
                }
            }
            int currTime = ((dayVal - 1) * 11) + currSlotVal + duration;
            if (currTime > 50) {
                currSlotVal = 1;
                dayVal = 1;
                weekVal += 1;
                if (weekVal > 4) {
                    continue;
                }
            }
            selectedIds.add(empIds.get(i));
            nextSlots.add(Arrays.asList(weekVal, dayVal, currSlotVal));
        }
        return new SelectedSlots(selectedIds, nextSlots);
    }

    public SelectedSlots getEndSlots(SelectedSlots startSlots, int numDays, int duration) {
        List<List<Integer>> nextSlots = new ArrayList<>();
        List<String> selectedIds = new ArrayList<>();
        for (int i=0;i<startSlots.selectedSlots.size();i++) {
            int endWeek = startSlots.selectedSlots.get(i).get(0);
            int endDay = startSlots.selectedSlots.get(i).get(1);
            int endSlot = startSlots.selectedSlots.get(i).get(2);
            int currDurration = duration;
            while (currDurration != 0) {
                if (currDurration > (11 - endSlot)) {
                    currDurration -= (11 - endSlot);
                    endSlot = 1;
                    endDay += 1;
                    if (endDay > numDays) {
                        endDay = 1;
                        endWeek += 1;
                    }
                } else {
                    endSlot = endSlot + currDurration;
                    currDurration = 0;
                }
            }
            selectedIds.add(startSlots.selectedIds.get(i));
            nextSlots.add(Arrays.asList(endWeek, endDay, endSlot));
        }
        return new SelectedSlots(selectedIds, nextSlots);
    }

    public Cart ScheduleMaintainance(String vin, Cart cart, LoginContext loginContext) {
        String nextSchedule = "A";
        DBConnection dbConn;
        Statement stmt;
        try {
            dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            stmt = dbConn.conn.createStatement();
            String sql = "select SCHEDULE from VEHICLE where VIN_NO='" + vin + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs != null && rs.isBeforeFirst()) {
                while (rs.next()) {
                    nextSchedule = rs.getString("SCHEDULE");
                }
            }
        } catch(Exception e) {
            System.out.println("Failed to fetch maintenance schedule details");
            System.out.println(e);
        }

        if (nextSchedule.equals("A")) {
            nextSchedule = "B";
        } else if (nextSchedule.equals("B")) {
            nextSchedule = "C";
        } else {
            nextSchedule = "A";
        }
        int cost = 0;
        int serviceDuration = 0;
        int sid = 113;
        if (nextSchedule.equals("B")) {
            sid = 114;
        } else if (nextSchedule.equals("C")) {
            sid = 115;
        }
        try {
            DBConnection dbConn2 = DBConnection.getDBConnection();
            Statement stmt2 = dbConn2.conn.createStatement();
            String sql2 = "select PRICE, DURATION from OFFERS o, VEHICLE v where v.VIN_NO='" +
                    vin + "' and o.SCID=" + loginContext.SCID + " and o.SID=" + sid + " and o.MFG=v.MFG";
            ResultSet rs2 = stmt2.executeQuery(sql2);
            while (rs2.next()) {
                cost = rs2.getInt("PRICE");
                serviceDuration = rs2.getInt("DURATION");
            }
        } catch(Exception e) {
            System.out.println("Failed to fetch maintenance schedule details");
            System.out.println(e);
        }
        if (cart.getTotalDuration() + serviceDuration > 11) {
            System.out.println("Cannot Schedule this service as the total services duration exceeds 11 hours");
        }
        Scanner in = new Scanner(System.in);
        System.out.println("Press 1 to add maintenance schedule " + nextSchedule + " in the cart");
        System.out.println("Press 2 to go back");
        int option = in.nextInt();
        if (option == 1) {
            if (cart.getTotalDuration() + serviceDuration > 11) {
                System.out.println("Cannot Schedule this service as the total services duration exceeds 11 hours");
                return cart;
            }
            cart.Maintainance = nextSchedule;
            cart.MaintenanceCost = cost;
            cart.MaintenanceDuration = serviceDuration;
        }
        cart.vinNumber = vin;
        return cart;
    }

    public Cart ScheduleRepairService(String vin, Cart cart, LoginContext loginContext) {
        Scanner in = new Scanner(System.in);
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "select DISTINCT s.CATEGORY as CATEGORY from REPAIR_SERVICE s, OFFERS o where o.scid=" + loginContext.SCID + " and o.SID=s.SID";
            ResultSet rs = stmt.executeQuery(sql);
            int i = 1;
            System.out.println("Enter your choice from below list");
            List<String> categories = new ArrayList<>();
            while (rs.next()) {
                System.out.println("" + i + ". " + rs.getString("CATEGORY"));
                categories.add(rs.getString("CATEGORY"));
                i++;
            }
            System.out.println("" + i + ". Go Back");
            int option = in.nextInt();
            if (option == i || option < 0 || option > categories.size()) {
                return cart;
            } else {
                return getServicesOfCategory(categories.get(option - 1), vin, cart, loginContext);
            }
        } catch(Exception e) {
            System.out.println("Failed to fetch repair schedule categories");
            System.out.println(e);
        }
        cart.vinNumber = vin;
        return cart;
    }

    // IMPLEMENT THIS ONCE JDBC IS IMPLEMENTED
    public Cart getServicesOfCategory(String category, String vin, Cart cart, LoginContext loginContext) {
        Scanner in = new Scanner(System.in);
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "select s.SID as SID, s.SNAME as SNAME, o.PRICE as PRICE, o.DURATION as Duration from SERVICE S, REPAIR_SERVICE r, OFFERS o, VEHICLE v where v.VIN_NO='" + vin + "' and v.MFG=o.MFG and o.scid=" + loginContext.SCID + " and o.SID=s.SID and r.CATEGORY='" + category + "' and r.sid=s.sid";
            ResultSet rs = stmt.executeQuery(sql);
            int i = 1;
            System.out.println("Enter your choice from below list");
            List<List<String>> services = new ArrayList<>();
            while (rs.next()) {
                if (!cart.RepairServiceList.contains(rs.getString("SID"))) {
                    System.out.println("" + i + ". " + rs.getString("SNAME"));
                    services.add(Arrays.asList(rs.getString("SID"), rs.getString("PRICE"), rs.getString("DURATION")));
                    i++;
                }
            }
            System.out.println("" + i + ". Go Back");
            int option = in.nextInt();
            if (option == i || option < 0 || option > services.size()) {
                return cart;
            } else {
                if (cart.getTotalDuration() + Integer.parseInt(services.get(option - 1).get(2)) > 11) {
                    System.out.println("Cannot Schedule this service as the total services duration exceeds 11 hours");
                    return cart;
                }
                cart.RepairServiceList.add(services.get(option - 1).get(0));
                cart.RepairServiceCostList.add(Integer.parseInt(services.get(option - 1).get(1)));
                cart.RepairServiceDurationList.add(Integer.parseInt(services.get(option - 1).get(2)));
            }
        } catch(Exception e) {
            System.out.println("Failed to fetch repair schedule categories");
            System.out.println(e);
        }
        return cart;
    }

    public void viewCart(Cart cartObj) {
        Scanner in = new Scanner(System.in);
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String ids = cartObj.getServicesList();
            String sql = "select SNAME from SERVICE where SID in " + ids;
            ResultSet rs = stmt.executeQuery(sql);
            int i = 1;
            System.out.println("Cart Items");
            while (rs.next()) {
                System.out.println("" + i + ". " + rs.getString("SNAME"));
                i++;
            }
        } catch(Exception e) {
            System.out.println("Failed to fetch cart");
            System.out.println(e);
        }
    }

    public void SubmitOrder(LoginContext loginContext, Cart cart, SelectedSlots startSlots, SelectedSlots endSlots, int optionVal, int numDays) {
        // assumes the cart is filled and has th
        int cost = cart.getTotalCost();
        int invoiceID = -1;

        // First create the invoice -> generate the id, put cost as bill, status 0 -> unpaid
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "SELECT COUNT(*) AS S FROM INVOICE";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                invoiceID = rs.getInt("S");
            }
            invoiceID += 1;
            sql = "INSERT INTO INVOICE(ORDER_ID, SCID, CID, BILL, STATUS) VALUES (" + invoiceID + "," + loginContext.SCID + ", " + loginContext.ID + ", " + cost + ", 0)";
            stmt.executeUpdate(sql);
        }catch (Exception e) {
            System.out.println("Failed to add in INVOICE " + e);
        }
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            try{
                System.out.println();
                for (int i = 0; i < cart.RepairServiceList.size(); i++) {
                    String serviceID = cart.RepairServiceList.get(i);
                    Statement stmt = dbConn.createConnection().createStatement();
                    String sql = "INSERT INTO SERVICE_EVENT VALUES(" + invoiceID + "," + loginContext.SCID + "," + loginContext.ID + "," + serviceID + ",'" + cart.vinNumber + "')";
                    System.out.println(sql);
                    stmt.executeUpdate(sql);
                }
                if (!cart.Maintainance.equals("")) {
                    int serviceID = 0;
                    if (cart.Maintainance.equals("A")){
                        serviceID = 113;
                    } else if (cart.Maintainance.equals("B")) {
                        serviceID = 114;
                    } else  {
                        serviceID = 115;
                    }
                        Statement stmt = dbConn.createConnection().createStatement();
                        String sql = "INSERT INTO SERVICE_EVENT VALUES(" + invoiceID + "," + loginContext.SCID + "," + loginContext.ID + "," + serviceID + ",'" + cart.vinNumber + "')";
                        stmt.executeUpdate(sql);

                        Statement stmt2 = dbConn.createConnection().createStatement();
                        sql = "UPDATE VEHICLE SET SCHEDULE = '" + cart.Maintainance + "' WHERE VIN_NO = '" + cart.vinNumber + "'";
                        System.out.println(sql);
                        stmt2.executeUpdate(sql);
                }
            }catch (Exception e) {
                System.out.println("Failed to put in service event " + e);
            }
        }catch (Exception e) {
            System.out.println("Trying to update service event " + e);
        }
        bookSlot(loginContext, startSlots, endSlots, optionVal, invoiceID, numDays);
    }

    public void bookSlot(LoginContext loginContext, SelectedSlots startSlots, SelectedSlots endSlots, int optionVal, int invoiceId, int numDays) {
        int startW = startSlots.selectedSlots.get(optionVal - 1).get(0);
        int startD = startSlots.selectedSlots.get(optionVal - 1).get(1);
        int startS = startSlots.selectedSlots.get(optionVal - 1).get(2);

        int endW = endSlots.selectedSlots.get(optionVal - 1).get(0);
        int endD = endSlots.selectedSlots.get(optionVal - 1).get(1);
        int endS = endSlots.selectedSlots.get(optionVal - 1).get(2);
        while (true) {
            int endVal = 11;
            if (startW == endW && startD == endD) {
                endVal = endS;
            }
            try {
                DBConnection dbConn = DBConnection.getDBConnection();
                dbConn.createConnection();
                Statement stmt = dbConn.conn.createStatement();
                String sql = "INSERT INTO HOURLY_EMPLOYEE_SCHEDULE(SCID, ORDER_ID, EMPID, WEEK, DAY, START_SLOT, END_SLOT) VALUES ("
                        + loginContext.SCID + "," + invoiceId + ", " + startSlots.selectedIds.get(optionVal) + ", "
                        + startW + ", " + startD + ", " + startS + ", " + endVal + ")";
                stmt.executeUpdate(sql);
            } catch(Exception e) {
                System.out.println("Failed to book slot");
                System.out.println(e);
                return;
            }
            if (startW == endW && startD == endD) {
                break;
            }
            startS = 1;
            startD += 1;
            if (startD > numDays) {
                startD = 1;
                startW += 1;
            }
        }
    }
}
