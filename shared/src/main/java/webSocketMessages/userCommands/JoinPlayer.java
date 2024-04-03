package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand{
    public JoinPlayer(String authToken) {
        super(authToken);
    }
    public String gameId;
    public ChessGame.TeamColor playerColor;
}
