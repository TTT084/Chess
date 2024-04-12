import chess.*;
import ui.UserInterface;

public class ClientMain {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        UserInterface usy = new UserInterface();
        usy.UI();
    }
    //observer no leaving
    //resign notif
    //game completion
}
