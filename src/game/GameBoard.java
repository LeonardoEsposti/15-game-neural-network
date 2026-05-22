package game;
import utils.UsefulFuncs;
import exceptions.outOfBoundsException;

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

    private int[] scrambleBoard(int scramble) {
        int dontMove = 0;
        while (scramble > 0){
            scramble -= 1;
            int newMove;
            do {
                newMove = (int)(Math.random() * 4) + 1;
            } while (newMove == dontMove); // keep looping until you get a valid move
            move(newMove);
            dontMove = (newMove + 1) % 4 + 1;   //mossa da NON fare (opposto di newMove)
            printBoard();
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
        if (cords > 12){
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



}
