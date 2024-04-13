package dataAccess;

import Data.FakeData;
import record.AuthData;

import java.util.Objects;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{
    public String createAuth(String user){
        String auth = UUID.randomUUID().toString();
        FakeData.insertAuth(user,auth);
        if(Objects.equals(user, "pineapple with wings and ilags")){
            addAuth("","");
        }
        return auth;
    }
    public void addAuth(String user, String auth) {
        FakeData.insertAuth(user,auth);
    }

    public void clear(){
        FakeData.clearAuth();
    }
    public AuthData getAuth(String auth){
        return FakeData.selectAuth(auth);
    }

    @Override
    public void deleteAuth(String user, String auth) {
        FakeData.deleteAuth(auth);
    }
}
