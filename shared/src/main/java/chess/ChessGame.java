package chess;

import java.util.Collection;
import chess.ChessBoard.*;

import static chess.ChessPiece.PieceType;
import static chess.ChessPiece.PieceType.KING;

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

    public ChessGame() {
        myBoard = new ChessBoard();
        myTeam = TeamColor.WHITE;
        boolean pieceBump=false;
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

            throw new RuntimeException("Not implemented");
    }

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
        if(checkMove(move)){
            myBoard.addPiece(end,piece);
            rmvPiece=myBoard.getPiece(start);
            myBoard.removePiece(start);
        }
        else{
            throw new RuntimeException("Not implemented");
        }

    }
    private void unmakeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition start=move.getStartPosition();
        ChessPosition end=move.getEndPosition();
        ChessPiece piece=myBoard.getPiece(end);
        if(checkMove(move)){
            myBoard.addPiece(start,piece);
            myBoard.addPiece(end,rmvPiece);
        }
        else{
            throw new RuntimeException("Not implemented");
        }
    }
    private boolean checkMove(ChessMove move){
        ChessPosition start=move.getStartPosition();
        ChessPosition end=move.getEndPosition();
        ChessPiece piece=myBoard.getPiece(start);
        Collection<ChessMove> validMoves=piece.pieceMoves(myBoard,start);
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
        return newPos;
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

            }
                return false;
        }
        return false;
    }

    private boolean hasMoves(TeamColor teamColor){
        ChessPosition newPos;
        ChessPiece newPiece;
        Collection<ChessMove> moves;
        ChessPosition dest;
        PieceType type;
        for(int i=1;i<9;i++){
            for(int x=1;x<9;x++){
                newPos = new ChessPosition(i,x);
                newPiece = myBoard.getPiece(newPos);
                if(newPiece!=null){
                    type=newPiece.getPieceType();
                    if(newPiece.getTeamColor()==teamColor){
                        moves=newPiece.pieceMoves(myBoard,newPos);
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
        throw new RuntimeException("Not implemented");
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
}
