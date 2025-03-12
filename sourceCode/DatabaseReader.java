package sourceCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseReader{
    private Connection conn;

    public DatabaseReader(String urlProp, String userProp, String inputUser, String inputPass) throws IOException, SQLException{
        Properties dbp = new Properties();
        Properties uP = new Properties();
        String dbPath = new File("src/properties/" + urlProp).getAbsolutePath();
        String userPath = new File("src/properties/" + userProp).getAbsolutePath();


        try (FileInputStream dbFile = new FileInputStream(dbPath); FileInputStream userFile = new FileInputStream(userPath)){
            dbp.load(dbFile);
            uP.load(userFile);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Driver NOT found");
        }

        // Connect to database
        String url = dbp.getProperty("db.url");
        String user = uP.getProperty("db.user");
        String password = uP.getProperty("db.password");

        conn = DriverManager.getConnection(url, user, password);
        System.out.println("WE CONNECTED");
    }

    public Connection getConnection(){
        return conn;
    }
}