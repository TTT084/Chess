package handlers;

import Requests.LogoutRequest;
import Services.LogoutService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class LogoutHandler {
    public Object handleRequest(Request req, Response res){
        Gson json = new Gson();
        LogoutRequest logoutRequest = json.fromJson(req.body(), LogoutRequest.class);

        LogoutService logServ = new LogoutService();
        String auth= req.headers("authorization");
        Responses.Response response = logServ.logout(auth);
        String message = response.getMessage();;
        if(auth==null|| Objects.equals(message, "Error: unauthorized")){
            //response = new Responses.Response();
            res.status(401);

            //serialize
            return json.toJson(response);
        }
        res.status(200);

        //serialize
        return json.toJson(response);
    }
}
