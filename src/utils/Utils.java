package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.*;

public class Utils{

  public static boolean saveJsonAsFile(JSONObject j, int index){ 
    try{
      String fileName = "savedAsJson";
      File f = new File(fileName+index+".json");

      if(f.createNewFile()){ 
        System.out.println("file created with name - "+fileName+index+".json");
      }else{ 
        return false;
      }

      FileWriter w = new FileWriter(fileName+index+".json");
      try{
        w.write(j.toString());
        w.close();
        System.out.println("json data writen to - "+fileName+index+".json\n");
      }catch(Exception e){
        e.printStackTrace();
        return false;
      }
      return true;
    }catch(Exception e){
      System.out.println(e);
      e.printStackTrace();
      return false;
    }
  }

}
