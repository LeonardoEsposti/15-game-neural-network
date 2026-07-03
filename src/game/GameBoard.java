package game;

import dataStructures.Queue;
import exceptions.OutOfBoundsException;
import java.util.Arrays;

public class GameBoard implements Moves {

    private int[] board = new int[16];
    private int coords = 15;

    public GameBoard() {
        for (int i = 1; i < 16; i++) {
            this.board[i - 1] = i;
        }
        this.board[this.coords] = 0;
    }

    public GameBoard(GameBoard gb) {
        this.board = gb.board;
        this.coords = gb.coords;
    }

    public GameBoard(int[] board) {
        this.board = board;
        for (int i = 0; i < 16; i++) {
            if (board[i] == 0) {
                this.coords = i;
                break;
            }
        }
    }

    public GameBoard(int scramble) {
        this();
        this.scrambleBoard(scramble);
    }

    public boolean isSolved() {
        for (int i = 0; i < 15; i++) {
            if (this.board[i] != i + 1)
                return false;
        }
        return true;
    }

    private void scrambleBoard(int scramble) {
        int dontMove = 0;
        while (scramble > 0) {
            scramble--;
            int newMove;
            do {
                newMove = (int) (Math.random() * 4) + 1;  // generates a random number between 1 and 4
            } while (newMove == dontMove);
            try {
                this.move(newMove);
                dontMove = (newMove + 1) % 4 + 1;  // opposite move is not allowed as next move
            } catch (OutOfBoundsException e) {
                scramble++;
            }
        }
    }

    private void move(int m) throws OutOfBoundsException {
        switch (m) {
            case 1 -> move(isUpLegal(coords), -4);
            case 2 -> move(isRightLegal(coords), 1);
            case 3 -> move(isDownLegal(coords), 4);
            case 4 -> move(isLeftLegal(coords), -1);
            default -> {
            }
        }
    }

    private void move(boolean isLegal, int offset) throws OutOfBoundsException {
        if (!isLegal) throw new OutOfBoundsException();
        int temp = board[coords+offset];
        board[coords+offset] = board[coords];
        board[coords] = temp;
        coords += offset;
    }

    private int[] legalMoves() {
        int[] legal = new int[4];
        if (isUpLegal(coords))
            legal[0] = 1;
        if (isRightLegal(coords))
            legal[1] = 2;
        if (isDownLegal(coords))
            legal[2] = 3;
        if (isLeftLegal(coords))
            legal[3] = 4;
        return legal;
    }

    public void printBoard() {
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                System.out.print(this.board[i+j*4]);
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    public int hashCode() {
        return Arrays.hashCode(this.board);
    }

    public String toString() {
        StringBuilder x = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            x.append(this.board[i]).append(",");
        }
        return x.toString();
    }

    public int[] toOneHotEncoding() {
        int[] res = new int[256];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (j == this.board[i])
                    res[i*16+j] = 1;
            }
        }
        return res;
    }

    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        GameBoard compare = (GameBoard) obj;
        if (compare != this) {
            for (int i = 0; i < 16; i++) {
                if (this.board[i] != compare.board[i])
                    return false;
            }
        }
        return true;
    }

    public Queue children() {
        Queue children = new Queue();
        for (int m : this.legalMoves()) {
            if (m != 0) {
                GameBoard child = new GameBoard(this);
                child.move(m);
                children.add(child, m);
            }
        }
        return children;
    }

    public Queue children(int exception) {
        Queue children = new Queue();
        for (int m : this.legalMoves()) {
            if (m != 0 && m != exception) {
                GameBoard child = new GameBoard(this);
                child.move(m);
                children.add(child, m);
            }
        }
        return children;
    }

    private boolean isRowConflict(int x1, int x2, int row) {
        return (board[x1 + 4 * row] != 0) && board[x2 + 4 * row] != 0
                && (board[x1 + 4 * row] - 1) / 4 == row && (board[x2 + 4 * row] - 1) / 4 == row  // if in the correct row
                && board[x1 + 4 * row] > board[x2 + 4 * row];  // if the left one is bigger
    }

    private boolean isColConflict(int y1, int y2, int column) {
        return (board[column + 4 * y1] != 0) && board[column + 4 * y2] != 0
                && (board[column + 4 * y1] - 1) % 4 == column && (board[column + 4 * y2] - 1) % 4 == column  // if in the correct column
                && board[column + 4 * y1] > board[column + 4 * y2];  // if the top one is bigger
    }

    private int linearConflictsRows() {
        int conflicts = 0;
        for (int y = 0; y < 4; y++) {
            boolean[] ignored = new boolean[4];
            while (true) {
                int[] linearConflicts = new int[4];
                for (int x1 = 0; x1 < 4; x1++) {
                    if (ignored[x1])
                        continue;
                    for (int x2 = x1 + 1; x2 < 4; x2++) {
                        if (ignored[x2])
                            continue;
                        if (this.isRowConflict(x1, x2, y)) {
                            linearConflicts[x1]++;
                            linearConflicts[x2]++;
                        }
                    }
                }
                int max = 0;
                int maxIndex = -1;
                for (int t = 0; t < linearConflicts.length; t++) {
                    int current = linearConflicts[t];
                    if (current > max) {
                        max = current;
                        maxIndex = t;
                    }
                }
                if (max == 0)
                    break;
                conflicts += 2;
                ignored[maxIndex] = true;
            }
        }
        return conflicts;
    }

    private int linearConflictsCols() {
        int conflicts = 0;
        for (int x = 0; x < 4; x++) {
            boolean[] ignored = new boolean[4];
            while (true) {
                int[] linearConflicts = new int[4];
                for (int y1 = 0; y1 < 4; y1++) {
                    if (ignored[y1])
                        continue;
                    for (int y2 = y1 + 1; y2 < 4; y2++) {
                        if (ignored[y2])
                            continue;
                        if (this.isColConflict(y1, y2, x)) {
                            linearConflicts[y1]++;
                            linearConflicts[y2]++;
                        }
                    }
                }
                int max = 0;
                int maxIndex = -1;
                for (int t = 0; t < linearConflicts.length; t++) {
                    int current = linearConflicts[t];
                    if (current > max) {
                        max = current;
                        maxIndex = t;
                    }
                }
                if (max == 0)
                    break;
                conflicts += 2;
                ignored[maxIndex] = true;
            }
        }
        return conflicts;
    }

    private int linearConflicts() {
        return linearConflictsRows() + linearConflictsCols();
    }

    private int manhattan() {
        int count = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[c + r * 4] == 0)
                    continue;
                int actualRow = (board[c + 4 * r] - 1) / 4;
                int actualCol = (board[c + 4 * r] - 1) % 4;
                count += Math.abs(r - actualRow) + Math.abs(c - actualCol);
            }
        }
        return count;
    }

    public int heuristic() {
        return manhattan() + linearConflicts();
    }
}