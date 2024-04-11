package ui;
import chess.ChessGame;
import chess.ChessMove;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;

import com.google.gson.Gson;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.net.URI;

public class WebsocketCommunicator extends Endpoint {
    Session session;
    ServerMessageObserver myObserver;
    public WebsocketCommunicator(ServerMessageObserver observer) throws Exception{
        myObserver=observer;
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);
        //add message handler
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String input) {
                Gson gson = new Gson();
                try {
                    ServerMessage message = gson.fromJson(input, ServerMessage.class);
                    myObserver.notify(message);
                } catch(Exception ex) {
                    Error error = new Error(ServerMessage.ServerMessageType.ERROR);
                    error.message= ex.getMessage();
                    myObserver.notify(error);
                }
            }
        });
    }
    public void onMessage(String input) {
        Gson gson = new Gson();
        try {
            ServerMessage message = gson.fromJson(input, ServerMessage.class);
            myObserver.notify(message);
        } catch(Exception ex) {
            //myObserver.notify(new ErrorMessage(ex.getMessage()));
        }
    }
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
    public void joinGame(String auth, ChessGame.TeamColor color, String id){
        JoinPlayer action = new JoinPlayer(auth);
        action.playerColor=color;
        action.gameId=id;
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        }
        catch (IOException e){
            System.out.println("Join game WS error " + e.getMessage());
        }
    }
    public void leave(String auth, String gameID){
        Leave action = new Leave(auth);
        action.gameID=gameID;
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        }
        catch (IOException e){
            System.out.println("Leave game WS error " + e.getMessage());
        }
    }
    public void observeGame(String auth, String gameId){
        JoinObserver action = new JoinObserver(auth);
        action.gameID = gameId;
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        }
        catch (IOException e){
            System.out.println("Observe game WS error " + e.getMessage());
        }
    }
    public void makeMove(String auth, String ID, ChessMove move){
        MakeMove action = new MakeMove(auth);
        action.gameID = ID;
        action.move=move;
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        }
        catch (IOException e){
            System.out.println("Make move WS error " + e.getMessage());
        }
    }
    public void resign(String auth, String gameId){
        Resign action = new Resign(auth);
        action.gameID = gameId;
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        }
        catch (IOException e){
            System.out.println("Resign WS error " + e.getMessage());
        }
    }
}
