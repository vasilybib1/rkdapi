package main;

// IMPORTS 
// Custom Jars
import org.json.*;
// User Made Packages
import requests.PostRequest;
// Java Packages
import java.io.*;
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Locale;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
import java.time.Instant;
import java.time.ZoneId;

// DONE - Token - adding comments
public class Token{

  private final boolean DEBUG = true;
  private final String TOKENURL = "https://api.rkd.refinitiv.com/api/TokenManagement/TokenManagement.svc/REST/Anonymous/TokenManagement_1/CreateServiceToken_1";

  private String token;
  private String appid;
  private String usr;
  private String password;
  private String jsonString;

  private JSONObject tokenJson;

  // defautl constructor that initiates the object
  public Token(String app, String user, String pass){
    // sets the variables
    appid = app;
    usr = user;
    password = pass;

    //creates the json string that will be sent in the post request
    jsonString = "{\"CreateServiceToken_Request_1\":{\"ApplicationID\":\""+appid+"\",\"Username\":\""+usr+"\",\"Password\":\""+password+"\"}}";
  }

  // makeToken creates a PostRequest object that then sends the real POST request
  public boolean makeToken() throws IOException{
    // makes the PostRequest 
    PostRequest tokenRequest = new PostRequest(TOKENURL, new JSONObject(jsonString), DEBUG);
    // sets no additional settings for the post request 
    String[][] temp = new String[1][1];
    temp[0][0] = "NO"; //go to PostRequest for an explenation on why
    // sends the request (returns null if request failed)
    tokenJson = tokenRequest.sendPost(temp);

    // handles if log in was successful or not
    if(tokenJson == null){
      return false;
    }
    return true;
  }

  // REM - baller string comparison

  // This comparision is called everytime a getToken is needed 
  // it checks the data and if its expired (if yes then is makes a new one)
  // else it just skips
  public void checkExp() throws IOException{
    // gets the expiration string from the data
    String expiration = tokenJson.getJSONObject("CreateServiceToken_Response_1").getString("Expiration");
    // gets current time in the format of the expiration time 
    String now = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSSSSS'Z'")
      .withZone(ZoneId.of("UTC"))
      .format(Instant.now());  
    
    // printing to console 
    //System.out.println(expiration);
    //System.out.println(now);
    //System.out.println(expiration.compareTo(now));

    // comparison that checks whether the time has passed the expiration time
    if(!(expiration.compareTo(now) > 0)){
      // if it did make a new token
      makeToken();
    }
  }
  
  // runs the checkexp to update the token if needed and then returns the token
  public String getToken(){
    // Try catch because checkExp throws IOException
    try{
      checkExp();
    }catch(Exception e){
      System.out.println(e);
      e.printStackTrace();
    }
    // returns the token from the json object
    return tokenJson.getJSONObject("CreateServiceToken_Response_1").getString("Token");
  }

}
