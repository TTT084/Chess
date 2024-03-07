package dataAccessTests;

import Services.*;
import chess.ChessGame;
import dataAccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import record.AuthData;
import record.GameData;
import record.UserData;

public class Phase4Tests {
    private String user;
    private String pass;
    private String email;
    private String auth;
    @BeforeEach
    public void setUp(){
        ClearService survy = new ClearService();
        survy.Clear();
        user = "myUsername";
        pass = "sillyPass";
        email = "myEmail";
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
    public void ClearUser(){
        UserDAO usery = new SQLUserDAO();
        usery.createUser("usey","","");
        Assertions.assertNotEquals("yuh","");
        usery.clear();
    }
    @Test
    public void InsertGame(){
        GameDAO gameBoi = new SQLGameDAO();
        GameData game = new GameData("1",null,null,"myGame",new ChessGame());
        gameBoi.createGame(game);
        Assertions.assertNotEquals("yuh","");
    }
}
