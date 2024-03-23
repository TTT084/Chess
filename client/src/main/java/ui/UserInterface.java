package ui;

import chess.ChessGame;
import record.GameData;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;


public class UserInterface {
    static String authToken;
    static boolean help = false;
    static boolean pursue = true;
    private static ArrayList<String> allGames = new ArrayList<>();

    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(System.in);

        //PreLogin(out, scanner);
        //PostLogin(out, scanner);
        UI();
    }
    public static void UI(){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(System.in);
        out.println("Welcome to chess!");
        out.println();
        while(pursue){
            if(authToken==null){
                if(help){
                    PreHelp(out, scanner);
                    help = false;
                }
                else {
                    PreLogin(out, scanner);
                }
            }
            else{
                if(help){
                    PostHelp(out,scanner);
                    help = false;
                }
                else {
                    PostLogin(out, scanner);
                }
            }
        }

    }
    public static void PreLogin(PrintStream out, Scanner scanner){
        out.println("1. Register <USERNAME> <PASSWORD> <EMAIL>");
        out.println("2. Login <USERNAME> <PASSWORD>");
        out.println("3. Quit");
        out.println("4. Help");
        String input = scanner.nextLine();
        String[] words = input.split(" ");
        PreInput(out,words);
    }

    private static void PreHelp(PrintStream out, Scanner scanner){
        out.println("HELP");
        out.println();
        out.println("1. Register <USERNAME> <PASSWORD> <EMAIL> -creates an account" );
        out.println("2. Login <USERNAME> <PASSWORD> - to play chess");
        out.println("3. Quit -exits program");
        out.println("4. Help -with possible commands");
        String input = scanner.nextLine();
        String[] words = input.split(" ");
        PreInput(out,words);
    }
    public static void PostLogin(PrintStream out, Scanner scanner){
        out.println();
        out.println("1. Create Game <NAME>");
        out.println("2. List Games");
        out.println("3. Join Game <ID> [W]hite[B]lack[]");
        out.println("4. Observe Game <ID>");
        out.println("5. Logout");
        out.println("6. Quit");
        out.println("7. Help");
        String input = scanner.nextLine();
        String[] words = input.split(" ");
        PostInput(out,words);
    }


    private static void PostHelp(PrintStream out, Scanner scanner){
        out.println("HELP");
        out.println();
        out.println("1. Create Game <NAME> -creates a new game");
        out.println("2. List Games -lists all games");
        out.println("3. Join Game <ID> [W]hite[B]lack -join a game as the white or black player");
        out.println("4. Observe Game <ID> -joins game as an observer");
        out.println("5. Logout");
        out.println("6. Quit -exits program");
        out.println("7. Help -with possible commands");
        String input = scanner.nextLine();
        String[] words = input.split(" ");
        PostInput(out,words);
    }
    private static void PreInput(PrintStream out, String[] words){
        if(words.length==0){
            out.println("Bad request. Please try again");
            return;
        }
        String input = words[0];
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
                if(words.length<4){
                    out.println("Bad request. Please try again");
                    return;
                }
                authToken=ServerFacade.Register(words[1],words[2],words[3]);
                if(authToken==null){
                    out.println("Register failed. Please try again");
                }
                break;
            case "2":
                if(words.length<3){
                    out.println("Bad request. Please try again");
                    return;
                }
                authToken=ServerFacade.Login(words[1],words[2]);
                if(authToken==null){
                    out.println("Login failed. Please try again");
                }
                break;
            case "3":
                ServerFacade.Quit();
                pursue=false;
                break;
            case "4":
                //PreHelp();
                help = true;
                return;
                //break;
            default:
                out.println("Bad request. Please try again");
        }
        Scanner scanner = new Scanner(System.in);
        out.println("Press Enter to continue");
        String enter = scanner.nextLine();
    }
    private static String PostInput(PrintStream out, String[] words){
        int num = 0;
        String insert = "";
        if(words.length==0){
            out.println("Bad request. Please try again");
            return null;
        }
        String input = words[0];
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
                if(words.length<2){
                    out.println("Bad request. Please try again");
                    break;
                }
                String id = ServerFacade.CreateGame(authToken,words[1]);
                if(id==null){
                    out.println("Create Game failed. Please try again");
                    break;
                }
                //out.print("Game ID: ");
                //out.println(id);
                break;
            case "2":
                HashSet<GameData> games = ServerFacade.ListGames(authToken);
                if(games==null){
                    out.println("List Game failed. Please try again");
                    break;
                }
                out.println("Games:");
                int x=1;
                allGames.clear();
                for(GameData game : games){
                    allGames.add(game.getGameID());
                    out.print(x);
                    x++;
                    out.print(" ");
                    out.print(game.getGameName());
                    out.print(" Blackplayer: ");
                    out.print(game.getBlackUsername());
                    out.print(" Whiteplayer: ");
                    out.println(game.getWhiteUsername());
                }
                break;
            case "3":
                if(words.length<3){
                    out.println("Bad request. Please try again");
                    break;
                }
                joinGame(out,words);
                break;
            case "4":
                if(words.length<2){
                    out.println("Bad request. Please try again");
                    break;
                }
                num = 0;
                try {
                    num = Integer.parseInt(words[1]);
                } catch (NumberFormatException e) {
                    out.println("Bad request. Please try again");
                    break;
                }
                insert = "";
                if(num<=allGames.size()){
                    insert = allGames.get(num-1);
                }
                boolean observe = ServerFacade.JoinGame(null,insert,authToken);
                if(observe){
                    out.println("Observing game");
                    ChessGame game = new ChessGame();
                    game.getBoard().resetBoard();
                    DrawBoard.drawGameBoard(game);
                }
                else {
                    out.println("Observe Game failed. Please try again");
                }
                break;
            case "5":
                ServerFacade.Logout(authToken);
                authToken=null;
                break;
            case "6":
                ServerFacade.Quit();
                pursue=false;
            case "7":
                //PostHelp();
                help = true;
                return null;
            default:
                out.println("Bad request. Please try again");
        }
        Scanner scanner = new Scanner(System.in);
        out.println("Press Enter to continue");
        String enter = scanner.nextLine();
        return null;
    }
    public static void joinGame(PrintStream out, String[] words){
        String input = words[2];
        input = input.toLowerCase();
        if(input.equals("w") || input.equals("white")){
            input = "White";
        } else if (input.equals("b")|| input.equals("black")) {
            input = "Black";
        }
        int num = 0;
        try {
            num = Integer.parseInt(words[1]);
        } catch (NumberFormatException e) {
            out.println("Bad request. Please try again");
            return;
        }
        String insert = "";
        if(num<=allGames.size()){
            insert = allGames.get(num-1);
        }
        boolean join = ServerFacade.JoinGame(input,insert,authToken);
        if(join){
            out.println("Joined game");
            ChessGame game = new ChessGame();
            game.getBoard().resetBoard();
            DrawBoard.drawGameBoard(game);

        }
        else {
            out.println("Join Game failed. Please try again");
        }
    }
}
