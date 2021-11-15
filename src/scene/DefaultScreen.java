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

  public JSONObject getInfo() throws IOException{

    String url = "http://api.rkd.refinitiv.com/api/Fundamentals/Fundamentals.svc/REST/Fundamentals_1/GetFinancialStatementsReports_1";
    String jsonString = "{\"GetFinancialStatementsReports_Request_1\":{\"companyId\":\"IBM.N\",\"companyIdType\":\"RIC\"}}";
    String[][] set = new String[2][2];
    
    // abstraction explain it well - why did i think of it
    set[0][0] = "X-Trkd-Auth-Token";
    set[0][1] = t.getToken();

    set[1][0] = "X-Trkd-Auth-ApplicationID";
    set[1][1] = "rkdapi";

    PostRequest fundamentals = new PostRequest(url, new JSONObject(jsonString), false);
    JSONObject temp = fundamentals.sendPost(set);

    return temp;
  }

  public JPanel getPanel(){
    return tab.getTable();
  }
}
