package game;
import dataStructures.queue;
import exceptions.EmptyQueueException;

import java.util.HashMap;

public class reverseScramble {

    public static HashMap<GameBoard, Integer> calculate(int ceiling)  {
        HashMap<GameBoard, Integer> distances = new HashMap<>();
        queue main_queue = new queue();
        queue temporary_queue = new queue();
        GameBoard solved = new GameBoard();
        main_queue.add(solved);
        distances.put(solved, 0);
        int distance = 0;
        while (distance < ceiling) {
            try {
                while (!main_queue.isEmpty()) {
                    GameBoard top = main_queue.get();
                    queue children = top.Children();
                    while (!children.isEmpty()) {
                        GameBoard child = children.get();
                        if (!distances.containsKey(child)) {
                            temporary_queue.add(child);
                            distances.put(child, distance + 1);
                        }
                    }
                }
                while (!temporary_queue.isEmpty()) {
                    main_queue.add(temporary_queue.get());
                }
            } catch(EmptyQueueException e ){;}
            distance ++;
        }
        return distances;
    }

}