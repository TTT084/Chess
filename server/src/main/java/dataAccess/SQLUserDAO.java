package dataAccess;

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
                name VARCHAR(255) NOT NULL,
                type VARCHAR(255) NOT NULL,
                PRIMARY KEY (id)
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
