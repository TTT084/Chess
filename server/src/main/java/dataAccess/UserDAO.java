package dataAccess;
import Data.FakeData;
import record.UserData;

public class UserDAO {
    public UserData getUser(String user){
        UserData response = FakeData.selectUsername(user);
//        if (response==null){
//            throw new DataAccessException("No user found");
//        }
        return response;
    }
    public void createUser(String name, String pass, String email){
        FakeData.insertUser(name,pass,email);
    }
}
