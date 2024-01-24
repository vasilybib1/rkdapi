package catalog.fundamentals;

import gui.*;
import requests.*;
import main.*;
import utils.Utils;
import gui.Panel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.awt.event.*;
import java.awt.*;

import org.json.*;
import javax.swing.*;

public class FinancialStatmentWindow{

  private JSONArray data;
  private JSONObject otherData;
  private ScrollableWindow window;
  private Panel p;
  private String ty;
  private String ti;

  private boolean isInterim = false;

  private final String FONT = Main.getFont();
  private final int SIZE = Main.getSize();

  public FinancialStatmentWindow(JSONObject data, String ty){ 
    if(ty.substring(ty.length()-1, ty.length()).equals("i")){ 
      this.data = data.getJSONObject("InterimPeriods").getJSONArray("FiscalPeriod");
      this.ti = ty.substring(0, ty.length()-1) + "(Interim)";
      this.ty = ty.substring(0, ty.length()-1);
      this.isInterim = true;
    }else{ 
      this.data = data.getJSONObject("AnnualPeriods").getJSONArray("FiscalPeriod");
      this.ti = ty + "(Annual)";
      this.ty = ty;
    }

    this.otherData = data;
    window = new ScrollableWindow(this.ti, 500, 500);
    p = makePanel();
    window.addPanel(p);
    window.packWin();
  }

