package sourceCode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class GUI implements ActionListener{
       //Class contents
       private static final int WINDOW_WIDTH = 1100;
       private static final int WINDOW_HEIGHT = 800;
       private static final int FIELD_WIDTH = 70; 
       private static final int MAX_WIDTH = 70; 
       private static final int BUT_WIDTH = 400;
    
       JFrame window = new JFrame("SQL CLIENT APPLICATION - (MJL - CNT 4714 - SPRING 2025 - PROJECT 3)");
    
       //private static final FlowLayout LAYOUT_STYLE = new FlowLayout();

       // Creating sections
       JPanel topSection = new JPanel();
       JPanel midSection = new JPanel();
       JPanel botSection = new JPanel();

        // Top section components
       

       // These need to be uneditable
      

       // Middle section components: 
       private static JLabel midTitle = new JLabel("Your shopping Cart is Currently Empty");
       
       // Bottom section components: 
       private static final JLabel botTitle  = new JLabel("User Controls");

       // Will split bottom section into two rows. First row will have title/ second row will have buttons
       JPanel botTopRow = new JPanel();
       JPanel botBotRow = new JPanel();

       // Buttons
       JButton connectBut = new JButton();
       JButton disconnectBut = new JButton();
       JButton clearSQLBut = new JButton();
       JButton executeSQLBut = new JButton();
       JButton clearResBut = new JButton();
       JButton exitAppBut = new JButton();

    
       public void createWindow(){
              // Creating window size, close when exiting, disable resizing
              window.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
              window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              window.setResizable(true);

              Container c = window.getContentPane();
              c.setLayout(new GridLayout(3, 1));
              // Adding sections
              c.add(topSection);
              c.add(midSection);
              c.add(botSection);

              //Top section
              

              // Set text color
              

              // Adding and aligning tags
              

              //_____________________________________________________
              // Middle Section
              

              // Adding title and text boxes
              
              //_____________________________________________________
              // Bottom Section 
              botSection.setBackground(Color.blue);
              botSection.setLayout(new BorderLayout()); 
              
              // Top Row components & settings
              
              

              // Bot Row components & settings
              


       
              // Setting size
              // set size of buttons
              connectBut.setPreferredSize(new Dimension(BUT_WIDTH,40));
              clearSQLBut.setPreferredSize(new Dimension(BUT_WIDTH,40));
              executeSQLBut.setPreferredSize(new Dimension(BUT_WIDTH,40));
              disconnectBut.setPreferredSize(new Dimension(BUT_WIDTH,40));
              clearResBut.setPreferredSize(new Dimension(BUT_WIDTH,40));
              exitAppBut.setPreferredSize(new Dimension(BUT_WIDTH,40));

              // Setting which one to show when first boot up: delete, add, checkout, empty
              
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
              public void actionPerformed(ActionEvent e) {}
}
