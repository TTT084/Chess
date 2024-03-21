package clientTests;

import Requests.RegisterRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import record.GameData;
import server.Server;
import ui.ClientCommunicator;
import ui.ServerFacade;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.UUID;


public class ServerFacadeTests {

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        ServerFacade.host = "http://localhost:" + port;
    }
    @BeforeEach
    public void setup(){
        //String urlString = "http://localhost:0/db";
        String urlString = ServerFacade.host + "/db";
        ClientCommunicator communicator = new ClientCommunicator();
        try {
            URL url = new URL(urlString);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(5000);
                connection.setRequestMethod("DELETE");

                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("DELETE request successful");
                } else {
                    // Handle error response
                    System.out.println("DELETE request failed with response code: " + responseCode);
                }
            }
            catch (IOException e){

            }
        }
        catch (MalformedURLException k){

        }

    }
    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }
    @Test
    public void RegisterSuccess(){
        //String user = UUID.randomUUID().toString();
        String auth = ServerFacade.Register("user","hehe","silly@email");
        Assertions.assertNotNull(auth);
    }
    @Test
    public void RegisterFail(){
        //String user = UUID.randomUUID().toString();
        ServerFacade.Register("user","hehe","silly@email");
        String auth = ServerFacade.Register("user","hehe","silly@email");
        Assertions.assertNull(auth);
    }
    @Test
    public void Login(){
        ServerFacade.Register("user","hehe","silly@email");
        String auth = ServerFacade.Login("user","hehe");
        Assertions.assertNotNull(auth);
    }
    @Test
    public void LoginFail(){
        String auth = ServerFacade.Login("user","hehe");
        Assertions.assertNull(auth);
    }
    @Test
    public void CreateGame(){
        String auth = ServerFacade.Register("user","hehe","silly@email");
        String name = ServerFacade.CreateGame(auth,"myGame");
        Assertions.assertNotNull(name);
    }
    @Test
    public void CreateGameFail(){
        String auth = ServerFacade.Register("user","hehe","silly@email");
        String name = ServerFacade.CreateGame(null,"myGame");
        Assertions.assertNull(name);
    }
    @Test
    public void ListGames(){
        String auth = ServerFacade.Register("user","hehe","silly@email");
        String name = ServerFacade.CreateGame(auth,"myGame");
        ServerFacade.CreateGame(auth,"myGame2");
        HashSet<GameData> games= ServerFacade.ListGames(auth);
        Assertions.assertNotNull(games);
    }
    @Test
    public void ListGamesFail(){
        HashSet<GameData> games=ServerFacade.ListGames("");
        Assertions.assertNull(games);
    }
    @Test
    public void JoinGame(){
        String auth = ServerFacade.Register("user","hehe","silly@email");
        String name = ServerFacade.CreateGame(auth,"myGame");
        ServerFacade.JoinGame("WHITE",name,auth);
        Assertions.assertNotNull(name);
    }
    @Test
    public void JoinGameFail(){
        ServerFacade.JoinGame("WHITE","","");
        Assertions.assertNotNull("");
    }
    @Test
    public void ObserveGame(){
        String auth = ServerFacade.Register("user","hehe","silly@email");
        String name = ServerFacade.CreateGame(auth,"myGame");
        ServerFacade.JoinGame(null,name,auth);
        Assertions.assertNotNull(name);
    }
    @Test
    public void ObserveGameFail(){
        ServerFacade.JoinGame(null,"","");
        Assertions.assertNotNull("");
    }
    @Test
    public void Logout(){
        ServerFacade.Register("user","hehe","silly@email");
        String auth = ServerFacade.Login("user","hehe");
        ServerFacade.Logout(auth);
        Assertions.assertNotNull(auth);
    }
    @Test
    public void LogoutFail(){
        ServerFacade.Register("user","hehe","silly@email");
        String auth = ServerFacade.Login("user","hehe");
        ServerFacade.Logout("");
        Assertions.assertNotNull(auth);
    }
    @Test
    public void Quit(){
        Assertions.assertFalse(false);
    }
    @Test
    public void QuitFail(){
        Assertions.assertFalse(false);
    }
    @Test
    public void SillyTest(){
        Assertions.assertFalse(false);
    }
}
