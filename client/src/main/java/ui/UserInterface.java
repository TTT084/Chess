package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class UserInterface {
    static String authToken;
    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        PreLogin(out);
        PostLogin(out);
    }
    public static void UI(){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        if(authToken==null){
            PreLogin(out);
        }
        else{
            PostLogin(out);
        }
    }
    public static void PreLogin(PrintStream out){
        out.println("Welcome to chess!");
        out.println();
        out.println("1. Register <USERNAME> <PASSWORD> <EMAIL>");
        out.println("2. Login <USERNAME> <PASSWORD>");
        out.println("3. Quit");
        out.println("4. Help");
    }
    private static String Register(){
        return null;
    }
    private static String Login(){
        return null;
    }
    private static void Quit(){

    }
    private static void PreHelp(){

    }
    public static void PostLogin(PrintStream out){
        out.println();
        out.println("1. Create Game <NAME>");
        out.println("2. List Games");
        out.println("3. Join Games <ID> [W]hite[B]lack[]<empty>");
        out.println("4. Observe Game <ID>");
        out.println("5. Logout");
        out.println("6. Quit");
        out.println("7. Help");
    }

    private static void CreateGame(){

    }
    private static void ListGames(){

    }
    private static void JoinGames(){

    }
    private static void OvserveGame(){

    }
    private static void Logout(){

    }
    private static void PostHelp(){

    }
}
