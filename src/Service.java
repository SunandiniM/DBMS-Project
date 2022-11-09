import java.sql.Statement;
import java.util.*;

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
                    System.out.println("Enter Service Category if it is a Repair Service. Else enter escape character '-'");
                    String serviceCategory = in.nextLine();
                    in = new Scanner(System.in);
                    System.out.println("Enter comma separated Schedule ID's if it is a Maintenance Service. Else enter escape character '-'");
                    String scheduleIdStr = in.nextLine();
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
                        if (!scheduleIdStr.equals("-")) {
                            String[] strParts = scheduleIdStr.split(",");
                            List<String> strList = Arrays.asList(strParts);
                            List<Integer> intList = new ArrayList<>();
                            for(String s : strList) intList.add(Integer.valueOf(s));
                            Collections.sort(intList, Collections.reverseOrder());
                            for (Integer i : intList) {
                                try {
                                    sql = "INSERT INTO MAINTAINANCE_SERVICE VALUES ('" + i + "', '" + serviceId + "')";
                                    stmt.executeUpdate(sql);
                                    System.out.println("Successfully added maintenance service for schedule " + i);
                                } catch(Exception e) {
                                    System.out.println("Failed to add maintenance service");
                                    System.out.println(e);
                                }
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



}
