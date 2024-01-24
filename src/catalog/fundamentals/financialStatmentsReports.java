package catalog.fundamentals;

import gui.Panel;
import gui.Cell;
import gui.Table;
import gui.ScrollableWindow;
import requests.*;
import main.*;
import utils.Utils;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.awt.event.*;

import org.json.*;
import javax.swing.*;

public class FinancialStatmentsReports{
  private String id;
  private String companyId;
  private JSONObject info;
  private JSONObject fR;

  private final String FONT = Main.getFont();
  private final int SIZE = Main.getSize();

  // REM - overloading a function for debugging
  public FinancialStatmentsReports(String id, String companyId, boolean debug){ // when calling the function it requests the default set from a json file
    this.id = id;
    this.companyId = companyId;
    
    try{
      info = new JSONObject(Utils.readFile("savedAsJson0.json", StandardCharsets.US_ASCII));
      // fR - fundamentals Reports 
      fR = info.getJSONObject("GetFinancialStatementsReports_Response_1").getJSONObject("FundamentalReports");
      // Utils.saveJsonAsFile(info, 0); - used for debugging purposes (knowing where the data is located in the json)
    }catch(Exception e){
      System.out.println(e);
      e.printStackTrace();
    }

  }

  // is an override of the above function as it doesn't take the debug variable 
  public FinancialStatmentsReports(String id, String companyId){ // when calling the function it requests the default set
    this.id = id;
    this.companyId = companyId;
    
    String url = "http://api.rkd.refinitiv.com/api/Fundamentals/Fundamentals.svc/REST/Fundamentals_1/GetFinancialStatementsReports_1";
    String jsonString = "{\"GetFinancialStatementsReports_Request_1\":{\"companyId\":\""+id+"\",\"companyIdType\":\""+companyId+"\"}}";
    String[][] set = new String[2][2];
    
    set[0][0] = "X-Trkd-Auth-Token";
    set[0][1] = Main.getT().getToken();

    set[1][0] = "X-Trkd-Auth-ApplicationID";
    set[1][1] = Main.getLogInDetails()[0]; 
    
    try{
      PostRequest fundamentals = new PostRequest(url, new JSONObject(jsonString), true);
      info = fundamentals.sendPost(set);
      // fR - fundamentals Reports 
      fR = info.getJSONObject("GetFinancialStatementsReports_Response_1").getJSONObject("FundamentalReports");

      Utils.saveJsonAsFile(info, 0);
      // Utils.saveJsonAsFile(info, 0); - used for debugging purposes (knowing where the data is located in the json)
    }catch(Exception e){
      System.out.println(e);
      e.printStackTrace();
    }

  }
  
  //gets the final panel from financial statment that will be attached to win
  public JPanel getPanel(){
    Panel p = new Panel();
    // basically padding from html
    int ind = 5;
    Insets in = new Insets(0, ind, 5, ind);
    
    p.add(makeSearchBar(), mGB(0,0, in));
    p.add(makeBannerPanel(), mGB(0,1, in));
    p.add(makeCoGeneralInfoPanel(), mGB(0, 2, in));
    p.add(makeCoIDPanel(), mGB(0, 3, in));
    p.add(makeIssuesPanel(), mGB(0, 4, in));
    p.add(makeButtonsPanel(), mGB(0,5, in));
    
    return p;
  }

