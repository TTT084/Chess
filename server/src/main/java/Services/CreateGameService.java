package Services;

import Responses.CreateGameResponse;
import chess.ChessGame;
import dataAccess.*;
import record.AuthData;
import record.GameData;
import java.util.Random;

public class CreateGameService {
    public CreateGameResponse createGame(String auth, String gameName){
        AuthDAO authAccess = new SQLAuthDAO();
        AuthData user = authAccess.getAuth(auth);
        if(user==null){
            CreateGameResponse response = new CreateGameResponse(null);
            response.setMessage("Error: unauthorized");
            return response;
        }

        GameDAO gameAccess = new SQLGameDAO();
        Random random = new Random();
        int gameID = random.nextInt(10000);
        ChessGame game = new ChessGame();
        String newGamename = "game"+gameID;
        String returnGameID = String.valueOf(gameID);
        GameData newGame = new GameData(returnGameID,null,null,gameName,game);
        int identity = gameAccess.createGame(newGame);
        //authAccess.updateAuth(user.getUsername());
        //CreateGameResponse response = new CreateGameResponse(returnGameID);
        CreateGameResponse response = new CreateGameResponse(String.valueOf(identity));
        return response;
    }
}
