package clientTests;

import Requests.RegisterRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ClientCommunicator;
import ui.ServerFacade;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;


public class ServerFacadeTests {

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
    }
    @BeforeEach
    public void setup(){
        String urlString = "http://localhost:8080/db";
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

    }
    @Test
    public void LoginFail(){

    }
    @Test
    public void CreateGame(){

    }
    @Test
    public void CreateGameFail(){

    }
    @Test
    public void ListGames(){
        ServerFacade.ListGames("");
    }
    @Test
    public void ListGamesFail(){
        ServerFacade.ListGames("");
    }
    @Test
    public void JoinGame(){

    }
    @Test
    public void JoinGameFail(){

    }
    @Test
    public void ObserveGame(){

    }
    @Test
    public void ObserveGameFail(){

    }
    @Test
    public void Logout(){

    }
    @Test
    public void LogoutFail(){

    }

}
