package dataAccess;
import Data.FakeData;

public class UserDAO {
    public boolean getUser(String user){
        String username = FakeData.selectUsername(user);
        if(username!=null){
            return true;
        }
        else{
            return false;
        }
    }
    public void createUser(String name, String pass, String email){
        FakeData.insertUser(name,pass,email);
    }
}
