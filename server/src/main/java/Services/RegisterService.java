package Services;

import Responses.RegisterResponse;
import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import record.UserData;

public class RegisterService {

    public RegisterResponse Register(String username, String pass, String email){
        UserDAO user = new UserDAO();
        UserData data;
        //try{
            data = user.getUser(username);
        //}
//        catch (DataAccessException e){
//            int dosomething = 1;
//        }
        if(data!=null){
            RegisterResponse response = new RegisterResponse("","");
            response.setMessage("Error: bad request");
            return response;
        }
        user.createUser(username,pass,email);

        AuthDAO auth = new AuthDAO();
        String token = auth.createAuth(username);
        RegisterResponse response = new RegisterResponse(token,username);
        return response;
    }
}
