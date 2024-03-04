package dataAccess;

import record.GameData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;

public class SQLGameDAO implements GameDAO{

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "monkeypie");
    }

    void makeSQLCalls() throws SQLException {
        try (var conn = getConnection()) {
            // Execute SQL statements on the connection here
        }
    }

    SQLGameDAO() {
        String createDatabase = "SELECT COUNT(*) AS count FROM information_schema.SCHEMATA WHERE SCHEMA_NAME = ?";
    }
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
