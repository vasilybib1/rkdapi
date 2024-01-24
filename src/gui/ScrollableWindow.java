package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


// DONE - ScrollableWindow - adding comments 
public class ScrollableWindow extends JFrame{
  
  // default constructor to make the window
  public ScrollableWindow(String title, int w, int h){ 
    this.setTitle(title);
    this.setLayout(new BorderLayout());
    // https://stackoverflow.com/questions/1944446/close-one-jframe-without-closing-another
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
    this.setSize(w, h);
  }

  // method for adding a paenl to the window
  public void addPanel(JPanel jp){ 
    this.add(BorderLayout.CENTER, new JScrollPane(jp));
  }

  // deleting everything from the window 
  public void clear(){
    this.getContentPane().removeAll();
    this.invalidate();
    this.validate();
  }

  // i dont know why these are here
  public void repaintWin(){this.repaint();}
  public void packWin(){this.pack();}

}
