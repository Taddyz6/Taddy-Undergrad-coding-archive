package cs1302.hw09;

import java.util.Scanner;

/**
 * A Tic-Tac-Toe Solver class.
 */
public class TTTSolver {

    /**
     * The entry point for the program.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        char player;
        System.out.println("Please enter an initial board state " +
                           "using 9 consecutive characters. Valid "  +
                           "characters are X, O, and -.");
        String board = promptBoard(input);
        System.out.println("Which player to evaluate the number of chance to win? Valid input: X" +
            " or O");
        while (true) {
            String temp = input.nextLine();
            if (temp.equals("X") || temp.equals("O")) {
                player = temp.charAt(0);
                break;
            } else {
                System.out.println("Invalid input.");
                System.out.println("X or O?");
            }
        }
        System.out.println(TTTUtility.isCat(board));

        printAllBoards(board);
        System.out.println("Ways " + player + " can win: " + countAllWinningBoards(board, player));
    } // main

    /**
     * Prompt the user for a valid board configuration.
     * @param input an input scanner
     * @return the board configuration
     */
    public static String promptBoard(Scanner input) {
        String board = input.nextLine();
        while (!TTTUtility.validGame(board)) {
            System.out.println("Invalid board. Try again.");
            board = input.nextLine();
        } // while
        return board;
    } // promptBoard

    /**
     * Given an initial board state, this method prints
     * all board states that can be reached via valid
     * sequence of moves by each player. Therefore, the
     * printout includes both intermediate board states
     * as well as completed board states.
     *
     * @param board the game board
     */
    public static void printAllBoards(String board) {
        System.out.println(board);
        if (TTTUtility.isCat(board) || TTTUtility.isWinner(board, 'X') ||
            TTTUtility.isWinner(board, 'O')) {
            System.out.print("");
        } else {
            for (int i = 0; i < board.length(); i ++) {
                char checkChar = board.charAt(i);
                if (checkChar == '-') {
                    char turn = TTTUtility.whoseTurn(board);
                    String before = board.substring(0, i);
                    String after = board.substring(i + 1);
                    String complete = before + turn + after;
                    printAllBoards(complete);
                }
            }
        }
    } // printAllBoards

/**
 * Given an initial board state and a player, returns a count of all winning
 * board states for that player that can be reached via a valid sequence of moves by each player.
 * @param board initial board state
 * @param player the the character that need to check
 * @return the total number of chance to win with given character
 */

    public static int countAllWinningBoards(String board, char player) {
        int count = 0;
        if (TTTUtility.isCat(board) || TTTUtility.isWinner(board, 'X') ||
            TTTUtility.isWinner(board, 'O')) {
            if (TTTUtility.isWinner(board, player)) {
                count ++;
            }
        } else {
            for (int i = 0; i < board.length(); i ++) {
                char checkChar = board.charAt(i);
                if (checkChar == '-') {
                    char turn = TTTUtility.whoseTurn(board);
                    String before = board.substring(0, i);
                    String after = board.substring(i + 1);
                    String complete = before + turn + after;
                    count +=  countAllWinningBoards(complete, player);
                }
            }
        }
        return count;

    }
} // TTTSolver
