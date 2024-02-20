package handlers;
import Services.RegisterService;
import spark.*;

public class RegisterHandler {

    public Object handleRequest(Request req, Response res){
        Request request = (req)gson.fromJson(reqData, LoginRequest.class);

        RegisterService service = new RegisterService();
        LoginResult result = service.login(request);

        return gson.toJson(result);
…

        //return null;
    }
}
