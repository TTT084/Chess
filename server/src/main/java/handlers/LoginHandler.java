package handlers;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import Responses.LoginResponse;
import Requests.LoginRequest;
import Services.LoginService;

import java.util.Objects;

public class LoginHandler {
    public Object handleRequest(Request req, Response res){
        Gson json = new Gson();
        LoginRequest loginRequest = json.fromJson(req.body(), LoginRequest.class);

        LoginService LogServ = new LoginService();
        String pass= loginRequest.getPassword();
        String name = loginRequest.getUsername();
        LoginResponse response = LogServ.Login(name,pass);
        if (Objects.equals(response.getMessage(), "Error: unauthorized")) {
            res.status(401);
        }
        //res.status();

        //serialize
        return json.toJson(response);
    }
}
