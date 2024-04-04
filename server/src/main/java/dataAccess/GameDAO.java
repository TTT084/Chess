package dataAccess;

import Data.FakeData;
import record.*;
import java.util.HashSet;

public interface GameDAO {
    public void clear();
    public HashSet<GameData> getGames();
    public int createGame(GameData game);
    public GameData joinGame(String gameID, Boolean isBlack, String username, Boolean isWatcher);
    public GameData getGame(String gameID);
}
