package dataAccess;

import Data.FakeData;

import java.util.UUID;

public class AuthDAO {
    public String createAuth(String user){
        String auth = UUID.randomUUID().toString();
        FakeData.insertAuth(user,auth);
        return auth;
    }
}
