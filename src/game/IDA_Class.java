package game;

import dataStructures.Queue;
import exceptions.EmptyQueueException;

import java.util.ArrayList;
import java.util.HashMap;

public class IDA_Class {
    private HashMap<GameBoard, Integer> data = ReverseScramble.calculate(12);

    private static java.util.HashSet<String> alreadySaved = new java.util.HashSet<>(); //avoid repetitions

    public int f(GameBoard board, int g, int bound, ArrayList<GameBoard> path) throws EmptyQueueException {
        int f = g + board.euristic();
        if (f > bound) {
            return f;
        }
        ;
        // sbagliato credo String code = board.toString(); // here you take the board in its string format
        if (data.containsKey(board)) {
            return -1;
        }
        int min = Integer.MAX_VALUE;
        Queue children = board.Children();
        while (children.isNotEmpty()) {
            GameBoard child = children.get();
            if (path.contains(child)) {
                continue;
            }
            path.add(child);
            int t = f(child, g + 1, bound, path);
            if (t == -1) {
                return -1;
            }
            if (t < min) {
                min = t;
            }
            path.removeLast();

        }
        return min;
    }
//basically: it checks its own expected future: if it's over the bound, returns it, if if found the path, returns -1.
    //if it's expected future it's fine, it checks that of its children. if any of them get it solved, it returns -1, otherwise it returns the expectancy of the best child

    public void ida(GameBoard board) throws EmptyQueueException {
        int t = 0;
        int bound = board.euristic();
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
                Queue children = current.Children();
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

            //to save on a file                                                                                     true means it adds on the current file
            try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter("training_data.csv", true))) {
                distance = path.size() - 1;
                for (int i = 0; i < path.size(); i++) {
                    GameBoard b = path.get(i);
                    String boardHash = b.toString();
                    if (!alreadySaved.contains(boardHash)) {

                        int trueDistance = distance - i;

                        writer.write(b.boardToSave() + trueDistance);
                        writer.newLine();

                        alreadySaved.add(boardHash);
                    }
                }
                System.out.println("Path solved. Unique boards appended to dataset.");

            } catch (java.io.IOException e) {
                System.out.println("Error while saving the training data.");
            }
            break;
        }


    }


}
