package handlers;

import Requests.LogoutRequest;
import Services.LogoutService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class LogoutHandler {
    public Object handleRequest(Request req, Response res){
        Gson json = new Gson();
        LogoutRequest logoutRequest = json.fromJson(req.body(), LogoutRequest.class);

        LogoutService LogServ = new LogoutService();
        String auth= logoutRequest.getAuthToken();
        Responses.Response response = LogServ.Logout(auth);
        res.status();

        //serialize
        return json.toJson(response);
    }
}
