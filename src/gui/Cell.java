package gui;

import java.awt.*;
import javax.swing.*;

public class Cell extends JTextArea{

  public Cell(){
    this.setText("hold");
  }

  public void change(String txt){
    this.setText(txt);
  }

  public JTextArea getArea(){
    return this;
  }
} 
