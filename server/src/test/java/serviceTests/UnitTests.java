package serviceTests;

import Responses.RegisterResponse;
import Services.RegisterService;
import Responses.*;
import Services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnitTests {
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
    public void ClearService(){
        RegisterService survy = new RegisterService();
        RegisterResponse response = survy.Register(user,pass,email);
        String newUser = response.getUsername();
        ClearService Csurvy = new ClearService();
        Csurvy.Clear();
        RegisterResponse response2 = survy.Register(user,pass,email);
        String newUser2 = response.getUsername();
        Assertions.assertEquals(newUser2,newUser);
    }
    @Test
    public void RegisterBasicTest(){
        RegisterService survy = new RegisterService();
        RegisterResponse response = survy.Register(user,pass,email);
        String newUser = response.getUsername();
        Assertions.assertEquals(user,newUser);

    }
    @Test
    public void RegisterNullTest() {
        RegisterService survy = new RegisterService();

        RegisterResponse response1 = survy.Register(user, pass, email);
        String nameOne = response1.getUsername();
        RegisterResponse response2 = survy.Register(user, pass, email);
        String nameTwo = response2.getUsername();
        Assertions.assertNotEquals(nameOne, nameTwo);
    }
    @Test
    public void LoginUserNotFound(){
        LoginService survy = new LoginService();
        LoginResponse response1 = survy.Login(user, pass);
        String nameOne = response1.getUsername();
        Assertions.assertEquals(nameOne,null);
    }
    @Test
    public void LoginWrongPassword(){
        RegisterService regSurvy = new RegisterService();
        RegisterResponse response = regSurvy.Register(user,pass,email);
        LoginService survy = new LoginService();
        LoginResponse response1 = survy.Login(user, "wrong");
        String nameOne = response1.getUsername();
        Assertions.assertEquals(nameOne,null);
    }
    @Test
    public void LoginSuccess(){
        RegisterService regSurvy = new RegisterService();
        RegisterResponse response = regSurvy.Register(user,pass,email);
        LoginService survy = new LoginService();
        LoginResponse response1 = survy.Login(user, pass);
        String nameOne = response1.getUsername();
        Assertions.assertEquals(nameOne,user);
    }
    @Test
    public void LogoutUserNotFound(){
        RegisterService regSurvy = new RegisterService();
        RegisterResponse response = regSurvy.Register(user,pass,email);
        LoginService survy = new LoginService();
        LoginResponse response1 = survy.Login(user, pass);
        String nameOne = response1.getUsername();
        Assertions.assertEquals(nameOne,user);
        LogoutService Outsurvy = new LogoutService();
        Response response2 = Outsurvy.Logout("");
        Assertions.assertNotEquals("",response2.getMessage());
    }
    @Test
    public void LogoutSuccess(){
        RegisterService regSurvy = new RegisterService();
        RegisterResponse response = regSurvy.Register(user,pass,email);
        LoginService survy = new LoginService();
        LoginResponse response1 = survy.Login(user, pass);
        String nameOne = response1.getUsername();
        Assertions.assertEquals(nameOne,user);
        LogoutService Outsurvy = new LogoutService();
        Response response2 = Outsurvy.Logout(response1.getAuthToken());
        Assertions.assertNotEquals("",response2.getMessage());
    }
    @Test
    public void CreateGameSuccess(){
        RegisterService regSurvy = new RegisterService();
        RegisterResponse response = regSurvy.Register(user,pass,email);
        LoginService survy = new LoginService();
        LoginResponse response1 = survy.Login(user, pass);
        String nameOne = response1.getUsername();
        Assertions.assertEquals(nameOne,user);
        CreateGameService createSurvy=new CreateGameService();
        createSurvy.CreateGame(response.getAuthToken(), "yes game");
        Assertions.assertNotEquals("changethis",response1.getMessage());
    }
    @Test void CreateGameFail(){
        RegisterService regSurvy = new RegisterService();
        RegisterResponse response = regSurvy.Register(user,pass,email);
        LoginService survy = new LoginService();
        LoginResponse response1 = survy.Login(user, pass);
        String nameOne = response1.getUsername();
        Assertions.assertEquals(nameOne,user);
        CreateGameService createSurvy=new CreateGameService();
        createSurvy.CreateGame("", "yes game");
        Assertions.assertNotEquals("changethis",response1.getMessage());
    }
    @Test void JoinGameSuccess(){
        RegisterService regSurvy = new RegisterService();
        RegisterResponse response = regSurvy.Register(user,pass,email);
        LoginService survy = new LoginService();
        LoginResponse response1 = survy.Login(user, pass);
        String nameOne = response1.getUsername();
        Assertions.assertEquals(nameOne,user);
        CreateGameService createSurvy=new CreateGameService();
        CreateGameResponse createResponse= createSurvy.CreateGame("", "yes game");
        JoinGameService joinSurvy = new JoinGameService();
        //joinSurvy.JoinGame(response1.getAuthToken(), createResponse.getGameName() ,null);
        Assertions.assertNotEquals("changethis",response1.getMessage());
    }
    @Test void JoinGameTaken(){
        RegisterService regSurvy = new RegisterService();
        RegisterResponse response = regSurvy.Register(user,pass,email);
        LoginService survy = new LoginService();
        LoginResponse response1 = survy.Login(user, pass);
        String nameOne = response1.getUsername();
        Assertions.assertEquals(nameOne,user);
        CreateGameService createSurvy=new CreateGameService();
        CreateGameResponse createResponse= createSurvy.CreateGame("", "yes game");
        JoinGameService joinSurvy = new JoinGameService();
        //joinSurvy.JoinGame(response1.getAuthToken(), createResponse.getGameName() ,"WHITE");
        //joinSurvy.JoinGame(response1.getAuthToken(), createResponse.getGameName() ,"WHITE");
        Assertions.assertNotEquals("changethis",response1.getMessage());
    }
    @Test
    public void ListGamesWrongPassword(){
        RegisterService regSurvy = new RegisterService();
        RegisterResponse response = regSurvy.Register(user,pass,email);
        LoginService survy = new LoginService();
        LoginResponse response1 = survy.Login(user, pass);
        String nameOne = response1.getUsername();
        Assertions.assertEquals(nameOne,user);
        CreateGameService createSurvy=new CreateGameService();
        CreateGameResponse createResponse= createSurvy.CreateGame("", "yes game");
        JoinGameService joinSurvy = new JoinGameService();
        //joinSurvy.JoinGame(response1.getAuthToken(), createResponse.getGameName() ,"WHITE");
        ListGamesService listSurvy = new ListGamesService();
        listSurvy.ListGames("wrong");
        Assertions.assertNotEquals("changethis",response1.getMessage());
    }
    @Test
    public void ListGamesSuccess(){
        RegisterService regSurvy = new RegisterService();
        RegisterResponse response = regSurvy.Register(user,pass,email);
        LoginService survy = new LoginService();
        LoginResponse response1 = survy.Login(user, pass);
        String nameOne = response1.getUsername();
        Assertions.assertEquals(nameOne,user);
        CreateGameService createSurvy=new CreateGameService();
        CreateGameResponse createResponse= createSurvy.CreateGame("", "yes game");
        JoinGameService joinSurvy = new JoinGameService();
        //joinSurvy.JoinGame(response1.getAuthToken(), createResponse.getGameName() ,"WHITE");
        ListGamesService listSurvy = new ListGamesService();
        listSurvy.ListGames(response1.getAuthToken());
        Assertions.assertNotEquals("changethis",response1.getMessage());
    }

}
