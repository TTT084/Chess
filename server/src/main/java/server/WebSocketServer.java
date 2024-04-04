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
        Spark.port(8080);
        Spark.webSocket("/connect", WebSocketServer.class);
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
                //case JOIN_PLAYER -> join(conn, msg);
                //case JOIN_OBSERVER -> observe(conn, msg);
                //case MAKE_MOVE -> move(conn, msg));
                //case LEAVE -> leave(conn, msg);
                //case RESIGN -> resign(conn, msg);
            }
        } else {
            //Connection.sendError(session.getRemote(), "unknown user");
        }
    }
    private String getConnection(String auth, Session session){
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
}
