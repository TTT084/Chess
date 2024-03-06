package dataAccess;

import record.GameData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;

public class SQLGameDAO implements GameDAO{
    public SQLGameDAO() {
        try(Connection conn = DatabaseManager.getConnection()){
            checkDatabase(conn);
        }
        catch (DataAccessException | SQLException e){
            return;
        }

    }
    private void checkDatabase(Connection conn) throws SQLException, DataAccessException {
        String databaseExist = "SELECT COUNT(*) AS count FROM information_schema.SCHEMATA WHERE SCHEMA_NAME = chess";
        try (var preparedStatement=conn.prepareStatement(databaseExist)){
            try(var rs = preparedStatement.executeQuery()){
                int count = rs.getInt(1);
                if(count==1){
                    DatabaseManager.createDatabase();
                    DatabaseManager.createTables(conn);
                }
            }
        }
    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "monkeypie");
    }

    void makeSQLCalls() throws SQLException {
        try (var conn = getConnection()) {
            // Execute SQL statements on the connection here
        }
    }
    @Override
    public void clear() {
        try(Connection conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("DELETE FROM games")) {
                preparedStatement.executeUpdate();
            }
        }
        catch (DataAccessException | SQLException e){
            return;
        }
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
