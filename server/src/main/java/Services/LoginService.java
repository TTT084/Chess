package Services;

import Responses.LoginResponse;
import dataAccess.*;
import record.UserData;

public class LoginService {
    public LoginResponse login(String name, String password){
        UserDAO userAccess = new SQLUserDAO();
        UserData user = userAccess.getUser(name);
        if(user==null){
            LoginResponse response = new LoginResponse(null,null);
            response.setMessage("Error: unauthorized");
            return response;
        }
        if(!userAccess.verifyPassword(user.getPassword(), password)){
            LoginResponse response = new LoginResponse(null,null);
            response.setMessage("Error: unauthorized");
            return response;
        }

        AuthDAO auth = new SQLAuthDAO();
        String token = auth.createAuth(name);
        LoginResponse response = new LoginResponse(name,token);
        return response;
    }
}
