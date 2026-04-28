import java.util.List;
import java.util.ArrayList;
public class GameBoard implements Moves{
    private int[][] board= new int[4][4];

    private int[] cords= new int[2];

    public GameBoard(){
        List<Integer> valuesList= valuesList();
        for (int i = 0; i < 16; i++) {
            int rng= (int)(Math.random()*(16-i));
            if (valuesList.get(rng)==0) {           //salva coordinate dello spazio libero
                cords[0] = i / 4;
                cords[1] = i % 4;
            }
            board[i/4][i%4]= valuesList.get(rng);
            valuesList.remove(rng);
        }
    }


    public int[] move(int m){
        if (m==1)
            return moveUP();
        else if (m==2)
            return moveUP();
        else if (m==3)
            return moveUP();
        else if (m==4)
            return moveUP();
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
    private static List<Integer> valuesList(){              //crea lista di numeri da 0 a 15 da cui pescare dopo
        List<Integer> x = new ArrayList<>(16);
        for(int i=0; i<16; i+=1){
            x.addLast(i);
        }
        return x;
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
