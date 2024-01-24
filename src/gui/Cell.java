package gui;

import java.awt.*;
import javax.swing.*;

// DONE - Cell - adding comments
public class Cell extends JTextArea{

  // creates the cell and sets the text inside of it to hold 
  // also makes it non editable
  public Cell(){
    this.setText("hold");
    // https://examples.javacodegeeks.com/desktop-java/swing/jtextfield/create-read-only-non-editable-jtextfield/
    this.setEditable(false);
  }

  // simple function to change the text 
  public void change(String txt){
    this.setText(txt);
  }

  // returns this cell
  public JTextArea getArea(){
    return this;
  }
} 
