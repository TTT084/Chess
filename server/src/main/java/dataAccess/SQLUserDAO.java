package dataAccess;

import chess.ChessGame;
import record.UserData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAO{
    public SQLUserDAO() {
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
        try(Connection conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("INSERT INTO User (username, password, email) VALUES(?, ?, ?)")) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, pass);
                preparedStatement.setString(3, email);

                preparedStatement.executeUpdate();
            }
        }
        catch (DataAccessException | SQLException e){
            return;
        }
    }

    @Override
    public void clear() {
        try(Connection conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("DELETE FROM user")) {
                //preparedStatement.setInt(1);
                preparedStatement.executeUpdate();
            }
        }
        catch (DataAccessException | SQLException e){
            return;
        }
    }
}
