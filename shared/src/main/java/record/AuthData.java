package record;

public class AuthData {
    private String authToken;
    private String username;

    public AuthData(String auth, String user){
        authToken=auth;
        username=user;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
