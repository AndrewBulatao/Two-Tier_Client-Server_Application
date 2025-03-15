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
        //System.out.println("WE CONNECTED");
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

    public String checkIfLoginValid(String selectedUser, String selectedDatabase, String userInput, String userPass) throws SQLException {
        // If the entered user doesn't match the selected user, return false
        if (!selectedUser.equals(userInput + ".properties")) {
            //System.out.println("Selected user does not match entered username.");
            //System.out.println(selectedUser + "\n");
            //System.out.println(userInput);
            return "ERROR: Selected user does not match entered username.";
        }

        Properties userProps = new Properties();
        String userPath = new File("sourceCode/properties/" + selectedUser).getAbsolutePath();  // Use the selected user for path

        // Check if the user properties file exists
        try (FileInputStream userFile = new FileInputStream(userPath)) {
            userProps.load(userFile);
        } catch (IOException e) {
            System.out.println("User properties file not found for: " + selectedUser);
            return ("ERROR: User properties file not found for" + selectedUser);
        }

        // Get the username and password from the properties file
        String storedUser = userProps.getProperty("db.user");
        String storedPass = userProps.getProperty("db.password");

        // Check if the entered username and password match the stored credentials
        if (!userInput.equals(storedUser) || !userPass.equals(storedPass)) {
            System.out.println("Invalid username or password.");
            return ("ERROR: Invalid username or password.");
        }

        // Check if the user has permission to access the selected database
        String query = "SELECT * FROM mysql.db WHERE user = '" +
                storedUser + "' AND host = '%' AND db = '" +
                selectedDatabase.replace(".properties", "") + "';"; // Need to remove .properties

        System.out.println(storedUser + "\n" + selectedDatabase);
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (!rs.next()) {
                System.out.println("User does not have permission to access the database: " + selectedDatabase);
                return ("Error: User does not have permission to access the database: " + selectedDatabase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error checking database access");
        }package sourceCode;

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
                //System.out.println("WE CONNECTED");
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

            public String checkIfLoginValid(String selectedUser, String selectedDatabase, String userInput, String userPass) throws SQLException {
                // If the entered user doesn't match the selected user, return false
                if (!selectedUser.equals(userInput + ".properties")) {
                    //System.out.println("Selected user does not match entered username.");
                    //System.out.println(selectedUser + "\n");
                    //System.out.println(userInput);
                    return "ERROR: Selected user does not match entered username.";
                }

                Properties userProps = new Properties();
                String userPath = new File("sourceCode/properties/" + selectedUser).getAbsolutePath();  // Use the selected user for path

                // Check if the user properties file exists
                try (FileInputStream userFile = new FileInputStream(userPath)) {
                    userProps.load(userFile);
                } catch (IOException e) {
                    System.out.println("User properties file not found for: " + selectedUser);
                    return ("ERROR: User properties file not found for" + selectedUser);
                }

                // Get the username and password from the properties file
                String storedUser = userProps.getProperty("db.user");
                String storedPass = userProps.getProperty("db.password");

                // Check if the entered username and password match the stored credentials
                if (!userInput.equals(storedUser) || !userPass.equals(storedPass)) {
                    System.out.println("Invalid username or password.");
                    return ("ERROR: Invalid username or password.");
                }

                // Check if the user has permission to access the selected database
                String query = "SELECT * FROM mysql.db WHERE user = '" +
                        storedUser + "' AND host = '%' AND db = '" +
                        selectedDatabase.replace(".properties", "") + "';"; // Need to remove .properties

                System.out.println(storedUser + "\n" + selectedDatabase);
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(query)) {

                    if (!rs.next()) {
                        System.out.println("User does not have permission to access the database: " + selectedDatabase);
                        return ("Error: User does not have permission to access the database: " + selectedDatabase);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new SQLException("Error checking database access");
                }
                return ("SUCCESS");
            }

            public String executeSQL(String query) throws SQLException {
                String lastResult = ""; // Store only the latest query result

                // Split the input query by semicolons
                String[] queries = query.split(";");

                try (Statement stmt = conn.createStatement()) {
                    for (String q : queries) {
                        q = q.trim(); // Remove whitespace

                        if (q.isEmpty()) continue; // Skip empty queries

                        boolean hasResultSet = stmt.execute(q);

                        if (hasResultSet) {
                            // Process SELECT and SHOW results
                            StringBuilder result = new StringBuilder();
                            try (ResultSet rs = stmt.getResultSet()) {
                                int columnCount = rs.getMetaData().getColumnCount();

                                // Append column headers
                                for (int i = 1; i <= columnCount; i++) {
                                    result.append(rs.getMetaData().getColumnName(i)).append("\t");
                                }
                                result.append("\n");

                                // Append row data
                                while (rs.next()) {
                                    for (int i = 1; i <= columnCount; i++) {
                                        result.append(rs.getString(i)).append("\t");
                                    }
                                    result.append("\n");
                                }
                            }
                            lastResult = result.toString(); // Store only the latest result
                        } else {
                            // Process UPDATE, INSERT, DELETE
                            lastResult = "Query executed successfully. Rows affected: " + stmt.getUpdateCount();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new SQLException("Error executing SQL: " + e.getMessage());
                }

                return lastResult; // Return only the latest command's output
            }




        }
        return ("SUCCESS");
    }

    public String executeSQL(String query) throws SQLException {
        String lastResult = ""; // Store only the latest query result

        // Split the input query by semicolons
        String[] queries = query.split(";");

        try (Statement stmt = conn.createStatement()) {
            for (String q : queries) {
                q = q.trim(); // Remove whitespace

                if (q.isEmpty()) continue; // Skip empty queries

                boolean hasResultSet = stmt.execute(q);

                if (hasResultSet) {
                    // Process SELECT and SHOW results
                    StringBuilder result = new StringBuilder();
                    try (ResultSet rs = stmt.getResultSet()) {
                        int columnCount = rs.getMetaData().getColumnCount();

                        // Append column headers
                        for (int i = 1; i <= columnCount; i++) {
                            result.append(rs.getMetaData().getColumnName(i)).append("\t");
                        }
                        result.append("\n");

                        // Append row data
                        while (rs.next()) {
                            for (int i = 1; i <= columnCount; i++) {
                                result.append(rs.getString(i)).append("\t");
                            }
                            result.append("\n");
                        }
                    }
                    lastResult = result.toString(); // Store only the latest result
                } else {
                    // Process UPDATE, INSERT, DELETE
                    lastResult = "Query executed successfully. Rows affected: " + stmt.getUpdateCount();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error executing SQL: " + e.getMessage());
        }

        return lastResult; // Return only the latest command's output
    }




}