package game;

import dataStructures.Queue;
import exceptions.EmptyQueueException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Generator implements ReverseScramble {

    private final HashMap<GameBoard, Integer> data = ReverseScramble.calculate(24);
    private static final HashSet<String> alreadySaved = new java.util.HashSet<>();  // avoids repetitions

    // checks the expected future of a board
    private int f(GameBoard board, int g, int bound, ArrayList<GameBoard> path) throws EmptyQueueException {
        int f = g + board.heuristic();
        if (f > bound)
            return f;
        if (this.data.containsKey(board))
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
            int distance = this.data.get(current);
            while (distance > 0) {
                Queue children = current.children();
                while (children.isNotEmpty()) {
                    GameBoard child = children.get();
                    if (this.data.containsKey(child) && this.data.get(child) == distance - 1) {
                        path.add(child);
                        current = child;
                        distance--;
                        break;
                    }
                }
            }
            this.save(path);
            break;
        }
    }

    private void save(ArrayList<GameBoard> path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/training/dataset.csv", true))) {
            int distance = path.size() - 1;
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
        } catch (IOException e) {
            System.out.println("Error while saving the training data.");
        }
    }
}
