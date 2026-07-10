package dataStructures;

import exceptions.EmptyQueueException;
import game.GameBoard;

public class Queue {

    public static class Node {
        public GameBoard value;
        public Integer move;
        public Node next;
    }

    public Node first = null;
    public Node last = null;

    public void add(GameBoard board) {
        Node newNode = new Node();
        newNode.value = board;
        if (first == null || last == null)
            first = newNode;
        else
            last.next = newNode;
        last = newNode;
    }

    public void add(GameBoard board, int move) {
        Node newNode = new Node();
        newNode.value = board;
        newNode.move = move;
        if (first == null || last == null)
            first = newNode;
        else
            last.next = newNode;
        last = newNode;
    }

    public GameBoard get() throws EmptyQueueException {
        if (first == null || last == null)
            throw new EmptyQueueException();
        else {
            GameBoard returnValue = first.value;
            first = first.next;
            if (first == null)
                last = null;
            return returnValue;
        }
    }

    public boolean isNotEmpty() {
        return first != null || last != null;
    }
}