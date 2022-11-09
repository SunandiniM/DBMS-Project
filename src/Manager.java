import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Manager {
    LoginContext loginContext;

    Manager() {
        loginContext = null;
    }

    Manager(LoginContext lc) {
        loginContext = lc;
    }

    public void LandingPageMenu() {
        while (true) {
            Scanner in = new Scanner(System.in);

            System.out.println("Press 1 to Setup Store");
            System.out.println("Press 2 to Add New Employee");
            System.out.println("Press 3 to log out");

            int option = in.nextInt();
            switch (option) {
                case 1:
                    SetupStore();
                    break;
                case 2:
                    AddEmployees();
                    break;
                case 3:
                    return;
                default:
                    break;
            }
        }

    }

    public void SetupStore() {
        while (true) {
            System.out.println("Press 1 to add employees");
            System.out.println("Press 2 to setup operational hours");
            System.out.println("Press 3 to setup service prices");
            System.out.println("Press 4 to go back");

            Scanner in = new Scanner(System.in);
            int option = in.nextInt();

            switch (option) {
                case 1:
                    AddEmployees();
                    break;
                case 2:
                    SetupOperationalHours();
                    break;
                case 3:
                    SetupServicePrices();
                    break;
                case 4:
                    return;
                default:
                    break;
            }
        }

    }

    public void AddEmployees() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Employee id");
        long empID = Long.parseLong(in.nextLine());
        in = new Scanner(System.in);
        System.out.println("Enter Employee's First Name");
        String fname = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter Employee's Last name");
        String lname = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter Employee's Address");
        String address = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter Employee's Email");
        String email = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter Employee's Phone Number");
        long phoneNum = Long.parseLong(in.nextLine());
        in = new Scanner(System.in);
        System.out.println("Select Employee Role\n 1. Receptionist\n 2. Mechanic");
        int role = in.nextInt();
        in = new Scanner(System.in);
        System.out.println("Enter Employee's Salary");
        int salary = in.nextInt();
        boolean returnFlag = true;
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "INSERT INTO EMPLOYEES VALUES ('" + loginContext.SCID + "', '" + empID + "', '" + fname + "', '" + lname + "', '" +
                    address + "', '" + email + "', '" + phoneNum + "', '" + role + "', '" + fname + lname + "')";
            stmt.executeUpdate(sql);
            try {
                sql = "INSERT INTO WORKSIN VALUES ('" + loginContext.SCID + "', '" + empID + "', '" + salary + "')";
                stmt.executeUpdate(sql);
                System.out.println("Successfully added store employee");
            } catch (Exception e) {
                System.out.println("Failed to Create store employee");
                System.out.println(e);
                returnFlag = false;
            }
            if (!returnFlag) {
                sql = "DELETE FROM EMPLOYEES WHERE SCID=" + loginContext.SCID + " AND EMPID=" + empID;
                stmt.executeUpdate(sql);
                System.out.println("Removed Employee entry");
            }
        } catch(Exception e) {
            System.out.println("Failed to add employee");
            System.out.println(e);
        }
    }

    public void SetupServicePrices() {
        System.out.println("Press 1 Setup Maintenance Service prices");
        System.out.println("Press 2 Setup Repair Service prices");
        System.out.println("Press 3 to go back");

        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        switch (option) {
            case 1:
                SetupMaintainanceServicePrices();
            case 2:
                SetupRepairServicePrices();
            case 3:
            default:
        }
    }

    public void SetupMaintainanceServicePrices() {
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("1. Setup Prices");
            System.out.println("2. Go Back");
            int option = in.nextInt();
            switch (option) {
                case 1:
                    while (true) {
                        in = new Scanner(System.in);
                        System.out.println("Enter Manufacturer Name");
                        String manf = in.nextLine();
                        in = new Scanner(System.in);
                        System.out.println("Enter Schedule A price");
                        int aPrice = in.nextInt();
                        in = new Scanner(System.in);
                        System.out.println("Enter Schedule A duration");
                        int aDur = in.nextInt();
                        in = new Scanner(System.in);
                        System.out.println("Enter Schedule B price");
                        int bPrice = in.nextInt();
                        in = new Scanner(System.in);
                        System.out.println("Enter Schedule A duration");
                        int bDur = in.nextInt();
                        in = new Scanner(System.in);
                        System.out.println("Enter Schedule C price");
                        int cPrice = in.nextInt();
                        in = new Scanner(System.in);
                        System.out.println("Enter Schedule A duration");
                        int cDur = in.nextInt();
                        try {
                            DBConnection dbConn = DBConnection.getDBConnection();
                            dbConn.createConnection();
                            Statement stmt = dbConn.conn.createStatement();
                            for (int i=113; i<116;i++) {
                                try {
                                    String sql = "INSERT INTO OFFERS VALUES ('" + i + "', '" + loginContext.SCID + "', '" + manf + "', '" + aDur + "', '" + aPrice + "')";
                                    stmt.executeUpdate(sql);
                                    System.out.println("Successfully added maintenance service price for schedule " + i);
                                } catch (Exception e) {
                                    System.out.println("Failed to setup maintenance service price for schedule " + i);
                                    System.out.println(e);
                                }
                            }
                        } catch(Exception e) {
                            System.out.println("Failed to setup maintenance service price");
                            System.out.println(e);
                        }
                        in = new Scanner(System.in);
                        System.out.println("Have more manufacturers?\n1. Yes\n2. No");
                        if (in.nextInt() == 2) {
                            break;
                        }
                    }
                    break;
                case 2:
                    return;
                default:
                    break;
            }
        }
    }

    public void SetupRepairServicePrices() {
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("1. Setup Prices");
            System.out.println("2. Go Back");
            int option = in.nextInt();
            switch (option) {
                case 1:
                    while (true) {
                        ResultSet rs;
                        DBConnection dbConn;
                        String sql;
                        Statement stmt;
                        try {
                            dbConn = DBConnection.getDBConnection();
                            dbConn.createConnection();
                            stmt = dbConn.conn.createStatement();
                            in = new Scanner(System.in);
                            System.out.println("Enter Manufacturer Name");
                            String manf = in.nextLine();
                            sql = "select s1.SID as SID, s1.SNAME as SNAME from SERVICE s1, REPAIR_SERVICE s2 where s1.SID = s2.SID and s1.SID not in" +
                                    "(select DISTINCT o2.SID from OFFERS o2 where o2.SCID=" + loginContext.SCID + " and o2.MFG='" + manf + "')";
                            rs = stmt.executeQuery(sql);
                            while (rs.next()) {
                                in = new Scanner(System.in);
                                System.out.println("Enter price for " + rs.getString("SNAME"));
                                int price = in.nextInt();
                                in = new Scanner(System.in);
                                System.out.println("Enter duration for " + rs.getString("SNAME"));
                                int dur = in.nextInt();
                                try {
                                    sql = "INSERT INTO OFFERS VALUES ('" + rs.getString("SID") + "', '" + loginContext.SCID + "', '" + manf + "', '" + dur + "', '" + price + "')";
                                    System.out.println("SQL Query");
                                    System.out.println(sql);
                                    Statement stmt2 = dbConn.conn.createStatement();
                                    stmt2.executeUpdate(sql);
                                    System.out.println("Successfully added repair service price for service " + rs.getString("SID"));
                                } catch (Exception e) {
                                    System.out.println("Failed to setup repair service price for schedule " + rs.getString("SID"));
                                    System.out.println(e);
                                }
                            }
                        } catch(Exception e) {
                            System.out.println("Failed to setup repair service price");
                            System.out.println(e);
                        }
                        in = new Scanner(System.in);
                        System.out.println("Have more manufacturers?\n1. Yes\n2. No");
                        if (in.nextInt() == 2) {
                            break;
                        }
                    }
                    break;
                case 2:
                    return;
                default:
                    break;
            }
        }
    }

    public void SetupOperationalHours() {
        Scanner in = new Scanner(System.in);
        System.out.println("Is the store open on a saturday? 1 for yes and 0 for no");
        int option = in.nextInt();
        try {
            DBConnection dbConn = DBConnection.getDBConnection();
            dbConn.createConnection();
            Statement stmt = dbConn.conn.createStatement();
            String sql = "UPDATE SERVICE_CENTER SET SERVICE_CENTER.OPEN_SATURDAY='" + option + "' where SERVICE_CENTER.SCID=" + loginContext.SCID;
            stmt.executeUpdate(sql);
            System.out.println("Successfully updated store operation hours");
        } catch(Exception e) {
            System.out.println("Failed to update store operation hours");
            System.out.println(e);
        }
    }

    public boolean AskManager(long storeId, DBConnection dbConn) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Manager's id");
        long empID = Long.parseLong(in.nextLine());
        in = new Scanner(System.in);
        System.out.println("Enter Manager's First Name");
        String fname = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter Manager's Last name");
        String lname = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter Manager's Address");
        String address = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter Manager's Email");
        String email = in.nextLine();
        in = new Scanner(System.in);
        System.out.println("Enter Manager's Phone Number");
        long phoneNum = Long.parseLong(in.nextLine());
        in = new Scanner(System.in);
        System.out.println("Enter Manager's Salary");
        int salary = in.nextInt();
        boolean returnFlag = true;
        try {
            Statement stmt = dbConn.conn.createStatement();
            String sql = "INSERT INTO EMPLOYEES VALUES ('" + storeId + "', '" + empID + "', '" + fname + "', '" + lname + "', '" +
                    address + "', '" + email + "', '" + phoneNum + "', '" + "MANAGER" + "', '" + fname + lname + "')";
            stmt.executeUpdate(sql);
            try {
                sql = "INSERT INTO WORKSIN VALUES ('" + storeId + "', '" + empID + "', '" + salary + "')";
                stmt.executeUpdate(sql);
                System.out.println("Successfully added store manager");
            } catch (Exception e) {
                System.out.println("Failed to Create store manager");
                System.out.println(e);
                returnFlag = false;
            }
        } catch(Exception e) {
            System.out.println("Failed to create manager");
            System.out.println(e);
            returnFlag = false;
        }
        return returnFlag;
    }
}
