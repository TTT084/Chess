package dataAccessTests;

import Services.*;
import chess.ChessGame;
import dataAccess.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import record.AuthData;
import record.GameData;
import record.UserData;

import java.util.HashSet;

public class Phase4Tests {
    private String user;
    private String pass;
    private String email;
    private String auth;
    @BeforeEach
    public void setUp(){
        ClearService survy = new ClearService();
        survy.clear();
        user = "myUsername";
        pass = "sillyPass";
        email = "myEmail";
    }
    @AfterEach
    public void cleanup(){
        ClearService survy = new ClearService();
        survy.clear();
    }
    @Test
    public void InsertAuth(){
        AuthDAO authy = new SQLAuthDAO();
        auth = authy.createAuth("usey");
        Assertions.assertNotNull(auth);
    }
    @Test
    public void InsertBadAuth(){
        AuthDAO authy = new SQLAuthDAO();
        auth = authy.createAuth("usey");
        Assertions.assertNotNull(auth);
    }
    @Test
    public void GetAuth(){
        AuthDAO authy = new SQLAuthDAO();
        auth = authy.createAuth("usey");
        AuthData data = authy.getAuth(auth);
        Assertions.assertNotNull(data);
    }

    @Test
    public void GetBadAuth(){
        AuthDAO authy = new SQLAuthDAO();
        AuthData data = authy.getAuth("silly auth");
        Assertions.assertNull(data);
    }
    @Test
    public void ClearAuth(){
        AuthDAO authy = new SQLAuthDAO();
        String auth1 = authy.createAuth("usey");
        Assertions.assertNotNull(auth1);
        authy.clear();

        //Assertions.assertEquals(newUser2,newUser);
    }
    @Test
    public void ClearBadAuth(){
        AuthDAO authy = new SQLAuthDAO();
        String auth1 = authy.createAuth("baduser");
        Assertions.assertNotNull(auth1);
        authy.clear();
    }
    @Test
    public void DeleteAuth(){
        AuthDAO authy = new SQLAuthDAO();
        auth = authy.createAuth("usey");
        authy.deleteAuth("usey",auth);
        Assertions.assertNotNull(auth);
    }
    @Test
    public void DeleteBadAuth(){
        AuthDAO authy = new SQLAuthDAO();
        auth = authy.createAuth("Badusey");
        authy.deleteAuth("Badusey",auth);
        Assertions.assertNotNull(auth);
    }
    @Test
    public void InsertUser(){
        UserDAO usery = new SQLUserDAO();
        usery.createUser("user","pass","myemail");
        Assertions.assertNotEquals("yuh","yeh");
    }
    @Test
    public void InsertBadUser(){
        UserDAO usery = new SQLUserDAO();
        usery.createUser("user","pass","myemail");
        Assertions.assertNotEquals("yuh","yeh");
    }
    @Test
    public void GetUser(){
        UserDAO usery = new SQLUserDAO();
        usery.createUser("user","pass","myemail");
        UserData myUser = usery.getUser("user");
        String name = myUser.getUsername();
        Assertions.assertEquals("user",name);
    }
    @Test
    public void GetBadUser(){
        UserDAO usery = new SQLUserDAO();
        usery.createUser("Baduser","pass","myemail");
        UserData myUser = usery.getUser("Baduser");
        String name = myUser.getUsername();
        Assertions.assertNotEquals("user",name);
    }
    @Test
    public void ClearUser(){
        UserDAO usery = new SQLUserDAO();
        usery.createUser("usey","","");
        Assertions.assertNotEquals("yuh","");
        usery.clear();
    }
    @Test
    public void ClearBadUser(){
        UserDAO usery = new SQLUserDAO();
        usery.createUser("Badusey","","");
        Assertions.assertNotEquals("yuh","");
        usery.clear();
    }
    @Test
    public void VeryifyPassword(){
        UserDAO usery = new SQLUserDAO();
        boolean hmmm=usery.verifyPassword("uij","yuh");
        Assertions.assertFalse(hmmm);
        usery.clear();
    }
    @Test
    public void VeryifyBadPassword(){
        UserDAO usery = new SQLUserDAO();
        boolean hmmm=usery.verifyPassword("uij","yuh");
        Assertions.assertFalse(hmmm);
        usery.clear();
    }
    @Test
    public void ClearGame(){
        GameDAO gameBoi = new SQLGameDAO();
        gameBoi.clear();
        Assertions.assertNotEquals("yuh","");
    }
    @Test
    public void ClearBadGame(){
        GameDAO gameBoi = new SQLGameDAO();
        gameBoi.clear();
        Assertions.assertNotEquals("yuh","asiurgh");
    }
    @Test
    public void InsertGame(){
        GameDAO gameBoi = new SQLGameDAO();
        GameData game = new GameData("1",null,null,"myGame",new ChessGame());
        gameBoi.createGame(game);
        Assertions.assertNotEquals("yuh","");
    }
    @Test
    public void InsertGameFail(){
        GameDAO gameBoi = new SQLGameDAO();
        GameData game = new GameData("1",null,null,"myGame",new ChessGame());
        gameBoi.createGame(game);
        Assertions.assertNotEquals("yuh","");
    }
    @Test
    public void GetGames(){
        GameDAO gameBoi = new SQLGameDAO();
        GameData game = new GameData("1",null,null,"myGame",new ChessGame());
        gameBoi.createGame(game);
        HashSet<GameData> games = gameBoi.getGames();
        Assertions.assertNotNull(games);
    }
    @Test
    public void GetGamesFail(){
        GameDAO gameBoi = new SQLGameDAO();
        gameBoi.clear();
        HashSet<GameData> games = gameBoi.getGames();
        HashSet<GameData> sillygames = new HashSet<>();
        Assertions.assertEquals(sillygames,games);
    }
    @Test
    public void JoinGameWatch(){
        GameDAO gameBoi = new SQLGameDAO();
        gameBoi.clear();
        GameData game = new GameData("1",null,null,"myGame",new ChessGame());
        String id = String.valueOf(gameBoi.createGame(game));
        GameData gamy=gameBoi.joinGame(id,false,"watcher",true);
        Assertions.assertEquals(id,gamy.getGameID());
    }
    @Test
    public void JoinGameWhite(){
        GameDAO gameBoi = new SQLGameDAO();
        gameBoi.clear();
        GameData game = new GameData("1",null,null,"myGame",new ChessGame());
        String id = String.valueOf(gameBoi.createGame(game));
        GameData gamy=gameBoi.joinGame(id,false,"Moon",false);
        Assertions.assertEquals(id,gamy.getGameID());
    }
    @Test
    public void JoinGameBlack(){
        GameDAO gameBoi = new SQLGameDAO();
        gameBoi.clear();
        GameData game = new GameData("1",null,null,"myGame",new ChessGame());
        String id = String.valueOf(gameBoi.createGame(game));
        GameData gamy=gameBoi.joinGame(id,true,"Darkness",false);
        Assertions.assertEquals(id,gamy.getGameID());
    }
    @Test
    public void JoinBadGame(){
        GameDAO gameBoi = new SQLGameDAO();
        gameBoi.clear();
        GameData game = new GameData("1",null,null,"myGame",new ChessGame());
        String id = String.valueOf(gameBoi.createGame(game));
        GameData gamy=gameBoi.joinGame("-15",false,"Moon",false);
        Assertions.assertNull(gamy);
    }
}
