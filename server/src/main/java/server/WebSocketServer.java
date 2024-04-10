package server;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.SQLAuthDAO;
import dataAccess.SQLGameDAO;

import record.AuthData;
import record.GameData;
import spark.Spark;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.Leave;
import webSocketMessages.userCommands.UserGameCommand;

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@WebSocket
public class WebSocketServer {
    //gameID, authtokens of users in game
    Map<String, HashSet<String>> gameMap = new HashMap<>();
    //authtoken, session
    Map<String, Session> sessionMap = new HashMap<>();
    public static void main(String[] args) {
        Spark.get("/echo/:msg", (req, res) -> "HTTP response: " + req.params(":msg"));
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String msg) throws Exception {
        //UserGameCommand command = readJson(msg, UserGameCommand.class);
        UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);
        sessionMap.put(command.getAuthString(), session);
        var conn = getConnection(command.getAuthString(), session);
        //Connection conn = null;
        if (conn != null) {
            switch (command.getCommandType()) {
                case JOIN_PLAYER -> join(conn, msg, session);
                case JOIN_OBSERVER -> observe(conn, msg, session);
                //case MAKE_MOVE -> move(conn, msg));
                case LEAVE -> leave(conn, msg);
                //case RESIGN -> resign(conn, msg);
            }
        } else {
            //Connection.sendError(session.getRemote(), "unknown user");
        }
    }
    private Connection getConnection(String auth, Session session){
        return null;
    }
    private void join(Connection conn, String msg, Session session) throws IOException {
        Gson json = new Gson();
        JoinPlayer command = new Gson().fromJson(msg, JoinPlayer.class);
        String sending;
        sessionMap.put(command.getAuthString(),session);
        AuthDAO authy = new SQLAuthDAO();
        AuthData data = authy.getAuth(command.getAuthString());
        String username = data.getUsername();
        String color;
        if(command.playerColor== ChessGame.TeamColor.WHITE){
            color = "White";
        }
        else{
            color = "Black";
        }
        String output = username + " has joined as the " + color + " player";
        if(gameMap.containsKey(command.gameId)){
            HashSet<String> players = gameMap.get(command.gameId);
            command.getAuthString();
            if(!players.isEmpty()) {
                for (String user : players) {
                    Session userSession = sessionMap.get(user);
                    Notification notif = new Notification(ServerMessage.ServerMessageType.NOTIFICATION);
                    notif.message=output;
                    sending = json.toJson(notif);
                    userSession.getRemote().sendString(sending);
                }
            }
            players.add(command.getAuthString());
        }
        else{
            HashSet<String> hashy = new HashSet<>();
            hashy.add(command.getAuthString());
            gameMap.put(command.gameId, hashy);
        }
        LoadGame game = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME);
        GameDAO gamy = new SQLGameDAO();
        GameData thisData = gamy.getGame(command.gameId);
        game.game=thisData.getGame();
        sending = json.toJson(game);
        session.getRemote().sendString(sending);
    }
    private void leave(Connection conn, String msg){
        GameDAO gamy = new SQLGameDAO();
        AuthDAO authy = new SQLAuthDAO();
        Gson json = new Gson();
        Leave command = new Gson().fromJson(msg, Leave.class);
        AuthData user = authy.getAuth(command.getAuthString());
        gamy.leaveGame(command.gameID,user.getUsername());
        String output = user.getUsername() + "has left the game";
        sendMessage(ServerMessage.ServerMessageType.NOTIFICATION, command.gameID, command.getAuthString(),output,"");
    }
    private void observe(Connection conn, String msg, Session session){
        Gson json = new Gson();
        JoinPlayer command = new Gson().fromJson(msg, JoinPlayer.class);
        String sending;
        sessionMap.put(command.getAuthString(),session);
        AuthDAO authy = new SQLAuthDAO();
        AuthData data = authy.getAuth(command.getAuthString());
        String username = data.getUsername();

        String output = username + " is observing";
        if(gameMap.containsKey(command.gameId)){
            HashSet<String> players = gameMap.get(command.gameId);
            command.getAuthString();
            if(!players.isEmpty()) {
                for (String user : players) {
                    Session userSession = sessionMap.get(user);
                    Notification notif = new Notification(ServerMessage.ServerMessageType.NOTIFICATION);
                    notif.message=output;
                    sending = json.toJson(notif);
                    try {
                        userSession.getRemote().sendString(sending);
                    }
                    catch (IOException e){

                    }
                }
            }
            players.add(command.getAuthString());
        }
        else{
            HashSet<String> hashy = new HashSet<>();
            hashy.add(command.getAuthString());
            gameMap.put(command.gameId, hashy);
        }
        LoadGame game = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME);
        GameDAO gamy = new SQLGameDAO();
        GameData thisData = gamy.getGame(command.gameId);
        game.game=thisData.getGame();
        sending = json.toJson(game);
        try {
            session.getRemote().sendString(sending);
        }
        catch (IOException e){
        }
    }
    private void sendMessage(ServerMessage.ServerMessageType type, String gameId, String auth, String output, String sending){
        Gson json = new Gson();
        HashSet<String> players = gameMap.get(gameId);
        if(!players.isEmpty()) {
            for (String user : players) {
                Session userSession = sessionMap.get(user);
                if(!userSession.isOpen()){
                    //remove from maps
                    continue;
                }
                Notification notif = new Notification(ServerMessage.ServerMessageType.NOTIFICATION);
                notif.message=output;
                sending = json.toJson(notif);
                try {
                    userSession.getRemote().sendString(sending);
                }
                catch (IOException e) {
                    //throw new RuntimeException(e);
                }
            }
        }
    }
}