  private JPanel makeSearchBar(){ 
    // sets pannel with padding settings
    Panel p = new Panel();
    Insets in = new Insets(0, 1, 0, 5);
    
    // makes the UI
    JLabel labelID = new JLabel("Company ID");
    Cell name = new Cell();
    name.setEditable(true);
    name.change("IBM.N");
    // adds the ui to the pannel
    p.add(labelID, mGB(0,0, in));
    p.add(name, mGB(1,0, in));
    
    // makes more UI
    JLabel labelType = new JLabel("ID Type");
    Cell type = new Cell();
    type.setEditable(true);
    type.change("RIC");
    // add the UI to the pannel
    p.add(labelType, mGB(2,0, in));
    p.add(type, mGB(3,0, in));
    
    // more UI
    JButton button = new JButton("Search");  
    p.add(button, mGB(4,0, in));

    // The search function
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // gets the window from main
        ScrollableWindow w = Main.getWin();
        try{
          // tries to get the data from the server (if something fails the try catch will go the catch statement)
          FinancialStatmentsReports report = new FinancialStatmentsReports(name.getText(), type.getText());
          w.clear();
          w.add(report.getPanel());
          w.packWin();
        // if something fails go here \/
        }catch(Exception ee){
          // shows that ID or ID type was inputed wrong 
          JOptionPane.showMessageDialog(w, "The Inputed Company ID or ID Type is Invalid");
          // resets the window
          FinancialStatmentsReports report = new FinancialStatmentsReports("IBM.N", "RIC");
          w.clear();
          w.add(report.getPanel());
          w.packWin();
        }
      }
    });

    p.setF(FONT, SIZE);
    return p;
  }

  private JPanel makeButtonsPanel(){ 
    Panel p = new Panel();

    int ind = 0;
    Insets in = new Insets(ind, ind, ind, ind);
    
    JSONObject data = fR.getJSONObject("ReportFinancialStatements")
      .getJSONObject("FinancialStatements");


    JLabel label1 = new JLabel("Annual");
    Panel p1 = new Panel();

    String type1 = "Income Statment (INC)";
    JButton button1 = new JButton(type1);  
    button1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        FinancialStatmentWindow fsw = new FinancialStatmentWindow(data, type1);
      }
    });
    p1.add(button1, mGB(0, 0, in));

    String type2 = "Balance Sheet (BAL)"; 
    JButton button2 = new JButton(type2);  
    button2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        FinancialStatmentWindow fsw = new FinancialStatmentWindow(data, type2);
      }
    });
    p1.add(button2, mGB(1, 0, in));


    String type3 = "Cash Flow (CAS)";
    JButton button3 = new JButton(type3);  
    button3.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        FinancialStatmentWindow fsw = new FinancialStatmentWindow(data, type3);
      }
    });
    p1.add(button3, mGB(2, 0, in));


    JButton infoButt1 = new JButton("Info");  
    infoButt1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        FinancialStatmentWindow fsw = new FinancialStatmentWindow(data, "Info");
      }
    });
    p.add(infoButt1, mGB(0, 2, in));


    JLabel label2 = new JLabel("Interim");
    Panel p2 = new Panel();

    String type4 = "Income Statment (INC)";
    JButton button4 = new JButton(type4);  
    button4.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        FinancialStatmentWindow fsw = new FinancialStatmentWindow(data, type4+"i");
      }
    });
    p2.add(button4, mGB(0, 0, in));

    String type5 = "Balance Sheet (BAL)"; 
    JButton button5 = new JButton(type5);  
    button5.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        FinancialStatmentWindow fsw = new FinancialStatmentWindow(data, type5+"i");
      }
    });
    p2.add(button5, mGB(1, 0, in));


    String type6 = "Cash Flow (CAS)";
    JButton button6 = new JButton(type6);  
    button6.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        FinancialStatmentWindow fsw = new FinancialStatmentWindow(data, type6+"i");
      }
    });
    p2.add(button6, mGB(2, 0, in));

    JButton infoButt2 = new JButton("Info");  
    infoButt2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        FinancialStatmentWindow fsw = new FinancialStatmentWindow(data, "Infoi");
      }
    });
    p.add(infoButt2, mGB(0, 5, in));

    p.add(label1, mGB(0, 0, in));
    p.add(p1, mGB(0, 1, in));
    p.add(label2, mGB(0, 3, in));
    p.add(p2, mGB(0, 4, in));

    p.setF(FONT, SIZE);
    label1.setFont(new Font(FONT, Font.BOLD, SIZE));
    label2.setFont(new Font(FONT, Font.BOLD, SIZE));
    return p;
  }

  private JPanel makeBannerPanel(){ 
    Panel p = new Panel();
    String name = fR.getJSONObject("ReportFinancialStatements")
                    .getJSONObject("CoIDs")
                    .getJSONArray("CoID")
                    .getJSONObject(1)
                    .getString("Value");
    JLabel label = new JLabel(name);
    label.setFont(new Font(FONT, Font.BOLD, SIZE+5));
    p.add(label);
    return p;
  }

  private JPanel makeIssuesPanel(){ //returns a panel for the coid from fundamentals
    Panel p = new Panel();

    int ind = 1;
    int val = 0;
    Insets in = new Insets(ind, ind, ind, ind);

    // cID - Compnay ID
    JSONArray issues = fR.getJSONObject("ReportFinancialStatements").getJSONObject("Issues").getJSONArray("Issue");
    
    Table t = new Table(issues.length()+1, 12, 1);
    p.add(t.getTable(), mGB(0, 1, in));
    Cell[][] cArr = t.getCells();

    String[] sArr = { 
      "Description",
      "Exchange\n",
      "Name",
      "Ticket",
      "RIC",
      "DisplayRIC",
      "InstrumentPI",
      "QuotePI",
      "Most Recent Split",
      "Split Value",
      "Type",
      "Order"
    };


    for(int i = 0; i < cArr.length; i++){
      cArr[i][0].change(sArr[i]);
    }
      
    int w = 0;
    for(int i = 0; i < sArr.length; i++){
      w = Math.max(sArr[i].length(), w);
    }

    t.setWidthColumn(w+1, 0);
    val += w;

    for(int a = 0; a < issues.length(); a++){
      JSONObject ii = issues.getJSONObject(a);

      String[] vArr = { 
        ii.getString("Desc"),
        ii.getJSONObject("Exchange").getString("Value")+" \n("+ii.getJSONObject("Exchange").getString("Code")+"/"+ii.getJSONObject("Exchange").getString("Country")+")",
        ii.getJSONArray("IssueID").getJSONObject(0).getString("Value"),
        ii.getJSONArray("IssueID").getJSONObject(1).getString("Value"),
        ii.getJSONArray("IssueID").getJSONObject(2).getString("Value"),
        ii.getJSONArray("IssueID").getJSONObject(3).getString("Value"),
        ii.getJSONArray("IssueID").getJSONObject(4).getString("Value"),
        ii.getJSONArray("IssueID").getJSONObject(5).getString("Value"),
        ii.getJSONObject("MostRecentSplit").getString("Date"),
        ii.getJSONObject("MostRecentSplit").getString("Value"),
        ii.getString("Type"),
        ""+ii.getInt("Order")
      };

      for(int i = 0; i < cArr.length; i++){
        cArr[i][a+1].change(vArr[i]);
      }
    
      int w1 = ii.getJSONObject("Exchange").getString("Value").length()+2;
      for(int i = 0; i < sArr.length; i++){
        if(i != 1){ 
          w1 = Math.max(vArr[i].length(), w1);
        }
      }

      t.setWidthColumn(w1+1, a+1);

      val += w1+1;
    }

    Cell title = new Cell();
    title.change("Issues");
    p.add(title, mGB(0,0, in));
    p.setF(FONT, SIZE);
    title.setFont(new Font(FONT, Font.BOLD, SIZE));
    title.setColumns(val+1);

    return p;
  }

  private JPanel makeCoIDPanel(){ //returns a panel for the coid from fundamentals
    Panel p = new Panel();

    int ind = 1;
    Insets in = new Insets(ind, ind, ind, ind);

    Table t = new Table(2, 4, 1);
    p.add(t.getTable(), mGB(0,1, in));
    Cell[][] cArr = t.getCells();

    String[] sArr = { 
      "RepNo",
      "CompanyName",
      "IRSNo",
      "CIKNo"
    };

    // cID - Compnay ID
    JSONObject cID = fR.getJSONObject("ReportFinancialStatements").getJSONObject("CoIDs");

    String[] vArr = { 
      cID.getJSONArray("CoID").getJSONObject(0).getString("Value"),
      cID.getJSONArray("CoID").getJSONObject(1).getString("Value"),
      cID.getJSONArray("CoID").getJSONObject(2).getString("Value"),
      cID.getJSONArray("CoID").getJSONObject(3).getString("Value")
    };

    // uses the string arr and values to fill in the table 
    for(int i = 0; i < cArr.length; i++){
      cArr[i][0].change(sArr[i]);
      cArr[i][1].change(vArr[i]);
    }

    int w0 = 0; // width 0
    int w1 = 0; // width 1
    for(int i = 0; i < sArr.length; i++){
      w0 = Math.max(sArr[i].length(), w0);
      w1 = Math.max(vArr[i].length(), w1);
    }

    t.setWidthColumn(w0+1, 0);
    t.setWidthColumn(w1+1, 1);

    Cell title = new Cell();
    title.change("Compnay IDs (CoIDs)");
    p.add(title, mGB(0,0, in));
    p.setF(FONT, SIZE);
    title.setFont(new Font(FONT, Font.BOLD, SIZE));
    title.setColumns(w0+w1+(ind*2));
    return p;
  }

  private JPanel makeCoGeneralInfoPanel(){ //returns a panel for the cogeneralinfo from fundamentals
    Panel p = new Panel();

    int ind = 1;
    Insets in = new Insets(ind, ind, ind, ind);

    // inits the table and adds table to panel 
    // also makes array of string names and values 
    Table t = new Table(2, 7, 1);
    p.add(t.getTable(), mGB(0,1, in));
    Cell[][] cArr = t.getCells();
    
    // Titles for the value fields 
    String[] sArr = {
      "Status (CoStatus)", 
      "Type (CoType)", 
      "Last Modified", 
      "Last Available Annual", 
      "Last Available Interim", 
      "Most Recent Exchange",
      "Reporting Currency"
    };

    // cGI - coGeneralInfo
    JSONObject cGI = fR.getJSONObject("ReportFinancialStatements").getJSONObject("CoGeneralInfo");

    String[] vArr = { 
      cGI.getJSONObject("CoStatus").getString("Value"),
      cGI.getJSONObject("CoType").getString("Value")+" ("+cGI.getJSONObject("CoType").getString("Code")+")",
      cGI.getString("LastModified"),
      cGI.getString("LatestAvailableAnnual"),
      cGI.getString("LatestAvailableInterim"),
      cGI.getJSONObject("MostRecentExchange").getString("Value")+" ("+cGI.getJSONObject("MostRecentExchange").getString("Date")+")",
      cGI.getJSONObject("ReportingCurrency").getString("Value")+" ("+cGI.getJSONObject("ReportingCurrency").getString("Value")+")"
    };

    // uses the string arr and values to fill in the table 
    for(int i = 0; i < cArr.length; i++){
      cArr[i][0].change(sArr[i]);
      cArr[i][1].change(vArr[i]);
    }

    // calculates the width for the text boxes  
    int w0 = 0; // width 0
    int w1 = 0; // width 1
    for(int i = 0; i < sArr.length; i++){
      w0 = Math.max(sArr[i].length(), w0);
      w1 = Math.max(vArr[i].length(), w1);
    }
    
    // REM - had to switch to mono text
    t.setWidthColumn(w0+1, 0);
    t.setWidthColumn(w1+1, 1);

    Cell title = new Cell();
    title.change("Compnay General Information (CoGeneralInfo)");
    p.add(title, mGB(0,0, in));
    p.setF(FONT, SIZE);
    title.setFont(new Font(FONT, Font.BOLD, SIZE));
    title.setColumns(w0+w1+(ind*2));
    return p;
  }


  //used for makeing the grid bag constrains/
  private GridBagConstraints mGB(int x, int y, Insets i){ 
    GridBagConstraints b = new GridBagConstraints();
    b.gridx = x;
    b.gridy = y;
    b.insets = i;
    return b;
  }

  //used for makeing the grid bag constrains but without insets (also is an override)
  private GridBagConstraints mGB(int x, int y){      
    GridBagConstraints b = new GridBagConstraints();
    b.gridx = x;
    b.gridy = y;
    return b;
  }

}
