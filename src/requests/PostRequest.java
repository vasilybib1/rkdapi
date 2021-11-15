package requests;


import java.io.*;
import java.net.*;
import org.json.*;

public class PostRequest{
  private URL postURL;
  private JSONObject jsonPost;
  private String contentType = "application/json";
  private boolean debug;

  public PostRequest(String url, JSONObject json, boolean debug) throws MalformedURLException {
    postURL = new URL(url);
    jsonPost = json;
    this.debug = debug;
  }

  public JSONObject sendPost(String[][] args) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) postURL.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", contentType);
    
    if(!args[0][0].equals("NO")){
      for(int i = 0; i < args.length; i++){
        connection.setRequestProperty(args[i][0], args[i][1]);
      }
    }

		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();
		os.write(jsonPost.toString().getBytes()); 
    os.flush();
		os.close();

    int responseCode = connection.getResponseCode();
    if(debug) {System.out.println("POST request code :: "+responseCode);}
    
    if(responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader temp = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();
			
			while((line = temp.readLine()) != null) {
				response.append(line);
			}
			
			temp.close();
			
			if(debug) {System.out.println(response.toString());}
			return new JSONObject(response.toString());
			
		} else {// if connection didn't happen
			if(debug) {System.out.println("POST request failed");}
			return null;
    }
  }


}

