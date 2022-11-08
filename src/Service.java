import java.sql.Statement;
import java.util.Scanner;

public class Service {
    public void AskService() {
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("1. Add Service");
            System.out.println("2. Go Back");
            int option = in.nextInt();
            switch (option) {
                case 1:
                    in = new Scanner(System.in);
                    System.out.println("Enter Service ID:");
                    int serviceId = in.nextInt();
                    in = new Scanner(System.in);
                    System.out.println("Enter Service Name:");
                    String serviceName = in.next();
                    in = new Scanner(System.in);
                    System.out.println("Enter Service Category if it is a Repair Service. Else enter escape character '0':");
                    String serviceCategory = in.next();
                    in = new Scanner(System.in);
                    System.out.println("Enter Schedule ID if it is a Maintenance Service. Else enter escape character '0':");
                    int scheduleId = in.nextInt();
                    try {
                        DBConnection dbConn = DBConnection.getDBConnection();
                        dbConn.createConnection();
                        Statement stmt = dbConn.conn.createStatement();
                        String sql = "INSERT INTO SERVICE VALUES ('" + serviceId + "', '" + serviceName + "')";
                        stmt.executeUpdate(sql);
                        System.out.println("Successfully added a new service");
                        if (!serviceCategory.equals("0")) {
                            sql = "INSERT INTO REPAIR_SERVICE VALUES ('" + serviceId + "', '" + serviceCategory + "')";
                            stmt.executeUpdate(sql);
                            System.out.println("Successfully added repair service");
                        }
                        if (scheduleId != 0) {
                            sql = "INSERT INTO MAINTAINANCE_SERVICE VALUES ('" + scheduleId + "', '" + serviceId + "')";
                            stmt.executeUpdate(sql);
                            System.out.println("Successfully added maintenance service");
                        }
                        dbConn.closeConnection();
                    } catch(Exception e) {
                        System.out.println("Failed to add a new service");
                    }
                    break;
                case 2:
                    return;
            }
        }
    }

    // should have customer id, and service center id as input to this
    public void AskToShowServiceHistory(String CustomerID, String SCID) {
        System.out.println("Press 1 to see service history or anything else to go back");
        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        if (option == 1) {
            System.out.println("Enter the CAR's VIN number");
            String vinNumber = in.nextLine();
            // make a JDBC call to get the service history
        }else{
            return;
        }
    }
}
