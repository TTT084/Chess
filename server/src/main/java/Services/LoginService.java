package Services;

import Response.LoginResponse;
import Response.RegisterResponse;
import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import record.UserData;

public class LoginService {
    public LoginResponse Login(String name, String password){
        UserDAO userAccess = new UserDAO();
        UserData user = userAccess.getUser(name);
        if(user==null){
            LoginResponse response = new LoginResponse("","");
            response.setMessage("Error: unauthorized");
            return response;
        }
        if(!password.equals(user.getPassword())){
            LoginResponse response = new LoginResponse("","");
            response.setMessage("Error: unauthorized");
            return response;
        }

        AuthDAO auth = new AuthDAO();
        String token = auth.updateAuth(name);
        LoginResponse response = new LoginResponse(name,token);
        return response;
    }
}
