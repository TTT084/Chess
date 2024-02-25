package handlers;

import Requests.LogoutRequest;
import Responses.ListGameResponse;
import Services.ListGamesService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    public Object handleRequest(Request req, Response res){
        Gson json = new Gson();
        LogoutRequest lgRequest = json.fromJson(req.body(), LogoutRequest.class);

        ListGamesService LGServ = new ListGamesService();
        String auth= lgRequest.getAuthToken();
        ListGameResponse response = LGServ.ListGames(auth);
        res.status();

        //serialize
        return json.toJson(response);
    }
}
