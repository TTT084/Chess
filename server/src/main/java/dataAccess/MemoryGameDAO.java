package dataAccess;

import Data.FakeData;
import record.GameData;

import java.util.HashSet;

public class MemoryGameDAO {
    public void clear(){
        FakeData.clearGames();
    }
    public HashSet<GameData> getGames(){
        return FakeData.getGames();
    }
    public void createGame(GameData game){
        FakeData.createGame(game);
    }
    public void joinGame(String gameID, Boolean isBlack, String username, Boolean isWatcher) {
        FakeData.updateGame(gameID, isBlack, username, isWatcher);
    }
}
