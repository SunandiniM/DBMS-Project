import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    protected static final String jdbcUrl = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
    public static Connection conn;
    private final String dbUserName, dbPassword;
    private static DBConnection dbConnection;
    private DBConnection() {
        dbUserName = "vmalapa";
        dbPassword = "abcd1234";
    }

    public static DBConnection getDBConnection(){
        if(dbConnection == null){
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        if (jdbcUrl == null){
            throw new SQLException("URL NULL", "08001");
        }
        if (conn == null){
            try{
                conn = (Connection) DriverManager.getConnection(jdbcUrl, dbUserName, dbPassword);
            }catch(SQLException sqlExcpt){
                throw sqlExcpt;
            }
        }
        return conn;
    }

    protected void closeConnection(){
        if(conn != null) {
            try {
                conn.close();
            } catch(Throwable whatever) {}
        }
    }

    void testrun() {
        try{
            int serviceId = 101;
            String serviceName = "Engine Repair";
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO SERVICE VALUES ('" + serviceId + "', '" + serviceName + "')";
            stmt.execute(sql);
            System.out.println("Completed Query Execution");
        } catch(Exception e) {
            ((PrintStream) null).println(e);
        }
    }
}