  private Panel makePanel(){ 
    Panel p = new Panel();
    int ind = 2;
    Insets in = new Insets(ind, ind, ind, ind);

    if(ty.equals("Income Statment (INC)")){ 
      int countYear = 0; int countStr = 0;
      Table t = new Table(data.length()+1, data.getJSONObject(0)
                .getJSONArray("Statement")
                .getJSONObject(0)
                .getJSONArray("lineItem").length()+1, 1);
      Cell[][] cArr = t.getCells();
      for(int i = 0; i < cArr.length; i++){
        for(int a = 0; a < cArr[i].length; a++){
          if(i == 0 && a != 0){ 
            cArr[i][a].change(data.getJSONObject(countYear).getString("FiscalYear"));
            countYear++;
          }
          else if(a == 0 && i != 0){ 
            String code = data.getJSONObject(0)
                .getJSONArray("Statement")
                .getJSONObject(0)
                .getJSONArray("lineItem")
                .getJSONObject(countStr)
                .getString("coaCode");
            
            String decode = "???";
            JSONArray temp = otherData.getJSONObject("COAMap").getJSONArray("mapItem");
            for(int d = 0; d < temp.length(); d++){
              if(temp.getJSONObject(d).getString("coaItem").equals(code)){ 
                decode = temp.getJSONObject(d).getString("Value");
              } 
            }
            cArr[i][a].change(decode +" ("+code+")");
            countStr++;
          }
        }
      }
      for(int i = 0; i < cArr[0].length; i++){
        if(i != 0){ 
          JSONArray temp = data.getJSONObject(i-1)
            .getJSONArray("Statement")
            .getJSONObject(0)
            .getJSONArray("lineItem");
          for(int a = 1; a < temp.length()+1; a++){
            for(int b = 0; b < cArr.length; b++){

              String code = temp.getJSONObject(a-1).getString("coaCode");
              String decode = "???";
              JSONArray temp1 = otherData.getJSONObject("COAMap").getJSONArray("mapItem");
              for(int d = 0; d < temp1.length(); d++){
                if(temp1.getJSONObject(d).getString("coaItem").equals(code)){ 
                  decode = temp1.getJSONObject(d).getString("Value");
                } 
              }
              
              // finds the correct cell to modify
              String text = decode +" ("+code+")"; 
              if(cArr[b][0].getText().equals(text)){ 
                
                // gets the value (aka the number that needs to be rounded)
                String value = temp.getJSONObject(a-1).getString("Value");
                
                // looks if the data given was a decimal 
                // some of the data doesn't have '.' which means there is no point in looping
                boolean streakOfZero = true;
                boolean isDecimal = false;
                for(int z = 0; z < value.length(); z++){
                  if(value.charAt(z) == '.'){ 
                    isDecimal = true;
                  }
                }
                
                // loops from right to left and removes all of zeros 
                String fvalue = "";
                if(isDecimal){ 
                  for(int z = value.length()-1; z >= 0; z--){
                    if(streakOfZero && value.charAt(z) != '0'){ 
                      streakOfZero = false;
                    }
                    if(!streakOfZero){ 
                      fvalue = value.charAt(z) + fvalue;
                    }
                  }
                }else{ 
                  fvalue = value;
                }
                // if last character is a '.' then remove it
                if(fvalue.charAt(fvalue.length()-1) == '.'){ 
                  fvalue = fvalue.substring(0, fvalue.length()-1);
                }
                
                // adds the final value
                cArr[b][i].change(fvalue);
              }

            }
          }
        }
      }

      for(int a = 0; a < cArr[0].length; a++){
        int w = 0;
        for(int i = 0; i < cArr.length; i++){
          w = Math.max(cArr[i][a].getText().length(), w);
        }
        t.setWidthColumn(w+1, a);
      }
      
      for(int i = 0; i < cArr.length; i++){
        for(int z = 0; z < cArr[i].length; z++){
          if(i != 0 && z != 0 && cArr[i][z].getText().equals("hold")){ 
            cArr[i][z].change("-");
          }
        }
      }
      
      if(isInterim){ 
        cArr[0][0].change("Income Statment (INC) (Interim)");
      }else{ 
        cArr[0][0].change("Income Statment (INC) (Annual)");
      }

      p.add(t.getTable(), mGB(0,1,in));
      p.setF(FONT, SIZE);
      
      for(int i = 0; i < cArr[0].length; i++){
        cArr[0][i].setFont(new Font(FONT, Font.BOLD, SIZE));
      }
    }else if(ty.equals("Balance Sheet (BAL)")){ 
      int countYear = 0; int countStr = 0;
      Table t = new Table(data.length()+1, data.getJSONObject(0)
                .getJSONArray("Statement")
                .getJSONObject(1)
                .getJSONArray("lineItem").length()+1, 1);
      Cell[][] cArr = t.getCells();
      for(int i = 0; i < cArr.length; i++){
        for(int a = 0; a < cArr[i].length; a++){
          if(i == 0 && a != 0){ 
            cArr[i][a].change(data.getJSONObject(countYear).getString("FiscalYear"));
            countYear++;
          }
          else if(a == 0 && i != 0){ 
            String code = data.getJSONObject(0)
                .getJSONArray("Statement")
                .getJSONObject(1)
                .getJSONArray("lineItem")
                .getJSONObject(countStr)
                .getString("coaCode");
            
            String decode = "???";
            JSONArray temp = otherData.getJSONObject("COAMap").getJSONArray("mapItem");
            for(int d = 0; d < temp.length(); d++){
              if(temp.getJSONObject(d).getString("coaItem").equals(code)){ 
                decode = temp.getJSONObject(d).getString("Value");
              } 
            }
            cArr[i][a].change(decode +" ("+code+")");
            countStr++;
          }
        }
      }
      for(int i = 0; i < cArr[0].length; i++){
        if(i != 0){ 
          JSONArray temp = data.getJSONObject(i-1)
            .getJSONArray("Statement")
            .getJSONObject(1)
            .getJSONArray("lineItem");
          for(int a = 1; a < temp.length()+1; a++){
            for(int b = 0; b < cArr.length; b++){

              String code = temp.getJSONObject(a-1).getString("coaCode");
              String decode = "???";
              JSONArray temp1 = otherData.getJSONObject("COAMap").getJSONArray("mapItem");
              for(int d = 0; d < temp1.length(); d++){
                if(temp1.getJSONObject(d).getString("coaItem").equals(code)){ 
                  decode = temp1.getJSONObject(d).getString("Value");
                } 
              }

              String text = decode +" ("+code+")"; 
              if(cArr[b][0].getText().equals(text)){ 
                String value = temp.getJSONObject(a-1).getString("Value");
                
                boolean streakOfZero = true;
                boolean isDecimal = false;
                for(int z = 0; z < value.length(); z++){
                  if(value.charAt(z) == '.'){ 
                    isDecimal = true;
                  }
                }

                String fvalue = "";
                if(isDecimal){ 
                  for(int z = value.length()-1; z >= 0; z--){
                    if(streakOfZero && value.charAt(z) != '0'){ 
                      streakOfZero = false;
                    }
                    if(!streakOfZero){ 
                      fvalue = value.charAt(z) + fvalue;
                    }
                  }
                }else{ 
                  fvalue = value;
                }
                if(fvalue.charAt(fvalue.length()-1) == '.'){ 
                  fvalue = fvalue.substring(0, fvalue.length()-1);
                }

                cArr[b][i].change(fvalue);
              }
            }
          }
        }
      }

      for(int a = 0; a < cArr[0].length; a++){
        int w = 0;
        for(int i = 0; i < cArr.length; i++){
          w = Math.max(cArr[i][a].getText().length(), w);
        }
        t.setWidthColumn(w+1, a);
      }
      
      for(int i = 0; i < cArr.length; i++){
        for(int z = 0; z < cArr[i].length; z++){
          if(i != 0 && z != 0 && cArr[i][z].getText().equals("hold")){ 
            cArr[i][z].change("-");
          }
        }
      }


      if(isInterim){ 
        cArr[0][0].change("Balance Sheet (BAL) (Interim)");
      }else{ 
        cArr[0][0].change("Balance Sheet (BAL) (Annual)");
      }

      p.add(t.getTable(), mGB(0,1,in));
      p.setF(FONT, SIZE);
      
      for(int i = 0; i < cArr[0].length; i++){
        cArr[0][i].setFont(new Font(FONT, Font.BOLD, SIZE));
      }
    }else if(ty.equals("Cash Flow (CAS)")){ 
      int countYear = 0; int countStr = 0;
      Table t = new Table(data.length()+1, data.getJSONObject(0)
                .getJSONArray("Statement")
                .getJSONObject(2)
                .getJSONArray("lineItem").length()+1, 1);
      Cell[][] cArr = t.getCells();
      for(int i = 0; i < cArr.length; i++){
        for(int a = 0; a < cArr[i].length; a++){
          if(i == 0 && a != 0){ 
            cArr[i][a].change(data.getJSONObject(countYear).getString("FiscalYear"));
            countYear++;
          }
          else if(a == 0 && i != 0){ 
            String code = data.getJSONObject(0)
                .getJSONArray("Statement")
                .getJSONObject(2)
                .getJSONArray("lineItem")
                .getJSONObject(countStr)
                .getString("coaCode");
            
            String decode = "???";
            JSONArray temp = otherData.getJSONObject("COAMap").getJSONArray("mapItem");
            for(int d = 0; d < temp.length(); d++){
              if(temp.getJSONObject(d).getString("coaItem").equals(code)){ 
                decode = temp.getJSONObject(d).getString("Value");
              } 
            }
            cArr[i][a].change(decode +" ("+code+")");
            countStr++;
          }
        }
      }
      for(int i = 0; i < cArr[0].length; i++){
        if(i != 0){ 
          JSONArray temp = data.getJSONObject(i-1)
            .getJSONArray("Statement")
            .getJSONObject(2)
            .getJSONArray("lineItem");
          for(int a = 1; a < temp.length()+1; a++){
            for(int b = 0; b < cArr.length; b++){

              String code = temp.getJSONObject(a-1).getString("coaCode");
              String decode = "???";
              JSONArray temp1 = otherData.getJSONObject("COAMap").getJSONArray("mapItem");
              for(int d = 0; d < temp1.length(); d++){
                if(temp1.getJSONObject(d).getString("coaItem").equals(code)){ 
                  decode = temp1.getJSONObject(d).getString("Value");
                } 
              }

              String text = decode +" ("+code+")"; 
              if(cArr[b][0].getText().equals(text)){ 
                String value = temp.getJSONObject(a-1).getString("Value");
                
                boolean streakOfZero = true;
                boolean isDecimal = false;
                for(int z = 0; z < value.length(); z++){
                  if(value.charAt(z) == '.'){ 
                    isDecimal = true;
                  }
                }

                String fvalue = "";
                if(isDecimal){ 
                  for(int z = value.length()-1; z >= 0; z--){
                    if(streakOfZero && value.charAt(z) != '0'){ 
                      streakOfZero = false;
                    }
                    if(!streakOfZero){ 
                      fvalue = value.charAt(z) + fvalue;
                    }
                  }
                }else{ 
                  fvalue = value;
                }
                if(fvalue.charAt(fvalue.length()-1) == '.'){ 
                  fvalue = fvalue.substring(0, fvalue.length()-1);
                }

                cArr[b][i].change(fvalue);
              }
            }
          }
        }
      }

      for(int a = 0; a < cArr[0].length; a++){
        int w = 0;
        for(int i = 0; i < cArr.length; i++){
          w = Math.max(cArr[i][a].getText().length(), w);
        }
        t.setWidthColumn(w+1, a);
      }
      
      for(int i = 0; i < cArr.length; i++){
        for(int z = 0; z < cArr[i].length; z++){
          if(i != 0 && z != 0 && cArr[i][z].getText().equals("hold")){ 
            cArr[i][z].change("-");
          }
        }
      }

      if(isInterim){ 
        cArr[0][0].change("Cash Flow (CAS) (Interim)");
      }else{ 
        cArr[0][0].change("Cash Flow (CAS) (Annual)");
      }

      p.add(t.getTable(), mGB(0,1,in));
      p.setF(FONT, SIZE);
      
      for(int i = 0; i < cArr[0].length; i++){
        cArr[0][i].setFont(new Font(FONT, Font.BOLD, SIZE));
      }
    }else if(ty.equals("Info")){
      Table t;
      if(!isInterim){ t = new Table(2, 7, ind); }
      else{ t = new Table(2, 5, ind); }

      JSONObject infoData;
      if(isInterim){ 
        infoData = otherData.getJSONObject("InterimPeriods");
      }else{ 
        infoData = otherData.getJSONObject("AnnualPeriods");
      }

      Cell[][] cArr = t.getCells();

      infoData = infoData.getJSONArray("FiscalPeriod").getJSONObject(infoData.getJSONArray("FiscalPeriod").length()-1);
      infoData = infoData.getJSONArray("Statement").getJSONObject(0);
      infoData = infoData.getJSONObject("FPHeader");

      if(!isInterim){ 
        String[] sArr = { 
          "Auditor Name",
          "Auditor Opinion",
          "Period Length",
          "Source",
          "Statement Date",
          "Update Type",
          "Period Type"
        };


        String[] vArr = {
          infoData.getJSONObject("AuditorName").getString("Value")+" ("+infoData.getJSONObject("AuditorName").getString("Code")+")",
          infoData.getJSONObject("AuditorOpinion").getString("Value")+" ("+infoData.getJSONObject("AuditorOpinion").getString("Code")+")",
          ""+infoData.getInt("PeriodLength"),
          infoData.getJSONObject("Source").getString("Value")+" ("+infoData.getJSONObject("Source").getString("Date")+")",
          infoData.getString("StatementDate"),
          infoData.getJSONObject("UpdateType").getString("Value")+" ("+infoData.getJSONObject("UpdateType").getString("Code")+")",
          infoData.getJSONObject("periodType").getString("Value")+" ("+infoData.getJSONObject("periodType").getString("Code")+")"
        };

        for(int i = 0; i < vArr.length; i++){
          cArr[i][0].change(sArr[i]);
          cArr[i][1].change(vArr[i]);
        }

        int w0 = 0;
        int w1 = 0;
        for(int i = 0; i < sArr.length; i++){
          w0 = Math.max(sArr[i].length(), w0);
          w1 = Math.max(vArr[i].length(), w1);
        }
        t.setWidthColumn(w0+1, 0);
        t.setWidthColumn(w1+1, 1);
      }else{ 

        String[] sArr = { 
          "Period Length",
          "Source",
          "Statement Date",
          "Update Type",
          "Period Type"
        };

        String[] vArr = {
          ""+infoData.getInt("PeriodLength"),
          infoData.getJSONObject("Source").getString("Value")+" ("+infoData.getJSONObject("Source").getString("Date")+")",
          infoData.getString("StatementDate"),
          infoData.getJSONObject("UpdateType").getString("Value")+" ("+infoData.getJSONObject("UpdateType").getString("Code")+")",
          infoData.getJSONObject("periodType").getString("Value")+" ("+infoData.getJSONObject("periodType").getString("Code")+")"
        };

        for(int i = 0; i < vArr.length; i++){
          cArr[i][0].change(sArr[i]);
          cArr[i][1].change(vArr[i]);
        }

        int w0 = 0;
        int w1 = 0;
        for(int i = 0; i < sArr.length; i++){
          w0 = Math.max(sArr[i].length(), w0);
          w1 = Math.max(vArr[i].length(), w1);
        }
        t.setWidthColumn(w0+1, 0);
        t.setWidthColumn(w1+1, 1);

      }
      p.add(t.getTable());
      p.setF(FONT, SIZE);

    }

    return p;
  }

  private GridBagConstraints mGB(int x, int y, Insets i){  //used for makeing the grid bag constrains
    GridBagConstraints b = new GridBagConstraints();
    b.gridx = x;
    b.gridy = y;
    b.insets = i;
    return b;
  }
  private GridBagConstraints mGB(int x, int y){  //used for makeing the grid bag constrains but without insets (also is an override)
    GridBagConstraints b = new GridBagConstraints();
    b.gridx = x;
    b.gridy = y;
    return b;
  }


}
