package Services;

import Responses.CreateGameResponse;
import Responses.ListGameResponse;
import Responses.Response;
import dataAccess.*;
import record.AuthData;
import record.GameData;

import java.util.HashSet;
import java.util.Objects;

public class JoinGameService {
    public Response JoinGame(String auth, String gameID, String color){
        AuthDAO authAccess = new SQLAuthDAO();
        AuthData user = authAccess.getAuth(auth);
        if(user==null){
            ListGameResponse response = new ListGameResponse(null);
            response.setMessage("Error: unauthorized");
            return response;
        }
        CreateGameResponse response = new CreateGameResponse(null);
        boolean isWatcher = false;
        boolean isBlack=false;
        boolean hasColor = false;
        if(color==null){
            isWatcher=true;
            //return response;
        }
        else if(color.equals("BLACK")||color.equals("Black")){
            hasColor=true;
            isBlack=true;
        }
        else if(color.equals("WHITE")||color.equals("White")){
            hasColor=true;
        }
        GameDAO gameAccess = new SQLGameDAO();
        GameData game =gameAccess.joinGame(gameID,isBlack,user.getUsername(),isWatcher);

        if(game==null){
            response = new CreateGameResponse(null);
            response.setMessage("Error: bad request");
            return response;
        }
        if(!Objects.equals(game.getGameID(), gameID)){
            response = new CreateGameResponse(null);
            response.setMessage("Error: bad request");
            return response;
        }
        if(isBlack){
            if(!Objects.equals(game.getBlackUsername(), user.getUsername())){
                response = new CreateGameResponse(null);
                response.setMessage("Error: already taken");
                return response;
            }
        }
        else if (!isWatcher&&!hasColor){
            if(!Objects.equals(game.getWhiteUsername(), user.getUsername())){
                response = new CreateGameResponse(null);
                response.setMessage("Error: already taken");
                return response;
            }
        }
        response.setGameName(gameID);
        return response;
    }
}
