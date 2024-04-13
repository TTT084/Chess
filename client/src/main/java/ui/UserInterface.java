package ui;

import chess.*;
import com.google.gson.Gson;
import record.GameData;
import webSocketMessages.serverMessages.*;
import webSocketMessages.serverMessages.Error;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static ui.EscapeSequences.*;


public class UserInterface implements ServerMessageObserver {
    static String authToken;
    static boolean help = false;
    static boolean pursue = true;
    private static ArrayList<String> allGames = new ArrayList<>();
    private static ChessGame myGame = null;
    private static String gameID = "";
    private ServerFacade facade;
    private static String team=null;
    public void main(String[] args) {
        uI();
    }
    public void uI(){
        facade = new ServerFacade(this);
        facade.messageObserver(this);
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(System.in);
        out.println("Welcome to chess!");
        out.println();
        while(pursue){
            if(authToken==null){
                if(help){
                    preHelp(out, scanner);
                    help = false;
                }
                else {preLogin(out, scanner);}
            }
            else{
                if(help){
                    postHelp(out,scanner);
                    help = false;
                }
                else {postLogin(out, scanner);}
            }
        }

    }
    public void preLogin(PrintStream out, Scanner scanner){
        out.println("1. Register <USERNAME> <PASSWORD> <EMAIL>");
        out.println("2. Login <USERNAME> <PASSWORD>");
        out.println("3. Quit");
        out.println("4. Help");
        String input = scanner.nextLine();
        String[] words = input.split(" ");
        preInput(out,words);
    }

