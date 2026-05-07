import game.GameBoard;

public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        NeuronTest N1= new NeuronTest(gameBoard,0,0);
        N1.run();
    }
}