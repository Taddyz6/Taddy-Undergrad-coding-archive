package cs1302.game;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The driver class of
 * a classic game {@code MinesweeperGame}.
 */
public class MinesweeperDriver {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner in = new Scanner(System.in);
        String seedPath = args[0];

        MinesweeperGame myGame = new MinesweeperGame(in, seedPath);

        myGame.play();
    }

}
