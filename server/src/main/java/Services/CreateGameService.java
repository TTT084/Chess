package Services;

import Responses.CreateGameResponse;
import Responses.LoginResponse;
import Responses.Response;
import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.MemoryAuthDAO;
import record.AuthData;
import record.GameData;
import java.util.Random;

public class CreateGameService {
    public CreateGameResponse CreateGame(String auth, String gameName){
        AuthDAO authAccess = new MemoryAuthDAO();
        AuthData user = authAccess.getAuth(auth);
        if(user==null){
            CreateGameResponse response = new CreateGameResponse(null);
            response.setMessage("Error: unauthorized");
            return response;
        }

        GameDAO gameAccess = new GameDAO();
        Random random = new Random();
        int gameID = random.nextInt(1000);
        ChessGame game = new ChessGame();
        String newGamename = "game"+gameID;
        GameData newGame = new GameData(gameID,null,null,gameName,game);
        gameAccess.createGame(newGame);
        //authAccess.updateAuth(user.getUsername());
        CreateGameResponse response = new CreateGameResponse(gameName);
        return response;
    }
}
