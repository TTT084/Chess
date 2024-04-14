package ui;

import java.io.IOException;
import java.util.HashSet;


import Requests.*;

import Responses.*;

import chess.ChessGame;
import chess.ChessMove;
import record.GameData;

public class ServerFacade {
    //doesnt have HTTP stuff
    //7 methods with 2-3 lines of code
    // calls client communicator
    public static String host = "http://localhost:8080";
    private static WebsocketCommunicator ws;
    ServerFacade(ServerMessageObserver observer){
        try{
           ws = new WebsocketCommunicator(observer);
        }
        catch (Exception e){
            System.out.println("Websocket error "+e);
        }
    }
    public void messageObserver(ServerMessageObserver observer){
        try{
            ws = new WebsocketCommunicator(observer);
        }
        catch (Exception e){
            System.out.println("Websocket error"+e);
        }
    }
    public static String register(String username, String password, String email){
        String path = "/user";
        //String host = "http://localhost:8080";
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
            System.out.println("Register error" +e);
        }
        return null;
    }
    public static String login(String username, String password){
        String path = "/session";
        //String host = "http://localhost:8080";
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
            System.out.println("Login error "+e);
        }
        return null;
    }
    public static void quit(){

    }
    public static String createGame(String auth, String name){
        String path = "/game";
        //String host = "http://localhost:8080";
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
            System.out.println("CreateGame error" +e);
        }
        return path;
    }
    public static HashSet<GameData> listGames(String auth){
        String path = "/game";
        //String host = "http://localhost:8080";
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
            System.out.println("ListGame error" +e);
        }
        return null;
    }
    public static boolean joinGame(String color, String iD, String auth){
        ChessGame.TeamColor team;
        if(color == "White"){
            team = ChessGame.TeamColor.WHITE;
        }
        else{
            team = ChessGame.TeamColor.BLACK;
        }
        String path = "/game";
        //String host = "http://localhost:8080";
        String url = host + path;
        ClientCommunicator communicator = new ClientCommunicator();
        JGRequest reg = new JGRequest(color,iD);
        try{
            communicator.doPut(url,reg,auth);
            ws.joinGame(auth,team,iD);
        }
        catch (IOException e){
            System.out.println("Join game error" + e);
            return false;
        }
        return true;
    }
    public static boolean observeGame(String iD, String auth){
        String path = "/game";
        String url = host + path;
        ClientCommunicator communicator = new ClientCommunicator();
        JGRequest reg = new JGRequest(null,iD);
        try{
            communicator.doPut(url,reg,auth);
            ws.observeGame(auth,iD);
            return true;
        }
        catch (IOException e){
            System.out.println("Observe game error" + e);
            return false;
        }
        //return false;
    }
    public static void logout(String auth){
        String path = "/session";
        //String host = "http://localhost:8080";
        String url = host + path;
        ClientCommunicator communicator = new ClientCommunicator();
        LogoutRequest reg = new LogoutRequest(auth);
        try{
            communicator.doDelete(url,reg,auth);
        }
        catch (IOException e){
            System.out.println("Logout error" +e);
        }
        return;
    }
    public static void leave(String auth, String iD){
        ws.leave(auth, iD);
    }
    public static void makeMove(String auth, String iD, ChessMove move){
        ws.makeMove(auth,iD,move);
    }
    public static void resign(String auth, String gameID){
        ws.resign(auth, gameID);
    }
}
