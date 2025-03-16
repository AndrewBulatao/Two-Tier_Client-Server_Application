package sourceCode;

import javax.swing.table.DefaultTableModel;
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
import java.util.Vector;


public class DatabaseReader {
    private Connection conn;

    // Constructor to initialize the database connection
    public DatabaseReader(String urlProp, String userProp, String inputUser, String inputPass) throws IOException, SQLException {
        Properties dbp = new Properties();
        Properties uP = new Properties();

        String dbPath = new File("sourceCode/properties/" + urlProp).getAbsolutePath();
        String userPath = new File(userProp).getAbsolutePath();

        try (FileInputStream dbFile = new FileInputStream(dbPath); FileInputStream userFile = new FileInputStream(userPath)) {
            dbp.load(dbFile);
            uP.load(userFile);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("\nDriver NOT found\n");
        }

        // Connect to the database
        String url = dbp.getProperty("db.url");
        String user = uP.getProperty("db.user");
        String password = uP.getProperty("db.password");

        conn = DriverManager.getConnection(url, user, password);
    }

    // Method to get all users
    public List<String> getUsers() throws SQLException {
        List<String> users = new ArrayList<>();
        String query = "SELECT user, host FROM mysql.user WHERE user NOT IN ('mysql.infoschema', 'mysql.session', 'mysql.sys');";  // Exclude 'root' user
        System.out.println(query);

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Loop through the result set and print out the data for each row
            while (rs.next()) {
                String user = rs.getString("user") + ".properties";
                String host = rs.getString("host");
                System.out.println("User: " + user + ", Host: " + host); // Print each user and host
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving users");
        }

        return users;
    }




    // Method to get all databases
    public List<String> getDatabases() throws SQLException {
        List<String> databases = new ArrayList<>();
        String query = "SELECT schema_name FROM information_schema.schemata WHERE schema_name NOT IN ('information_schema', 'mysql', 'performance_schema', 'sys');";

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

    // Method to check if login is valid
    public String checkIfLoginValid(String selectedUser, String selectedDatabase, String userInput, String userPass) throws SQLException {
        if (!selectedUser.equals(userInput + ".properties")) {
            return "ERROR: Selected user does not match entered username.";
        }

        Properties userProps = new Properties();
        String userPath = new File("sourceCode/properties/" + selectedUser).getAbsolutePath();

        try (FileInputStream userFile = new FileInputStream(userPath)) {
            userProps.load(userFile);
        } catch (IOException e) {
            return ("ERROR: User properties file not found for " + selectedUser);
        }

        String storedUser = userProps.getProperty("db.user");
        String storedPass = userProps.getProperty("db.password");

        if (!userInput.equals(storedUser) || !userPass.equals(storedPass)) {
            return ("ERROR: Invalid username or password.");
        }

        return ("SUCCESS");
    }

    // Method to execute SQL queries (SELECT, UPDATE, INSERT, DELETE)
    public DefaultTableModel executeSQL(String query) throws SQLException {
        DefaultTableModel tableModel = new DefaultTableModel(); // Table model for JTable
        String[] queries = query.split(";"); // Split query into individual parts, assuming semi-colons separate them

        try (Statement stmt = conn.createStatement()) {
            for (int i = 0; i < queries.length; i++) {
                String q = queries[i].trim(); // Remove leading and trailing whitespace

                if (q.isEmpty()) continue; // Skip empty queries

                // Clear any existing rows before processing a new query
                tableModel.setRowCount(0);

                boolean hasResultSet = stmt.execute(q);

                if (hasResultSet) {
                    // Process SELECT or SHOW results
                    try (ResultSet rs = stmt.getResultSet()) {
                        int columnCount = rs.getMetaData().getColumnCount();

                        // Add column names to table model
                        Vector<String> columnNames = new Vector<>();
                        for (int col = 1; col <= columnCount; col++) {
                            columnNames.add(rs.getMetaData().getColumnName(col));
                        }
                        tableModel.setColumnIdentifiers(columnNames);

                        // Add rows to table model
                        while (rs.next()) {
                            Vector<Object> row = new Vector<>();
                            for (int col = 1; col <= columnCount; col++) {
                                row.add(rs.getObject(col));
                            }
                            tableModel.addRow(row);
                        }
                    }
                } else {
                    // Process UPDATE, INSERT, DELETE queries (handle update count if needed)
                    tableModel.addRow(new Object[]{"Query executed successfully. Rows affected: " + stmt.getUpdateCount()});
                }
            }
        } catch (SQLException e) {
            // Print the stack trace to the terminal
            e.printStackTrace();

            // Throw an exception with a more detailed message including the SQL error
            throw new SQLException("Error executing SQL: " + e.getMessage());
        }

        return tableModel; // Return the table model with results
    }


    public String getFullURL(String curURL) {
        try {
            Properties dbp = new Properties();

            // Use the full URL with .properties
            String dbPath = new File("sourceCode/properties/" + curURL).getAbsolutePath();

            // Debug print to verify the formed path
            System.out.println("Path to properties file: " + dbPath);

            // Check if the file exists before proceeding
            File file = new File(dbPath);
            if (!file.exists()) {
                System.out.println("File does not exist at: " + dbPath);
                return "Error loading URL";  // Return early if file doesn't exist
            }

            // Load the properties file
            try (FileInputStream dbFile = new FileInputStream(dbPath)) {
                dbp.load(dbFile);
            }

            // Get and return the db.url from the properties
            return dbp.getProperty("db.url");

        } catch (IOException e) {
            e.printStackTrace();
            return "Error loading URL";  // In case of failure
        }
    }


}
