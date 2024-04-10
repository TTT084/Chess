package ui;
import chess.ChessGame;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;

import com.google.gson.Gson;
import webSocketMessages.userCommands.JoinObserver;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.Leave;
import webSocketMessages.userCommands.UserGameCommand;

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
                    //myObserver.notify(new ErrorMessage(ex.getMessage()));
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
            System.out.println("error" + e);
        }
    }
    public void leave(String auth, String gameID){
        Leave action = new Leave(auth);
        action.gameID=gameID;
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        }
        catch (IOException e){

        }
    }
    public void observeGame(String auth, String gameId){
        JoinObserver action = new JoinObserver(auth);
        action.gameID = gameId;
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        }
        catch (IOException e){

        }
    }
}
