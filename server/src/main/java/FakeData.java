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

    public FakeData() {
        users=new HashSet<>();
        authTokens = new HashSet<>();
        games = new HashSet<>();
    }

    public String selectUsername(String user){
        for(UserData data : users){
            if(Objects.equals(data.getUsername(), user)){
                return user;
            }
        }
        return null;
    }
    public void insertUser(String name, String pass, String email){
        users.add(new UserData(name,pass,email));
    }
    public void insertAuth(String name, String auth){
        authTokens.add(new AuthData(auth,name));
    }
}