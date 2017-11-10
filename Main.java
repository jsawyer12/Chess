import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static int[][] gameBoard;

    public static final int MIN = 0;
    public static final int MAX = 7;

    public static final int EMPTY = 0;
    public static final int BLACKBISH = 1;
    public static final int WHITEBISH = 2;
    public static final int BLACKKNIGHT = 3;
    public static final int WHITEKNIGHT = 4;
    public static final int BLACKROOK= 5;
    public static final int WHITEROOK = 6;
    public static final int BLACKQUEEN = 7;
    public static final int WHITEQUEEN = 8;
    public static final int BLACKKING = 9;
    public static final int WHITEKING = 10;
    public static final int BLACKPAWN = 11;
    public static final int WHITEPAWN = 12;

    public static int curColor;

    public static boolean checkMate() {

        return false;
    }

    public static boolean gameOver() {
        if (checkMate()) {
            return true;
        }
        else
            return false;
    }

    /**
     * Makes sure the Knight is moving the right way
     * @param orig
     * @param dest
     * @return
     */
    public static boolean knightTest(int[] orig, int[] dest) {
        ArrayList<int[]> knightMoves = new ArrayList<int[]>();
        int[][] potentialMoves = new int[8][2];
        potentialMoves[0][0] = orig[0] - 2;
        potentialMoves[0][1] = orig[1] - 1;
        potentialMoves[1][0] = orig[0] - 2;
        potentialMoves[1][1] = orig[1] + 1;
        potentialMoves[2][0] = orig[0] - 1;
        potentialMoves[2][1] = orig[1] - 2;
        potentialMoves[3][0] = orig[0] - 1;
        potentialMoves[3][1] = orig[1] + 2;
        potentialMoves[4][0] = orig[0] + 1;
        potentialMoves[4][1] = orig[1] - 2;
        potentialMoves[5][0] = orig[0] + 1;
        potentialMoves[5][1] = orig[1] + 2;
        potentialMoves[6][0] = orig[0] + 2;
        potentialMoves[6][1] = orig[1] - 1;
        potentialMoves[7][0] = orig[0] + 2;
        potentialMoves[7][1] = orig[1] + 1;
        for (int mo = 0; mo < 8; mo++) {
            if (potentialMoves[mo][0] == dest[0] && potentialMoves[mo][1] == dest[1]
                    && ((gameBoard[dest[1]][dest[0]] % 2) != curColor || gameBoard[dest[1]][dest[0]] == EMPTY))
                return true;
        }
        return false;
    }

    public static int[] retNextDiag(int[] orig, int[] dest, int[] tempCoord) {
        if (orig[0] < dest[0]) {
            tempCoord[0]++;
            if (orig[1] > dest[1])
                tempCoord[1]--;
            else
                tempCoord[1]++;
        }
        else {
            tempCoord[0]--;
            if (orig[1] > dest[1])
                tempCoord[1]--;
            else
                tempCoord[1]++;
        }
        return tempCoord;
    }

    public static boolean diagonTest(int[] orig, int[] dest) {
        int[] tempCoord = new int[] {orig[0], orig[1]};
        tempCoord = retNextDiag(orig, dest, tempCoord);
        while (gameBoard[tempCoord[1]][tempCoord[0]] == EMPTY) {
            if (tempCoord[0] == dest[0] && tempCoord[1] == dest[1]) {
                return true;
            }
            if ((tempCoord[0] == dest[0] && tempCoord[1] != dest[1]) || (tempCoord[0] != dest[0] && tempCoord[1] == dest[1]))
                return false;
            tempCoord = retNextDiag(orig, dest, tempCoord);
            System.out.println("temp: " +tempCoord[0] +"," +tempCoord[1]);
        }
        return false;
    }

    public static boolean outOfBoundsInputTest(int[] orig, int[] dest) {
        if (orig[0] < MIN || orig[0] > MAX)
            return false;
        if (orig[1] < MIN || orig[1] > MAX)
            return false;
        if (dest[0] < MIN || dest[0] > MAX)
            return false;
        if (dest[1] < MIN || dest[1] > MAX)
            return false;
        else
            return true;
    }

    public static boolean initiateTests(int[] orig, int[] dest) {
        if (!outOfBoundsInputTest(orig, dest))
            return false;
        if (curColor != (gameBoard[orig[1]][orig[0]] % 2) || gameBoard[orig[1]][orig[0]] == EMPTY)
            return false;
        else {
            switch (gameBoard[orig[1]][orig[0]]) {
                case BLACKBISH:
                case WHITEBISH:
                    if (!diagonTest(orig, dest))
                        return false;
                    break;
                case BLACKKNIGHT:
                case WHITEKNIGHT:
                    if (!knightTest(orig, dest))
                        return false;
                    break;
                case BLACKROOK:
                case WHITEROOK:
                    break;
                case BLACKQUEEN:
                case WHITEQUEEN:
                    break;
                case BLACKKING:
                case WHITEKING:
                    break;
                case BLACKPAWN:
                case WHITEPAWN:
                    break;
            }
            return true;
        }
    }

    /**
     * This switches around the coordinates so the first number actually represents the columns
     * and the second represents the rows, as they are x and y respectively
     * @param moveIn
     */
    public static void parseMove(String moveIn) {
        boolean canDo = true;
        String[] moveSpl = moveIn.split(" ");
        String[] orig = moveSpl[0].split(",");
        String[] dest = moveSpl[1].split(",");
        int[] origInt = new int[] {Integer.parseInt(orig[0]), Integer.parseInt(orig[1])};
        int[] destInt = new int[] {Integer.parseInt(dest[0]), Integer.parseInt(dest[1])};
        canDo = initiateTests(origInt, destInt);
        if (canDo) {
            movePiece(origInt, destInt);
            changeColor();
        }
        else {
            System.out.println("No can do saila");
        }
    }

    public static void printPlayer() {
        System.out.println();
        if (curColor == 1)
            System.out.println("It is Black's turn to play (Solid)");
        else
            System.out.println("It is White's turn to play (See Through)");
    }

    public static void moveInput(Scanner reader) {
        printPlayer();
        System.out.print("Enter the coordinates of a piece to move and its destination: ");
        String moveIn = reader.nextLine();
        parseMove(moveIn);
        System.out.println(moveIn);
    }

    public static void displayBoard() {
        System.out.println();
        for (int rows = 0; rows < 8; rows++) {
            System.out.print(rows +" ");
            for (int cols = 0; cols < 8; cols++) {
                switch (gameBoard[rows][cols]) {
                    case EMPTY:
                        System.out.print("▢ ");
                        break;
                    case BLACKBISH:
                        System.out.print("♝ ");
                        break;
                    case WHITEBISH:
                        System.out.print("♗ ");
                        break;
                    case BLACKKNIGHT:
                        System.out.print("♞ ");
                        break;
                    case WHITEKNIGHT:
                        System.out.print("♘ ");
                        break;
                    case BLACKROOK:
                        System.out.print("♜ ");
                        break;
                    case WHITEROOK:
                        System.out.print("♖ ");
                        break;
                    case BLACKQUEEN:
                        System.out.print("♛ ");
                        break;
                    case WHITEQUEEN:
                        System.out.print("♕ ");
                        break;
                    case BLACKKING:
                        System.out.print("♚ ");
                        break;
                    case WHITEKING:
                        System.out.print("♔ ");
                        break;
                    case BLACKPAWN:
                        System.out.print("♟ ");
                        break;
                    case WHITEPAWN:
                        System.out.print("♙ ");
                        break;
                }
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int k = 0; k < 8; k++) {
            System.out.print(k + " ");
        }
        System.out.println();
    }

    public static void initializeBoard() {
        gameBoard = new int[][] {{5,3,1,7,9,1,3,5},
                {11,11,11,11,11,11,11,11},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {12,12,12,12,12,12,12,12},
                {6,4,2,8,10,2,4,6}};
    }

    public static void movePiece(int[] orig, int[] dest) {
        int piece = gameBoard[orig[1]][orig[0]];
        gameBoard[orig[1]][orig[0]] = EMPTY;
        gameBoard[dest[1]][dest[0]] = piece;
    }

    public static void changeColor() {
        if (curColor == 1)
            curColor = 0;
        else
            curColor = 1;
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Welcome to Chess");
        initializeBoard();
        while (!gameOver()) {
            displayBoard();
            moveInput(reader);
        }
    }
}
