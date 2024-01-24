package requests;

import java.io.*;
import java.net.*;
import org.json.*;

// DONE - PostRequest - adding comments
public class PostRequest{
  private URL postURL;
  private JSONObject jsonPost;
  private String contentType = "application/json";
  private boolean debug;

  // a defautl constructor that sets the initial variables for the methods
  public PostRequest(String url, JSONObject json, boolean debug) throws MalformedURLException {
    postURL = new URL(url);
    jsonPost = json;
    this.debug = debug;
  }
  
  // REM - post request code clean

  // the actual code that sends the post request to the server 
  // it takes in a 2d array that holds aditional settings that might be required for getting data 
  // the 2d array is used in FinancialStatementsReport 
  //
  // some post request require additional settings to be set (ie token)
  public JSONObject sendPost(String[][] args) throws IOException {
	  HttpURLConnection connection = (HttpURLConnection) postURL.openConnection();
	  connection.setRequestMethod("POST");
	  connection.setRequestProperty("Content-Type", contentType);
    
    // checks if the 2d array needs to be added or not (token does not need this)
    if(!args[0][0].equals("NO")){
      for(int i = 0; i < args.length; i++){
        connection.setRequestProperty(args[i][0], args[i][1]);
      }
    }
    
    // source https://stackoverflow.com/questions/3324717/sending-http-post-request-in-java
	  connection.setDoOutput(true);
    // writes the data 
	  OutputStream os = connection.getOutputStream();
	  os.write(jsonPost.toString().getBytes()); 
    os.flush();
	  os.close();
   
    // sends the POST request and recieves code back
    int responseCode = connection.getResponseCode();
    if(debug) {System.out.println("POST request code :: "+responseCode);}
    
    // code 200 (if POST request was succesfull)
    if(responseCode == HttpURLConnection.HTTP_OK) {
      // reads the data being sent back
	    BufferedReader temp = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    String line;
	    StringBuffer response = new StringBuffer();
	    
	    while((line = temp.readLine()) != null) {
	      response.append(line);
	    }
      
      temp.close();
      
	    if(debug) {System.out.println(response.toString());} // logs if debug option is true
      try{
        return new JSONObject(response.toString()); // returns a json object with the data from server
      }catch(Exception e){ // error handling
        System.out.println(e);
        e.printStackTrace();
        System.out.println("Failed when trying to convert to JSONObject :: error with response formatting");
        System.out.println(response.toString());
        return null;
      }
    
    } else {// if connection didn't happen
      if(debug) {System.out.println("POST request failed");} // logs if debug option is true
	    return null;
    }
  }
}


