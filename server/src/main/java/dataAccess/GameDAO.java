package dataAccess;

import Data.FakeData;
import record.*;
import java.util.HashSet;

public class GameDAO {
    public void clear(){
        FakeData.clearGames();
    }
    public HashSet<GameData> getGames(){
        return FakeData.getGames();
    }
    public void createGame(GameData game){
        FakeData.createGame(game);
    }
    public void joinGame(String gameID, Boolean isBlack, String username){
        FakeData.updateGame(gameID, isBlack, username);
    }
}
