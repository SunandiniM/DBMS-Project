import java.util.Scanner;
import java.sql.*;

public class ReceptionistLandingPage {

    public void AskReceptionist() throws SQLException {
        System.out.println("Press 1 to add new customer profile");
        System.out.println("Press 2 to find customers with pending invoices");
        System.out.println("Press 3 for Logout");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        while (option != 3) {
            switch (option){
                case 1:
                    // Add new customer profile
                    System.out.println("Enter Customer Name:");
                    String custName = in.nextLine();
                    System.out.println("Enter Address:");
                    String address = in.nextLine();
                    System.out.println("Enter email:");
                    String email = in.nextLine();
                    System.out.println("Enter Username:");
                    String phoneNo = in.nextLine();
                    System.out.println("Enter VIN Number:");
                    String vinNumber = in.nextLine();
                    System.out.println("Enter Car Manufacture:");
                    String manufacture = in.nextLine();
                    System.out.println("Enter Mileage:");
                    String mileage = in.nextLine();
                    System.out.println("Enter Year:");
                    String year = in.nextLine();
                    System.out.println("Enter Service Center ID:");
                    String scID = in.nextLine();
                    try {
                        DBConnection dbConn = DBConnection.getDBConnection();
                        dbConn.createConnection();
                        Statement stmt = dbConn.conn.createStatement();
                        String sql = "INSERT INTO CUSTOMER VALUES ('" + custName + "', '" + scID + "')";
                        stmt.executeUpdate(sql);
                        PreparedStatement statement = dbConn.createConnection().prepareStatement("SELECT ID FROM CUSTOMER WHERE name = ?");
                        statement.setString(1, custName);
                        ResultSet rs = statement.executeQuery();
                        String CID = rs.getString(1);
                        sql = "INSERT INTO OWNED_BY VALUES ('" + CID + "', '" + vinNumber + "')";
                        stmt.executeUpdate(sql);
                        sql = "INSERT INTO VEHICLE VALUES ('" + vinNumber + "', '" + manufacture + "'+'" + mileage + "', '" + year + "')";
                        stmt.executeUpdate(sql);
                        System.out.println("Successfully added a new customer profile");
                    } catch(Exception e) {
                        System.out.println("Failed to add customer profile");
                    }
                    break;
                case 2:
                    try{
                    DBConnection dbConn = DBConnection.getDBConnection();
                    dbConn.createConnection();
                    PreparedStatement statement = dbConn.createConnection().
                            prepareStatement("SELECT CID, ORDER_ID, DATE, BILL FROM CUSTOMER WHERE status = 0");
                    ResultSet rs = statement.executeQuery();
                        while(rs.next())
                        {
                            System.out.println(rs.getString(1));
                            System.out.println(rs.getString(2));
                            System.out.println(rs.getString(3));
                            System.out.println(rs.getString(4));
                        }
                    System.out.println("Successfully displayed customers with pending invoices");
            } catch(Exception e) {
                System.out.println("Failed to display customers with pending invoices");
            }
                    break;
                case 3:
                    // Logout
                    return;
                default:
                    AskReceptionist();
            }
            System.out.println("Press 1 to add new customer profile");
            System.out.println("Press 2 to find customers with pending invoices");
            System.out.println("Press 3 for Logout");

            in = new Scanner(System.in);
            option = in.nextInt();
        }
    }
}
