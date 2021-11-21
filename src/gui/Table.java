package gui;

import java.awt.*;
import javax.swing.*;
import gui.*;

public class Table{
  
  private int width;
  private int height;
  private int column;
  private int row;

  private Panel pan;
  private Cell[][] cells;

  public Table(int c, int r){
    column = c;
    row = r;

    pan = new Panel();
    init();
  }

  public void init(){
    
    cells = new Cell[row][column];
    for(int i = 0; i < row; i++){
      for(int a = 0; a < column; a++){

        int ind = 2;

        Insets in = new Insets(ind, ind, ind, ind);
        GridBagConstraints b = new GridBagConstraints();
        Cell c = new Cell();
        cells[i][a] = c;
        
        b.gridx = a;
        b.gridy = i;
        b.insets = in;

        pan.add(c, b);
      }
    }
  }

  public Cell[][] getCells(){
    return cells;
  }

  public JPanel getTable(){
    return pan.getPanel();
  }

}
