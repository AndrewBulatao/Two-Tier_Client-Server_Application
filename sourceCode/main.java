package sourceCode;

import java.io.IOException;
import java.sql.SQLException;

    public class main {
        public static void main(String[] args) throws IOException, SQLException{

            DatabaseReader db = new DatabaseReader("bikedb.properties", "bikedb.properties", "root", "password123");
            
            GUI gui = new GUI(db);  // Pass the handler to the GUI constructor
            // Need to read in databases
            gui.createWindow();
    }
}
