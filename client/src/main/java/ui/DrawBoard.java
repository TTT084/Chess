package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static chess.ChessGame.TeamColor.WHITE;
import static ui.EscapeSequences.*;

public class DrawBoard {
    public static void main(String[] args){
        ChessGame game = new ChessGame();
        ChessBoard board = game.getBoard();
        board.resetBoard();;
        drawGameBoard(game);
    }
    public static void drawGameBoard(ChessGame game){
        ChessBoard board = game.getBoard();
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        drawBlackHeader(out);
        drawBlackRows(out, board);
        drawBlackHeader(out);

        out.print(SET_BG_COLOR_BLACK);
        out.println(EMPTY);

        drawWhiteHeader(out);
        drawWhiteRows(out, board);
        drawWhiteHeader(out);

        out.print(SET_BG_COLOR_BLACK);
        out.println(EMPTY);
    }
    private static void drawBlackHeader(PrintStream out){
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(" ");
        out.print(" a  b  c  d  e  f  g  h ");
        out.println(EMPTY);
    }
    private static void drawWhiteHeader(PrintStream out){
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(" ");
        out.print(" h  g  f  e  d  c  b  a ");
        out.println(EMPTY);
    }
    private static void drawBlackRows(PrintStream out, ChessBoard board) {
        for (int x = 8; x > 0; x--) {
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(x);
            for (int i = 1; i < 9; i++) {
                if (x % 2 == 0 && i %2==0) {
                    out.print(SET_BG_COLOR_BLACK);
                } else if(x % 2 != 0 && i %2!=0){
                    out.print(SET_BG_COLOR_BLACK);
                }
                else{
                    out.print(SET_BG_COLOR_WHITE);
                }
                drawSquare(out, board, x,i);
            }
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(" ");
            out.print(x);
            out.print("  ");
            out.println(EMPTY);
        }
    }
    private static void drawWhiteRows(PrintStream out, ChessBoard board) {
        for (int x = 1; x < 9; x++) {
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(x);
            for (int i = 8; i > 0; i--) {
                if (x % 2 == 0 && i %2==0) {
                    out.print(SET_BG_COLOR_BLACK);
                } else if(x % 2 != 0 && i %2!=0){
                    out.print(SET_BG_COLOR_BLACK);
                }
                else{
                    out.print(SET_BG_COLOR_WHITE);
                }
                drawSquare(out, board, x,i);
            }
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(" ");
            out.print(x);
            out.print("  ");
            out.println(EMPTY);
        }
    }
    private static void drawSquare(PrintStream out, ChessBoard board, int row, int col){
        ChessPosition pos = new ChessPosition(row,col);
        ChessPiece piece = board.getPiece(pos);
        if(piece==null){
            out.print("   ");
            return;
        }
        if(piece.getTeamColor()==WHITE){
            out.print(SET_TEXT_COLOR_RED);
        }
        else{
            out.print(SET_TEXT_COLOR_BLUE);
        }
        ChessPiece.PieceType pieceType = piece.getPieceType();
        switch(pieceType){
            case PAWN -> out.print(" P ");
            case ROOK -> out.print(" R ");
            case KNIGHT -> out.print(" N ");
            case BISHOP -> out.print(" B ");
            case QUEEN -> out.print(" Q ");
            case KING -> out.print(" K ");
            default -> out.print(EMPTY);
        }
    }
}
