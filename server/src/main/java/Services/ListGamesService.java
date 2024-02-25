package Services;

import Responses.ListGameResponse;
import Responses.LoginResponse;
import Responses.Response;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import record.AuthData;
import record.GameData;

import java.util.HashSet;

public class ListGamesService {
    public ListGameResponse ListGames(String auth){
        AuthDAO authAccess = new AuthDAO();
        AuthData user = authAccess.getAuth(auth);
        if(user==null){
            ListGameResponse response = new ListGameResponse(null);
            response.setMessage("Error: unauthorized");
            return response;
        }
        GameDAO gameAccess = new GameDAO();
        HashSet<GameData> games = gameAccess.getGames();

        ListGameResponse response = new ListGameResponse(games);
        return response;
    }
}
