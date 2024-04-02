package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand{
    public MakeMove(String authToken) {
        super(authToken);
    }
    public int gameID;
    public ChessMove move;
}
