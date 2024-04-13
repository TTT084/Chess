package Services;

import Responses.ListGameResponse;
import dataAccess.*;
import record.AuthData;
import record.GameData;

import java.util.HashSet;

public class ListGamesService {
    public ListGameResponse listGames(String auth){
        AuthDAO authAccess = new SQLAuthDAO();
        AuthData user = authAccess.getAuth(auth);
        if(user==null){
            ListGameResponse response = new ListGameResponse(null);
            response.setMessage("Error: unauthorized");
            return response;
        }
        GameDAO gameAccess = new SQLGameDAO();
        HashSet<GameData> games = gameAccess.getGames();

        ListGameResponse response = new ListGameResponse(games);
        return response;
    }
}
