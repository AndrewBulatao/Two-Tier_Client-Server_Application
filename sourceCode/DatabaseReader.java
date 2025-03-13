package sourceCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;  
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class DatabaseReader{
    private Connection conn;
    // url prop supposed to be the
    public DatabaseReader(String urlProp, String userProp, String inputUser, String inputPass) throws IOException, SQLException{
        Properties dbp = new Properties();
        Properties uP = new Properties();

        String dbPath = new File("sourceCode/properties/" + urlProp).getAbsolutePath();
        //System.out.println("\n" + dbPath + "\n");
        String userPath = new File(userProp).getAbsolutePath();


        try (FileInputStream dbFile = new FileInputStream(dbPath); FileInputStream userFile = new FileInputStream(userPath)){
            dbp.load(dbFile);
            uP.load(userFile);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("\nDriver NOT found\n");
        }

        // Connect to database
        String url = dbp.getProperty("db.url");
        String user = uP.getProperty("db.user");
        String password = uP.getProperty("db.password");

        conn = DriverManager.getConnection(url, user, password);
        System.out.println("WE CONNECTED");
    }

    public List<String> getUsers() throws SQLException {
        List<String> users = new ArrayList<>();
        String query = "SELECT DISTINCT user \n" +
                        "FROM mysql.user \n" +
                        "WHERE host = '%';\n";
    
        // Use try-with-resources for both the Connection and Statement objects
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Process the result set
            while (rs.next()) {
                String user = rs.getString("user") + ".properties";
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving users");
        }
        return users;
    }

    public List<String> getDatabases() throws SQLException {
        List<String> databases = new ArrayList<>();
        String query = "SELECT schema_name \n" +
                        "FROM information_schema.schemata \n" +
                        "WHERE schema_name NOT IN ('information_schema', 'mysql', 'performance_schema', 'sys');\n";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String db = rs.getString(1) + ".properties";
                databases.add(db);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving databases");
        }

        return databases;
    }
    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/operationslog"; 
        String user = "root";  
        String password = "password123"; 
        return DriverManager.getConnection(url, user, password);
    }
}