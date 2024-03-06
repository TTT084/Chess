package dataAccess;

import Data.FakeData;
import record.AuthData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO{
    public SQLAuthDAO() {
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
    @Override
    public String createAuth(String user) {
        String auth = UUID.randomUUID().toString();
        try(Connection conn = DatabaseManager.getConnection()){
            String insertAuth = "INSERT INTO Auth (username, authToken) VALUES(user, auth)";
            try (var preparedStatement = conn.prepareStatement(insertAuth)) {
                preparedStatement.executeUpdate();
            }
        }
        catch (SQLException | DataAccessException e){

        }
        return auth;
    }

    @Override
    public void addAuth(String user, String auth) {

    }

    @Override
    public void clear() {
        try(Connection conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("DELETE FROM auth")) {
                preparedStatement.executeUpdate();
            }
        }
        catch (DataAccessException | SQLException e){
            return;
        }
    }

    @Override
    public AuthData getAuth(String auth) {
        try(Connection conn = DatabaseManager.getConnection()){
            String returnAuth = "SELECT username, authToken FROM Auth WHERE authToken=auth?";
            try (var preparedStatement = conn.prepareStatement(returnAuth)) {
                preparedStatement.setString(1, auth);
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        var id = rs.getInt("username");
                        var name = rs.getString("authToken");
                        //String user = id;
                        AuthData authy = new AuthData(id,name);
                        return authy;
                    }
                }
            }
        }
        catch (DataAccessException | SQLException e){
            return null;
        }
    }

    @Override
    public void deleteAuth(String user, String auth) {

    }
}
