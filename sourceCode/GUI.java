package sourceCode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUI implements ActionListener{
       //Class contents
       private static final int WINDOW_WIDTH = 1100;
       private static final int WINDOW_HEIGHT = 800;
       private static final int LABEL_HEIGHT = 40; 
       private static final int LABEL_WIDTH = 250; 
       private static final int BUT_WIDTH = 250;
       private static final int BUT_HEIGHT = 40;
       private static final int DROP_HEIGHT = 40;
       private static final int DROP_WIDTH = 250;




       
    
       JFrame window = new JFrame("SQL CLIENT APPLICATION - (MJL - CNT 4714 - SPRING 2025 - PROJECT 3)");
    
       //private static final FlowLayout LAYOUT_STYLE = new FlowLayout();

       // Creating sections
       JPanel topSection = new JPanel();
       JPanel topRight = new JPanel();
       JPanel topLeft = new JPanel();

       JPanel botSection = new JPanel();

        // Top section components: 4 lables, 2 textfields, 2 dropdowns, 2 titles (labels), 1 huge text field, 
        // Setting sizes
        // 4 buttons (two for each column)
        private final JLabel connectionTitle = new JLabel("Connection Details");
        private final JLabel blankFiller = new JLabel("");

        private final JLabel sqlTitle = new JLabel("Enter an SQL Command");
        
        private final JLabel urlPropLabel = new JLabel("DB URL Properties");
        private final JLabel userPropLabel = new JLabel("User Properties");
        private final JLabel usernameLabel = new JLabel("Username");
        private final JLabel passwordLabel = new JLabel("Password");

        private static JTextField usernameText = new JTextField();
        private static JTextField passwordText = new JTextField();
        private static JTextArea sqlCmdText = new JTextArea();
        
        JComboBox<String> urlDrop = new JComboBox<>();
        JComboBox<String> userDrop = new JComboBox<>();


        // Bottom section: 2 Titles(labels), One big text box - make uneditable, two buttons
        private static JLabel connectionLabel = new JLabel("NO CONNECTION ESTABLISHED");
        private static JLabel resultLabel = new JLabel("SQL Execution Result Window");
        private static JTextArea resultArea = new JTextArea("");
       // Bottom section components: two labels, a panel that should not be editable
       private static final JLabel botTitle  = new JLabel("NO CONNECTIONS ESTABLISHED");


       // Buttons
       JButton connectBut = new JButton("Connect to Database");
       JButton disconnectBut = new JButton("Disconnect From Database");
       JButton clearSQLBut = new JButton("Clear SQL Command");
       JButton executeSQLBut = new JButton("Execute SQL Command");
       JButton clearResBut = new JButton("Clear Results Window");
       JButton exitAppBut = new JButton("Close application");

    
       public void createWindow(){
              // Creating window size, close when exiting, disable resizing
              window.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
              window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              window.setResizable(true);

              Container c = window.getContentPane();
              c.setLayout(new GridLayout(2, 1));
              // Adding sections
              c.add(topSection);
              c.add(botSection);

              //Top section

              // Add components
              topSection.setLayout(new GridLayout(1, 2));
              //topLeft.setLayout(new GridLayout(6,2));

              //topRight.setLayout(new GridLayout(3,2));
              topSection.add(topLeft);
              topSection.add(topRight);

              topRight.setBackground(Color.LIGHT_GRAY);

              // Add to left side
              topLeft.add(connectionTitle);
              topLeft.add(blankFiller);
              topLeft.add(urlPropLabel);
              topLeft.add(urlDrop);
              topLeft.add(userPropLabel);
              topLeft.add(userDrop);
              topLeft.add(usernameLabel);
              topLeft.add(usernameText);
              topLeft.add(usernameText);
              topLeft.add(passwordLabel);
              topLeft.add(passwordText);
              topLeft.add(connectBut);
              topLeft.add(disconnectBut);

              // Set Sizes
              connectionTitle.setPreferredSize(new Dimension(500,30)); 
              urlPropLabel.setPreferredSize(new Dimension(LABEL_WIDTH,LABEL_HEIGHT)); 
              userPropLabel.setPreferredSize(new Dimension(LABEL_WIDTH,LABEL_HEIGHT)); 
              usernameLabel.setPreferredSize(new Dimension(LABEL_WIDTH,LABEL_HEIGHT)); 
              usernameText.setPreferredSize(new Dimension(LABEL_WIDTH,LABEL_HEIGHT)); 
              passwordLabel.setPreferredSize(new Dimension(LABEL_WIDTH,LABEL_HEIGHT)); 
              passwordText.setPreferredSize(new Dimension(LABEL_WIDTH,LABEL_HEIGHT)); 
              connectBut.setPreferredSize(new Dimension(BUT_WIDTH,BUT_HEIGHT)); 
              disconnectBut.setPreferredSize(new Dimension(BUT_WIDTH,BUT_HEIGHT)); 
              userDrop.setPreferredSize(new Dimension(DROP_WIDTH,DROP_HEIGHT)); 
              urlDrop.setPreferredSize(new Dimension(DROP_WIDTH,DROP_HEIGHT)); 
              //_____________________________________________________
              // Add right
              topRight.add(sqlTitle);
              topRight.add(sqlCmdText);
              topRight.add(clearSQLBut);
              topRight.add(executeSQLBut);

              // Set sizes
              sqlTitle.setPreferredSize(new Dimension(500,30)); 
              sqlCmdText.setPreferredSize(new Dimension(500, 200));
              clearSQLBut.setPreferredSize(new Dimension(BUT_WIDTH, BUT_HEIGHT));
              executeSQLBut.setPreferredSize(new Dimension(BUT_WIDTH, BUT_HEIGHT));

              // Set colors
              connectionTitle.setForeground(Color.BLUE);
              sqlTitle.setForeground(Color.BLUE);
              clearSQLBut.setForeground(Color.RED);
              executeSQLBut.setForeground(Color.GREEN);
              //_____________________________________________________
              // Bottom Section 
              botSection.setBackground(Color.GRAY);
              //topSection.setLayout(new GridLayout(1,1));

              botSection.add(connectionLabel);
              botSection.add(resultLabel);
              botSection.add(resultArea);
              botSection.add(clearResBut);
              botSection.add(exitAppBut);

              // Setting size
              connectionLabel.setPreferredSize(new Dimension(550,30)); 
              resultLabel.setPreferredSize(new Dimension(545,30)); 
              resultArea.setPreferredSize(new Dimension(1000,250));
              clearResBut.setPreferredSize(new Dimension(BUT_WIDTH,BUT_HEIGHT));
              exitAppBut.setPreferredSize(new Dimension(BUT_WIDTH,BUT_HEIGHT));

              connectionLabel.setOpaque(true);
              connectionLabel.setBackground(Color.LIGHT_GRAY); 
              connectionLabel.setForeground(Color.RED);
              connectionLabel.setFont(new Font(botTitle.getFont().getName(),Font.BOLD, 20));

              connectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
              resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

              resultArea.setEditable(false);

              // Add ActionListener to buttons
              connectBut.addActionListener(this);
              clearSQLBut.addActionListener(this);
              executeSQLBut.addActionListener(this);
              disconnectBut.addActionListener(this);
              clearResBut.addActionListener(this);
              exitAppBut.addActionListener(this);
              
              // Make window visible
              window.setVisible(true);
       }
              

       // Implementing the required actionPerformed method from ActionListener
       @SuppressWarnings("unused")
       @Override
       public void actionPerformed(ActionEvent e) {
              Object s = e.getSource();

              if(s == clearSQLBut){
                     sqlCmdText.setText("");
              }

              if (s == exitAppBut){
                     System.exit(0);
                     window.dispose();

              if(s == clearResBut){
                     // Need to make sure something == nothing?
                     resultArea.setText("");
              }
              
       }
} // 
} // GUI class ends here
