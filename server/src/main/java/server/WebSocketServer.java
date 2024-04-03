package server;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.SQLAuthDAO;
import dataAccess.SQLGameDAO;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import record.AuthData;
import spark.Spark;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.Session;
import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
                case JOIN_PLAYER -> join(conn, msg);
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

    }
    private void join(Connection conn, String msg, Session session){
        JoinPlayer command = new Gson().fromJson(msg, JoinPlayer.class);
        sessionMap.put(command.getAuthString(),session);
        if(gameMap.containsKey(command.gameId)){
            HashSet<String> players = gameMap.get(command.gameId);
            for(String user:players){

            }
            players.add(command.getAuthString());
        }
        else{
            HashSet<String> hashy = new HashSet<>();
            hashy.add(command.getAuthString());
            gameMap.put(command.gameId, hashy);
        }
    }

}
