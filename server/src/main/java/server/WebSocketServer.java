package server;

import chess.ChessGame;
import chess.InvalidMoveException;
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
import webSocketMessages.userCommands.*;

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

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
        Notification notif = new Notification(ServerMessage.ServerMessageType.NOTIFICATION);
        notif.message="this works";
        Gson json = new Gson();
        String sending = json.toJson(notif);
//        try {
//            session.getRemote().sendString(sending);
//        }
//        catch (IOException e){
//
//        }
        if(command.getCommandType()==null){
            return;
        }
        switch (command.getCommandType()) {
            case JOIN_PLAYER -> join(msg, session);
            case JOIN_OBSERVER -> observe(msg, session);
            case MAKE_MOVE -> move(msg, session);
            case LEAVE -> leave(msg);
            case RESIGN -> resign(msg,session);
        }
    }
    private int getConnection(String auth, Session session){
        return 1;
    }
    private void join(String msg, Session session) throws IOException {
        Gson json = new Gson();
        JoinPlayer command = new Gson().fromJson(msg, JoinPlayer.class);
        String sending;
        sessionMap.put(command.getAuthString(),session);
        AuthDAO authy = new SQLAuthDAO();
        AuthData data = authy.getAuth(command.getAuthString());
        if(data==null){
            String out = "Error wrong AuthToken";
            sendError(command.gameID,out,session);
            return;
        }
        String username = data.getUsername();
        String color;
        LoadGame game = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME);
        GameDAO gamy = new SQLGameDAO();
        GameData thisData = gamy.getGame(command.gameID);
        if(thisData!=null){
            game.game=thisData.getGame();
        }
        else{
            String out = "Error wrong GameID";
            sendError(command.gameID,out,session);
            return;
        }
        if(!Objects.equals(thisData.getBlackUsername(), data.getUsername())){
            if(!Objects.equals(thisData.getWhiteUsername(), data.getUsername())) {
                String out = "Error can't join full game";
                sendError(command.gameID, out, session);
                return;
            }
        }
        sending = json.toJson(game);
        session.getRemote().sendString(sending);


        if(command.playerColor== ChessGame.TeamColor.WHITE){
            color = "White";
        }
        else{
            color = "Black";
        }
        String output = username + " has joined as the " + color + " player";
        if(gameMap.containsKey(command.gameID)){
            HashSet<String> players = gameMap.get(command.gameID);
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
            gameMap.put(command.gameID, hashy);
        }

    }
    private void leave(String msg){
        GameDAO gamy = new SQLGameDAO();
        AuthDAO authy = new SQLAuthDAO();
        Gson json = new Gson();
        Leave command = new Gson().fromJson(msg, Leave.class);
        if(command.gameID==null){
            return;
        }
        if(command.getAuthString()==null){
            return;
        }
        AuthData user = authy.getAuth(command.getAuthString());
        GameData data = gamy.getGame(command.gameID);
        if(Objects.equals(user.getUsername(), data.getBlackUsername())|| Objects.equals(user.getUsername(), data.getWhiteUsername())){
            gamy.leaveGame(command.gameID,user.getUsername());
        }
        HashSet<String> players = gameMap.get(command.gameID);
        players.remove(command.getAuthString());
        gameMap.put(command.gameID, players);
        String output = user.getUsername() + " has left the game";
        sendMessage(ServerMessage.ServerMessageType.NOTIFICATION, command.gameID, command.getAuthString(),output,"");
    }
    private void observe(String msg, Session session){
        Gson json = new Gson();
        JoinPlayer command = new Gson().fromJson(msg, JoinPlayer.class);
        String sending;
        sessionMap.put(command.getAuthString(),session);
        AuthDAO authy = new SQLAuthDAO();
        AuthData data = authy.getAuth(command.getAuthString());
        if(data==null){
            String out = "Error wrong AuthToken";
            sendError(command.gameID,out,session);
            return;
        }
        String username = data.getUsername();
        GameDAO gamy = new SQLGameDAO();
        GameData thisData = gamy.getGame(command.gameID);
        if(thisData==null){
            String out = "Error wrong GameID";
            sendError(command.gameID,out,session);
            return;
        }

        String output = username + " is observing";
        if(gameMap.containsKey(command.gameID)){
            HashSet<String> players = gameMap.get(command.gameID);
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
            gameMap.put(command.gameID, players);
        }
        else{
            HashSet<String> hashy = new HashSet<>();
            hashy.add(command.getAuthString());
            gameMap.put(command.gameID, hashy);
        }
        LoadGame game = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME);
        game.game=thisData.getGame();
        sending = json.toJson(game);
        try {
            session.getRemote().sendString(sending);
        }
        catch (IOException e){
        }
    }
    private void move(String msg, Session session){
        Gson json = new Gson();
        MakeMove command = new Gson().fromJson(msg, MakeMove.class);
        String sending;
        sessionMap.put(command.getAuthString(),session);
        AuthDAO authy = new SQLAuthDAO();
        AuthData data = authy.getAuth(command.getAuthString());
        String username = data.getUsername();
        GameDAO gamy = new SQLGameDAO();
        GameData myData = gamy.getGame(command.gameID);
        ChessGame myChess = myData.getGame();
        try {
            myChess.makeMove(command.move);
        }catch (InvalidMoveException e){
            System.out.println("Make move WS server error " + e.getMessage());
        }
        myData.setGame(myChess);
        gamy.makeMove(command.gameID,myData);
        loadGame(myChess, command.gameID);
        AuthData user = authy.getAuth(command.getAuthString());
        String startRow =Integer.toString(command.move.getStartPosition().getRow());
        String startCol = Integer.toString(command.move.getStartPosition().getColumn());
        String endRow = Integer.toString(command.move.getEndPosition().getRow());
        String endCol = Integer.toString(command.move.getEndPosition().getColumn());
        String end = command.move.getEndPosition().toString();
        String output = user.getUsername() + ": R:" + startRow + " C:" + startCol + "to R:" + endRow + " C:" + endCol;
        sendMessage(ServerMessage.ServerMessageType.NOTIFICATION, command.gameID, command.getAuthString(),output,"");
    }
    private void resign(String msg, Session session){
        GameDAO gamy = new SQLGameDAO();
        AuthDAO authy = new SQLAuthDAO();
        Gson json = new Gson();
        Resign command = new Gson().fromJson(msg, Resign.class);
        AuthData user = authy.getAuth(command.getAuthString());
        gamy.leaveGame(command.gameID,user.getUsername());
        GameData myData = gamy.getGame(command.gameID);
        ChessGame myChess = myData.getGame();
        myChess.resign();
        myData.setGame(myChess);
        gamy.makeMove(command.gameID,myData);
        String output = user.getUsername() + " has resigned";
        sendMessage(ServerMessage.ServerMessageType.NOTIFICATION, command.gameID, command.getAuthString(),output,"");
        loadGame(myChess, command.gameID);
    }
    private void sendMessage(ServerMessage.ServerMessageType type, String gameId, String auth, String output, String sending){
        Gson json = new Gson();
        HashSet<String> players = gameMap.get(gameId);
        if(!players.isEmpty()) {
            for (String user : players) {
                Session userSession = sessionMap.get(user);
                if(userSession==null){
                    continue;
                }
                if(!userSession.isOpen()){
                    sessionMap.remove(user);
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
    private void loadGame(ChessGame game, String gameId){
        String sending;
        Gson json = new Gson();
        HashSet<String> players = gameMap.get(gameId);
        if(!players.isEmpty()) {
            for (String user : players) {
                Session userSession = sessionMap.get(user);
                if(userSession==null){
                    continue;
                }
                if(!userSession.isOpen()){
                    sessionMap.remove(user);
                    continue;
                }
                LoadGame notif = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME);
                notif.game=game;
                sending = json.toJson(notif);
                try {
                    userSession.getRemote().sendString(sending);
                }
                catch (IOException e) {
                    System.out.println("Load Game WS server error " + e.getMessage());
                    //throw new RuntimeException(e);
                }
            }
        }
    }
    private void sendError(String gameId, String output, Session session) {
        Gson json = new Gson();
        webSocketMessages.serverMessages.Error error = new webSocketMessages.serverMessages.Error(ServerMessage.ServerMessageType.ERROR);
        error.errorMessage = output;
        String sending = json.toJson(error);
        try {
            session.getRemote().sendString(sending);
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
    }
}
