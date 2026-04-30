import java.util.List;
import java.util.ArrayList;
public class GameBoard implements Moves{
    private int[][] board= new int[4][4];

    private int[] cords= new int[]{3,3};

    public GameBoard(){
        final int SCRAMBLE= (int)(Math.random()*20)+50;
        board[3][3]=0;
        for (int i = 0; i < 15; i++) {
            board[i/4][i%4]=i+1;
        }
        board= scrambleBoard(SCRAMBLE);
    }

    private int[][] scrambleBoard(int scramble) {
        while (scramble>0){
            scramble-=1;
            move((int)(Math.random()*4)+1);
            printBoard();
        }
        return board;
    }


    public int[] move(int m){
        if (m==1)
            return moveUP();
        else if (m==2)
            return moveRIGHT();
        else if (m==3)
            return moveDOWN();
        else if (m==4)
            return moveLEFT();
        return null;
    }
    public int[] moveUP(){
        if (cords[0]==0)
            return cords;
        int i= cords[0];
        int j= cords[1];
        int temp= board[i][j];
        board[i][j]=board[i-1][j];
        board[i-1][j]=temp;
        cords[0]=i-1;
        return cords;
    }
    public int[] moveDOWN(){
        if (cords[0]==3)
            return cords;
        int i= cords[0];
        int j= cords[1];
        int temp= board[i][j];
        board[i][j]=board[i+1][j];
        board[i+1][j]=temp;
        cords[0]=i+1;
        return cords;
    }
    public int[] moveLEFT(){
        if (cords[1]==0)
            return cords;
        int i= cords[0];
        int j= cords[1];
        int temp= board[i][j];
        board[i][j]=board[i][j-1];
        board[i][j-1]=temp;
        cords[1]=j-1;
        return cords;
    }
    public int[] moveRIGHT(){
        if (cords[1]==3)
            return cords;
        int i= cords[0];
        int j= cords[1];
        int temp= board[i][j];
        board[i][j]=board[i][j+1];
        board[i][j+1]=temp;
        cords[1]=j+1;
        return cords;
    }

    public void printCords(){
        System.out.print("X: "+ cords[0]);
        System.out.println(" Y: "+ cords[1]);
    }
    public void printBoard(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(board[i][j]+ " ");
                if (board[i][j]<10)
                    System.out.print(" ");
                if (j!=3)
                    System.out.print("| ");
            }
            System.out.println();
        }
        printCords();
        System.out.println();
    }

    public boolean isComplete(){
        for (int i = 0; i < 15; i++) {
            if  (board[i/4][i%4]!=(i+1))
                return false;
        }
        return true;
    }
}
