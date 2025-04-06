#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

void copyFile(int fd) {
    char buffer[4096];
    ssize_t bytesRead;

    while ((bytesRead = read(fd, buffer, sizeof(buffer))) > 0) {
        if (write(STDOUT_FILENO, buffer, bytesRead) != bytesRead) {
            perror("Write error");
            exit(1);
        }
    }

    if (bytesRead < 0) {
        perror("Read error");
        exit(1);
    }
}

int main(int argc, char *argv[]) {
    if (argc == 1) {
        // If no file is specified, read from standard input.
        copyFile(STDIN_FILENO);
    } else {
        for (int i = 1; i < argc; i++) {
            if (argv[i][0] == '-') {
                // If a filename is "-", read from standard input.
                copyFile(STDIN_FILENO);
            } else {
                // Otherwise, open and read the specified file.
                int fd = open(argv[i], O_RDONLY);
                if (fd == -1) {
                    perror("File open error");
                    exit(1);
                }
                copyFile(fd);
                close(fd);
            }
        }
    }

    return 0;
}
