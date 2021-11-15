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

public class Token{


  private final boolean DEBUG = false;
  private final String TOKENURL = "https://api.rkd.refinitiv.com/api/TokenManagement/TokenManagement.svc/REST/Anonymous/TokenManagement_1/CreateServiceToken_1";

  private String token;
  private String appid;
  private String usr;
  private String password;
  private String jsonString;

  private JSONObject tokenJson;

  public Token(String app, String user, String pass){
    appid = app;
    usr = user;
    password = pass;

    jsonString = "{\"CreateServiceToken_Request_1\":{\"ApplicationID\":\""+appid+"\",\"Username\":\""+usr+"\",\"Password\":\""+password+"\"}}";
  }

  public boolean makeToken() throws IOException{
    PostRequest tokenRequest = new PostRequest(TOKENURL, new JSONObject(jsonString), DEBUG);
    String[][] temp = new String[1][1];
    temp[0][0] = "NO";
    tokenJson = tokenRequest.sendPost(temp);

    if(tokenJson == null){
      return false;
    }
    return true;
  }

  public void checkExp() throws IOException{
    String expiration = tokenJson.getJSONObject("CreateServiceToken_Response_1").getString("Expiration");
    String now = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSSSSS'Z'")
      .withZone(ZoneId.of("UTC"))
      .format(Instant.now());  
    
    if(!(expiration.compareTo(now) > 0)){
      makeToken();
    }
  }
  
  public String getToken(){
    return tokenJson.getJSONObject("CreateServiceToken_Response_1").getString("Token");
  }

}
