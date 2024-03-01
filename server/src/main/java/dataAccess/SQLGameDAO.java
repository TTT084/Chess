package dataAccess;

import record.GameData;

import java.util.HashSet;

public class SQLGameDAO implements GameDAO{
    @Override
    public void clear() {

    }

    @Override
    public HashSet<GameData> getGames() {
        return null;
    }

    @Override
    public void createGame(GameData game) {

    }

    @Override
    public GameData joinGame(String gameID, Boolean isBlack, String username, Boolean isWatcher) {
        return null;
    }
}