    private void preHelp(PrintStream out, Scanner scanner){
        out.println("HELP");
        out.println();
        out.println("1. Register <USERNAME> <PASSWORD> <EMAIL> -creates an account" );
        out.println("2. Login <USERNAME> <PASSWORD> - to play chess");
        out.println("3. Quit -exits program");
        out.println("4. Help -with possible commands");
        String input = scanner.nextLine();
        String[] words = input.split(" ");
        preInput(out,words);
    }
    public void postLogin(PrintStream out, Scanner scanner){
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
        postInput(out,words);
    }
    private void postHelp(PrintStream out, Scanner scanner){
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
        postInput(out,words);
    }
    private void preInput(PrintStream out, String[] words){
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
                authToken=facade.register(words[1],words[2],words[3]);
                if(authToken==null){out.println("Register failed. Please try again");}
                break;
            case "2":
                if(words.length<3){
                    out.println("Bad request. Please try again");
                    return;
                }
                authToken=facade.login(words[1],words[2]);
                if(authToken==null){out.println("Login failed. Please try again");}
                break;
            case "3":
                facade.quit();
                pursue=false;
                break;
            case "4":
                help = true;
                return;
            default:
                out.println("Bad request. Please try again");
        }
        Scanner scanner = new Scanner(System.in);
        out.println("Press Enter to continue");
        String enter = scanner.nextLine();
    }
    private String postInput(PrintStream out, String[] words){
        int num = 0;
        String insert = "";
        if(words.length==0){
            out.println("Bad request. Please try again");
            return null;
        }
        String input = words[0];
        input = input.toLowerCase();
        if(input.equals("create game") || input.equals("create")){input = "1";
        } else if (input.equals("list games") || input.equals("list")) {input = "2";
        }else if (input.equals("join game") || input.equals("join")) {input = "3";
        }else if (input.equals("observe game") || input.equals("observe")) {input = "4";
        }else if (input.equals("logout")) {input = "5";
        }else if (input.equals("quit")) {input = "6";
        }else if (input.equals("help")) {input = "7";
        }
        switch (input){
            case "1":
                if(words.length<2){
                    out.println("Bad request. Please try again");
                    break;
                }
                String id = facade.createGame(authToken,words[1]);
                if(id==null){
                    out.println("Create Game failed. Please try again");
                    break;
                }
                break;
            case "2":
                listGames(out);
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
                if(num<=allGames.size()){insert = allGames.get(num-1);}
                observe(out,insert);
                break;
            case "5":
                facade.logout(authToken);
                authToken=null;
                break;
            case "6":
                facade.quit();
                pursue=false;
            case "7":
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
    public void joinGame(PrintStream out, String[] words){
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
            gameID = insert;
        }
        boolean join = facade.joinGame(input,insert,authToken);
        if(join){
            team = input;
            out.println("Joined game");
            ChessGame game = new ChessGame();
            game.getBoard().resetBoard();
            DrawBoard.drawGameBoard(game, null,null,input);
            gameplay(out,new Scanner(System.in));
        }
        else {
            out.println("Join Game failed. Please try again");
        }
    }
    private void listGames(PrintStream out){
        HashSet<GameData> games = facade.listGames(authToken);
        if(games==null){
            out.println("List Game failed. Please try again");
            return;
        }
        out.println("Games:");
        int x=1;
        allGames.clear();
        for(GameData game : games){
            allGames.add(game.getGameID());
            out.print(x);
            x++;
            out.print(" " + game.getGameName());
            out.print(" Blackplayer: " + game.getBlackUsername());
            out.print(" Whiteplayer: ");
            out.println(game.getWhiteUsername());
        }
    }
    private void observe(PrintStream out, String insert){
        boolean observe = facade.observeGame(insert,authToken);
        if(observe){
            out.println("Observing game");
            gameID=insert;
            ChessGame game = new ChessGame();
            game.getBoard().resetBoard();
            DrawBoard.drawGameBoard(game, null,null,team);
            gameplay(out,new Scanner(System.in));
        }
        else {out.println("Observe Game failed. Please try again");}
    }
    public void gameplay(PrintStream out, Scanner scanner){
        out.println("1. Redraw Chess Board");
        out.println("2. Leave");
        out.println("3. Make Move<start> <end>");
        out.println("4. Resign");
        out.println("5. Highlight Legal Moves<square>");
        out.println("6. Help");
        String input = scanner.nextLine();
        String[] words = input.split(" ");
        gameInput(out,words);
    }
    public void gameHelp(PrintStream out, Scanner scanner){
        out.println("1. Redraw Chess Board -redraws the chess board");
        out.println("2. Leave -leaves the game");
        out.println("3. Make Move<start> <end> -makes a move");
        out.println("4. Resign -resigns from game");
        out.println("5. Highlight Legal Moves<square> -highlights all legal moves");
        out.println("6. Help -with possible commands");
        String input = scanner.nextLine();
        String[] words = input.split(" ");
        gameInput(out,words);
    }
    public void gameInput(PrintStream out,String[] words){
        Scanner scanner = new Scanner(System.in);
        int num = 0;
        String insert = "";
        if(words.length==0){
            out.println("Bad request. Please try again");
            return;
        }
        String input = words[0];
        input = input.toLowerCase();
        if(input.equals("redraw chess board") || input.equals("redraw")){input = "1";
        } else if (input.equals("leave")) {input = "2";
        } else if (input.equals("make move") || input.equals("move")) {input = "3";
        } else if (input.equals("resign")) {input = "4";
        } else if (input.equals("highlight legal moves") || input.equals("highlight")) {input = "5";
        } else if (input.equals("help")) {input = "6";
        }
        switch (input) {
            case "1": //redraw
                DrawBoard.drawGameBoard(myGame, null,null,team);
                break;
            case "2": //leave
               facade.leave(authToken,gameID);
               team=null;
               return;
            case "3": // make move
                if (words.length < 2) {
                    out.println("Bad request. Please try again");
                    break;
                }
                boolean moveMade = makeMove(words);
                if (!moveMade) {
                    out.println("Invalid move. Please try again");
                    break;
                }
                else{DrawBoard.drawGameBoard(myGame, null,null,team);}
                break;
            case "4": //resign
                out.println("Are you sure you want to resign?");
                out.println("Y or N");
                String reInput = scanner.nextLine();
                reInput = reInput.toLowerCase();
                if(reInput.equals("y") || reInput.equals("yes")){
                    facade.resign(authToken,gameID);
                    team=null;
                    return;
                }
                break;
            case "5": // highlight
                if (words.length < 1) {
                    out.println("Bad request. Please try again");
                    break;
                }
                DrawBoard.highlightMoves(myGame,letter2Number(words, 1),team);
                break;
            case "6": //help
                gameHelp(out,scanner);
            default:
                break;
        }
        gameplay(out,scanner);
    }
    public boolean makeMove(String[] words){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ChessPosition start = letter2Number(words,1);
        ChessPosition end = letter2Number(words,2);
        if(start==null||end==null){
            return false;
        }
        ChessPiece.PieceType prom = promotion(words);
        Collection <ChessMove> moves = myGame.validMoves(start);
        if(moves==null){
            return false;
        }
        for (ChessMove move : moves) {
            ChessPosition destination = move.getEndPosition();
            if (destination.equals(end)) {
                facade.makeMove(authToken,gameID,new ChessMove(start,end,prom));
                try {
                    myGame.makeMove(new ChessMove(start, end, prom));
                }
                catch(InvalidMoveException e){
                    return false;
                }
                return true;
            }
        }
        return false;
    }
    public ChessPosition letter2Number(String[] words, int a){
        char[] letters = words[a].toCharArray();
        if(letters.length<2){return null;}
        int row = Character.getNumericValue(letters[1]);
        int col = Character.getNumericValue(letters[0]);
        char first = Character.toLowerCase(letters[0]);
        switch (first){
            case 'a':
                col = 1;
                break;
            case 'b':
                col = 2;
                break;
            case 'c':
                col = 3;
                break;
            case 'd':
                col=4;
                break;
            case 'e':
                col = 5;
                break;
            case 'f':
                col = 6;
                break;
            case 'g':
                col =7;
                break;
            case 'h':
                col = 8;
                break;
        }
        return new ChessPosition(row,col);
    }
    private ChessPiece.PieceType promotion(String[] prom){
        if(prom.length<4){return null;}
        if(prom[3]==null){return null;}
        String input = prom[3].toLowerCase();
        if(input=="rook"||input=="r"){return ChessPiece.PieceType.ROOK;
        }
        if(input=="queen"||input=="q"){return ChessPiece.PieceType.QUEEN;
        }
        if(input=="knight"||input=="k"){return ChessPiece.PieceType.KNIGHT;
        }
        if(input=="bishop"||input=="b"){return ChessPiece.PieceType.BISHOP;
        }
        return null;
    }
    @Override
    public void notify(String input){
        Gson gson = new Gson();
        ServerMessage message = gson.fromJson(input, ServerMessage.class);
        switch (message.getServerMessageType()) {
            case NOTIFICATION -> displayNotification(input);
            case ERROR -> displayError(input);
            case LOAD_GAME -> loadGame(input);
        }
    }
    private void displayNotification(String message){
        Gson gson = new Gson();
        Notification notify = gson.fromJson(message, Notification.class);
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.println(notify.message);
    }
    private void displayError(String message){
        Gson gson = new Gson();
        Error err = gson.fromJson(message, Error.class);
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(SET_TEXT_COLOR_RED);
        out.println(err.errorMessage);
        out.print(RESET_TEXT_COLOR);
    }
    private void loadGame(String message){
        Gson gson = new Gson();
        LoadGame lGame = gson.fromJson(message, LoadGame.class);
        myGame = lGame.game;
        check(myGame);
        DrawBoard.drawGameBoard(myGame, null,null,team);
    }
    private void check(ChessGame game){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ChessGame.TeamColor colorTeam;
        if(team=="White"){
            colorTeam= ChessGame.TeamColor.WHITE;
        }
        else {
            colorTeam= ChessGame.TeamColor.BLACK;
        }
        if(game.isInCheck(colorTeam)){
            if(game.isInCheckmate(colorTeam)){
                out.println("Checkmate");
            } else if (game.isInStalemate(colorTeam)) {
                out.println("Stalemate");
            }else{
                out.println("Check");
            }
        }
    }
    public void setMyGame(ChessGame chess){
        myGame=chess;
    }
}