package ui;

import javax.websocket.Session;

public class WebsocketCommunicator {
    Session session;
    ServerMessageObserver myObserver;
    public WebsocketCommunicator(ServerMessageObserver observer){
        myObserver=observer;
    }
}
