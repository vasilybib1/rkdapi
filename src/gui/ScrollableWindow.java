package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ScrollableWindow extends JFrame{
  
  public ScrollableWindow(String title, int w, int h){ 
    this.setTitle(title);
    this.setLayout(new BorderLayout());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
    this.setSize(w, h);
  }

  public void addPanel(JPanel jp){ 
    this.add(BorderLayout.CENTER, new JScrollPane(jp));
  }

  public void clear(){
    this.getContentPane().removeAll();
    this.invalidate();
    this.validate();
  }

  public void repaintWin(){this.repaint();}
  public void packWin(){this.pack();}

}
