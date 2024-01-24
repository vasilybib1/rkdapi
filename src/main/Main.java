package main;

// IMPORTS
// Custom Jars
import org.json.*;
import javax.swing.*;
// User Made Packages 
import requests.PostRequest;
import scene.DefaultScreen;
import scene.Login;
import utils.Utils;
import gui.*;
// Java Packages
import java.io.*;
import java.util.*;
import java.awt.event.*;

// Terminal Set Up 
// cd $HOME/desktop/gru/java/rkdapi/src; javac -cp .:../external/orgJson.jar:../external/swingx.jar ./main/Main.java; java -cp .:../external/orgJson.jar:../external/swingx.jar main/Main

// Reseting the compile 
// find . -name "*.class" | xargs rm 

// Combined into one call
//cd $HOME/desktop/gru/java/rkdapi/src; clear; find . -name "*.class" | xargs rm; javac -cp .:../external/orgJson.jar:../external/swingx.jar ./main/Main.java; java -cp .:../external/orgJson.jar:../external/swingx.jar main/Main


// DONE - Main - adding comments
public class Main{

  private static ScrollableWindow window;
  private static Login logInScreen;
  private static DefaultScreen defaultScreen;
  private static Token t;
  private static String[] info;

  private static final String FONT = "Menlo";
  private static final int SIZE = 12;

  // initiates the main loop
  public static void main(String[] args){
    try{
      mainLoop();
    }catch(Exception e){
      System.out.println(e);
      e.printStackTrace();
    }
  }

  // the loop in which all of the logic is hosted 
  public static void mainLoop() throws IOException{ 
    window = new ScrollableWindow("rkdapi", 500, 500);
    // makes the login panel and adds it to the window
    logInScreen = new Login();
    window.addPanel(logInScreen.getPanel());
    window.packWin();
    
    // gets button from the log in screen 
    // so that verification code is in the main class
    JButton but = logInScreen.getButton();
    // adds on click action
    but.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // gets the log in details
        info = logInScreen.getLogInDetails();
        // try catch because some of these function can throw an exception
        try{
          // makes a token object and tries to log in to the server 
          t = new Token(info[0], info[1], info[2]);
          boolean succesful = t.makeToken(); // returns boolean depending if logged in
          // if the log in information is wrong 
          if(!succesful){ 
            but.setText("Invalid Log In Information");
            window.packWin();
            logInScreen.reset();
          // if log in was sucessful
          }else if(succesful){  
            // try catch because logInSuccessful() throws IOException
            try{
              logInSuccessful();
            // error handling
            }catch(Exception eee){     
              System.out.println(eee);
              eee.printStackTrace();
            }
          }
        // printes a stack trace if something wrong happens
        }catch(Exception ee){
          System.out.println(ee);
          ee.printStackTrace();
        }
      }
    });
  }

  // if the log in data inputed was correct
  public static void logInSuccessful() throws IOException{ 
    // for doing everything after log in was correct 
    window.clear();
    defaultScreen = new DefaultScreen(t);
    window.addPanel(defaultScreen.getPanel());
    window.packWin();
  }

  // loading a json file to test other aspects of the app 
  // and to not be slowed down by internet speed and work offline
  // done so that i dont have to log in every time I wanna see the changes

  public static void debugWithoutLogin() throws IOException{     
    window = new ScrollableWindow("rkdapi", 500, 500);
    window.clear();
    defaultScreen = new DefaultScreen();
    window.addPanel(defaultScreen.getPanel());
    window.packWin();
  }

  // more getter methods that return log in details or token
  public static String[] getLogInDetails(){ return info; }
  public static Token getT(){ return t; }

  // both methods are here to set universal font and size so 
  // u wont need to change the font and the size in each class
  public static String getFont(){ return FONT; }
  public static int getSize(){ return SIZE; }

  public static ScrollableWindow getWin(){ return window; }

}
