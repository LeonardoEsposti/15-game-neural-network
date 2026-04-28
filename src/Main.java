public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        gameBoard.printBoard();
        gameBoard.moveUP(); // same as gameboard.move(1)
        gameBoard.printBoard();
        gameBoard.moveRIGHT(); // same as gameboard.move(2)
        gameBoard.printBoard();
        gameBoard.moveDOWN(); // same as gameboard.move(3)
        gameBoard.printBoard();
        gameBoard.moveLEFT(); // same as gameboard.move(4)
        gameBoard.printBoard();
    }
}