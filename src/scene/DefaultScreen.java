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

public class DefaultScreen{
  
  private Table tab;
  private Token t;

  //private final int IND = 10;

  public DefaultScreen(Token tok){
    //ine = new Inests(0, IND, 0, IND);
    tab = new Table(5, 5);
    t = tok;
  }

  public JPanel getPanel(){
    FinancialStatmentsReports reportIBM = new FinancialStatmentsReports("IBM.N", "RIC");
    return reportIBM.getPanel();
  }
}
