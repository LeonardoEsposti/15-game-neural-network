package game;

import dataStructures.queue;
import exceptions.outOfBoundsException;

import java.util.Arrays;

public class GameBoard implements Moves {
    private int[] board = new int[16];

    private int cords = 15;

    public GameBoard(int[] input) {
        board = input;
    }

    public GameBoard() {
        board[15] = 0;
        for (int i = 1; i < 16; i++) {
            board[i - 1] = i;
        }
        board[15] = 0;
    }

    public GameBoard(int scramble) {
        for (int i = 1; i < 16; i++) {
            board[i - 1] = i;
        }
        board[15] = 0;
        board = scrambleBoard(scramble);
    }

    public int[] scrambleBoard(int scramble) {
        int dontMove = 0;
        while (scramble > 0) {
            scramble -= 1;
            int newMove;
            try {
                do {
                    newMove = (int) (Math.random() * 4) + 1;
                } while (newMove == dontMove); // keep looping until you get a valid move
                move(newMove);
                dontMove = (newMove + 1) % 4 + 1;   //mossa da NON fare (opposto di newMove)
            } catch (outOfBoundsException e) {
                scramble++;
            }
        }
        return board;
    }


    public int[] move(int m) throws outOfBoundsException {
        return switch (m) {
            case 1 -> moveUP();
            case 2 -> moveRIGHT();
            case 3 -> moveDOWN();
            case 4 -> moveLEFT();
            default -> null;
        };
    }


    public int[] moveUP() throws outOfBoundsException {
        if (cords < 4) {
            throw new outOfBoundsException();
        }
        int temp = board[cords - 4];
        board[cords - 4] = board[cords];
        board[cords] = temp;
        cords = cords - 4;
        return board;

    }

    public int[] moveDOWN() throws outOfBoundsException {
        if (cords > 11) {
            throw new outOfBoundsException();
        }
        int temp = board[cords + 4];
        board[cords + 4] = board[cords];
        board[cords] = temp;
        cords = cords + 4;
        return board;

    }

    public int[] moveLEFT() throws outOfBoundsException {
        if (cords % 4 == 0) {
            throw new outOfBoundsException();
        }
        int temp = board[cords - 1];
        board[cords - 1] = board[cords];
        board[cords] = temp;
        cords = cords - 1;
        return board;

    }

    public int[] moveRIGHT() throws outOfBoundsException {
        if (cords % 4 == 3) {
            throw new outOfBoundsException();
        }
        int temp = board[cords + 1];
        board[cords + 1] = board[cords];
        board[cords] = temp;
        cords = cords + 1;
        return board;
    }

    public void printCords() {
        System.out.print(cords);
        ;
    }

