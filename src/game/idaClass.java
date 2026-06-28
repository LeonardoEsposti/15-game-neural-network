package game;
import java.util.HashMap;
import java.util.ArrayList;
import dataStructures.queue;
import exceptions.EmptyQueueException;


public class idaClass {
    private HashMap<GameBoard, Integer > data;

    public int f(GameBoard board, int g, int bound, ArrayList<GameBoard> path ) throws EmptyQueueException {
        int f = g + board.euristic();
        if(f>bound){return f;};
        String code = board.toString(); // here you take the board in its string format
        if (data.containsKey(code)){
            return -1;
        }
        int min = Integer.MAX_VALUE;
        queue children = board.Children();
        while (!children.isEmpty()){
            GameBoard child = children.get();
            if(path.contains(child)){
                continue;
            }
            path.add(child);
            int t = f(child, g+1 , bound , path);
            if (t == -1){
                return -1;
            }
            if (t<min){
                min = t;
            }
            path.remove(path.size()-1);

        }
        return min;
    }
//basically: it checks its own expected future: if it's over the bound, returns it, if if found the path, returns -1.
    //if it's expected future it's fine, it checks that of its children. if any of them get it solved, it returns -1, otherwise it returns the expectancy of the best child

    public void ida(GameBoard board) throws EmptyQueueException{
        int t = 0;
        int bound = board.euristic();
        while (true){
            ArrayList<GameBoard> path = new  ArrayList<>();
            path.add(board);
            t = f(board, 0, bound , path);
            if (t != -1){
                bound = t;
                continue;
            }
            GameBoard lastBoard =  path.getLast();
            int distance = data.get(lastBoard.toString());
            for (int i = distance; i<path.size()+distance;i++){
                //System.out.println(path.get(path.size() - 1 - (i -distance) ) + i);
            }
            break;
            }


        }


    }
