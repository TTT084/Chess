package Data;

import chess.ChessGame;
import org.eclipse.jetty.server.Authentication;
import record.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class FakeData {
    private static HashSet<UserData> users;
    private static HashSet<AuthData> authTokens;
    private static HashSet<GameData> games;

    static {
        users=new HashSet<>();
        authTokens = new HashSet<>();
        games = new HashSet<>();
    }

    public static UserData selectUsername(String user){
        for(UserData data : users){
            if(Objects.equals(data.getUsername(), user)){
                return data;
            }
        }
        return null;
    }
    public static void insertUser(String name, String pass, String email){
        users.add(new UserData(name,pass,email));
    }
    public static void insertAuth(String name, String auth){
        authTokens.add(new AuthData(auth,name));
    }
    public static void updateAuth(String name, String auth){
        for (AuthData data: authTokens){
            if(Objects.equals(data.getUsername(), name)){
                data.setAuthToken(auth);
            }
        }
    }
    public static AuthData selectAuth(String auth){
        for(AuthData data : authTokens){
            if(Objects.equals(data.getAuthToken(), auth)){
                return data;
            }
        }
        return null;
    }
    public static void clearUser(){
        users.clear();
    }
    public static void clearAuth(){
        authTokens.clear();
    }
    public static void clearGames(){
        games.clear();
    }
    public static HashSet<GameData> getGames(){
        return games;
    }
    public static void createGame(GameData game){
        games.add(game);
    }
}
