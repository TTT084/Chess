package clientTests;

import Requests.RegisterRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ClientCommunicator;
import ui.ServerFacade;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


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
        ServerFacade.Register("newUser","hehe","silly@email");
    }

}
