package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;


public class UserInterface {
    static String authToken;
    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(System.in);

        PreLogin(out, scanner);
        PostLogin(out, scanner);
    }
    public static void UI(){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(System.in);
        if(authToken==null){
            PreLogin(out, scanner);
        }
        else{
            PostLogin(out, scanner);
        }
    }
    public static void PreLogin(PrintStream out, Scanner scanner){
        out.println("Welcome to chess!");
        out.println();
        out.println("1. Register <USERNAME> <PASSWORD> <EMAIL>");
        out.println("2. Login <USERNAME> <PASSWORD>");
        out.println("3. Quit");
        out.println("4. Help");
        String input = scanner.next();
        String name = scanner.next();
        String pass = scanner.next();
        String email = scanner.next();
    }

    private static void PreHelp(PrintStream out, Scanner scanner){
        out.println("HELP");
        out.println();
        out.println("1. Register <USERNAME> <PASSWORD> <EMAIL> -creates an account" );
        out.println("2. Login <USERNAME> <PASSWORD> - to play chess");
        out.println("3. Quit -exits program");
        out.println("4. Help -with possible commands");
    }
    public static void PostLogin(PrintStream out, Scanner scanner){
        out.println();
        out.println("1. Create Game <NAME>");
        out.println("2. List Games");
        out.println("3. Join Game <ID> [W]hite[B]lack[]<empty>");
        out.println("4. Observe Game <ID>");
        out.println("5. Logout");
        out.println("6. Quit");
        out.println("7. Help");
        String input = scanner.next();
        String name = scanner.next();
        String player = scanner.next();
    }


    private static void PostHelp(PrintStream out, Scanner scanner){
        out.println("HELP");
        out.println();
        out.println("1. Create Game <NAME> -creates a new game");
        out.println("2. List Games -lists all games");
        out.println("3. Join Game <ID> [W]hite[B]lack[]<empty> -join a game as the white or black player or as an observer");
        out.println("4. Observe Game <ID> -joins game as an observer");
        out.println("5. Logout");
        out.println("6. Quit -exits program");
        out.println("7. Help -with possible commands");
    }
    private static void PreInput(PrintStream out, String input, String name, String pass, String email){
        input = input.toLowerCase();
        if(input.equals("register")){
            input = "1";
        } else if (input.equals("login")) {
            input = "2";
        }else if (input.equals("quit")) {
            input = "3";
        }else if (input.equals("help")) {
            input = "4";
        }
        switch (input){
            case "1":
                authToken=ServerFacade.Register();
                 break;
            case "2":
                authToken=ServerFacade.Login();
                break;
            case "3":
                ServerFacade.Quit();
                break;
            case "4":
                PreHelp();
                break;
            default:
                out.println("Bad request. Please try again");
        }
    }
    private static void PostInput(PrintStream out, String input, String name, String pass, String email){
        input = input.toLowerCase();
        if(input.equals("create game") || input.equals("create")){
            input = "1";
        } else if (input.equals("list games") || input.equals("list")) {
            input = "2";
        }else if (input.equals("join game") || input.equals("join")) {
            input = "3";
        }else if (input.equals("observe game") || input.equals("observe")) {
            input = "4";
        }else if (input.equals("logout")) {
            input = "5";
        }else if (input.equals("quit")) {
            input = "6";
        }else if (input.equals("help")) {
            input = "7";
        }
        switch (input){
            case "1":
                ServerFacade.CreateGame();
                break;
            case "2":
                ServerFacade.ListGames();
                break;
            case "3":
                ServerFacade.JoinGames();
                break;
            case "4":
                ServerFacade.OvserveGame();
                break;
            case "5":
                ServerFacade.Logout();
                break;
            case "6":
                ServerFacade.Quit();
            case "7":
                PostHelp();
            default:
                out.println("Bad request. Please try again");
        }
    }
}
