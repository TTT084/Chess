package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import chess.ChessGame.*;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    PieceType chessType;
    ChessGame.TeamColor chessColor;
    private boolean pieceBump;


    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        chessColor=pieceColor;
        chessType=type;
        pieceBump=false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return chessType == that.chessType && chessColor == that.chessColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chessType, chessColor);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return chessColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return chessType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece;
        PieceType type;
        int [][] valid;
        Collection<ChessMove> validMovie = new ArrayList<>();
        Collection<ChessMove> validMoves= new HashSet<>();

        validMoves.add(new ChessMove(myPosition,new ChessPosition(0,0),null));
        //return validMoves;
        if(board.getPiece(myPosition)==null){
            return null;
        }
        else
            piece = board.getPiece(myPosition);
        type = piece.getPieceType();
        switch(type){
            case KING:
                valid =kingMoves(piece,myPosition, board);
            case QUEEN:

            case PAWN:

            case ROOK:

            case BISHOP:
                valid=bishopMoves(piece,myPosition, board);
            case KNIGHT:

            default: return null;
        }
    }
    public int[][] kingMoves(ChessPiece piece, ChessPosition startPosition, ChessBoard board){
        int mainRow= startPosition.getRow();
        int mainCol=startPosition.getColumn();
        int counter =0;
        int[][] moves = new int[8][2];
        ChessPosition newPos = new ChessPosition(mainRow,mainCol);
//        for(int i = 0; i<8; i++){
//            if(myBoard.getPiece!=null){
//
//            }
//        }
        //N
        newPos.setPosition(mainRow+1,mainCol);
        moves[counter]=moveCheck(newPos, board);
        counter++;
        //NE
        newPos.setPosition(mainRow+1,mainCol+1);
        moves[counter]=moveCheck(newPos, board);
        counter++;
        //E
        newPos.setPosition(mainRow,mainCol+1);
        moves[counter]=moveCheck(newPos, board);
        counter++;
        //SE
        newPos.setPosition(mainRow-1,mainCol+1);
        moves[counter]=moveCheck(newPos, board);
        counter++;
        //S
        newPos.setPosition(mainRow-1,mainCol);
        moves[counter]=moveCheck(newPos, board);
        counter++;
        //SW
        newPos.setPosition(mainRow-1,mainCol-1);
        moves[counter]=moveCheck(newPos, board);
        counter++;
        //W
        newPos.setPosition(mainRow,mainCol-1);
        moves[counter]=moveCheck(newPos, board);
        counter++;
        //NW
        newPos.setPosition(mainRow+1,mainCol);
        moves[counter]=moveCheck(newPos, board);
        counter++;
        return moves;
    }
    public int[][] bishopMoves (ChessPiece piece, ChessPosition startPosition, ChessBoard board){
        int mainRow= startPosition.getRow();
        int mainCol=startPosition.getColumn();
        int counter =0;
        int[][] moves = new int[16][2];
        int change=0;
        ChessPosition newPos = new ChessPosition(mainRow,mainCol);
        //NE
        for(int x=0;x<4;x++){
            change=x+1;
            newPos.setPosition(mainRow-change,mainCol+change);
            moves[counter]=moveCheck(newPos, board);
            counter++;
            if(pieceBump){
                break;
            }
        }
        //SE
        change=0;
        for(int x=0;x<4;x++){
            change=x+1;
            newPos.setPosition(mainRow+change,mainCol+change);
            moves[counter]=moveCheck(newPos, board);
            counter++;
            if(pieceBump){
                break;
            }
        }
        //SW
        change=0;
        for(int x=0;x<4;x++){
            change=x+1;
            newPos.setPosition(mainRow+change,mainCol-change);
            moves[counter]=moveCheck(newPos, board);
            counter++;
            if(pieceBump){
                break;
            }
        }
        //NW
        change=0;
        for(int x=0;x<4;x++){
            change=x+1;
            newPos.setPosition(mainRow-change,mainCol-change);
            moves[counter]=moveCheck(newPos, board);
            counter++;
            if(pieceBump){
                break;
            }
        }
        return moves;
    }
    public int[] moveCheck(ChessPosition pos, ChessBoard board) {
        int row = pos.getRow();
        int col = pos.getColumn();
        int[] moves = new int[2];
        if (row != 0 && col != 0) {
            if (row != 9 && col != 9) {
                if(board.getPiece(pos)==null){
                    moves[0] = row;
                    moves[1] = col;
                } else if (board.getPiece(pos).chessColor!=chessColor) {
                    moves[0]=row;
                    moves[1]=row;
                }
                else{
                    pieceBump=true;
                }

            }
        }
        return moves;
    }
}
