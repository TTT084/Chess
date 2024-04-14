package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import static chess.ChessGame.TeamColor.WHITE;
import static ui.EscapeSequences.*;

public class DrawBoard {
    public static void main(String[] args){
        ChessGame game = new ChessGame();
        ChessBoard board = game.getBoard();
        board.resetBoard();;
        //drawGameBoard(game, null);
        highlightMoves(game, new ChessPosition(2,2),"Black");
    }
    public static void drawGameBoard(ChessGame game, Collection<ChessMove> moves, ChessPosition start, String color){
        ChessBoard board = game.getBoard();
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        if(color==null){
            drawWhiteHeader(out);
            drawWhiteRows(out, board, moves, start);
            drawWhiteHeader(out);
        } else if (color=="White") {
            drawBlackHeader(out);
            drawBlackRows(out, board, moves,start);
            drawBlackHeader(out);
        } else if (color=="Black") {
            drawWhiteHeader(out);
            drawWhiteRows(out, board, moves, start);
            drawWhiteHeader(out);
        }
        //out.print(SET_BG_COLOR_BLACK);
        //out.print(SET_TEXT_COLOR_WHITE);
       out.print(RESET_TEXT_COLOR);
        out.print(RESET_BG_COLOR);
        out.println(EMPTY);
        //out.println("test");
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
    private static void drawBlackRows(PrintStream out, ChessBoard board, Collection<ChessMove> moves, ChessPosition start) {
        for (int x = 8; x > 0; x--) {
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(x);
            for (int i = 1; i < 9; i++) {
                chessSquare(out, board, moves, start, x, i);
            }
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(" ");
            out.print(x);
            out.print("  ");
            out.println(EMPTY);
        }
    }
    private static void chessSquare(PrintStream out, ChessBoard board, Collection<ChessMove> moves, ChessPosition start, int x, int i) {
        ChessPosition myPos = new ChessPosition(x,i);
        ChessPosition end = null;
        if(moves!=null) {
            for (ChessMove move : moves) {
                ChessPosition destination = move.getEndPosition();
                if (destination.equals(myPos)) {
                    end = myPos;
                }
            }
        }
        if (x % 2 == 0 && i %2==0) {
            out.print(SET_BG_COLOR_BLACK);
            if(end!=null){
                if(end.getRow()==x && end.getColumn()==i){
                    out.print(SET_BG_COLOR_DARK_GREEN);
                }
            }
        } else if(x % 2 != 0 && i %2!=0){
            out.print(SET_BG_COLOR_BLACK);
            if(end!=null){
                if(end.getRow()==x && end.getColumn()==i){
                    out.print(SET_BG_COLOR_DARK_GREEN);
                }
            }
        }
        else{
            out.print(SET_BG_COLOR_WHITE);
            if(end!=null){
                if(end.getRow()==x && end.getColumn()==i){
                    out.print(SET_BG_COLOR_GREEN);
                }
            }
        }
        drawSquare(out, board, x,i, start,end);
    }

    private static void drawWhiteRows(PrintStream out, ChessBoard board, Collection<ChessMove> moves, ChessPosition start) {
        for (int x = 1; x < 9; x++) {
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(x);
            for (int i = 8; i > 0; i--) {
                chessSquare(out, board, moves, start, x, i);
            }
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(" ");
            out.print(x);
            out.print("  ");
            out.println(EMPTY);
        }
    }
    private static void drawSquare(PrintStream out, ChessBoard board, int row, int col, ChessPosition start, ChessPosition end){
        ChessPosition pos = new ChessPosition(row,col);
        ChessPiece piece = board.getPiece(pos);
        if(start!=null){
            if(start.getRow()==row && start.getColumn()==col){
                out.print(SET_BG_COLOR_YELLOW);
            }
        }

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
    public static void highlightMoves(ChessGame game, ChessPosition position, String color){
        Collection<ChessMove> moves= game.validMoves(position);
        drawGameBoard(game,moves, position, color);
    }
}
