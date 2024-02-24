package handlers;

import Requests.RegisterRequest;
import Services.RegisterService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import Response.LoginResponse;
import Requests.LoginRequest;
import Services.LoginService;

public class LoginHandler {
    public Object handleRequest(Request req, Response res){
        Gson json = new Gson();
        LoginRequest loginRequest = json.fromJson(req.body(), LoginRequest.class);

        LoginService LogServ = new LoginService();
        String pass= loginRequest.getPassword();
        String name = loginRequest.getUsername();
        LoginResponse response = LogServ.Login(name,pass);
        res.status();

        //serialize
        return json.toJson(response);
    }
}
