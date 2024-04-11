package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand{
    public MakeMove(String authToken) {
        super(authToken);
        commandType=CommandType.MAKE_MOVE;
    }
    public String gameID;
    public ChessMove move;
}
