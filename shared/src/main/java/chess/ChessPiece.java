package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import chess.ChessGame.*;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

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
        Collection<ChessMove> validMoves = new ArrayList<>();
        //Collection<ChessMove> validMoves= new HashSet<>();

        //validMoves.add(new ChessMove(myPosition,new ChessPosition(0,0),null));
        //return validMoves;
        if(board.getPiece(myPosition)==null){
            return null;
        }
        else
            piece = board.getPiece(myPosition);
        type = piece.getPieceType();
        switch(type){
            case KING:
                validMoves =kingMoves(piece,myPosition, board, validMoves);
                return validMoves;
            case QUEEN:

            case PAWN:
                validMoves=pawnMoves(piece,myPosition,board,validMoves);
                return validMoves;
            case ROOK:
                validMoves=rookMoves(piece,myPosition,board,validMoves);
                return validMoves;
            case BISHOP:
                validMoves=bishopMoves(piece,myPosition, board, validMoves);
                return validMoves;
            case KNIGHT:

            default: return validMoves;
        }
        //return validMoves;
    }
    public Collection<ChessMove> kingMoves(ChessPiece piece, ChessPosition startPosition, ChessBoard board, Collection<ChessMove> validMoves){
        int mainRow= startPosition.getRow();
        int mainCol=startPosition.getColumn();
        int counter = 0;
        boolean checker=false;
        Collection<ChessMove> moves=validMoves;
        ChessPosition newPos = new ChessPosition(mainRow,mainCol);
//        for(int i = 0; i<8; i++){
//            if(myBoard.getPiece!=null){
//
//            }
//        }
        //N
        newPos = new ChessPosition(mainRow+1,mainCol);
        if(moveCheck(newPos,board)){
            moves.add(new ChessMove(startPosition,newPos,null));
        }
//        moves[counter]=moveCheck(newPos, board);
        //counter++;
        //NE
        newPos = new ChessPosition(mainRow+1,mainCol+1);
        if(moveCheck(newPos,board)){
            moves.add(new ChessMove(startPosition,newPos,null));
        }
        //E
        newPos = new ChessPosition(mainRow,mainCol+1);
        if(moveCheck(newPos,board)){
            moves.add(new ChessMove(startPosition,newPos,null));
        }
        //SE
        newPos = new ChessPosition(mainRow-1,mainCol+1);
        if(moveCheck(newPos,board)){
            moves.add(new ChessMove(startPosition,newPos,null));
        }
        //S
        newPos = new ChessPosition(mainRow-1,mainCol);
        if(moveCheck(newPos,board)){
            moves.add(new ChessMove(startPosition,newPos,null));
        }
        //SW
        newPos = new ChessPosition(mainRow-1,mainCol-1);
        if(moveCheck(newPos,board)){
            moves.add(new ChessMove(startPosition,newPos,null));
        }
        //W
        newPos = new ChessPosition(mainRow,mainCol-1);
        if(moveCheck(newPos,board)){
            moves.add(new ChessMove(startPosition,newPos,null));
        }
        //NW
        newPos = new ChessPosition(mainRow+1,mainCol-1);
        if(moveCheck(newPos,board)){
            moves.add(new ChessMove(startPosition,newPos,null));
        }
        return moves;
    }
    public Collection<ChessMove> pawnMoves (ChessPiece piece, ChessPosition startPosition, ChessBoard board, Collection<ChessMove> validMoves) {
        int mainRow = startPosition.getRow();
        int mainCol = startPosition.getColumn();
        int counter = 0;
//        int[][] moves = new int[16][2];
        Collection<ChessMove> moves = validMoves;
        int change = 0;
        ChessPosition newPos = new ChessPosition(mainRow, mainCol);
        if(mainRow==2 && WHITE == board.getPiece(newPos).getTeamColor()){
            newPos= new ChessPosition(mainRow+2,mainCol);
            if(moveCheck(newPos,board)){
                moves.add(new ChessMove(startPosition,newPos,null));
            }
        }
        if (mainRow == 7 && BLACK == board.getPiece(newPos).getTeamColor()) {
            newPos= new ChessPosition(mainRow-2,mainCol);
            if(moveCheck(newPos,board)){
                moves.add(new ChessMove(startPosition,newPos,null));
            }
        }
        if(moveCheck(newPos,board)){
            moves.add(new ChessMove(startPosition,newPos,null));
        }
        return moves;
    }
    public Collection<ChessMove> bishopMoves (ChessPiece piece, ChessPosition startPosition, ChessBoard board, Collection<ChessMove> validMoves){
        int mainRow= startPosition.getRow();
        int mainCol=startPosition.getColumn();
        int counter =0;
//        int[][] moves = new int[16][2];
        Collection<ChessMove> moves=validMoves;
        int change=0;
        ChessPosition newPos = new ChessPosition(mainRow,mainCol);
        //NE
        for(int x=0;x<4;x++){
            change=x+1;
            newPos= new ChessPosition(mainRow-change,mainCol+change);
            if(moveCheck(newPos,board)){
                moves.add(new ChessMove(startPosition,newPos,null));
            }
//            moves[counter]=moveCheck(newPos, board);
//            counter++;
            if(pieceBump){
                break;
            }
        }
        pieceBump=false;
        //SE
        change=0;
        for(int x=0;x<4;x++){
            change=x+1;
            newPos=new ChessPosition(mainRow+change,mainCol+change);
            if(moveCheck(newPos,board)){
                moves.add(new ChessMove(startPosition,newPos,null));
            }
            if(pieceBump){
                break;
            }
        }
        pieceBump=false;
        //SW
        change=0;
        for(int x=0;x<4;x++){
            change=x+1;
            newPos=new ChessPosition(mainRow+change,mainCol-change);
            if(moveCheck(newPos,board)){
                moves.add(new ChessMove(startPosition,newPos,null));
            }
            if(pieceBump){
                break;
            }
        }
        pieceBump=false;
        //NW
        change=0;
        for(int x=0;x<4;x++){
            change=x+1;
            newPos=new ChessPosition(mainRow-change,mainCol-change);
            if(moveCheck(newPos,board)){
                moves.add(new ChessMove(startPosition,newPos,null));
            }
            if(pieceBump){
                break;
            }
        }
        return moves;
    }

    public Collection<ChessMove> rookMoves (ChessPiece piece, ChessPosition startPosition, ChessBoard board, Collection<ChessMove> validMoves) {
        int mainRow = startPosition.getRow();
        int mainCol = startPosition.getColumn();
        Collection<ChessMove> moves = validMoves;
        int change = 0;
        ChessPosition newPos = new ChessPosition(mainRow,mainCol);
        //N
        for(int x=0;x<8;x++){
            change=x+1;
            newPos = new ChessPosition(mainRow+change,mainCol);
            if(moveCheck(newPos,board)){
                moves.add(new ChessMove(startPosition,newPos,null));
            }
            if(pieceBump){
                break;
            }
        }
        pieceBump=false;
        //E
        for(int x=0;x<8;x++){
            change=x+1;
            newPos = new ChessPosition(mainRow,mainCol+change);
            if(moveCheck(newPos,board)){
                moves.add(new ChessMove(startPosition,newPos,null));
            }
            if(pieceBump){
                break;
            }
        }
        pieceBump=false;
        //S
        for(int x=0;x<8;x++){
            change=x+1;
            newPos = new ChessPosition(mainRow-change,mainCol);
            if(moveCheck(newPos,board)){
                moves.add(new ChessMove(startPosition,newPos,null));
            }
            if(pieceBump){
                break;
            }
        }
        pieceBump=false;
        //W
        for(int x=0;x<4;x++){
            change=x+1;
            newPos = new ChessPosition(mainRow,mainCol-change);
            if(moveCheck(newPos,board)){
                moves.add(new ChessMove(startPosition,newPos,null));
            }
            if(pieceBump){
                break;
            }
        }
        return moves;
    }
    public boolean moveCheck(ChessPosition pos, ChessBoard board) {
        int row = pos.getRow();
        int col = pos.getColumn();
        boolean check = false;
        if (row > 0 && col > 0) {
            if (row < 9 && col < 9) {
                if(board.getPiece(pos)==null){
                    check=true;
                } else if (board.getPiece(pos).chessColor!=chessColor) {
                    check=true;
                    pieceBump=true;
                }
                else{
                    pieceBump=true;
                }

            }
        }
        return check;
    }
}
