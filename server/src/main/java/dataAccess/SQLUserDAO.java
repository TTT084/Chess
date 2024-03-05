package dataAccess;

import chess.ChessGame;
import record.UserData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAO{
    SQLUserDAO() {
        try(Connection conn = DatabaseManager.getConnection()){
            checkDatabase(conn);
        }
        catch (DataAccessException | SQLException e){
            return;
        }

    }
    private void checkDatabase(Connection conn) throws SQLException {
        String databaseExist = "SELECT COUNT(*) AS count FROM information_schema.SCHEMATA WHERE SCHEMA_NAME = chess";
        try (var preparedStatement=conn.prepareStatement(databaseExist)){
            try(var rs = preparedStatement.executeQuery()){
                int count = rs.getInt(1);
                if(count==1){
                    createDatabase(conn);
                }
            }
        }
    }
    private void createDatabase(Connection conn) throws SQLException {
        var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS pet_store");
        createDbStatement.executeUpdate();

        var createUserTable = """
            CREATE TABLE  IF NOT EXISTS User (
                id INT NOT NULL AUTO_INCREMENT,
                username VARCHAR(255) NOT NULL,
                password VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL
            )""";
        var createAuthTable = """
            CREATE TABLE  IF NOT EXISTS Auth (
                id INT NOT NULL AUTO_INCREMENT,
                username VARCHAR(255) NOT NULL,
                authToken VARCHAR(255) NOT NULL
                
            )""";
        var createGameTable = """
            CREATE TABLE  IF NOT EXISTS Games (
                id INT NOT NULL AUTO_INCREMENT,
                whiteUsername VARCHAR(255),
                blackUsername VARCHAR(255),
                gameName VARCHAR(255) NOT NULL,
                game NOT NULL
                
            )""";
    }

    private void makeSQLCalls() throws SQLException, DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            // Execute SQL statements on the connection here
        }
    }
    @Override
    public UserData getUser(String user) {
        return null;
    }

    @Override
    public void createUser(String name, String pass, String email) {

    }

    @Override
    public void clear() {

    }
}
