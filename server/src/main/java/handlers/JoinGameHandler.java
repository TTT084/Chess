package handlers;

import Requests.CGRequest;
import Services.JoinGameService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class JoinGameHandler {
    public Object handleRequest(Request req, Response res){
        Gson json = new Gson();
        CGRequest cgRequest = json.fromJson(req.body(), CGRequest.class);
        req.headers("authorization");

        JoinGameService JGServ = new JoinGameService();
        String auth= cgRequest.getAuthToken();
        String gameName = cgRequest.getGameName();
        Responses.Response response = JGServ.JoinGame(auth,gameName);
        res.status();

        //serialize
        return json.toJson(response);
    }
}
