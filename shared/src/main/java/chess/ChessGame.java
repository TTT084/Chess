package chess;

import java.util.Collection;
import chess.ChessBoard.*;

import static chess.ChessPiece.PieceType;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    public Object myBoard = new ChessBoard();

    public ChessGame() {
        //myBoard = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        throw new RuntimeException("Not implemented");
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
        ChessPiece piece;
        PieceType type;
        int [][] valid;
        if(myBoard.getPiece(startPosition)==null){
            return null;
        }
        else
            piece = myBoard.getPiece(startPosition);
        type = piece.getPieceType();
        switch(type){
            case KING:
                valid =KingMoves(piece,startPosition);
            case QUEEN:

            case PAWN:

            case ROOK:

            case BISHOP:

            case KNIGHT:

            default: return null;
        }
    }

    /**
     * Looks at Valid King moves
     */
    public int[][] KingMoves(ChessPiece piece, ChessPosition startPosition){
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
        newPos.setPosition(mainRow+1,mainCol);
        if(mainRow+1!=8 && myBoard.getPiece()!=null){
            moves[counter][0]=mainRow+1;
            moves[counter][1]=mainCol;
        }
        return moves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
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
        throw new RuntimeException("Not implemented");
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        throw new RuntimeException("Not implemented");
    }
}
