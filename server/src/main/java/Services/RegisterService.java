package Services;

import Response.RegisterResponse;
import dataAccess.AuthDAO;
import dataAccess.UserDAO;

public class RegisterService {

    public RegisterResponse Register(String username, String pass, String email){
        UserDAO user = new UserDAO();
        if(user.getUser(username)){
            return null;
        }

        AuthDAO auth = new AuthDAO();
        String token = auth.createAuth(username);
        RegisterResponse response = new RegisterResponse(token,username);
        return response;
    }
}
