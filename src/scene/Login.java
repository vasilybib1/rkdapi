package scene;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import gui.*;
import main.Token;
import main.Main;

public class Login{

  private Insets in;
  private Panel pan;

  private String appidStr;
  private String userStr;
  private String passStr;

  private final int IND = 10;
  private final int SIZE = 22;
  private final Dimension DIM = new Dimension(SIZE*10, SIZE);

  private JTextField appid;
  private JTextField user;
  private JPasswordField pass;
  private JButton button;

  public Login(){
    pan = new Panel();
    in = new Insets(0, IND, 0, IND);

    makeLabel("AppID", 0, 0);
    appid = makeTextField(SIZE, 0, 1);
    
    makeLabel("User", 0, 2);
    user = makeTextField(SIZE, 0, 3);
    
    //https://docs.oracle.com/javase/7/docs/api/javax/swing/JPasswordField.html
    makeLabel("Password", 0, 4);
    GridBagConstraints b = new GridBagConstraints();
    b.gridx = 0;
    b.gridy = 5;
    b.insets = in;
    pass = new JPasswordField();

    pass.setMinimumSize(DIM);
    pass.setMaximumSize(DIM);
    pass.setPreferredSize(DIM);
    pass.setSize(DIM);

    pan.add(pass, b);

    button = makeButton("Log In", 0, 6);

  }

  public JButton getButton(){ return button; }

  public JPanel getPanel(){ return pan; }

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

  private void makeLabel(String str, int x, int y){
    GridBagConstraints b = new GridBagConstraints();
    b.gridx = x;
    b.gridy = y;
    b.insets = in;
    JLabel label = new JLabel(str);
    pan.add(label, b);
  }

  private JTextField makeTextField(int size, int x, int y){
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

  private JButton makeButton(String str, int x, int y){
    GridBagConstraints b = new GridBagConstraints();
    b.gridx = x;
    b.gridy = y;
    b.insets = in;
    JButton button = new JButton(str);  
    pan.add(button, b);
    return button;
  }
  
  public String[] getLogInDetails(){
    String[] temp = new String[3];
    temp[0] = appid.getText();
    temp[1] = user.getText();
    temp[2] = pass.getText();
    return temp;
  }
}
