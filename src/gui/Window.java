package gui;

import javax.swing.*;
import java.awt.event.*; 
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Color;
import gui.*;


// DONE - Window - adding comments 
public class Window extends JFrame{
  
  private final String TITLE = "RKD API Window";
  
  // default constructor
  public Window(int width, int height){

    // https://stackoverflow.com/questions/1944446/close-one-jframe-without-closing-another
    // DISPOSE_ON_CLOSE or the EXIT_ON_CLOSE
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setSize(width, height);
    this.setVisible(true);
    this.setTitle(TITLE);


  }

  // adding panels 
  public void addPanel(JPanel j){
    this.add(j);
    this.pack();
  }

  // deleting everything from the window
  //https://stackoverflow.com/questions/9347076/how-to-remove-all-components-from-a-jframe-in-java
  public void clear(){
    this.getContentPane().removeAll();
    this.invalidate();
    this.validate();
  }

  // idk why these are here 
  public void repaintWin(){this.repaint();}
  public void packWin(){this.pack();}
  
}
