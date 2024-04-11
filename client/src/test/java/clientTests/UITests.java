package clientTests;

import chess.ChessGame;
import org.junit.jupiter.api.Test;
import ui.UserInterface;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UITests {
    @Test
    public void highlight(){
        UserInterface usy = new UserInterface();
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(System.in);
        String input = "5 B2";
        String[] words = input.split(" ");
        usy.setMyGame(new ChessGame());
        usy.gameInput(out,words);
    }
    @Test
    public void makeMove(){
        UserInterface usy = new UserInterface();
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(System.in);
        String input = "3 B2 B3";
        String[] words = input.split(" ");
        usy.setMyGame(new ChessGame());
        usy.gameInput(out,words);
    }
}
