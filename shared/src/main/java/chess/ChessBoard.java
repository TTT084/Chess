package chess;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static chess.ChessPiece.PieceType.*;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private ChessPiece[][] playBoard;
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        playBoard[position.getRow()][position.getColumn()]=piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        playBoard= new ChessPiece[8][8];
        //Black team
        //first row
        playBoard[0][0]= new ChessPiece(BLACK, ROOK);
        playBoard[0][1]= new ChessPiece(BLACK, KNIGHT);
        playBoard[0][2]= new ChessPiece(BLACK, BISHOP);
        playBoard[0][3]= new ChessPiece(BLACK, QUEEN);
        playBoard[0][4] = new ChessPiece(BLACK, KING);
        playBoard[0][5]= new ChessPiece(BLACK,BISHOP);
        playBoard[0][6]= new ChessPiece(BLACK,KNIGHT);
        playBoard[0][7]= new ChessPiece(BLACK,ROOK);
        //second row
        playBoard[1][0]= new ChessPiece(BLACK,PAWN);
        playBoard[1][1]= new ChessPiece(BLACK,PAWN);
        playBoard[1][2]= new ChessPiece(BLACK,PAWN);
        playBoard[1][3]= new ChessPiece(BLACK,PAWN);
        playBoard[1][4]= new ChessPiece(BLACK,PAWN);
        playBoard[1][5]= new ChessPiece(BLACK,PAWN);
        playBoard[1][6]= new ChessPiece(BLACK,PAWN);
        playBoard[1][7]= new ChessPiece(BLACK,PAWN);

        //White team
        //8th row
        playBoard[7][0]= new ChessPiece(WHITE, ROOK);
        playBoard[7][1]= new ChessPiece(WHITE, KNIGHT);
        playBoard[7][2]= new ChessPiece(WHITE, BISHOP);
        playBoard[7][3]= new ChessPiece(WHITE, QUEEN);
        playBoard[7][4] = new ChessPiece(WHITE, KING);
        playBoard[7][5]= new ChessPiece(WHITE,BISHOP);
        playBoard[7][6]= new ChessPiece(WHITE,KNIGHT);
        playBoard[7][7]= new ChessPiece(WHITE,ROOK);
        //second row
        playBoard[6][0]= new ChessPiece(WHITE,PAWN);
        playBoard[6][1]= new ChessPiece(WHITE,PAWN);
        playBoard[6][2]= new ChessPiece(WHITE,PAWN);
        playBoard[6][3]= new ChessPiece(WHITE,PAWN);
        playBoard[6][4]= new ChessPiece(WHITE,PAWN);
        playBoard[6][5]= new ChessPiece(WHITE,PAWN);
        playBoard[6][6]= new ChessPiece(WHITE,PAWN);
        playBoard[6][7]= new ChessPiece(WHITE,PAWN);
        throw new RuntimeException("Not implemented");
    }
}
