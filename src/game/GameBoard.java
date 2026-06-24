package game;
import utils.UsefulFuncs;
import exceptions.outOfBoundsException;
import dataStructures.queue;

import java.util.Arrays;

public class GameBoard implements Moves, UsefulFuncs<int[]> {
    private int[] board = new int[16];

    private int cords = 15;

    public GameBoard() {
        board[15] = 0;
        for (int i = 1; i < 16; i++) {
            board[i-1] = i;
        }
        board[15] = 0;
    }
    public GameBoard(int scramble) {
        for (int i = 1; i < 16; i++) {
            board[i-1] = i;
        }
        board[15] = 0;
        board = scrambleBoard(scramble);
    }

    public int[] scrambleBoard(int scramble) {
        int dontMove = 0;
        while (scramble > 0){
            scramble -= 1;
            int newMove;
            try{
            do {
                newMove = (int)(Math.random() * 4) + 1;
            } while (newMove == dontMove); // keep looping until you get a valid move
            move(newMove);
            dontMove = (newMove + 1) % 4 + 1;   //mossa da NON fare (opposto di newMove)
            printBoard();
            } catch (outOfBoundsException e){
                scramble++;
            }
        }
        return board;
    }


    public int[]  move(int m) throws outOfBoundsException {
        return switch (m) {
            case 1 -> moveUP();
            case 2 -> moveRIGHT();
            case 3 -> moveDOWN();
            case 4 -> moveLEFT();
            default -> null;
        };
    }


    public int[] moveUP() throws outOfBoundsException{
        if (cords < 4){
            throw new outOfBoundsException();
        }
        int temp = board[cords-4];
        board[cords-4] = board[cords];
        board[cords]= temp;
        cords = cords-4;
        return board;

    }
    public int[] moveDOWN() throws outOfBoundsException{
        if (cords > 11){
            throw new outOfBoundsException();
        }
        int temp = board[cords+4];
        board[cords + 4] = board[cords];
        board[cords]= temp;
        cords = cords +4;
        return board;

    }
    public int[] moveLEFT() throws outOfBoundsException {
        if (cords %4 == 0){
            throw new outOfBoundsException();
        }
        int temp = board[cords-1];
        board[cords - 1] = board[cords];
        board[cords]= temp;
        cords = cords -1;
        return board;

    }
    public int[] moveRIGHT() throws outOfBoundsException {
        if (cords %4 == 3){
            throw new outOfBoundsException();
        }
        int temp = board[cords+1];
        board[cords + 1] = board[cords];
        board[cords]= temp;
        cords = cords + 1;
        return board;
    }

    public void printCords() {
        System.out.print(cords);
        ;
    }
    public void printBoard() {
        for (int j = 0; j < 4; j++){
            for (int i = 0; i < 4; i++) {
                System.out.print(board[i + 4*j]);
                System.out.print("| ");
            }
                System.out.println();
        }

        printCords();
        System.out.println();
    }

    public boolean isComplete(){
        for (int i = 0; i < 15; i++) {
            if (board[i] != i+1){
                return false;}
        }
        return true;
    }

    public int[] get() {
        return board;
    }

    public int nextBestMove(){
        return 0;
    }

    public boolean equals(Object obj){ //here we use object because we need to override method equals for the hash map to work

        if (obj == null || this.getClass() != obj.getClass())
        {return false;}
        GameBoard compare = (GameBoard) obj; //here you are doing "casting": you're telling the compiler that the obj is actually a gameboard

        if(compare == this){
            return true;
        }
        else{
            for (int i=0; i<16; i++){
                if (board[i] != compare.board[i])
                    return false;

            }
         return true;

        }
    }
    public int hashCode() {
        return Arrays.hashCode(this.board);
    }



    public int[] legalMoves(){
        int[] legal = new int[4]; // it's already filled with zeros
        if (cords >= 4){
            legal[0] = 1;
        }
        if (cords % 4 != 3){
            legal[1] = 2;
        }
        if (cords < 12){
            legal[2] = 3;
        }
        if (cords % 4 != 0){
            legal[3] = 4;
        }
        return legal;
    }
    public GameBoard copy(){
        GameBoard copy = new GameBoard();
        for (int i = 0 ; i < 16; i++){
            copy.board[i] = board[i];
        }
        copy.cords = this.cords;
        return copy;

    }
    public queue Children(){
        queue children = new queue();
        for(int move: this.legalMoves()){
            if(move != 0){
                GameBoard child = this.copy();
                child.move(move);
                children.add(child);
            }
        }
        return children;
    }

}




