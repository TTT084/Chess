package dataAccess;

import record.UserData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAO{
    SQLUserDAO() {
        String createDatabase = "SELECT COUNT(*) AS count FROM information_schema.SCHEMATA WHERE SCHEMA_NAME = ?";
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
