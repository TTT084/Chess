package Services;

import Responses.LoginResponse;
import dataAccess.AuthDAO;
import dataAccess.MemoryAuthDAO;
import dataAccess.UserDAO;
import record.UserData;

public class LoginService {
    public LoginResponse Login(String name, String password){
        UserDAO userAccess = new UserDAO();
        UserData user = userAccess.getUser(name);
        if(user==null){
            LoginResponse response = new LoginResponse(null,null);
            response.setMessage("Error: unauthorized");
            return response;
        }
        if(!password.equals(user.getPassword())){
            LoginResponse response = new LoginResponse(null,null);
            response.setMessage("Error: unauthorized");
            return response;
        }

        AuthDAO auth = new MemoryAuthDAO();
        String token = auth.createAuth(name);
        LoginResponse response = new LoginResponse(name,token);
        return response;
    }
}
