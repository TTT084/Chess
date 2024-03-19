package ui;

import java.io.IOException;
import java.util.HashSet;


import Requests.*;
import Responses.CreateGameResponse;
import Responses.ListGameResponse;
import Responses.LoginResponse;
import Responses.RegisterResponse;
import record.GameData;

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
            RegisterResponse rep = communicator.doPost(url,reg,RegisterResponse.class,null);
            //RegisterResponse rep= new Gson().fromJson(input, RegisterResponse.class);
            if(rep==null){
                return null;
            }
            return rep.getAuthToken();
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
            LoginResponse rep = communicator.doPost(url,reg, LoginResponse.class,null);
            if(rep==null){
                return null;
            }
            return rep.getAuthToken();
        }
        catch (IOException e){
            System.out.println("error");
        }
        return null;
    }
    public static void Quit(){

    }
    public static String CreateGame(String auth, String name){
        String path = "/game";
        String host = "http://localhost:8080";
        String url = host + path;
        ClientCommunicator communicator = new ClientCommunicator();
        CGRequest reg = new CGRequest(auth,name);
        try{
            CreateGameResponse game = communicator.doPost(url,reg, CreateGameResponse.class,auth);
            if(game==null){
                return null;
            }
            return game.getGameName();
        }
        catch (IOException e){
            System.out.println("error");
        }
        return path;
    }
    public static HashSet<GameData> ListGames(String auth){
        String path = "/game";
        String host = "http://localhost:8080";
        String url = host + path;
        ClientCommunicator communicator = new ClientCommunicator();
        LogoutRequest reg = new LogoutRequest(auth);
        try{
            ListGameResponse rep = communicator.doGet(url,reg, ListGameResponse.class,auth);
            if(rep==null){
                return null;
            }
            return rep.getGames();
        }
        catch (IOException e){
            System.out.println("error");
        }
        return null;
    }
    public static void JoinGame(String color, String ID, String auth){
        String path = "/game";
        String host = "http://localhost:8080";
        String url = host + path;
        ClientCommunicator communicator = new ClientCommunicator();
        JGRequest reg = new JGRequest(color,ID);
        try{
            communicator.doPut(url,reg,auth);
        }
        catch (IOException e){
            System.out.println("error");
        }
        return;
    }
    public static void OvserveGame(String ID, String auth){
        JoinGame(null,ID,auth);
    }
    public static void Logout(String auth){
        String path = "/session";
        String host = "http://localhost:8080";
        String url = host + path;
        ClientCommunicator communicator = new ClientCommunicator();
        LogoutRequest reg = new LogoutRequest(auth);
        try{
            communicator.doPut(url,reg,auth);
        }
        catch (IOException e){
            System.out.println("error");
        }
        return;
    }
}
