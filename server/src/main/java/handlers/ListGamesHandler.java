package handlers;

import Requests.LogoutRequest;
import Responses.ListGameResponse;
import Services.ListGamesService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class ListGamesHandler {
    public Object handleRequest(Request req, Response res){
        Gson json = new Gson();
        LogoutRequest lgRequest = json.fromJson(req.body(), LogoutRequest.class);

        ListGamesService lGServ = new ListGamesService();
        String auth= req.headers("authorization");
        ListGameResponse response = lGServ.listGames(auth);
        res.status(200);
        if (Objects.equals(response.getMessage(), "Error: unauthorized")) {
            res.status(401);
            return json.toJson(response);
        }

        //serialize
        return json.toJson(response);
    }
}