    public void printBoard() {
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                System.out.print(board[i + 4 * j]);
                System.out.print("| ");
            }
            System.out.println();
        }

        printCords();
        System.out.println();
    }

    public boolean isComplete() {
        for (int i = 0; i < 15; i++) {
            if (board[i] != i + 1) {
                return false;
            }
        }
        return true;
    }

    public int[] get() {
        return board;
    }


    public boolean equals(Object obj) { //here we use object because we need to override method equals for the hash map to work

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        GameBoard compare = (GameBoard) obj; //here you are doing "casting": you're telling the compiler that the obj is actually a gameboard

        if (compare == this) {
            return true;
        } else {
            for (int i = 0; i < 16; i++) {
                if (board[i] != compare.board[i])
                    return false;

            }
            return true;

        }
    }

    public int hashCode() {
        return Arrays.hashCode(this.board);
    }
    public String boardToSave(){
        String x="";
        for (int i = 0; i < 16; i++) {
            x+=board[i]+",";
        }
        return x;
    }
    @Override //to override the inherited method by using an understandable name
    public String toString(){
        return boardToSave();
    }

    public int[] legalMoves() {
        int[] legal = new int[4]; // it's already filled with zeros
        if (cords >= 4) {
            legal[0] = 1;
        }
        if (cords % 4 != 3) {
            legal[1] = 2;
        }
        if (cords < 12) {
            legal[2] = 3;
        }
        if (cords % 4 != 0) {
            legal[3] = 4;
        }
        return legal;
    }

    public GameBoard copy() {
        GameBoard copy = new GameBoard();
        for (int i = 0; i < 16; i++) {
            copy.board[i] = board[i];
        }
        copy.cords = this.cords;
        return copy;

    }

    public queue Children() {
        queue children = new queue();
        for (int move : this.legalMoves()) {
            if (move != 0) {
                GameBoard child = this.copy();
                child.move(move);
                children.add(child);
            }
        }
        return children;
    }


    public boolean is_conflict_rows(int x, int x2, int row) {
        if ((board[x + 4 * row] != 0) && board[x2 + 4 * row] != 0
                && (board[x + 4 * row] - 1) / 4 == row && (board[x2 + 4 * row] - 1) / 4 == row //if in the correct row
                && board[x + 4 * row] > board[x2 + 4 * row]) { //if the left one is bigger
            return true;
        }
        return false;
    }

    public boolean is_conflict_columns(int y, int y2, int column) {
        if ((board[column + 4 * y] != 0) && board[column + 4 * y2] != 0
                && (board[column + 4 * y] - 1) % 4 == column && (board[column + 4 * y2] - 1) % 4 == column //in the correct column
                && board[column + 4 * y] > board[column + 4 * y2]) { //if the top one is bigger
            return true;
        }
        return false;
    }

    public int linearConflictsRows() {
        int conflicts = 0;
        for (int y = 0; y < 4; y++) {
            boolean[] ignored = new boolean[4];
            while (true) {
                int[] lineConflicts = new int[4];
                for (int x = 0; x < 4; x++) {
                    if (ignored[x]) {
                        continue;
                    }
                    for (int x2 = x + 1; x2 < 4; x2++) {
                        if (ignored[x2]) {
                            continue;
                        }
                        if (this.is_conflict_rows(x, x2, y)) {
                            lineConflicts[x]++;
                            lineConflicts[x2]++;
                        }

                    }

                }
                int max = 0;
                int maxIndex = -1;
                for (int t = 0; t < lineConflicts.length; t++) {
                    int current = lineConflicts[t];
                    if (current > max) {
                        max = current;
                        maxIndex = t;
                    }
                }
                if (max == 0) {
                    break;
                }
                conflicts += 2;
                ignored[maxIndex] = true;


            }
        }
        return conflicts;
    }

    public int linearConflictsColumns() {
        int conflicts = 0;
        for (int x = 0; x < 4; x++) {
            boolean[] ignored = new boolean[4];
            while (true) {
                int[] lineConflicts = new int[4];
                for (int y = 0; y < 4; y++) {
                    if (ignored[y]) {
                        continue;
                    }
                    for (int y2 = y + 1; y2 < 4; y2++) {
                        if (ignored[y2]) {
                            continue;
                        }
                        if (this.is_conflict_columns(y, y2, x)) {
                            lineConflicts[y]++;
                            lineConflicts[y2]++;
                        }

                    }

                }
                int max = 0;
                int maxIndex = -1;
                for (int t = 0; t < lineConflicts.length; t++) {
                    int current = lineConflicts[t];
                    if (current > max) {
                        max = current;
                        maxIndex = t;
                    }
                }
                if (max == 0) {
                    break;
                }
                conflicts += 2;
                ignored[maxIndex] = true;


            }
        }
        return conflicts;
    }

    public int linearConflicts() {
        return linearConflictsRows() + linearConflictsColumns();
    }

    public int Manhattan() {
        int counter = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[c + 4 * r] == 0) {
                    continue;
                }
                int actualRow = (board[c + 4 * r] - 1) / 4;
                int actualCol = (board[c + 4 * r] - 1) % 4;
                counter += Math.abs(r - actualRow) + Math.abs(c - actualCol);
            }
        }
        return counter;
    }

    public int euristic() {
        return Manhattan() + linearConflicts();
    }
}





