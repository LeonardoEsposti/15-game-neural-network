package game;

public interface Moves {

    default boolean isUpLegal(int coords) {
        return coords >= 4;
    }

    default boolean isRightLegal(int coords) {
        return coords % 4 != 3;
    }

    default boolean isDownLegal(int coords) {
        return coords <= 11;
    }

    default boolean isLeftLegal(int coords) {
        return coords % 4 != 0;
    }

    default String getMove(int move) {
        return switch (move) {
            case 1 -> "UP (^)";
            case 2 -> "RIGHT (>)";
            case 3 -> "DOWN (v)";
            case 4 -> "LEFT (<)";
            default -> "ERROR";
        };
    }

    default String getMoveByOffset(int offset) {
        return switch (offset) {
            case -4 -> "UP (^)";
            case 1 -> "RIGHT (>)";
            case 4 -> "DOWN (v)";
            case -1 -> "LEFT (<)";
            default -> "ERROR";
        };
    }
}