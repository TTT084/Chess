package webSocketMessages.userCommands;

public class JoinObserver extends UserGameCommand{
    public JoinObserver(String authToken) {
        super(authToken);
        commandType=CommandType.JOIN_OBSERVER;
    }
    public String gameID;
}
