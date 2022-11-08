import java.util.Scanner;
import java.sql.*;

public class AdminLandingPage {
    public void AskAdmin() throws SQLException {
        System.out.println("Press 1 for System Setup");
        System.out.println("Press 2 to Add new store");
        System.out.println("Press 3 to Add new service");
        System.out.println("Press 4 for Logout");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        while (option != 4) {
            switch (option){
                case 1:
                    // system setup object initialise
                    SystemSetup sysObj = new SystemSetup();
                    sysObj.AskSystemSetup();
                    break;
                case 2:
                    // Add a new store
                    break;
                case 3:
                    in = new Scanner(System.in);
                    System.out.println("Enter Service ID:");
                    int serviceId = in.nextInt();
                    in = new Scanner(System.in);
                    System.out.println("Enter Service Name:");
                    String serviceName = in.next();
                    in = new Scanner(System.in);
                    System.out.println("Enter Service Category:");
                    String serviceCategory = in.next();
                    in = new Scanner(System.in);
                    System.out.println("Enter Service Duration:");
                    int serviceDuration = in.nextInt();
                    try {
                        DBConnection dbConn = DBConnection.getDBConnection();
                        dbConn.createConnection();
                        Statement stmt = dbConn.conn.createStatement();
                        String sql = "INSERT INTO SERVICE VALUES ('" + serviceId + "', '" + serviceName + "')";
                        stmt.executeUpdate(sql);
                        sql = "INSERT INTO REPAIR_SERVICE VALUES ('" + serviceId + "', '" + serviceCategory + "')";
                        stmt.executeUpdate(sql);
                        System.out.println("Successfully added a new service");
                        dbConn.closeConnection();
                    } catch(Exception e) {
                        System.out.println("Failed to add a new service");
                    }
                    break;
                case 4:
                    // Logout
                    return;
                default:
                    AskAdmin();
            }
            System.out.println("Press 1 for System Setup");
            System.out.println("Press 2 to Add new store");
            System.out.println("Press 3 to Add new service");
            System.out.println("Press 4 for Logout");

            in = new Scanner(System.in);
            option = in.nextInt();
        }
    }
}
