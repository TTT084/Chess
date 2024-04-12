package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand{
    public JoinPlayer(String authToken) {
        super(authToken);
        commandType=CommandType.JOIN_PLAYER;
    }
    public String gameID;
    public ChessGame.TeamColor playerColor;
}
