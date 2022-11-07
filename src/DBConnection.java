import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    protected static final String jdbcUrl = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
    private static Connection conn;
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

    public Connection createConnection() throws SQLException {
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
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection(
                    "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01","rdange","200259721");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from EMPLOYEES");
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while(rs.next())  {
                for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    Object object = rs.getObject(columnIndex);
                    System.out.printf("%s, ", object == null ? "NULL" : object.toString());
                }
                System.out.printf("%n");
            }
            con.commit();
            con.close();
        } catch(Exception e) {
            ((PrintStream) null).println(e);
        }
    }
}
