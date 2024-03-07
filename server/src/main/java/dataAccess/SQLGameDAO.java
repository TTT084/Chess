package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import record.GameData;
import record.UserData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;

public class SQLGameDAO implements GameDAO{
    public SQLGameDAO() {
        try{
            DatabaseManager.createDatabase();
        }
        catch (DataAccessException e){
            System.out.println("Creating database error");
        }
        try(Connection conn = DatabaseManager.getConnection()){
            //checkDatabase(conn);
            DatabaseManager.createTables(conn);
        }
        catch (DataAccessException | SQLException e){
            System.out.println("Creating tables error");
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
        Gson json = new Gson();
        HashSet<GameData> games = new HashSet<>();
        try(Connection conn = DatabaseManager.getConnection()){
            String returnUser = "SELECT id, whiteUsername, blackUsername, gameName, game FROM Games";
            try (var preparedStatement = conn.prepareStatement(returnUser)) {
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String whiteUser = rs.getString("whiteUsername");
                        String blackUser = rs.getString("blackUsername");
                        String nameGame = rs.getString("gameName");
                        String game = rs.getString("game");

                        games.add(new GameData(Integer.toString(id),whiteUser,blackUser,nameGame,json.fromJson(game, ChessGame.class)));
                    }
                }
            }
        }
        catch (DataAccessException | SQLException e){
            System.out.println("Get User error");
            return null;
        }
        return games;
    }

    @Override
    public void createGame(GameData game) {
        Gson json = new Gson();
        try(Connection conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("INSERT INTO Games (gameName, game) VALUES(?, ?)")) {
                preparedStatement.setString(1, game.getGameName());
                preparedStatement.setString(2, json.toJson(game.getGame()));

                preparedStatement.executeUpdate();
            }
        }
        catch (DataAccessException | SQLException e){
            return;
        }
    }

    @Override
    public GameData joinGame(String gameID, Boolean isBlack, String username, Boolean isWatcher) {
        return null;
    }
}
