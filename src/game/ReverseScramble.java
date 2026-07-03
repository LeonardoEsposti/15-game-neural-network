package game;

import dataStructures.Queue;
import exceptions.EmptyQueueException;
import java.util.HashMap;

class ReverseScramble {

    static HashMap<GameBoard, Integer> calculate(int ceiling) {
        HashMap<GameBoard, Integer> distances = new HashMap<>();
        Queue mainQueue = new Queue();
        Queue tempQueue = new Queue();
        GameBoard solved = new GameBoard();
        mainQueue.add(solved);
        distances.put(solved, 0);
        int distance = 0;
        while (distance < ceiling) {
            try {
                while (mainQueue.isNotEmpty()) {
                    GameBoard top = mainQueue.get();
                    Queue children = top.children();
                    while (children.isNotEmpty()) {
                        GameBoard child = children.get();
                        if (!distances.containsKey(child)) {
                            tempQueue.add(child);
                            distances.put(child, distance + 1);
                        }
                    }
                }
                while (tempQueue.isNotEmpty()) {
                    mainQueue.add(tempQueue.get());
                }
            } catch (EmptyQueueException e) {
                System.out.println("Empty queue");
            }
            distance++;
        }
        return distances;
    }
}