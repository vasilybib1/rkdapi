package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.json.*;

// DONE - Utils - adding comments
public class Utils{

  // REM - saving a json as a file 

  // saves a JSONObject j as a .json file with teh name savedAsJson and than the index 
  // the index allows me to save mutliple files when runnning the code and labeling them with the indexes
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
      }catch(Exception ee){
        ee.printStackTrace();
        return false;
      }
      return true;
    }catch(Exception e){
      System.out.println(e);
      e.printStackTrace();
      return false;
    }
  }

  // REM - loading the json back 
  
  // readFile takes the path aka the file name and takes the encoding of the file 
  // both of these are then used to read the file and a string is returned
  public static String readFile(String path, Charset encoding) throws IOException{
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, encoding);
  }

}
