import exceptions.EmptyQueueException;
import game.GameBoard;
import java.util.HashMap;
import game.reverseScramble;
public class Main {
    public static void main(String[] args) {
        HashMap<GameBoard, Integer> dataset = reverseScramble.calculate(22);
        System.out.println("Stati generati: " + dataset.size());

    }
}