package ui;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;

import com.google.gson.Gson;

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
}
