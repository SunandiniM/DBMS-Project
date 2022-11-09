import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

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

        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
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
                DBConnection dbConn = DBConnection.getDBConnection();
                dbConn.createConnection();
                Statement stmt = dbConn.conn.createStatement();
                String sql = "select PRICE from OFFERS o, VEHICLE v where v.VIN_NO='" +
                        vin + "' and o.SCID=" + loginContext.SCID + " and o.SID=" + sid + " and o.MFG=v.MFG";
                ResultSet rs = stmt.executeQuery(sql);
                System.out.print("We are here");
                while (rs.next()) {
                    System.out.print("We are here 2");
                    System.out.println(rs.getString("PRICE"));
                    cart.MaintenanceCost = rs.getInt("PRICE");
                }
                System.out.println(cart.Maintainance);
                System.out.println(cart.MaintenanceCost);
            } catch(Exception e) {
                System.out.println("Failed to fetch maintenance schedule details");
                System.out.println(e);
            }
        }
        return cart;
    }

    public Cart ScheduleRepairService(String vin, Cart cart, LoginContext loginContext) {
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "select DISTINCT s.CATEGORY from REPAIR_SERVICE s, OFFERS o where o.scid=30001 and o.SID=s.SID";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs != null && rs.isBeforeFirst()) {
                while (rs.next()) {
//                    nextSchedule = rs.getString("SCHEDULE");
                }
            }
        } catch(Exception e) {
            System.out.println("Failed to fetch maintenance schedule details");
            System.out.println(e);
        }
        String[] listOfCategories = {"Engine Services", "Exhaust Services", "Electrical Services",
                "Transmission Services", "Tire Services", "Heating and AC Services"};

        for (int i = 0; i < listOfCategories.length; i++) {
            System.out.println("Enter " + (i + 1) + " to pick " + listOfCategories[i]);
        }

        System.out.println("Enter anything else to go back");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        if (option > listOfCategories.length) {
            return null;
        }

        //

        return cart;
    }

    // IMPLEMENT THIS ONCE JDBC IS IMPLEMENTED
    public Cart getServicesOfCategory(String category, Cart cart) {
        // call JDBC to get all the repair services

        return cart;
    }
}
