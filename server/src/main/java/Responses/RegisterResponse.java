package Responses;

public class RegisterResponse extends Response {
    private String authToken;
    private String username;

    public RegisterResponse(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
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
