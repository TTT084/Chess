package webSocketMessages.serverMessages;

import chess.ChessGame;

public class LoadGame extends ServerMessage{
    public LoadGame(ServerMessageType type) {
        super(type);
    }
    public ChessGame game;
}
