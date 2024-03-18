package ui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;


import Requests.CGRequest;
import Requests.LoginRequest;
import Requests.LogoutRequest;
import Requests.RegisterRequest;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import javax.xml.transform.SourceLocator;

public class ServerFacade {
    //doesnt have HTTP stuff
    //7 methods with 2-3 lines of code
    // calls client communicator
    ServerFacade(){

    }
    public static String Register(String username, String password, String email){
        String path = "/user";
        String host = "http://localhost:8080";
        String url = host + path;
        ClientCommunicator communicator = new ClientCommunicator();
        RegisterRequest reg = new RegisterRequest(username,email,password);
        try{
            communicator.doPost(url,reg);
        }
        catch (IOException e){
            System.out.println("error");
        }
        return null;
    }
    public static String Login(String username,String password){
        String path = "/session";
        String host = "http://localhost:8080";
        String url = host + path;
        ClientCommunicator communicator = new ClientCommunicator();
        LoginRequest reg = new LoginRequest(username,password);
        try{
            communicator.doPost(url,reg);
        }
        catch (IOException e){
            System.out.println("error");
        }
        return null;
    }
    public static void Quit(){

    }
    public static void CreateGame(String auth, String name){
        String path = "/game";
        String host = "http://localhost:8080";
        String url = host + path;
        ClientCommunicator communicator = new ClientCommunicator();
        CGRequest reg = new CGRequest(auth,name);
        try{
            communicator.doPost(url,reg);
        }
        catch (IOException e){
            System.out.println("error");
        }
    }
    public static void ListGames(String auth){
        String path = "/game";
        String host = "http://localhost:8080";
        String url = host + path;
        ClientCommunicator communicator = new ClientCommunicator();
        LogoutRequest reg = new LogoutRequest(auth);
        try{
            communicator.doGet(url);
        }
        catch (IOException e){
            System.out.println("error");
        }
    }
    public static void JoinGame(){

    }
    public static void OvserveGame(){

    }
    public static void Logout(){
    }
}
