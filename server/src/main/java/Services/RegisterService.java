package Services;

import dataAccess.AuthDAO;
import dataAccess.UserDAO;

public class RegisterService {

    public String Register(String username, String pass, String email){
        UserDAO user = new UserDAO();

        AuthDAO auth = new AuthDAO();
        return null;
    }
}
