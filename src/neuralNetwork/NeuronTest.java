package neuralNetwork;

import game.GameBoard;

public class NeuronTest extends Thread{
    //test a caso niari


    private final int ROW;
    private final int COL;
    GameBoard board;
    public NeuronTest(GameBoard board, int r, int c){
        this.board=board;
        ROW=r;
        COL=c;
    }
    @Override
    public void run(){
        int tile=board.get()[ROW][COL];
        if (tile==1){
            //reward (?????????)
            System.out.println("Casella (row"+ ROW + ", col " + COL +") è giusta");
        }
        else if (tile==2 || tile==5){ //questi dovranno essere parametri (e dovrebbero anche essere 4
                                      //or per le caselle che non sono sul bordo) che dipendo da row e col
            //rewardignos
            System.out.println("Casella (row"+ ROW + ", col " + COL +") è vicina");
        }
        else{
            System.out.println("Casella (row"+ ROW + ", col " + COL +") è una merda");
        }

    }

}
