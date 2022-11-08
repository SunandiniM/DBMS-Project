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
                    long serviceId = Long.parseLong(in.nextLine());
                    in = new Scanner(System.in);
                    System.out.println("Enter Service Name:");
                    String serviceName = in.nextLine();
                    in = new Scanner(System.in);
                    System.out.println("Enter Service Category if it is a Repair Service. Else enter escape character '0':");
                    String serviceCategory = in.nextLine();
                    in = new Scanner(System.in);
                    System.out.println("Enter Schedule ID if it is a Maintenance Service. Else enter escape character '0':");
                    long scheduleId = Long.parseLong(in.nextLine());
                    try {
                        DBConnection dbConn = DBConnection.getDBConnection();
                        dbConn.createConnection();
                        Statement stmt = dbConn.conn.createStatement();
                        String sql = "INSERT INTO SERVICE VALUES ('" + serviceId + "', '" + serviceName + "')";
                        stmt.executeUpdate(sql);
                        System.out.println("Successfully added a new service");
                        if (!serviceCategory.equals("0")) {
                            try {
                                sql = "INSERT INTO REPAIR_SERVICE VALUES ('" + serviceId + "', '" + serviceCategory + "')";
                                stmt.executeUpdate(sql);
                                System.out.println("Successfully added repair service");
                            } catch(Exception e) {
                                System.out.println("Failed to add repair service");
                                System.out.println(e);
                            }
                        }
                        if (scheduleId != 0) {
                            try {
                                sql = "INSERT INTO MAINTAINANCE_SERVICE VALUES ('" + scheduleId + "', '" + serviceId + "')";
                                stmt.executeUpdate(sql);
                                System.out.println("Successfully added maintenance service");
                            } catch(Exception e) {
                                System.out.println("Failed to add maintenance service");
                                System.out.println(e);
                            }
                        }
                    } catch(Exception e) {
                        System.out.println("Failed to add a new service");
                        System.out.println(e);
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
