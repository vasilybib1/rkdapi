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

public class Main{

  private static ScrollableWindow window;
  private static Login logInScreen;
  private static DefaultScreen defaultScreen;
  private static Token t;
  private static String[] info;

  // initiates the main loop
  public static void main(String[] args){
    try{
      mainLoop();
    }catch(Exception e){}
  }

  // new line
  // the loop in which all of the logic is hosted 
  public static void mainLoop() throws IOException{ 
    window = new ScrollableWindow("rkdapi", 500, 500);
    //logInScreen = new Login(window);
    logInScreen = new Login();
    window.addPanel(logInScreen.getPanel());
    window.packWin();
    
    JButton but = logInScreen.getButton();
    but.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        info = logInScreen.getLogInDetails();
        try{
          t = new Token(info[0], info[1], info[2]);
          boolean temp = t.makeToken();
          if(!temp){
            but.setText("Invalid Log In Information");
            logInScreen.reset();
          }else if(temp){

            window.clear();
            defaultScreen = new DefaultScreen(t);
            window.addPanel(defaultScreen.getPanel());
            window.packWin();

          }
        }catch(Exception ee){
          System.out.println(ee);
        }
      }
    });
  }

  
  public static boolean recieveToken(Token token) throws IOException{
    t = token;
    if(t.makeToken()){
      System.out.println(t.getToken());
      return true;
    }else{ return false; }
  }

  public static String[] getLogInDetails(){ return info; }
  public static Token getT(){ return t; }

}
