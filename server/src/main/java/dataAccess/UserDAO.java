package dataAccess;
import Data.FakeData;
import record.UserData;

public interface UserDAO {
    public UserData getUser(String user);
    public void createUser(String name, String pass, String email);
    public void clear();
}
