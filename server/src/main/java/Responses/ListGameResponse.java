package Responses;

import record.GameData;

import java.util.HashSet;

public class ListGameResponse extends Response{
    private HashSet<GameData> games;

    public ListGameResponse(HashSet<GameData> games) {
        this.games = games;
    }

    public HashSet<GameData> getGames() {
        return games;
    }

    public void setGames(HashSet<GameData> games) {
        this.games = games;
    }
}
