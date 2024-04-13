package Responses;

public class CreateGameResponse extends Response{
    String gameID;

    public CreateGameResponse(String gameName) {
        this.gameID = gameName;
    }

    public String getGameName() {
        return gameID;
    }

    public void setGameName(String gameName) {
        this.gameID = gameName;
    }
}
