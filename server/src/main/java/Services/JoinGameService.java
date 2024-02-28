package Services;

import Responses.ListGameResponse;
import Responses.Response;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.MemoryAuthDAO;
import record.AuthData;
import record.GameData;

import java.util.HashSet;

public class JoinGameService {
    public Response JoinGame(String auth, String gameID, String color){
        AuthDAO authAccess = new MemoryAuthDAO();
        AuthData user = authAccess.getAuth(auth);
        if(user==null){
            ListGameResponse response = new ListGameResponse(null);
            response.setMessage("Error: unauthorized");
            return response;
        }
        boolean isBlack=false;
        if(color.equals("BLACK")){
            isBlack=true;
        }
        GameDAO gameAccess = new GameDAO();
        gameAccess.joinGame(gameID,isBlack,user.getUsername());

        Response response = new Response();
        return response;
    }
}
