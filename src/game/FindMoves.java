package game;
import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import java.nio.channels.FileChannel;
import java.nio.MappedByteBuffer;
public interface FindMoves {

    default void computeValues(GameBoard board) {
        return;
    }
    default long boardEncoding(GameBoard board,int partition) {
        long encodedBoard = 0;
        int[][] board2DArray= board.get();
        for(int i = 0; i < 16; i++){        // trasforma la board in un intero di 64 bit
            if (board2DArray[i/4][i%4] <= partition)
                encodedBoard= (encodedBoard <<4) | board2DArray[i/4][i%4];
            else
                encodedBoard = encodedBoard << 4;
        }
        return encodedBoard;
    }

    default int[][] decodeBoard(long encodedBoard) {
        int[][] decodedBoard = new int[4][4];
        for (int i = 15; i>=0; i--){
            decodedBoard[i/4][i%4]= (int) (encodedBoard  & 15); //mask degli ultimi 4 bit
            encodedBoard= encodedBoard >>> 4;
        }
        return decodedBoard;
    }

}
