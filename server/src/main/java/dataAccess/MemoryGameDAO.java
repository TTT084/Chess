package dataAccess;

import Data.FakeData;
import record.GameData;

import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{
    public void clear(){
        FakeData.clearGames();
    }
    public HashSet<GameData> getGames(){
        return FakeData.getGames();
    }
    public int createGame(GameData game){
        FakeData.createGame(game);
        return 0;
    }
    public GameData joinGame(String gameID, Boolean isBlack, String username, Boolean isWatcher) {
        return FakeData.updateGame(gameID, isBlack, username, isWatcher);
    }

    @Override
    public GameData getGame(String gameID) {
        return null;
    }

    @Override
    public void leaveGame(String gameID, String username) {

    }

    @Override
    public void makeMove(String gameID, GameData game) {

    }
}
