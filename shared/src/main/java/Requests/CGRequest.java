package Requests;

public class CGRequest {
    String authToken;
    String gameName;

    public CGRequest(String authToken, String gameName) {
        this.authToken = authToken;
        this.gameName = gameName;
    }

    //public String getAuthToken() {return authToken;}

    //public void setAuthToken(String authToken) {this.authToken = authToken;}

    public String getGameName() {
        return gameName;
    }

    //public void setGameName(String gameName) {this.gameName = gameName;}
}
