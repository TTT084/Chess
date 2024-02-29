package handlers;

import Requests.JGRequest;
import Services.JoinGameService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class JoinGameHandler {
    public Object handleRequest(Request req, Response res){
        Gson json = new Gson();
        JGRequest jgRequest = json.fromJson(req.body(), JGRequest.class);

        JoinGameService JGServ = new JoinGameService();
        String auth= req.headers("authorization");
        String gameName = jgRequest.getGameID();
        String color =jgRequest.getPlayerColor();
        Responses.Response response = JGServ.JoinGame(auth,gameName, color);
        if (Objects.equals(response.getMessage(), "Error: unauthorized")) {
            res.status(401);
            return json.toJson(response);
        }
        if (Objects.equals(response.getMessage(), "Error: already taken")) {
            res.status(403);
            return json.toJson(response);
        }
        if (Objects.equals(response.getMessage(), "Error: bad request")) {
            res.status(400);
            return json.toJson(response);
        }
        res.status(200);

        //serialize
        return json.toJson(response);
    }
}
