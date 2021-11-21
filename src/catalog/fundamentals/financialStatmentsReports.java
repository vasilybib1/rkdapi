package catalog.fundamentals;


import gui.Panel;
import gui.Cell;
import requests.*;
import main.*;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.*;

import org.json.*;
import javax.swing.*;

public class FinancialStatmentsReports{
  private String id;
  private String companyId;
  private JSONObject info;

  public FinancialStatmentsReports(String id, String companyId){ 
    this.id = id;
    this.companyId = companyId;
    
    String url = "http://api.rkd.refinitiv.com/api/Fundamentals/Fundamentals.svc/REST/Fundamentals_1/GetFinancialStatementsReports_1";
    String jsonString = "{\"GetFinancialStatementsReports_Request_1\":{\"companyId\":\""+id+"\",\"companyIdType\":\""+companyId+"\"}}";
    String[][] set = new String[2][2];
    
    // abstraction explain it well - why did i think of it
    set[0][0] = "X-Trkd-Auth-Token";
    set[0][1] = Main.getT().getToken();

    set[1][0] = "X-Trkd-Auth-ApplicationID";
    set[1][1] = Main.getLogInDetails()[0]; // replace with log in info
    
    try{
      PostRequest fundamentals = new PostRequest(url, new JSONObject(jsonString), false);
      info = fundamentals.sendPost(set);
    }catch(Exception e){
      System.out.println(e);
      e.printStackTrace();
    }

  }

  public JPanel getPanel(){ 
    Panel p = new Panel();
    Cell c = new Cell();
    GridBagConstraints b = new GridBagConstraints();
    p.add(c.getArea(), b);
    return p;
  }

}
