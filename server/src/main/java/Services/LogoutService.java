package Services;
import Responses.LoginResponse;
import Responses.Response;
import dataAccess.*;
import record.AuthData;

public class LogoutService {
    public Response Logout(String auth){
        AuthDAO authAccess = new SQLAuthDAO();
        AuthData user = authAccess.getAuth(auth);
        if(user==null){
            LoginResponse response = new LoginResponse(null,null);
            response.setMessage("Error: unauthorized");
            return response;
        }
        //delete authtoken based on the authtoken
        authAccess.deleteAuth(user.getUsername(), user.getAuthToken());
        Response response = new Response();
        return response;
    }
}
