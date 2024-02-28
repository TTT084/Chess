package handlers;

import Requests.JGRequest;
import Services.JoinGameService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class JoinGameHandler {
    public Object handleRequest(Request req, Response res){
        Gson json = new Gson();
        JGRequest jgRequest = json.fromJson(req.body(), JGRequest.class);

        JoinGameService JGServ = new JoinGameService();
        String auth= req.headers("authorization");
        String gameName = jgRequest.getGameID();
        String color =jgRequest.getPlayerColor();
        Responses.Response response = JGServ.JoinGame(auth,gameName, color);
        res.status();

        //serialize
        return json.toJson(response);
    }
}
