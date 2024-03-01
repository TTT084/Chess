package dataAccess;

import record.UserData;

public class SQLUserDAO implements UserDAO{
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
