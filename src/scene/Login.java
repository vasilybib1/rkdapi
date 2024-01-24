package scene;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.Font;

import gui.*;
import main.Token;
import main.Main;

// DONE - Login - adding comments 
public class Login{

  private Insets in;
  private Panel pan;

  private String appidStr;
  private String userStr;
  private String passStr;

  private final int IND = 10;
  private final int SIZE = 22;
  private final Dimension DIM = new Dimension(SIZE*10, SIZE);

  private final String F = Main.getFont();
  private final int S = Main.getSize();

  private JTextField appid;
  private JTextField user;
  private JPasswordField pass;
  private JButton button;

  // default constructor for makeing a login page 
  // makes panel and insets creatss teh labels, text fields, stuff like that 
  // after calling this u can call the getPanel to get the panel made by the constructor
  public Login(){
    pan = new Panel();
    in = new Insets(0, IND, 0, IND);

    GridBagConstraints bb = new GridBagConstraints();
    bb.gridx = 0;
    bb.gridy = 0;
    bb.insets = in;
    JLabel label = new JLabel("Log In");
    pan.add(label, bb);

    makeLabel("AppID", 0, 1);
    appid = makeTextField(0, 2);
    
    makeLabel("User", 0, 3);
    user = makeTextField(0, 4);
    
    //https://docs.oracle.com/javase/7/docs/api/javax/swing/JPasswordField.html
    makeLabel("Password", 0, 5);
    GridBagConstraints b = new GridBagConstraints();
    b.gridx = 0;
    b.gridy = 6;
    b.insets = in;
    pass = new JPasswordField();

    pass.setMinimumSize(DIM);
    pass.setMaximumSize(DIM);
    pass.setPreferredSize(DIM);
    pass.setSize(DIM);

    pan.add(pass, b);

    button = makeButton("Log In", 0, 7);
    pan.setF(F, S);
    label.setFont(new Font(F, Font.BOLD, S+5));

  }

  // returns the button object 
  // only useful for the main class 
  public JButton getButton(){ return button; }

  // retunrs the panel for adding to the window
  public JPanel getPanel(){ return pan; }

  // a method for clearing the fields so that if the password or username is wrong 
  // it resets the fields
  public void reset(){
    appid.setMinimumSize(DIM);
    appid.setMaximumSize(DIM);
    appid.setPreferredSize(DIM);
    appid.setSize(DIM);
    appid.setText("");

    user.setMinimumSize(DIM);
    user.setMaximumSize(DIM);
    user.setPreferredSize(DIM);
    user.setSize(DIM);
    user.setText("");

    pass.setMinimumSize(DIM);
    pass.setMaximumSize(DIM);
    pass.setPreferredSize(DIM);
    pass.setSize(DIM);
    pass.setText("");
  }

  // code to make it a label this is made to save lines and make it more readable 
  // as i need to create multiple labels 
  // str - is string of the label, x is the x coordinate of the gridbag constraints 
  // y is the y coordinate of the gridbagconstraints 
  private void makeLabel(String str, int x, int y){
    GridBagConstraints b = new GridBagConstraints();
    b.gridx = x;
    b.gridy = y;
    b.insets = in;
    JLabel label = new JLabel(str);
    pan.add(label, b);
  }

  // same thing as the label just to save code and make it more readable 
  private JTextField makeTextField(int x, int y){
    GridBagConstraints b = new GridBagConstraints();
    b.gridx = x;
    b.gridy = y;
    b.insets = in;
    JTextField field = new JTextField();

    field.setMinimumSize(DIM);
    field.setMaximumSize(DIM);
    field.setPreferredSize(DIM);
    field.setSize(DIM);

    pan.add(field, b);
    return field;
  }

  // creates the button also made in a function for readability 
  // same as label 
  private JButton makeButton(String str, int x, int y){
    GridBagConstraints b = new GridBagConstraints();
    b.gridx = x;
    b.gridy = y;
    b.insets = in;
    JButton button = new JButton(str);  
    pan.add(button, b);
    return button;
  }
  
  // returns the log in details that were enterd
  // TODO - could add encryption ??
  public String[] getLogInDetails(){
    String[] temp = new String[3];
    temp[0] = appid.getText();
    temp[1] = user.getText();
    temp[2] = pass.getText();
    return temp;
  }
}
