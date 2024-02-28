package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    int rowPos;
    int colPos;

    public ChessPosition(int row, int col) {
        rowPos=row;
        colPos=col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return rowPos;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return colPos;
    }

    /**
     *
     * @param row of new place
     * @param col of new place
     * sets the row and column
     */
    public void setPosition(int row, int col){
        rowPos=row;
        colPos=col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPosition that)) return false;
        return rowPos == that.rowPos && colPos == that.colPos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowPos, colPos);
    }

    @Override
    public String toString() {
        return "ChessPosition{" +
                "RowPos=" + rowPos +
                ", ColPos=" + colPos +
                '}';
    }
}



