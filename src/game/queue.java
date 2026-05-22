package game;

public class queue {

    private class node {
        GameBoard value;
        node next;
    }

    node first = null;
    node last = null;

    public void add(GameBoard to_add) {
        node NewNode = new node();
        NewNode.value = to_add;
        if (first == null || last == null) {
            first = NewNode;
            last = NewNode;
        } else {
            last.next = NewNode;
            last = NewNode;
        }
    }

    public GameBoard get() throws EmptyQueueException {
        if (first == null || last == null){
            throw new EmptyQueueException();
        }
        else {
            GameBoard returnValue = first.value;
            first = first.next;
            if (first == null){
                last = null;
            }
            return returnValue;
        }
    }

}

