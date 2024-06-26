package dataAccess;

import Data.FakeData;
import record.UserData;

public class MemoryUserDAO implements UserDAO{
    public UserData getUser(String user){
        UserData response = FakeData.selectUsername(user);
        return response;
    }
    public void createUser(String name, String pass, String email){
        FakeData.insertUser(name,pass,email);
    }
    public void clear(){
        FakeData.clearUser();
    }

    @Override
    public boolean verifyPassword(String hashedPassword, String password) {
        return false;
    }
}
