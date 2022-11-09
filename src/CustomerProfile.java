import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class CustomerProfile {
    public void AskForProfile(LoginContext loginContext) {
        System.out.println("Enter 1 to View Profile");
        System.out.println("Enter 2 to Add a car to profile");
        System.out.println("Enter 3 to Delete a car from profile");
        System.out.println("Enter 4 to go back");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        switch (option) {
            case 1:
                ShowCustomerProfile(loginContext);
                break;
            case 2:
                AskToAddCar(loginContext);
                break;
            case 3:
                AskToRemoveCar(loginContext);
                break;
            case 4:
                return;
            default:
                return;
        }

        return;
    }

    public void ShowCustomerProfile(LoginContext loginContext) {
        // call jdbc function to show the customer profile
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql1 = "SELECT * FROM CUSTOMER WHERE CID =" + loginContext.ID + " AND SCID=" + loginContext.SCID;
            ResultSet rs1 = stmt.executeQuery(sql1);

            while (rs1.next()) {
                System.out.println(loginContext.ID + " " + rs1.getString("FNAME") + " " + rs1.getString("LNAME") + " " +
                        rs1.getString("ADDRESS") + " " + rs1.getString("EMAIL") + " ");
            }

        }catch (Exception e) {
            System.out.println("Failed to get customer profile "+e);
        }
    }

    public void AskToRemoveCar(LoginContext loginContext) {
        // display all the cars
        Scanner in = new Scanner(System.in);
        System.out.println("Press 1 to delete a car from the profile or any other key to go back");
        int option = in.nextInt();
        in.nextLine();
        System.out.println("You selected " + option);

        if (option == 1) {
            System.out.println("Enter the vin number of the car you want to delete");
            String vin = in.nextLine();
            // call jdbc function to delete

            try {
                DBConnection dbConn = DBConnection.getDBConnection();
                dbConn.createConnection();
                Statement stmt = dbConn.conn.createStatement();
                String sql1 = "DELETE FROM VEHICLE WHERE VIN_NO =" + "'" + vin + "'";
                stmt.executeUpdate(sql1);
                sql1 = "DELETE FROM OWNS WHERE VIN_NO =" + "'" + vin + "' AND CID =" + loginContext.ID;
                stmt.executeUpdate(sql1);
            }catch (Exception e) {
                System.out.println("Failed to delete vehicle "+e);
            }
        }else {
            return;
        }
    }

    public void AskToAddCar(LoginContext loginContext) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the VIN Number of CAR");
        String vin = in.nextLine();

        System.out.println("Enter the car manufacturer name");
        String manName = in.nextLine();

        System.out.println("Enter the mileage");
        double mileage = in.nextDouble();

        System.out.println("Enter the year");
        int year = in.nextInt();

        System.out.println("Press 1 to save it or 2 to cancel and go back");
        int option = in.nextInt();

        if (option == 1) {
            // call the JDBC function to save a car
            try {
                DBConnection dbConn = DBConnection.getDBConnection();
                dbConn.createConnection();
                Statement stmt = dbConn.conn.createStatement();
                String sql = "INSERT INTO VEHICLE VALUES ('" + vin + "', " + year + ",'" + manName + "', " + mileage + "," + "'N'" + ")";
                System.out.println("Vehicle in : " + sql);
                stmt.executeUpdate(sql);
            }catch (Exception e) {
                System.out.println("Failed to add vehicle " + e);
            }

            try {
                DBConnection dbConn = DBConnection.getDBConnection();
                dbConn.createConnection();
                Statement stmt = dbConn.conn.createStatement();
                String sql = "INSERT INTO OWNS VALUES (" +loginContext.SCID + ", " + loginContext.ID + ", '" + vin + "')";
                stmt.executeUpdate(sql);
                System.out.println("Successfully added a new customer profile");
            }catch (Exception e) {
                System.out.println("Failed to add in OWNED_BY" + e);
            }
        }else{
            return;
        }
    }
}
