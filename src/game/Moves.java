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
}