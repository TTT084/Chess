package webSocketMessages.userCommands;

public class Leave extends UserGameCommand{
    public Leave(String authToken) {
        super(authToken);
        commandType=CommandType.LEAVE;
    }
    public String gameID;
}
