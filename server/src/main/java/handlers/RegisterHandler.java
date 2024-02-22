package handlers;
import Requests.RegisterRequest;
import Services.RegisterService;
import com.google.gson.GsonBuilder;
import spark.*;
import com.google.gson.Gson;

public class RegisterHandler {

    public Object handleRequest(Request req, Response res){
        //deserialize
        Gson json = new Gson();
        RegisterRequest registerRequest = json.fromJson(req.body(), RegisterRequest.class);

        RegisterService regServ = new RegisterService();
//        GsonBuilder builder = new GsonBuilder();
//        builder.registerTypeAdapter(req);
//        Gson gson = builder.create();

        //Gson gson = new Gson();

        Request request = gson.fromJson(req, LoginRequest.class);

        RegisterService service = new RegisterService();
        LoginResult result = service.login(request);

        //serialize
        return gson.toJson(result);
…

        //return null;
    }
}
