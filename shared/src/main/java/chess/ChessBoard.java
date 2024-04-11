package chess;

import java.util.Arrays;

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
        playBoard = new ChessPiece[9][9];
        resetBoard();
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        playBoard[position.getRow()][position.getColumn()] = piece;
    }

    public void removePiece(ChessPosition position) {
        playBoard[position.getRow()][position.getColumn()] = null;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        if (row > 8 || col > 8) {
            return null;
        }
        if (row < 0 || col < 0) {
            return null;
        }
        if (playBoard[row][col] != null) {
            return playBoard[row][col];
        } else
            return null;
    }


    public void doSomething() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessBoard that)) return false;
        return Arrays.deepEquals(playBoard, that.playBoard);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(playBoard);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "playBoard=" + Arrays.deepToString(playBoard) +
                '}';
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        this.clearBoard();
        //Black team
        //first row
        playBoard[8][8] = new ChessPiece(BLACK, ROOK);
        playBoard[8][7] = new ChessPiece(BLACK, KNIGHT);
        playBoard[8][6] = new ChessPiece(BLACK, BISHOP);
        playBoard[8][4] = new ChessPiece(BLACK, QUEEN);
        playBoard[8][5] = new ChessPiece(BLACK, KING);
        playBoard[8][3] = new ChessPiece(BLACK, BISHOP);
        playBoard[8][2] = new ChessPiece(BLACK, KNIGHT);
        playBoard[8][1] = new ChessPiece(BLACK, ROOK);
        //second row
        playBoard[7][8] = new ChessPiece(BLACK, PAWN);
        playBoard[7][1] = new ChessPiece(BLACK, PAWN);
        playBoard[7][2] = new ChessPiece(BLACK, PAWN);
        playBoard[7][3] = new ChessPiece(BLACK, PAWN);
        playBoard[7][4] = new ChessPiece(BLACK, PAWN);
        playBoard[7][5] = new ChessPiece(BLACK, PAWN);
        playBoard[7][6] = new ChessPiece(BLACK, PAWN);
        playBoard[7][7] = new ChessPiece(BLACK, PAWN);

        //White team
        //8th row
        playBoard[1][1] = new ChessPiece(WHITE, ROOK);
        playBoard[1][2] = new ChessPiece(WHITE, KNIGHT);
        playBoard[1][3] = new ChessPiece(WHITE, BISHOP);
        playBoard[1][4] = new ChessPiece(WHITE, QUEEN);
        playBoard[1][5] = new ChessPiece(WHITE, KING);
        playBoard[1][6] = new ChessPiece(WHITE, BISHOP);
        playBoard[1][7] = new ChessPiece(WHITE, KNIGHT);
        playBoard[1][8] = new ChessPiece(WHITE, ROOK);
        //second row
        playBoard[2][8] = new ChessPiece(WHITE, PAWN);
        playBoard[2][1] = new ChessPiece(WHITE, PAWN);
        playBoard[2][2] = new ChessPiece(WHITE, PAWN);
        playBoard[2][3] = new ChessPiece(WHITE, PAWN);
        playBoard[2][4] = new ChessPiece(WHITE, PAWN);
        playBoard[2][5] = new ChessPiece(WHITE, PAWN);
        playBoard[2][6] = new ChessPiece(WHITE, PAWN);
        playBoard[2][7] = new ChessPiece(WHITE, PAWN);
        //throw new RuntimeException("Not implemented");
    }
    public void clearBoard(){
        playBoard=new ChessPiece[9][9];
    }
}
