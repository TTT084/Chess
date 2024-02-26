package Services;

import Responses.LoginResponse;
import Responses.Response;
import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import record.AuthData;
import record.GameData;
import java.util.Random;

public class CreateGameService {
    public Response CreateGame(String auth, String gameName){
        AuthDAO authAccess = new AuthDAO();
        AuthData user = authAccess.getAuth(auth);
        if(user==null){
            LoginResponse response = new LoginResponse("","");
            response.setMessage("Error: unauthorized");
            return response;
        }

        GameDAO gameAccess = new GameDAO();
        Random random = new Random();
        int gameID = random.nextInt(1000);
        ChessGame game = new ChessGame();
        String newGamename = "game"+gameID;
        GameData newGame = new GameData(gameID,null,null,newGamename,game);
        gameAccess.createGame(newGame);
        authAccess.updateAuth(user.getUsername());
        Response response = new Response();
        return response;
    }
}
