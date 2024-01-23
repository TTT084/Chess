package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {
    ChessPosition location;
    ChessPosition destination;
    ChessPiece.PieceType promotion;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        location =startPosition;
        destination = endPosition;
        promotion=promotionPiece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessMove chessMove)) return false;
        return Objects.deepEquals(location, chessMove.location) && Objects.deepEquals(destination, chessMove.destination) && promotion == chessMove.promotion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, destination, promotion);
    }

    @Override
    public String toString() {
        return "ChessMove{" +
                "location=" + location +
                ", destination=" + destination +
                ", promotion=" + promotion +
                '}';
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return location;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return destination;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        if(destination.getRow()==8){
            return promotion;
        }
        else{
            return null;
        }
    }
}
