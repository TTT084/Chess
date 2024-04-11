package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import record.GameData;
import record.UserData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

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
            System.out.println("Clearing Games error");
        }
    }

    @Override
    public HashSet<GameData> getGames() {
        Gson json = new Gson();
        HashSet<GameData> games = new HashSet<>();
        try(Connection conn = DatabaseManager.getConnection()){
            String returnUser = "SELECT id, whiteUsername, blackUsername, gameName, game FROM games";
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
    public int createGame(GameData game) {
        Gson json = new Gson();
        String gameString = json.toJson(game.getGame());
        try(Connection conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("INSERT INTO games (gameName, game) VALUES(?, ?)", RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, game.getGameName());
                preparedStatement.setString(2, gameString);

                preparedStatement.executeUpdate();

                var resultSet = preparedStatement.getGeneratedKeys();
                var ID = 0;
                if (resultSet.next()) {
                    ID = resultSet.getInt(1);
                }

                return ID;
            }
        }
        catch (DataAccessException | SQLException e){
            return -1;
        }
    }

    @Override
    public GameData joinGame(String gameID, Boolean isBlack, String username, Boolean isWatcher) {
        GameData game = getGame(gameID);
        if(isWatcher){
            return game;
        }
        if(game==null){
            return null;
        }
        if(isBlack){
            if(Objects.equals(game.getBlackUsername(), "")||game.getBlackUsername()==null){
                game.setBlackUsername(username);
            }
        }
        else{
            if(Objects.equals(game.getWhiteUsername(), "")||game.getWhiteUsername()==null){
                game.setWhiteUsername(username);
            }
        }
        updateGame(gameID,game);
        return game;
    }
    private void updateGame(String gameID, GameData game){
        String update = "UPDATE games SET whiteUsername=?, blackUsername=? WHERE id=?";
        String black = game.getBlackUsername();
        String white = game.getWhiteUsername();
        int number = Integer.parseInt(gameID);
        try(Connection conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement(update)) {
                preparedStatement.setString(1, white);
                preparedStatement.setString(2, black);
                preparedStatement.setInt(3, number);

                preparedStatement.executeUpdate();
            }
        }
        catch (DataAccessException | SQLException e){
            System.out.println("Creating tables error");
        }
    }
    public GameData getGame(String gameID){
        if(gameID==null){
            return null;
        }
        Gson json = new Gson();
        GameData gaming = null;
        try(Connection conn = DatabaseManager.getConnection()){
            String returnUser = "SELECT id, whiteUsername, blackUsername, gameName, game FROM games WHERE id=?";
            try (var preparedStatement = conn.prepareStatement(returnUser)) {
                preparedStatement.setInt(1, Integer.parseInt(gameID));
                try (var rs = preparedStatement.executeQuery()) {
                    preparedStatement.setString(1, gameID);
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String whiteUser = rs.getString("whiteUsername");
                        if (rs.wasNull()) {
                            whiteUser = null; // Set whiteUser to null if it was retrieved as SQL NULL
                        }
                        String blackUser = rs.getString("blackUsername");
                        if (rs.wasNull()) {
                            blackUser = null; // Set whiteUser to null if it was retrieved as SQL NULL
                        }
                        String nameGame = rs.getString("gameName");
                        String game = rs.getString("game");

                        gaming = (new GameData(Integer.toString(id),whiteUser,blackUser,nameGame,json.fromJson(game, ChessGame.class)));
                    }
                }
            }
        }
        catch (DataAccessException | SQLException e){
            System.out.println("Get User error");
            return null;
        }
        return gaming;
    }

    @Override
    public void leaveGame(String gameID, String username) {
        GameData game = getGame(gameID);
        String update = "UPDATE games SET whiteUsername=?, blackUsername=? WHERE id=?";
        String black = game.getBlackUsername();
        String white = game.getWhiteUsername();
        if(Objects.equals(black, username)){
            black = null;
        } else if (Objects.equals(white, username)) {
            white = null;
        }
        int number = Integer.parseInt(gameID);
        try(Connection conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement(update)) {
                preparedStatement.setString(1, white);
                preparedStatement.setString(2, black);
                preparedStatement.setInt(3, number);

                preparedStatement.executeUpdate();
            }
        }
        catch (DataAccessException | SQLException e){
            System.out.println("Creating tables error");
        }
    }

    @Override
    public void makeMove(String gameID, GameData game) {
        String update = "UPDATE games SET game=? WHERE id=?";
        Gson json = new Gson();
        String gameString = json.toJson(game.getGame());
        int number = Integer.parseInt(gameID);
        try(Connection conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement(update)) {
                preparedStatement.setString(1, gameString);
                preparedStatement.setInt(2, number);

                preparedStatement.executeUpdate();
            }
        }
        catch (DataAccessException | SQLException e){
            System.out.println("Creating tables error");
        }
    }
}
