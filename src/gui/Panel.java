package gui;

import java.awt.*;
import javax.swing.*;
import gui.*;

// DONE - Panel - adding comments
//https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
public class Panel extends JPanel{

  // makes the panel
  public Panel(){
    this.setLayout(new GridBagLayout());
  }

  // returns the panel
  public JPanel getPanel(){
    return this;
  }

  // REM - recursive function
  
  // recursive function to set fonts for every component inside the panel
  public void setF(String f, int n){ 
    for(Component c : this.getComponents()){ 
      if(c instanceof Panel){ 
        ((Panel)c).setF(f, n);
      }else{ 
        c.setFont(new Font(f, Font.PLAIN, n));
      }
    }
  }


}


