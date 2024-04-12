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

    private boolean hasMoved;


    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        chessColor=pieceColor;
        chessType=type;
        pieceBump=false;
        hasMoved=false;
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

    public void traveling(){
        hasMoved=true;
    }
    public boolean didItMove(){
        return hasMoved;
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
        //Collection<ChessMove> validMoves = new ArrayList<>();
        Collection<ChessMove> validMoves= new HashSet<>();

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
                //validMoves=queenMoves(piece,myPosition,board,validMoves);
                validMoves=rookMoves(piece,myPosition,board,validMoves);
                validMoves=bishopMoves(piece,myPosition, board, validMoves);
                return validMoves;
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
                validMoves=knightMoves(piece,myPosition, board, validMoves);
                return validMoves;
            default: return validMoves;
        }
        //return validMoves;
    }
    private Collection<ChessMove> kingMoves(ChessPiece piece, ChessPosition startPosition, ChessBoard board, Collection<ChessMove> validMoves){
        int mainRow= startPosition.getRow();
        int mainCol=startPosition.getColumn();
        int counter = 0;
        boolean checker=false;
        Collection<ChessMove> moves=validMoves;
        ChessPosition newPos = new ChessPosition(mainRow,mainCol);
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
        //Castling
        if(!hasMoved && false){
            newPos = new ChessPosition(mainRow,mainCol-2);
            moves.add(new ChessMove(startPosition,newPos,null));
            newPos = new ChessPosition(mainRow,mainCol+2);
            moves.add(new ChessMove(startPosition,newPos,null));
        }
        return moves;
    }
    private Collection<ChessMove> pawnMoves (ChessPiece piece, ChessPosition startPosition, ChessBoard board, Collection<ChessMove> validMoves) {
        int mainRow = startPosition.getRow();
        int mainCol = startPosition.getColumn();
        int counter = 0;
        Collection<ChessMove> moves = validMoves;
        int change = 0;
        pieceBump=false;
        ChessPosition newPos = new ChessPosition(mainRow, mainCol);
        if(board.getPiece(newPos).getTeamColor()==WHITE){
            newPos= new ChessPosition(mainRow+1,mainCol);
            if(moveCheck(newPos,board) && !pieceBump){
                if((mainRow+1)!=8){
                    moves.add(new ChessMove(startPosition,newPos,null));
                }
                else {
                    moves=pawnPromote(startPosition,newPos,moves);
                }
                if(mainRow==2){
                    pieceBump=false;
                    newPos= new ChessPosition(mainRow+2,mainCol);
                    if(moveCheck(newPos,board) && !pieceBump){
                        moves.add(new ChessMove(startPosition,newPos,null));
                    }
                }
            }
            pieceBump=false;
            newPos= new ChessPosition(mainRow+1,mainCol+1);
            if(moveCheck(newPos,board)&&pieceBump){
                if((mainRow+1)!=8){
                    moves.add(new ChessMove(startPosition,newPos,null));
                }
                else {
                    moves=pawnPromote(startPosition,newPos,moves);
                }
            }
            pieceBump=false;
            newPos= new ChessPosition(mainRow+1,mainCol-1);
            if(moveCheck(newPos,board)&&pieceBump){
                if((mainRow+1)!=8){
                    moves.add(new ChessMove(startPosition,newPos,null));
                }
                else {
                    moves=pawnPromote(startPosition,newPos,moves);
                }
            }
        }
        newPos = new ChessPosition(mainRow, mainCol);
        pieceBump=false;
        if(board.getPiece(newPos).getTeamColor()==BLACK){
            newPos= new ChessPosition(mainRow-1,mainCol);
            if(moveCheck(newPos,board) && !pieceBump){
                if((mainRow-1)!=1){
                    moves.add(new ChessMove(startPosition,newPos,null));
                }
                else {
                    moves=pawnPromote(startPosition,newPos,moves);
                }
                if(mainRow==7){
                    newPos= new ChessPosition(mainRow-2,mainCol);
                    pieceBump=false;
                    if(moveCheck(newPos,board)&& !pieceBump){
                        moves.add(new ChessMove(startPosition,newPos,null));
                    }
                }
            }
            pieceBump=false;
            newPos= new ChessPosition(mainRow-1,mainCol+1);
            if(moveCheck(newPos,board)&&pieceBump){
                if((mainRow-1)!=1){
                    moves.add(new ChessMove(startPosition,newPos,null));
                }
                else {
                    moves=pawnPromote(startPosition,newPos,moves);
                }
            }
            pieceBump=false;
            newPos= new ChessPosition(mainRow-1,mainCol-1);
            if(moveCheck(newPos,board)&&pieceBump){
                if((mainRow-1)!=1){
                    moves.add(new ChessMove(startPosition,newPos,null));
                }
                else {
                    moves=pawnPromote(startPosition,newPos,moves);
                }
            }
        }
        return moves;
    }
    private Collection<ChessMove> pawnPromote(ChessPosition startPosition,ChessPosition newPos, Collection<ChessMove> moves){
        moves.add(new ChessMove(startPosition,newPos,PieceType.QUEEN));
        moves.add(new ChessMove(startPosition,newPos,PieceType.KNIGHT));
        moves.add(new ChessMove(startPosition,newPos,PieceType.ROOK));
        moves.add(new ChessMove(startPosition,newPos,PieceType.BISHOP));
        return moves;
    }
    private Collection<ChessMove> bishopMoves (ChessPiece piece, ChessPosition startPosition, ChessBoard board, Collection<ChessMove> validMoves){
        int mainRow= startPosition.getRow();
        int mainCol=startPosition.getColumn();
        int counter =0;
//        int[][] moves = new int[16][2];
        Collection<ChessMove> moves=validMoves;
        int change=0;
        ChessPosition newPos = new ChessPosition(mainRow,mainCol);
        //NE
        pieceBump=false;
        for(int x=0;x<8;x++){
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
        for(int x=0;x<8;x++){
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
        for(int x=0;x<8;x++){
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
        for(int x=0;x<8;x++){
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

    private Collection<ChessMove> rookMoves (ChessPiece piece, ChessPosition startPosition, ChessBoard board, Collection<ChessMove> validMoves) {
        int mainRow = startPosition.getRow();
        int mainCol = startPosition.getColumn();
        Collection<ChessMove> moves = validMoves;
        int change = 0;
        ChessPosition newPos = new ChessPosition(mainRow,mainCol);
        pieceBump=false;
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
        for(int x=0;x<8;x++){
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
    private Collection<ChessMove> knightMoves(ChessPiece piece, ChessPosition startPosition, ChessBoard board, Collection<ChessMove> validMoves) {
        int mainRow = startPosition.getRow();
        int mainCol = startPosition.getColumn();
        int counter = 0;
        boolean checker = false;
        Collection<ChessMove> moves = validMoves;
        ChessPosition newPos = new ChessPosition(mainRow, mainCol);
        //Up Left
        newPos = new ChessPosition(mainRow + 2, mainCol-1);
        if (moveCheck(newPos, board)) {
            moves.add(new ChessMove(startPosition, newPos, null));
        }
        //Up right
        newPos = new ChessPosition(mainRow + 2, mainCol+1);
        if (moveCheck(newPos, board)) {
            moves.add(new ChessMove(startPosition, newPos, null));
        }
        //Right up
        newPos = new ChessPosition(mainRow + 1, mainCol+2);
        if (moveCheck(newPos, board)) {
            moves.add(new ChessMove(startPosition, newPos, null));
        }
        //right down
        newPos = new ChessPosition(mainRow - 1, mainCol+2);
        if (moveCheck(newPos, board)) {
            moves.add(new ChessMove(startPosition, newPos, null));
        }
        //down right
        newPos = new ChessPosition(mainRow - 2, mainCol+1);
        if (moveCheck(newPos, board)) {
            moves.add(new ChessMove(startPosition, newPos, null));
        }
        //down left
        newPos = new ChessPosition(mainRow - 2, mainCol-1);
        if (moveCheck(newPos, board)) {
            moves.add(new ChessMove(startPosition, newPos, null));
        }
        //left down
        newPos = new ChessPosition(mainRow - 1, mainCol-2);
        if (moveCheck(newPos, board)) {
            moves.add(new ChessMove(startPosition, newPos, null));
        }
        //left up
        newPos = new ChessPosition(mainRow + 1, mainCol-2);
        if (moveCheck(newPos, board)) {
            moves.add(new ChessMove(startPosition, newPos, null));
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
