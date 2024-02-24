package Services;
import Responses.LoginResponse;
import Responses.Response;
import dataAccess.AuthDAO;
import record.AuthData;

public class LogoutService {
    public Response Logout(String auth){
        AuthDAO authAccess = new AuthDAO();
        AuthData user = authAccess.getAuth(auth);
        if(user==null){
            LoginResponse response = new LoginResponse("","");
            response.setMessage("Error: unauthorized");
            return response;
        }
        if(!auth.equals(user.getAuthToken())){
            LoginResponse response = new LoginResponse("","");
            response.setMessage("Error: unauthorized");
            return response;
        }
        authAccess.updateAuth(user.getUsername());
        Response response = new Response();
        return response;
    }
}
