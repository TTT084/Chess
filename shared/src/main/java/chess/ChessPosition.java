package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    int RowPos;
    int ColPos;

    public ChessPosition(int row, int col) {
        RowPos=row;
        ColPos=col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return RowPos;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return ColPos;
    }

    /**
     *
     * @param row of new place
     * @param col of new place
     * sets the row and column
     */
    public void setPosition(int row, int col){
        RowPos=row;
        ColPos=col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPosition that)) return false;
        return RowPos == that.RowPos && ColPos == that.ColPos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(RowPos, ColPos);
    }
}


