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
}
