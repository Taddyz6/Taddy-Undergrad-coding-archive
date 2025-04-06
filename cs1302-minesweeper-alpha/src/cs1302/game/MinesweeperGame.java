package cs1302.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The {@code MinesweeperGame} class provides a non-recursive
 * non-GUI version of the game Minesweeper.
 */
public class MinesweeperGame {
    private int row;
    private int col;
    private int round;
    private int minNumber;
    private String[][] square;
    private String[][] noFog;
    private boolean[][] haveMine;
    private boolean[][] revealed;
    private boolean noFogFlag = false;
    private Scanner myCommandScanner;

    /**
     * The {@code MinesweeperGame} constructor that read the contents of the
     * file whose path is stored in {@code seed} and initialize variable.
     * @param seedPath address that refers to the seed file
     * @param cInput address that refer to the input
     * @throws FileNotFoundException if seed file cannot be found or executed
     */
    public MinesweeperGame(Scanner cInput, String seedPath) throws FileNotFoundException {
        try {
            File filename = new File(seedPath);
            myCommandScanner = cInput;
            Scanner seedScanner = new Scanner(filename);
            row = 0;
            col = 0;
            if (seedScanner.hasNextInt()) {
                row = seedScanner.nextInt();
            } else {
                System.err.println("SeedFileStructure Error:Cannot creat game with" + seedPath);
                System.out.println();
                System.exit(3);
            }
            if (seedScanner.hasNextInt()) {
                col = seedScanner.nextInt();
            } else {
                System.err.println("SeedFileStructure Error:Cannot creat game with " + seedPath);
                System.out.println();
                System.exit(3);
            }
            if (seedScanner.hasNextInt()) {
                minNumber = seedScanner.nextInt();
            } else {
                System.err.println("Seed File Structure Error: Cannot creat game with " + seedPath);
                System.out.println();
                System.exit(3);
            }
            if ((row < 5) || (col < 5) || (this.minNumber  > row * col - 1)) {
                System.out.println();
                System.err.println("Seed File Structure Error: Cannot create a mine " +
                                    seedPath + " size should be with at least 5 rows and columns");
                System.exit(3);
            }
            initializeArray();
            for (int i = 0; i < minNumber; i++) {
                int mineRow = 0;
                int mineCol = 0;
                if (seedScanner.hasNextInt()) {
                    mineRow = seedScanner.nextInt();
                    if (seedScanner.hasNextInt()) {
                        mineCol = seedScanner.nextInt();
                    } else {
                        System.err.println("SeedFileStructure Error:Cannot creat game" + seedPath);
                        System.out.println();
                        System.exit(3);
                    }
                } else {
                    System.err.println("SeedFileStructure Error:Cannot creat game with" + seedPath);
                    System.out.println();
                    System.exit(3);
                }
                haveMine[mineRow][mineCol] = true;
            }
        } catch (FileNotFoundException fnfe) {
            System.err.println("Seed file not found Error: Cannot create game with FileName " +
                                seedPath + " because it cannot be found or read due to permission");
            System.exit(1);
        }
    }

    /**
     * Initialize the array.
     */
    private void initializeArray() {
        haveMine = new boolean[row][col];
        noFog = new String[row][col];
        square = new String[row][col];
        revealed = new boolean[row][col];

        for (int x = 0; x < row; x++) {
            for (int y = 0; y < col; y++) {
                haveMine[x][y] = false;
                revealed[x][y] = false;
                if (col > 10) {
                    noFog[x][y] = " ";
                    square[x][y] = "     ";
                } else {
                    noFog[x][y] = " ";
                    square[x][y] = " ";
                }
            }
        }
    }


