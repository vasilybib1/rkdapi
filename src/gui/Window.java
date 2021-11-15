package gui;

import javax.swing.*;
import java.awt.event.*; 
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Color;
import gui.*;

public class Window extends JFrame{
  
  private final String TITLE = "RKD API Window";
  
  public Window(int width, int height){
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(width, height);
    this.setVisible(true);
    this.setTitle(TITLE);
  }

  public void addPanel(JPanel j){
    this.add(j);
    this.pack();
  }

  //https://stackoverflow.com/questions/9347076/how-to-remove-all-components-from-a-jframe-in-java
  public void clear(){
    this.getContentPane().removeAll();
    this.invalidate();
    this.validate();

  }

  public void repaintWin(){this.repaint();}
  public void packWin(){this.pack();}
  
}
