#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

void head(int lines) {
    char buffer[1024];  // Adjust the buffer size as needed
    int line_count = 0;

    while (fgets(buffer, sizeof(buffer), stdin) != NULL && line_count < lines) {
        printf("%s", buffer);
        line_count++;
    }
}

int main(int argc, char *argv[]) {
    int option;
    int lines = 10;  // Default value for -n option

    while ((option = getopt(argc, argv, "n:")) != -1) {
        switch (option) {
            case 'n':
                lines = atoi(optarg);
                break;
            default:
                fprintf(stderr, "Usage: %s [-n number] [files...]\n", argv[0]);
                exit(EXIT_FAILURE);
        }
    }

    if (lines <= 0) {
        fprintf(stderr, "Invalid number of lines.\n");
        exit(EXIT_FAILURE);
    }

    if (optind == argc) {
        // No files specified, read from standard input
        head(lines);
    } else {
        // Process files and display the specified number of lines
        for (int i = optind; i < argc; i++) {
            FILE *file = fopen(argv[i], "r");
            if (file == NULL) {
                perror("Error opening file");
                continue;
            }
            head(lines);
            fclose(file);
        }
    }

    return 0;
}
