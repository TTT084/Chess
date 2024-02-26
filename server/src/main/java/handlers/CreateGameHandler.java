package handlers;

import Requests.CGRequest;
import Responses.ListGameResponse;
import Services.CreateGameService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    public Object handleRequest(Request req, Response res){
        Gson json = new Gson();
        CGRequest cgRequest = json.fromJson(req.body(), CGRequest.class);

        CreateGameService GCServ = new CreateGameService();
        String auth= cgRequest.getAuthToken();
        String gameName = cgRequest.getGameName();
        Responses.Response response = GCServ.CreateGame(auth,gameName);
        res.status();

        //serialize
        return json.toJson(response);
    }
}
