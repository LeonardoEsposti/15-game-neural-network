import exceptions.EmptyQueueException;
import game.GameBoard;
import game.IDA_Class;

public class Main {
    public static void main(String[] args) throws EmptyQueueException {
        int[] testBoard = {
                4, 3, 2, 1,
                5, 6, 11, 8,
                9, 10, 7, 12,
                13, 15, 14, 0
        };
        IDA_Class solver = new IDA_Class();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            GameBoard gameBoard = new GameBoard(100);
            solver.ida(gameBoard);
        }

    }
}