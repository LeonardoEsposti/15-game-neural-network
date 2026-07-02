package game;

import dataStructures.Queue;
import exceptions.EmptyQueueException;
import java.util.HashMap;

public class ReverseScramble {

    public static HashMap<GameBoard, Integer> calculate(int ceiling) {
        HashMap<GameBoard, Integer> distances = new HashMap<>();
        Queue main_queue = new Queue();
        Queue temporary_queue = new Queue();
        GameBoard solved = new GameBoard();
        main_queue.add(solved);
        distances.put(solved, 0);
        int distance = 0;
        while (distance < ceiling) {
            try {
                while (main_queue.isNotEmpty()) {
                    GameBoard top = main_queue.get();
                    Queue children = top.children();
                    while (children.isNotEmpty()) {
                        GameBoard child = children.get();
                        if (!distances.containsKey(child)) {
                            temporary_queue.add(child);
                            distances.put(child, distance + 1);
                        }
                    }
                }

                while (temporary_queue.isNotEmpty()) {
                    main_queue.add(temporary_queue.get());
                }
            } catch (EmptyQueueException e) {
                System.out.println("Empty queue");
            }
            distance++;
        }
        return distances;
    }

}