package dataAccess;

import Data.FakeData;
import record.AuthData;

import java.lang.module.ResolutionException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO{
    public SQLAuthDAO()  {
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
    @Override
    public String createAuth(String user) {
        String auth = UUID.randomUUID().toString();
        try(Connection conn = DatabaseManager.getConnection()){
            String insertAuth = "INSERT INTO Auth (username, authToken) VALUES(?, ?)";
            try (var preparedStatement = conn.prepareStatement(insertAuth)) {
                preparedStatement.setString(1, user);
                preparedStatement.setString(2, auth);

                preparedStatement.executeUpdate();
            }
        }
        catch (SQLException | DataAccessException e){
            System.out.println("Creating auth error");
        }
        return auth;
    }

//    @Override
//    public void addAuth(String user, String auth) {}

    @Override
    public void clear() {
        try(Connection conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("DELETE FROM Auth")) {
                preparedStatement.executeUpdate();
            }
        }
        catch (DataAccessException | SQLException e){
            System.out.println("Clear error");
            return;
        }
    }

    @Override
    public AuthData getAuth(String auth) {
        AuthData authy = null;
        try(Connection conn = DatabaseManager.getConnection()){
            String returnAuth = "SELECT username, authToken FROM Auth WHERE authToken=?";
            try (var preparedStatement = conn.prepareStatement(returnAuth)) {
                preparedStatement.setString(1, auth);
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        String name = rs.getString("username");
                        String token = rs.getString("authToken");
                        authy = new AuthData(token,name);
                    }
                }
            }
        }
        catch (DataAccessException | SQLException e){
            System.out.println("Get auth error");
            return null;
        }
        return authy;
    }

    @Override
    public void deleteAuth(String user, String auth) {
        try(Connection conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("DELETE FROM Auth WHERE authToken=?")) {
                preparedStatement.setString(1, auth);
                preparedStatement.executeUpdate();
            }
        }
        catch (DataAccessException | SQLException e){
            return;
        }
    }
}
