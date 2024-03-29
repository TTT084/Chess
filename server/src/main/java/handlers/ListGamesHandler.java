package handlers;

import Requests.LogoutRequest;
import Responses.ListGameResponse;
import Services.ListGamesService;
import Services.LogoutService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import record.*;
import java.util.HashSet;
import java.util.Objects;

public class ListGamesHandler {
    public Object handleRequest(Request req, Response res){
        Gson json = new Gson();
        LogoutRequest lgRequest = json.fromJson(req.body(), LogoutRequest.class);

        ListGamesService LGServ = new ListGamesService();
        String auth= req.headers("authorization");
        ListGameResponse response = LGServ.ListGames(auth);
        res.status(200);
        if (Objects.equals(response.getMessage(), "Error: unauthorized")) {
            res.status(401);
            return json.toJson(response);
        }

        //serialize
        return json.toJson(response);
    }
}
