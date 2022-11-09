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
                    break;
                case 4:
                    return;
                default:
                    break;
            }
        }
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
        if (nextSchedule == "A") {
            nextSchedule = "B";
        } else if (nextSchedule == "B") {
            nextSchedule = "C";
        } else {
            nextSchedule = "A";
        }
        Scanner in = new Scanner(System.in);
        System.out.println("Press 1 to add maintenance schedule " + nextSchedule + " in the cart");
        System.out.println("Press 2 to go back");
        int option = in.nextInt();
        if (option == 1){
            cart.Maintainance = nextSchedule;
            cart.MaintenanceCost = 0;
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
                    cart.MaintenanceCost = rs2.getInt("PRICE");
                    cart.MaintenanceDuration = rs2.getInt("DURATION");
                }
            } catch(Exception e) {
                System.out.println("Failed to fetch maintenance schedule details");
                System.out.println(e);
            }
        }
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
        return cart;
    }

    // IMPLEMENT THIS ONCE JDBC IS IMPLEMENTED
    public Cart getServicesOfCategory(String category, String vin, Cart cart, LoginContext loginContext) {
//        select s.SNAME as SNAME from SERVICE S, REPAIR_SERVICE r, OFFERS o, VEHICLE v where v.VIN_NO='88TSM888' and v.MFG=o.MFG and o.scid=30003 and o.SID=s.SID and r.CATEGORY='Tire Services' and r.sid=s.sid;
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
                System.out.println("" + i + ". " + rs.getString("SNAME"));
                services.add(Arrays.asList(rs.getString("SID"), rs.getString("PRICE"), rs.getString("DURATION")));
                i++;
            }
            System.out.println("" + i + ". Go Back");
            int option = in.nextInt();
            if (option == i || option < 0 || option > services.size()) {
                return cart;
            } else {
                cart.RepairServiceList.add(services.get(i).get(0));
                cart.RepairServiceCostList.add(Integer.parseInt(services.get(i).get(1)));
                cart.RepairServiceDurationList.add(Integer.parseInt(services.get(i).get(2)));
            }
        } catch(Exception e) {
            System.out.println("Failed to fetch repair schedule categories");
            System.out.println(e);
        }
        System.out.println(cart.toString());
        return cart;
    }
}