    /**
     * Returns the number of mines adjacent to the specified
     * square in the grid.
     *
     * @param row the row index of the square
     * @param col the column index of the square
     * @return the number of adjacent mines
     */
    private int getNumAdjMines(int row, int col) {
        int result = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i != row || j != col) {
                    if (inPosition(i, j) && haveMine[i][j]) {
                        result++;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Make sure square is in the map.
     *
     * @param row the row index of the square
     * @param col the column index of the square
     * @return true if the square is in the map
     */
    private boolean inPosition(int row, int col) {
        return (row >= 0 && row < haveMine.length &&
                col >= 0 && col < haveMine[0].length);
    }

    /**
     * Print the welcome when the user start this game.
     */
    public void printWelcome( ) {
        System.out.println("        _");
        System.out.println("  /\\/\\ (F)_ __   ___  _____      _____  ___ _ __   ___ _ __");
        System.out.println(" /    \\| | '_ \\ / _ \\" +
                           "/ __\\ \\ /\\ / / _ \\/ _ \\ '_ \\ / _ \\ '__|");
        System.out.println("/ /\\/\\ \\ | | | |  __/\\__ \\\\ V  V /  __/  __/ |_) |  __/ |");
        System.out.println("\\/    \\/_|_| |_|\\___||___/ \\_/\\_/ \\___|\\___| .__/ \\___|_|");
        System.out.println("                             ALPHA EDITION |_| v2022.sp");
        System.out.println("");
    }

    /**
RStudio is an integrated development environment (IDE) for R. RStudio allows users to develop and edit
programs in R by supporting a large number of statistical packages, higher quality graphics, and the ability to
manage your workspace. It includes a console, syntax-highlighting editor that supports direct code execution,
as well as tools for plotting, history, debugging and workspace management. It is available in two formats:
RStudio Desktop is a regular desktop application while RStudio Server runs on a remote server and
allows accessing RStudio using a web browser. RStudio Desktop are available for Windows, macOS,
and Linux. RStudio Server and Server Pro run on Debian, Ubuntu, Red Hat Linux, CentOS,
openSUSE and SLES. JJ Allaire, creator of the programming language ColdFusion, founded RStudio.
Hadley Wickham is the Chief Scientist at RStudio
* Print the correct contents of the mine field to
     * make standard output.
     */
    public void printMineField() {
        System.out.print(" ");
        for (int x = 0; x < square.length; x++ ) {
            if (square.length > 10) {
                if (x < 10) {
                    System.out.print(" ");
                }
            }
            System.out.print(" " + x + "|");
            for (int y = 0; y < square[1].length; y++) {
                if (noFogFlag) {

                    if (haveMine[x][y]) {
                        if (!square[x][y].contains("F")) {
                            System.out.print("< >");
                        } else {
                            System.out.print("<F>");
                        }
                    } else {
                        System.out.print(" " + square[x][y] + " ");
                    }
                } else {
                    System.out.print(" " + square[x][y] + " ");
                }
                if (y < square[1].length - 1) {
                    System.out.print("|");
                }
            }

            System.out.println("|");

            if (square.length > 10) {
                System.out.print(" ");
            } else {
                System.out.print(" ");
            }
        }

        if (square.length > 10) {
            System.out.print("      ");
        } else {
            System.out.print("   ");
        }

        for (int z = 0; z < square[0].length; z++) {
            if (square[0].length > 10) {
                if (z >= 10) {
                    System.out.print("   " + z + "   ");
                } else {
                    System.out.print("   " + z + "    ");
                }
            } else {
                System.out.print(" " + z + "  ");
            }
        }
        System.out.println();
        if (noFogFlag) {
            noFogFlag = false;
        }
    }

    /**
     * make the user know how to
     * input from the standard input.
     */
    public void promptUser() {
        printRound();
        printMineField();
        printBash();
        readCommand(myCommandScanner);
    }

    /**
     * Return the true if this all correct and win this game.
     *
     * @return true if all condition are meet to win this game
     */
    public boolean isWon() {
        boolean allMineReveal = true;
        boolean allSquareReveal = true;

        for (int x = 0; x < square.length; x++) {
            for (int y = 0; y < square[0].length; y++) {
                if (haveMine[x][y]) {
                    if (!square[x][y].contains("F")) {
                        allMineReveal = false;
                    }
                } else {
                    if (!revealed[x][y]) {
                        allSquareReveal = false;
                    }
                }
            }
        }

        return allSquareReveal && allMineReveal;
    }

    /**
     * This is for read the command.
     *
     * @param key a scanner object that read formatted input
     */
    public void readCommand(Scanner key) {
        String input = key.nextLine().trim();
        Scanner keyboard = new Scanner(input);
        String command = keyboard.next().trim();

        switch (command) {
        case "q":
        case "quit":
            quit();
            break;
        case "h":
        case "help":
            printHelpingMsg();
            break;
        case "nofog":
            noFog();
            break;
        case "g":
        case "guess":
            if (!guess(keyboard)) {
                printCommandNotRecognized();
            }
            break;
        case "r":
        case "reveal":
            if (!reveal(keyboard)) {
                printCommandNotRecognized();
            }
            break;
        case "m":
        case "mark":
            if (!mark(keyboard)) {
                printCommandNotRecognized();
            }
            break;
        default:
            printCommandNotRecognized();
        }
    }

    /**
     * Print the game over message.
     */
    public void printloss() {
        System.out.println("  Oh no... You revealed a mine!");
        System.out.println("  __ _  __ _ _ __ ___   ___    _____   _____ _ __");
        System.out.println(" / _` |/ _` | '_ ` _ \\ / _ \\  / _ \\ \\ / / _ \\ '__|");
        System.out.println("| (_| | (_| | | | | | |  __/ | (_) \\ V /  __/ |");
        System.out.println(" \\__, |\\__,_|_| |_| |_|\\___|  \\___/ \\_/ \\___|_|");
        System.out.println(" |___/");
        System.out.println();
    }

    /**
     * Provides the main game loop by involking other instance method.
     */
    public void play() {
        printWelcome();
        while (true) {
            promptUser();
            if (isWon()) {
                break;
            }
        }
        printwin();
        System.exit(0);
    }

    /**
     * Return the final score.
     *
     * @return score the final score
     */
    private String score() {
        return String.format("%.2f", 100.0 * square.length * square[0].length / round);
    }

    /**
     * The command h or help.
     */
    private void printHelpingMsg() {
        round++;
        System.out.println();
        System.out.println();
        System.out.println("Command Available...");
        System.out.println(" - Reveal: r/reveal row col");
        System.out.println(" - Mark: m/mark row col");
        System.out.println(" - Guess: g/guess row col");
        System.out.println(" - Help: h/help");
        System.out.println(" - Quit: q/quit");
        System.out.println();
    }

    /**
     * Print the quit message and
     * quit the game after user want to.
     */
    private void quit() {
        System.out.println("\nQuit the game... \nBye!");
        System.exit(0);
    }

    /**
     * Show the mine location for the next round.
     */
    private void noFog() {
        round++;
        noFogFlag = true;
        System.out.println();
    }

    /**
     * Print the no fog array if user want to do that.
     */
    private void printNoFog() {
        for (int x = 0; x < noFog.length; x++) {
            for (int y = 0; y < noFog[0].length; y++) {
                if (haveMine[x][y] && !square[x][y].strip().equals("F")) {
                    System.out.print("<>");
                } else {
                    System.out.print(square[x][y]);
                }

                if (y < noFog[x].length - 1) {
                    System.out.print("!");
                }
            }
            System.out.println();
        }
        System.out.print("      ");
        for (int x = 0; x < square[0].length; x++) {
            System.out.print(x + "      ");
        }
        System.out.println();
    }

    /**
     * Guess which square have mine.
     * @param key a scanner object that reads formatted input
     * @return false if the coordincates out of bounds
     */
    private boolean guess(Scanner key) {
        boolean guess = false;
        int guessRow = 0;
        int guessCol = 0;

        if (key.hasNextInt()) {
            guessRow = key.nextInt();
            if (key.hasNextInt()) {
                guessCol = key.nextInt();

                if (!key.hasNextInt() && inPosition(guessRow, guessCol)) {
                    guess = true;
                    round++;
                    if (square[0].length > 10) {
                        square[guessRow][guessCol] = "  ?  ";
                    } else {
                        square[guessRow][guessCol] = "?";
                    }
                    System.out.println();
                }
            }
        }

        return guess;
    }

    /**
     * Mark a square if it is mine.
     *
     * @param key a scanner object that reads formatted input
     * @return false if the coordincates out of bounds
     */
    private boolean mark(Scanner key) {
        boolean mark = false;
        int markRow = 0;
        int markCol = 0;
        if (key.hasNextInt()) {
            markRow = key.nextInt();
            if (key.hasNextInt()) {
                markCol = key.nextInt();

                if (!key.hasNextInt() && inPosition(markRow, markCol)) {
                    mark = true;
                    round++;
                    if (square[0].length > 10) {
                        square[markRow][markCol] = "  F  ";
                    } else {
                        square[markRow][markCol] = "F";
                    }
                    System.out.println();
                }
            }
        }

        return mark;
    }

    /**
     * The boolean reveal.
     *
     * @param key a scanner object that reads formatted input
     * @return false if the coordincates out of bounds
     */
    private boolean reveal(Scanner key) {
        boolean reveal = false;
        int revealRow = 0;
        int revealCol = 0;
        if (key.hasNextInt()) {
            revealRow = key.nextInt();
            if (key.hasNextInt()) {
                revealCol = key.nextInt();

                if (!key.hasNextInt() && inPosition(revealRow, revealCol)) {
                    if (haveMine[revealRow][revealCol]) {
                        this.printloss();
                        System.exit(0);
                    }
                    revealed[revealRow][revealCol] = true;
                    reveal = true;
                    round++;
                    if (square[0].length > 10) {
                        square[revealRow][revealCol] = "" + getNumAdjMines(revealRow, revealCol);
                    } else {
                        square[revealRow][revealCol] = "" + getNumAdjMines(revealRow, revealCol);
                    }
                    System.out.println();
                }
            }
        }

        return reveal;
    }

    /**
     * Print the round.
     */
    private void printRound() {
        System.out.println("\n Rounds Completed: " + round);
        System.out.println();
    }

    /**
     * Print the bash.
     */
    private void printBash() {
        System.out.println();
        System.out.println("minesweeper-alpha: ");
    }

    /**
     * Print command not recognized.
     */
    private void printCommandNotRecognized() {
        System.out.println();
        System.out.println();
        System.out.println("Input Error: Command not recognized!");
    }

    /**
     * Print the win.
     */
    public void printwin() {
        System.out.println();
        System.out.println("░░░░░░░░░▄░░░░░░░░░░░░░░▄░░░░ \"So Doge\"");
        System.out.println("░░░░░░░░▌▒█░░░░░░░░░░░▄▀▒▌░░░");
        System.out.println("░░░░░░░░▌▒▒█░░░░░░░░▄▀▒▒▒▐░░░ \"Such Score\"");
        System.out.println("░░░░░░░▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐░░░");
        System.out.println("░░░░░▄▄▀▒░▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐░░░ \"Much Minesweeping\"");
        System.out.println("░░░▄▀▒▒▒░░░▒▒▒░░░▒▒▒▀██▀▒▌░░░");
        System.out.println("░░▐▒▒▒▄▄▒▒▒▒░░░▒▒▒▒▒▒▒▀▄▒▒▌░░ \"Wow\"");
        System.out.println("░░▌░░▌█▀▒▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐░░");
        System.out.println("░▐░░░▒▒▒▒▒▒▒▒▌██▀▒▒░░░▒▒▒▀▄▌░");
        System.out.println("░▌░▒▄██▄▒▒▒▒▒▒▒▒▒░░░░░░▒▒▒▒▌░");
        System.out.println("▀▒▀▐▄█▄█▌▄░▀▒▒░░░░░░░░░░▒▒▒▐░");
        System.out.println("▐▒▒▐▀▐▀▒░▄▄▒▄▒▒▒▒▒▒░▒░▒░▒▒▒▒▌");
        System.out.println("▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒▒▒░▒░▒░▒▒▐░");
        System.out.println("░▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒░▒░▒░▒░▒▒▒▌░");
        System.out.println("░▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▒▄▒▒▐░░");
        System.out.println("░░▀▄▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▄▒▒▒▒▌░░");
        System.out.println("░░░░▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀░░░ CONGRATULATIONS!");
        System.out.println("░░░░░░▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀░░░░░ YOU HAVE WON!");
        System.out.println("░░░░░░░░░▒▒▒▒▒▒▒▒▒▒▀▀░░░░░░░░ SCORE:" + score());
    }
}
