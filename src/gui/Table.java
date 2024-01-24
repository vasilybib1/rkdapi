package gui;

import java.awt.*;
import javax.swing.*;
import gui.*;

// DONE - Table - adding comments 
public class Table{
  
  private int column;
  private int row;
  private int ind;

  private Panel pan;
  private Cell[][] cells;

  // constructor to make a table 
  // calls the init function that creates the table and fills it with cells 
  public Table(int c, int r, int i){
    column = c;
    row = r;
    ind = i;
    pan = new Panel();
    init();
  }

  // this is only called in the constructor 
  // just initialises the table with default values 
  public void init(){
    
    cells = new Cell[row][column];
    for(int i = 0; i < row; i++){
      for(int a = 0; a < column; a++){

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

  // makes all the cells inside the table have the same width 
  // this is made just so that the app looks pretty
  public void setSameWidth(int w){ 
    for(int i = 0; i < row; i++){
      for(int a = 0; a < column; a++){
        cells[i][a].setColumns(w);
      }
    }
  }

  // setWidthColumn takes in a column value (c) 
  // it sets every item in the column c to the same width 
  // also just for the app to look pretty 
  // Cell.setColumns(w) is changing the width of the cell (the name is pretty confusing)
  public void setWidthColumn(int w, int c){ 
    for(int i = 0; i < row; i++){
      cells[i][c].setColumns(w);
    }
  }

  // simple getter methods
  public Cell[][] getCells(){ return cells; }
  public JPanel getTable(){ return pan.getPanel(); }

  
}
