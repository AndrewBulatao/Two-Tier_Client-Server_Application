package sourceCode;

import java.io.IOException;
import java.sql.SQLException;

    public class main {
        public static void main(String[] args) throws IOException, SQLException{

            DatabaseReader db = new DatabaseReader("project3.properties", "sourceCode/properties/root.properties", "root", "password123");
            System.out.println("\n HELLOOOOO \n");
            
            GUI gui = new GUI(db);  // Pass the handler to the GUI constructor
            // Need to read in databases
            gui.createWindow();
    }
}
