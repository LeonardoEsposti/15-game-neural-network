import exceptions.EmptyQueueException;
import game.GameBoard;
import java.util.HashMap;
import game.reverseScramble;

public class Main {
    public static void main(String[] args) {
        int[] testBoard = {
                4,  3,  2,  1,
                5,  6,  11,  8,
                9,  10, 7, 12,
                13, 15, 14, 0
        };
        GameBoard tavola = new GameBoard(testBoard);
        System.out.println(tavola.linearConflicts());
    }
}