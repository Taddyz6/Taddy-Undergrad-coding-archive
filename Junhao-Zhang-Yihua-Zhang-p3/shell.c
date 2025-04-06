#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>

#define BUFFSIZE 4096

// Function to check if the program is being run on the main odin server
void check()
{
    char hostname[10];
    gethostname(hostname, 9);
    hostname[9] = '\0';
    if (strcmp(hostname, "csci-odin") == 0)
    {
        fprintf(stderr, "WARNING: TO MINIMIZE THE RISK OF FORK BOMBING THE ODIN SERVER,\nYOU MUST RUN THIS PROGRAM ON ONE OF THE VCF CLUSTER NODES!!!\n");
        exit(EXIT_FAILURE);
    }
}

int main()
{
    check();
    setbuf(stdout, NULL);

    int n;
    char cmd[BUFFSIZE];

    // Get the user's home directory
    const char *home = getenv("HOME");
    if (home == NULL)
    {
        fprintf(stderr, "Error: HOME environment variable not set.\n");
        exit(EXIT_FAILURE);
    }

    // Set the current working directory to the user's home directory upon initial launch
    if (chdir(home) != 0)
    {
        perror("chdir");
        exit(EXIT_FAILURE);
    }

    while (1)
    {
        char *args[BUFFSIZE];
        char cwd[BUFFSIZE];

        // Display the current working directory as part of the prompt
        if (getcwd(cwd, sizeof(cwd)) != NULL)
        {
            if (strstr(cwd, home) == cwd)
            {
                printf("1730sh:~%s$ ", cwd + strlen(home));
            }
            else
            {
                printf("1730sh:%s$ ", cwd);
            }
        }
        else
        {
            perror("getcwd");
            exit(EXIT_FAILURE);
        }

        // Read user input
        n = read(STDIN_FILENO, cmd, BUFFSIZE);

        if (n > 1)
        {
            cmd[n - 1] = '\0';

            // Tokenize the input command
            char *token = strtok(cmd, " ");
            int i = 0;
            while (token != NULL)
            {
                args[i++] = token;
                token = strtok(NULL, " ");
            }
            args[i] = NULL;

            // Check for special commands
            if (strcmp(args[0], "exit") == 0)
            {
                exit(EXIT_SUCCESS);
            }
            else if (strcmp(args[0], "cd") == 0)
            {
                // Change the current working directory
                if (args[1] != NULL)
                {
                    if (chdir(args[1]) != 0)
                    {
                        perror("cd");
                    }
                }
                continue;
            }

            pid_t pid = fork();

            if (pid == -1)
            {
                perror("fork");
                exit(EXIT_FAILURE);
            }
            else if (pid == 0)
            {
                // Child process

                // Check for input/output redirection
                for (int j = 0; args[j] != NULL; j++)
                {
                    if (strcmp(args[j], "<") == 0)
                    {
                        // Redirect standard input
                        int fd = open(args[j + 1], O_RDONLY);
                        if (fd == -1)
                        {
                            perror("open");
                            exit(EXIT_FAILURE);
                        }
                        dup2(fd, STDIN_FILENO);
                        close(fd);
                        args[j] = NULL;
                    }
                    else if (strcmp(args[j], ">") == 0)
                    {
                        // Redirect standard output (truncate)
                        int fd = open(args[j + 1], O_WRONLY | O_CREAT | O_TRUNC, 0666);
                        if (fd == -1)
                        {
                            perror("open");
                            exit(EXIT_FAILURE);
                        }
                        dup2(fd, STDOUT_FILENO);
                        close(fd);
                        args[j] = NULL;
                    }
                    else if (strcmp(args[j], ">>") == 0)
                    {
                        // Redirect standard output (append)
                        int fd = open(args[j + 1], O_WRONLY | O_CREAT | O_APPEND, 0666);
                        if (fd == -1)
                        {
                            perror("open");
                            exit(EXIT_FAILURE);
                        }
                        dup2(fd, STDOUT_FILENO);
                        close(fd);
                        args[j] = NULL;
                    }
                }

                // Execute the command using execvp
                if (execvp(args[0], args) == -1)
                {
                    perror("execvp");
                    exit(EXIT_FAILURE);
                }
            }
            else
            {
                // Parent process
                waitpid(pid, NULL, 0);
            }
        }
    }

    return 0;
} // main
