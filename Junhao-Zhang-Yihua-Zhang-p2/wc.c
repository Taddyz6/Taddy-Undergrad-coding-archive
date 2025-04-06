#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <unistd.h>

void wc(char *filename) {
    FILE *file = fopen(filename, "r");
    if (file == NULL) {
        perror("Error opening file");
        return;
    }

    int lines = 0;
    int words = 0;
    int bytes = 0;
    int in_word = 0;  // Tracks whether we're inside a word

    int c;
    while ((c = fgetc(file)) != EOF) {
        bytes++;
        if (c == '\n') {
            lines++;
        }
        if (isspace(c)) {
            in_word = 0;
        } else if (!in_word) {
            in_word = 1;
            words++;
        }
    }

    fclose(file);
    printf("%d %d %d %s\n", lines, words, bytes, filename);
}

int main(int argc, char *argv[]) {
    int option;
    int count_lines = 0;
    int count_words = 0;
    int count_bytes = 0;

    while ((option = getopt(argc, argv, "clw")) != -1) {
        switch (option) {
            case 'c':
                count_bytes = 1;
                break;
            case 'l':
                count_lines = 1;
                break;
            case 'w':
                count_words = 1;
                break;
            default:
                fprintf(stderr, "Usage: %s [-clw] [files...]\n", argv[0]);
                exit(EXIT_FAILURE);
        }
    }

    if (optind == argc) {
        // No files specified, read from standard input
        wc("-");
    } else {
        // Process files and display counts based on the specified options
        for (int i = optind; i < argc; i++) {
            wc(argv[i]);
        }

        if (argc - optind > 1) {
            // Display total count if more than one file is specified
            printf("%d %d %d total\n", count_lines, count_words, count_bytes);
        }
    }

    return 0;
}
