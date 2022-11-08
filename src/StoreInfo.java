import java.sql.Statement;
import java.util.Scanner;

public class StoreInfo {
    public void AskStoreInfo() {
        while (true) {
            System.out.println("Press 1 for Add Store");
            System.out.println("Press 2 to go back");

            Scanner in = new Scanner(System.in);
            int option = in.nextInt();
            switch (option){
                case 1:
                    // Get store information
                    in = new Scanner(System.in);
                    System.out.println("Enter store ID:");
                    long ID = Long.parseLong(in.nextLine());
                    in = new Scanner(System.in);
                    System.out.println("Enter Address:");
                    String address = in.nextLine();
                    in = new Scanner(System.in);
                    System.out.println("Enter Phone Number:");
                    long phoneNum = Long.parseLong(in.nextLine());
                    // GET MANAGER's information
                    in = new Scanner(System.in);
                    System.out.println("Get Min wage for mechnics");
                    int minWage = in.nextInt();
                    in = new Scanner(System.in);
                    System.out.println("Get Max wage for mechanics");
                    int maxWage = in.nextInt();
                    try {
                        DBConnection dbConn = DBConnection.getDBConnection();
                        dbConn.createConnection();
                        Statement stmt = dbConn.conn.createStatement();
                        String sql = "INSERT INTO SERVICE_CENTER VALUES ('"
                                + ID + "', '" + address + "', '" + phoneNum + "', '" + 0 + "', '" + minWage + "', '" + maxWage + "')";
                        stmt.executeUpdate(sql);
                        System.out.println("Successfully added a new store");
                        Manager m = new Manager();
                        boolean managerFlag = m.AskManager(ID, dbConn);
                        if (!managerFlag) {
                            sql = "DELETE FROM SERVICE_CENTER WHERE SID=" + ID;
                            stmt.executeUpdate(sql);
                            System.out.println("Removed Store Successfully");
                        }
                        dbConn.closeConnection();
                    } catch(Exception e) {
                        System.out.println("Failed to create store");
                        System.out.println(e);
                    }
                case 2:
                    return;
                default:
                    AskStoreInfo();
            }
        }
    }
}
