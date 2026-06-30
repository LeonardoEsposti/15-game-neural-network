package dataStructures;

import exceptions.EmptyQueueException;
import game.GameBoard;

public class Queue {

    public static class Node {
        public GameBoard value;
        public Node next;
    }

    public Node first = null;
    public Node last = null;

    public void add(GameBoard to_add) {
        Node newNode = new Node();
        newNode.value = to_add;
        if (first == null || last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
    }

    public GameBoard get() throws EmptyQueueException {
        if (first == null || last == null) {
            throw new EmptyQueueException();
        } else {
            GameBoard returnValue = first.value;
            first = first.next;
            if (first == null) {
                last = null;
            }
            return returnValue;
        }
    }

    public boolean isNotEmpty() {
        return first != null || last != null;
    }

}

