package server;

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import spark.Spark;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.Session;

public class WebSocketServer {
    public static void main(String[] args) {
        Spark.port(8080);
        Spark.webSocket("/connect", WebSocketServer.class);
        Spark.get("/echo/:msg", (req, res) -> "HTTP response: " + req.params(":msg"));
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String msg) throws Exception {
        //UserGameCommand command = readJson(msg, UserGameCommand.class);
        UserGameCommand command = null;
        //var conn = getConnection(command.authToken, session);
        String conn = null;
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

}
