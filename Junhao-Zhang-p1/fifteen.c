/**************************************************************************
 * This work originally copyright David J. Malan of Harvard University, or-
 * iginally released under:
 * CC BY-NC-SA 3.0  http://creativecommons.org/licenses/by-nc-sa/3.0/
 * licensing.
 *
 * It has been adapted for use in csci 1730.  Per the share-alike
 * clause of this license, this document is also released under the
 * same license.
 **************************************************************************/
/**
 * fifteen.c
 *
 * Implements Game of Fifteen (generalized to d x d).
 *
 * Usage: fifteen d
 *
 * whereby the board's dimensions are to be d x d,
 * where d must be in [DIM_MIN,DIM_MAX]
 *
 * Note that usleep is obsolete, but it offers more granularity than
 * sleep and is simpler to use than nanosleep; `man usleep` for more.
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

// constants
#define DIM_MIN 3
#define DIM_MAX 9

// board
int board[DIM_MAX][DIM_MAX];

// dimensions
int d;

// prototypes
void clear(void);
void greet(void);
void init(void);
void draw(void);
int move(int tile);
int won(void);

int main(int argc, char* argv[])
{
    // ensure proper usage
    if (argc != 2)
    {
        printf("Usage: fifteen d\n");
        return 1;
    }

    // ensure valid dimensions
    d = atoi(argv[1]);
    if (d < DIM_MIN || d > DIM_MAX)
    {
        printf("Board must be between %i x %i and %i x %i, inclusive.\n",
            DIM_MIN, DIM_MIN, DIM_MAX, DIM_MAX);
        return 2;
    }

    // open log
    FILE* file = fopen("log.txt", "w");
    if (file == NULL)
    {
        return 3;
    }

    // greet user with instructions
    greet();

    // initialize the board
    init();

    // accept moves until game is won
    while (1)
    {
        // clear the screen
        clear();

        // draw the current state of the board
        draw();

        // log the current state of the board (for testing)
        for (int i = 0; i < d; i++)
        {
            for (int j = 0; j < d; j++)
            {
                fprintf(file, "%i", board[i][j]);
                if (j < d - 1)
                {
                    fprintf(file, "|");
                }
            }
            fprintf(file, "\n");
        }
        fflush(file);

        // check for win
        if (won())
        {
            printf("win!\n");
            break;
        }

        // prompt for move
        printf("Tile to move (0 to exit): ");
        int tile;
		scanf("%d", &tile);
		getchar();

        // quit if user inputs 0 (for testing)
        if (tile == 0)
        {
            break;
        }

        // log move (for testing)
        fprintf(file, "%i\n", tile);
        fflush(file);

        // move if possible, else report illegality
        if (!move(tile))
        {
            printf("\nIllegal move.\n");
            usleep(50000);
        }

        // sleep thread for animation's sake
        usleep(50000);
    }

    // close log
    fclose(file);

    // success
    return 0;
}

/**
 * Clears screen using ANSI escape sequences.
 */
void clear(void)
{
    printf("\033[2J");
    printf("\033[%d;%dH", 0, 0);
}

/**
 * Greets player.
 */
void greet(void)
{
    clear();
    printf("WELCOME TO GAME OF FIFTEEN\n");
    usleep(200000);
}

/**
 * Initializes the game's board with tiles numbered 1 through d*d - 1
 * (i.e., fills 2D array with values but does not actually print them).
 */
void init(void)
{
  int tileValue = d * d - 1;

    for (int i = 0; i < d; i++)
    {
        for (int j = 0; j < d; j++)
        {
            board[i][j] = tileValue;
            tileValue--;
        }
    }

    // If the number of tiles is odd, swap the last two tiles to ensure solvability.
    if (d % 2 == 0)
    {
        int temp = board[d - 1][d - 2];
        board[d - 1][d - 2] = board[d - 1][d - 3];
        board[d - 1][d - 3] = temp;
    }
}

/**
 * Prints the board in its current state.
 */
void draw(void)
{
  for (int i = 0; i < d; i++)
    {
        for (int j = 0; j < d; j++)
        {
            if (board[i][j] == 0)
            {
                // Print an empty space for the empty tile.
                printf("  ");
            }
            else
            {
                // Print the tile's value with proper formatting.
                printf("%2d", board[i][j]);
            }

            // Print a separator between tiles.
            if (j < d - 1)
            {
                printf(" | ");
            }
        }
        // Move to the next row.
        printf("\n");
        // Print a horizontal separator between rows.
        if (i < d - 1)
        {
            for (int j = 0; j < d; j++)
            {
                printf("----");
                if (j < d - 1)
                {
                    printf("+");
                }
            }
            printf("\n");
        }
    }
}

/**
 * If tile borders empty space, moves tile and returns 1 (true), else
 * returns 0 (false).
 * # int tile
 */
int move(int tile)
{
  // Find the current position of the selected tile.
    int tileRow, tileCol;
    for (int i = 0; i < d; i++)
    {
        for (int j = 0; j < d; j++)
        {
            if (board[i][j] == tile)
            {
                tileRow = i;
                tileCol = j;
                break;
            }
        }
    }

    // Check if the selected tile is adjacent to the empty space.

    // Check above.
    if (tileRow > 0 && board[tileRow - 1][tileCol] == 0)
    {
        // Swap the selected tile and the empty space.
        board[tileRow][tileCol] = 0;
        board[tileRow - 1][tileCol] = tile;
        return 1; // Move successful.
    }

    // Check below.
    if (tileRow < d - 1 && board[tileRow + 1][tileCol] == 0)
    {
        // Swap the selected tile and the empty space.
        board[tileRow][tileCol] = 0;
        board[tileRow + 1][tileCol] = tile;
        return 1; // Move successful.
    }

    // Check left.
    if (tileCol > 0 && board[tileRow][tileCol - 1] == 0)
    {
        // Swap the selected tile and the empty space.
        board[tileRow][tileCol] = 0;
        board[tileRow][tileCol - 1] = tile;
        return 1; // Move successful.
    }

    // Check right.
    if (tileCol < d - 1 && board[tileRow][tileCol + 1] == 0)
    {
        // Swap the selected tile and the empty space.
        board[tileRow][tileCol] = 0;
        board[tileRow][tileCol + 1] = tile;
        return 1; // Move successful.
    }
    // If none of the above conditions were met, the move is illegal.
    return 0;
}


/**
 * Returns true if game is won (i.e., board is in winning configuration),
 * else false.
 */
int won(void)
{
    int expectedValue = 1;

    for (int i = 0; i < d; i++)
    {
        for (int j = 0; j < d; j++)
        {
            if (board[i][j] != expectedValue)
            {
                // The current tile does not have the expected value.
                // Check if it's the last tile and it's empty (0).
                if (i == d - 1 && j == d - 1 && board[i][j] == 0)
                {
                    return 1; // Game won.
                }
                else
                {
                    return 0; // Game not won.
                }
            }
            expectedValue++;
        }
    }

    // If we reach this point, the board is in the winning configuration.
    return 1; // Game won
}
