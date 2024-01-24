package scene;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import org.json.*;

import gui.*;
import requests.*;
import catalog.fundamentals.FinancialStatmentsReports;
import main.Token;


// DONE - DefaultScreen - adding comments
public class DefaultScreen{
  
  private Token t;

  private boolean debug = false;

  // takes in the token and this is default constructor 
  public DefaultScreen(Token tok){
    //ine = new Inests(0, IND, 0, IND);
    t = tok;
  }
  
  // overrides the top constructor
  // this is for testing so i can code faster so instead of loging in i can just load from a json script
  public DefaultScreen(){ this.debug = true; }

  // returns the panel that is then added to the window
  // also checks if its debug or not 
  // if it is then it asks FinancialStatmentsReports to start in debug mode 
  // if it isn't then it does the normal request to the server
  public JPanel getPanel(){ 
    if(this.debug){ 
      FinancialStatmentsReports reportIBM = new FinancialStatmentsReports("IBM.N", "RIC", true);
      return reportIBM.getPanel();
    }else{ 
      FinancialStatmentsReports reportIBM = new FinancialStatmentsReports("IBM.N", "RIC");
      return reportIBM.getPanel();
    }
  }



}
