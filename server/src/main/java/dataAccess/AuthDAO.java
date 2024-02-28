package dataAccess;

import Data.FakeData;
import record.AuthData;

import java.util.UUID;

public interface AuthDAO {
    public String createAuth(String user);

    public void addAuth(String user, String auth);
    public void clear();
    public AuthData getAuth(String auth);
    public void deleteAuth(String user, String auth);
}
