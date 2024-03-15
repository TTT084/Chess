package ui;

import java.io.IOException;
import Requests.RegisterRequest;

public class ServerFacade {
    //doesnt have HTTP stuff
    //7 methods with 2-3 lines of code
    // calls client communicator
    ServerFacade(){

    }
    public static String Register(String username, String password, String email){
        String path = "/user";
        String host = "http://localhost:8080";
        String url = host + path;
        ClientCommunicator communicator = new ClientCommunicator();
        RegisterRequest reg = new RegisterRequest(username,password,email);
        try{
            communicator.doPost(url,reg);
        }
        catch (IOException e){

        }
        return null;
    }
    public static String Login(){
        return null;
    }
    public static void Quit(){

    }
    public static void CreateGame(){

    }
    public static void ListGames(){

    }
    public static void JoinGames(){

    }
    public static void OvserveGame(){

    }
    public static void Logout(){
    }
}
