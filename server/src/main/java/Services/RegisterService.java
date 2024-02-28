package Services;

import Responses.RegisterResponse;
import dataAccess.AuthDAO;
import dataAccess.MemoryAuthDAO;
import dataAccess.UserDAO;
import record.UserData;

public class RegisterService {

    public RegisterResponse Register(String username, String pass, String email){
        if(username==null|| username.isEmpty() ||pass==null||pass.isEmpty()||email==null||email.isBlank()){
            RegisterResponse response = new RegisterResponse(null,null);
            response.setMessage("Error: bad request");
            return response;
        }
        UserDAO user = new UserDAO();
        UserData data;
        //try{
            data = user.getUser(username);
        //}
//        catch (DataAccessException e){
//            int dosomething = 1;
//        }
        if(data!=null){
            RegisterResponse response = new RegisterResponse(null,null);
            response.setMessage("Error: already taken");
            return response;
        }
        user.createUser(username,pass,email);

        AuthDAO auth = new MemoryAuthDAO();
        String token = auth.createAuth(username);
        RegisterResponse response = new RegisterResponse(token,username);
        return response;
    }
}
