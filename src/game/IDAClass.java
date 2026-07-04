package game;

import dataStructures.Queue;
import exceptions.EmptyQueueException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class IDAClass {

    private final HashMap<GameBoard, Integer> data = ReverseScramble.calculate(24);
    private static final HashSet<String> alreadySaved = new java.util.HashSet<>();  // avoids repetitions

    public int f(GameBoard board, int g, int bound, ArrayList<GameBoard> path) throws EmptyQueueException {
        int f = g + board.heuristic();
        if (f > bound)
            return f;
        if (data.containsKey(board))
            return -1;
        int min = Integer.MAX_VALUE;
        Queue children = board.children();
        while (children.isNotEmpty()) {
            GameBoard child = children.get();
            if (path.contains(child))
                continue;
            path.add(child);
            int t = f(child, g + 1, bound, path);
            if (t == -1)
                return -1;
            if (t < min)
                min = t;
            path.removeLast();
        }
        return min;
    }

    // basically, it checks its own expected future:
    // - if it's over the bound, returns it, if it finds the path, returns -1;
    // - if its expected future is fine, it checks that of its children:
    //      - if any of them get it solved, it returns -1;
    //      - otherwise it returns the expectancy of the best child;

    public void ida(GameBoard board) throws EmptyQueueException {
        int t;
        int bound = board.heuristic();
        while (true) {
            ArrayList<GameBoard> path = new ArrayList<>();
            path.add(board);
            t = f(board, 0, bound, path);
            if (t != -1) {
                bound = t;
                continue;
            }
            GameBoard current = path.getLast();
            int distance = data.get(current);
            while (distance > 0) {
                Queue children = current.children();
                while (children.isNotEmpty()) {
                    GameBoard child = children.get();
                    if (data.containsKey(child) && data.get(child) == distance - 1) {
                        path.add(child);
                        current = child;
                        distance--;
                        break;
                    }
                }
            }

            // saving on a file
            try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter("src/training/dataset.csv", true))) {
                distance = path.size() - 1;
                for (int i = 0; i < path.size(); i++) {
                    GameBoard b = path.get(i);
                    String boardHash = b.toString();
                    if (!alreadySaved.contains(boardHash)) {
                        int trueDistance = distance - i;
                        writer.write(b.toString() + trueDistance);
                        writer.newLine();
                        alreadySaved.add(boardHash);
                    }
                }
                System.out.println("Path solved and board written in the dataset.");

            } catch (java.io.IOException e) {
                System.out.println("Error while saving the training data.");
            }
            break;
        }
    }
}
