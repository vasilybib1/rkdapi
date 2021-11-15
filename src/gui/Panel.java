package gui;

import java.awt.*;
import javax.swing.*;
import gui.*;

//https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
public class Panel extends JPanel{

    public Panel(){
      this.setLayout(new GridBagLayout());
    }

    public JPanel getPanel(){
      return this;
    }
}
