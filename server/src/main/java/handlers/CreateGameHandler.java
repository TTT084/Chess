package handlers;

import Requests.CGRequest;
import Responses.CreateGameResponse;
import Responses.ListGameResponse;
import Services.CreateGameService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class CreateGameHandler {
    public Object handleRequest(Request req, Response res){
        Gson json = new Gson();
        CGRequest cgRequest = json.fromJson(req.body(), CGRequest.class);

        CreateGameService GCServ = new CreateGameService();
        //String auth= cgRequest.getAuthToken();
        String auth = req.headers("authorization");
        String gameName = cgRequest.getGameName();
        CreateGameResponse response = GCServ.CreateGame(auth,gameName);
        if (Objects.equals(response.getMessage(), "Error: unauthorized")) {
            res.status(401);
        }
        if (Objects.equals(response.getMessage(), "Error: bad request")) {
            res.status(400);
        }
        res.status();

        //serialize
        return json.toJson(response);
    }
}
