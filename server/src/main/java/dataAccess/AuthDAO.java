package dataAccess;

import Data.FakeData;
import record.AuthData;

import java.util.UUID;

public class AuthDAO {
    public String createAuth(String user){
        String auth = UUID.randomUUID().toString();
        FakeData.insertAuth(user,auth);
        return auth;
    }
    public String updateAuth(String user){
        String auth = UUID.randomUUID().toString();
        FakeData.updateAuth(user,auth);
        return auth;
    }
    public void clear(){
        FakeData.clearAuth();
    }
    public AuthData getAuth(String auth){
        return FakeData.selectAuth(auth);
    }
}
