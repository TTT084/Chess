package chess;

import java.util.Collection;
import java.util.HashSet;

import chess.ChessBoard.*;

import static chess.ChessPiece.PieceType;
import static chess.ChessPiece.PieceType.KING;
import static chess.ChessPiece.PieceType.PAWN;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard myBoard;
    private TeamColor myTeam;
    private ChessPiece rmvPiece;
    private boolean promote;
    private boolean checkingMate;

    private boolean whiteKing;
    private boolean blackKing;
    private boolean leftBlackRook;
    private boolean rightBlackRook;
    private boolean leftWhiteRook;
    private boolean rightWhiteRook;

    public ChessGame() {
        myBoard = new ChessBoard();
        myTeam = TeamColor.WHITE;
        boolean pieceBump=false;
        promote=false;
        blackKing = false;
        whiteKing=false;
        leftBlackRook=false;
        rightBlackRook=false;
        leftWhiteRook=false;
        rightWhiteRook=false;
        checkingMate=false;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return myTeam;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        myTeam=team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        if(checkingMate){
            return null;
        }
        Collection <ChessMove> validMoves = new HashSet<>();
        ChessPiece piece = myBoard.getPiece(startPosition);
        if(piece==null){
            return validMoves;
        }
        Collection <ChessMove> moves = piece.pieceMoves(myBoard, startPosition);
        TeamColor color = piece.getTeamColor();
        for(ChessMove element: moves){
            if(piece.getPieceType()==KING){
                if(castling(startPosition,element)&&startPosition==null){
                    validMoves.add(element);
                    continue;
                }
            }
            if(!fakeMove(element)){
                continue;
            }
//            if(!isInCheck(myTeam)){
            if(!isInCheck(color)){
                validMoves.add(element);
            }
            unmakeMove(element);
        }
        return validMoves;
    }

    private boolean castling(ChessPosition pos, ChessMove move){
        ChessPosition rookPos = new ChessPosition(0,0);
        ChessMove newMove = new ChessMove(pos,move.destination,null);
        ChessPosition dest = move.getEndPosition();
        int row = dest.getRow();
        int col= dest.getColumn();
        if((pos.getColumn()+1)<move.destination.getColumn()){
            rookPos.setPosition(pos.getRow(),8);
            if(myBoard.getPiece(rookPos)==null){
                return false;
            }
            if(!myBoard.getPiece(rookPos).didItMove()){
                if(!isInCheck(myTeam)){
                    dest=new ChessPosition(row,col+1);
                    newMove=new ChessMove(pos,dest,null);
                    if(fakeMove(newMove)){
                        if(!isInCheck(myTeam)){
                            unmakeMove(newMove);
                            dest=new ChessPosition(row,col+2);
                            newMove=new ChessMove(pos,dest,null);
                            if(fakeMove(newMove)){
                                if(!isInCheck(myTeam)){
                                    unmakeMove(newMove);
                                    return true;
                                }
                                unmakeMove(newMove);
                            }
                        }
                        unmakeMove(newMove);
                    }
                }
            }
        }
        if((pos.getColumn()+1)<move.destination.getColumn()){
            rookPos.setPosition(pos.getRow(),1);
            if(myBoard.getPiece(rookPos)==null){
                return false;
            }
            if(!myBoard.getPiece(rookPos).didItMove()){
                if(!isInCheck(myTeam)){
                    dest=new ChessPosition(row,col-1);
                    newMove=new ChessMove(pos,dest,null);
                    if(fakeMove(newMove)){
                        if(!isInCheck(myTeam)){
                            unmakeMove(newMove);
                            dest=new ChessPosition(row,col-2);
                            newMove=new ChessMove(pos,dest,null);
                            if(fakeMove(newMove)){
                                if(!isInCheck(myTeam)){
                                    unmakeMove(newMove);
                                    return true;
                                }
                                unmakeMove(newMove);
                            }
                        }
                        unmakeMove(newMove);
                    }
                }
            }
        }
        return false;
    }
    //private boolean kingGlide(ChessPosition pos,ChessMove move){return true;}
    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition start=move.getStartPosition();
        ChessPosition end=move.getEndPosition();
        ChessPiece piece=myBoard.getPiece(start);
        PieceType promotion = move.getPromotionPiece();
        TeamColor color = piece.getTeamColor();
        Collection<ChessMove> validMoves=validMoves(start);
        if(color!=myTeam){
            throw new InvalidMoveException("Invalid move");
        }
        if(validMoves==null){
            throw new InvalidMoveException("Invalid move");
        }
        if(validMoves.contains(move)){
        //if(checkMove(move)){
            if(promotion!=null){
                piece.chessType=promotion;
                promote=true;
            }
            rmvPiece=myBoard.getPiece(end);
            myBoard.addPiece(end,piece);
            myBoard.removePiece(start);
            piece.traveling();
            if(myTeam==TeamColor.WHITE){
                myTeam=TeamColor.BLACK;
            }
            else{
                myTeam=TeamColor.WHITE;
            }
        }
        else{
            throw new InvalidMoveException("Invalid move");
        }

    }

    /**
     *
     * @param move
     * @return
     */
    public boolean fakeMove(ChessMove move) {
        int row=move.destination.getRow();
        int col = move.destination.getColumn();
        if (row > 8 || col > 8) {
            return false;
        }
        if (row < 0 || col < 0) {
            return false;
        }
        ChessPosition start=move.getStartPosition();
        ChessPosition end=move.getEndPosition();
        ChessPiece piece=myBoard.getPiece(start);
        PieceType promotion = move.getPromotionPiece();
        if(checkMove(move)){
            if(promotion!=null){
                piece.chessType=promotion;
                promote=true;
            }
            rmvPiece=myBoard.getPiece(end);
            myBoard.addPiece(end,piece);
            myBoard.removePiece(start);
            return true;
        }
        return false;
    }
    private void unmakeMove(ChessMove move) /* throws InvalidMoveException */ {
        ChessPosition start=move.getStartPosition();
        ChessPosition end=move.getEndPosition();
        ChessPiece piece=myBoard.getPiece(end);
        if(promote){
            piece.chessType=PAWN;
            promote=false;
        }
        myBoard.addPiece(start,piece);
        myBoard.addPiece(end,rmvPiece);
    }
    private boolean checkMove(ChessMove move){
        ChessPosition start=move.getStartPosition();
        ChessPosition end=move.getEndPosition();
        ChessPiece piece=myBoard.getPiece(start);
        Collection<ChessMove> validMoves;
        //Collection<ChessMove> correctMoves;
        if(piece!=null){
            validMoves=piece.pieceMoves(myBoard,start);
            //correctMoves=validMoves(start);
        }
        else{
            return false;
        }
        for(ChessMove element: validMoves){
            if(element.equals(move)){
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition newPos;
        ChessPiece newPiece;
        Collection<ChessMove> moves;
        ChessPosition kingPos = findKing(teamColor);
        if(kingPos==null){
            return false;
        }
        ChessPosition dest;
        PieceType type;
        for(int i=1;i<9;i++){
            for(int x=1;x<9;x++){
                newPos = new ChessPosition(i,x);
                newPiece = myBoard.getPiece(newPos);
                if(newPiece!=null){
                    type=newPiece.getPieceType();
                    if(newPiece.getTeamColor()!=teamColor){
                        moves=newPiece.pieceMoves(myBoard,newPos);
                        for(ChessMove element:moves){
                            dest=element.destination;
                            if(dest.equals(kingPos)){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    private ChessPosition findKing(TeamColor teamColor){
        ChessPosition newPos = null;
        ChessPiece newPiece;
        Collection<ChessMove> moves;
        for(int i=1;i<9;i++){
            for(int x=1;x<9;x++){
                newPos = new ChessPosition(i,x);
                newPiece = myBoard.getPiece(newPos);
                if(newPiece!=null){
                    if(newPiece.getPieceType()==KING){
                        if(newPiece.getTeamColor()==teamColor){
                            return newPos;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if(isInCheck(teamColor)){
            if(!hasMoves(teamColor)){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    private boolean hasMoves(TeamColor teamColor){
        ChessPosition newPos;
        ChessPiece newPiece;
        Collection<ChessMove> moves=new HashSet<>();
        ChessPosition dest;
        PieceType type;
        for(int i=1;i<9;i++){
            for(int x=1;x<9;x++){
                newPos = new ChessPosition(i,x);
                newPiece = myBoard.getPiece(newPos);
                if(newPiece!=null){
                    type=newPiece.getPieceType();
                    if(newPiece.getTeamColor()==teamColor){
                        //try {
                            moves=validMoves(newPos);
                        if(!moves.isEmpty()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if(!hasMoves(teamColor)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        myBoard=board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return myBoard;
    }
    public void resign(){
        checkingMate=true;
    }
    public boolean getCheckingMate(){
        return checkingMate;
    }
}
