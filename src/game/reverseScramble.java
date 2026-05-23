package game;
import dataStructures.queue;
import exceptions.EmptyQueueException;

import java.util.HashMap;

public class reverseScramble {
    HashMap<GameBoard, Integer> distanze = new HashMap<>();
    queue coda = new queue();

    public HashMap calculate(int ceiling, GameBoard father) {
        coda.add(father);
        GameBoard tempFather = new GameBoard();
        queue temporary = new queue();
        int distance = -1;
        while (distance < ceiling) {
            distance += 1;
            while (coda.first != null && coda.last != null) {
                try {
                    tempFather = coda.get();
                    distanze.put(tempFather, distance);
                    queue children = tempFather.Children();

                    while (children.first != null && children.last != null) {
                        GameBoard child = children.get();
                        if (!distanze.containsKey(child)) {
                            temporary.add(child);
                        }


                    }


                } catch (EmptyQueueException e) {
                    break;
                }
                ;

            }

        }
        try {
            while (temporary.first != null && temporary.last != null) {
                coda.add(temporary.get());
            }
        } catch (EmptyQueueException e) {
            ;
        }

    return distanze;
    }
}