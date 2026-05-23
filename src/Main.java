import game.GameBoard;

public class Main {
    public static void main(String[] args) {
        GameBoard prova = new GameBoard(5);
        prova.printBoard();
        System.out.println(prova.legalMoves());

    }
}