#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>

#define BUFFSIZE 4096

/* Retrieve the hostname and make sure that this program is not being run on the main odin server.
 * It must be run on one of the vcf cluster nodes (vcf0 - vcf3).
 */
void check()
{
        char hostname[10];
        gethostname(hostname, 9);
        hostname[9] = '\0';
        if (strcmp(hostname, "csci-odin") == 0) {
                fprintf(stderr, "WARNING: TO MINIMIZE THE RISK OF FORK BOMBING THE ODIN SERVER,\nYOU MUST RUN THIS PROGRAM ON ONE OF THE VCF CLUSTER NODES!!!\n");
                exit(EXIT_FAILURE);
        } // if
} // check

int main()
{
	check();
	setbuf(stdout, NULL); // makes printf() unbuffered
	int n;
	char cmd[BUFFSIZE];

	// Project 3 TODO: set the current working directory to the user home directory upon initial launch of the shell
	// You may use getenv("HOME") to retrive the user home directory
	chdir(getenv("HOME"));



	// inifite loop that repeated prompts the user to enter a command
	while (1) {
		printf("1730sh:");
		// Project 3 TODO: display the current working directory as part of the prompt
		char cwd[BUFFSIZE];
		getcwd(cwd, BUFFSIZE);
		printf("1730sh:%s$", cwd);
		printf("$ ");
		n = read(STDIN_FILENO, cmd, BUFFSIZE);

		// if user enters a non-empty command
		if (n > 1) {
			cmd[n-1] = '\0'; // replaces the final '\n' character with '\0' to make a proper C string


			// Lab 06 TODO: parse/tokenize cmd by space to prepare the
			// command line argument array that is required by execvp().
			// For example, if cmd is "head -n 1 file.txt", then the
			// command line argument array needs to be
			// ["head", "-n", "1", "file.txt", NULL].
			char *args[BUFFSIZE];
			char *token = strtok(cmd, " ");
			int i = 0;
			while (token != NULL) {
				args[i++] = token;
				token = strtok(NULL, " ");
				}
				args[i] = NULL;


			// Lab 07 TODO: if the command contains input/output direction operators
			// such as "head -n 1 < input.txt > output.txt", then the command
			// line argument array required by execvp() needs to be
			// ["head", "-n", "1", NULL], while the "< input.txt > output.txt" portion
			// needs to be parsed properly to be used with dup2(2) inside the child process
			int input_fd, output_fd;

			for (int i = 0; args[i] != NULL; i++) {
    			// Check for input redirection ("<")
    			if (strcmp(args[i], "<") == 0) {
        			args[i] = NULL; // Terminate the command here
        			if (args[i + 1] != NULL) {
            			input_fd = open(args[i + 1], O_RDONLY);
            			if (input_fd == -1) {
                			perror("open");
                			exit(EXIT_FAILURE);
            			}
            			dup2(input_fd, STDIN_FILENO);
            			close(input_fd);
        			}
    			}

    			// Check for output redirection (">")
    			if (strcmp(args[i], ">") == 0) {
        			args[i] = NULL; // Terminate the command here
        			if (args[i + 1] != NULL) {
            			output_fd = open(args[i + 1], O_WRONLY | O_CREAT | O_TRUNC, 0666);
            			if (output_fd == -1) {
                			perror("open");
                			exit(EXIT_FAILURE);
            			}
            			dup2(output_fd, STDOUT_FILENO);
            			close(output_fd);
        			}
    			}
			}


			// Lab 06 TODO: if the command is "exit", quit the program
			 if (strcmp(args[0], "exit") == 0) {
    			exit(EXIT_SUCCESS);
			 }


			// Project 3 TODO: else if the command is "cd", then use chdir(2) to
			// to support change directory functionalities
			else if (strcmp(args[0], "cd") == 0) {
				if (args[1] != NULL) {
					if (chdir(args[1]) != 0) {
						perror("chdir");
						}
				}
			}


			// Lab 06 TODO: for all other commands, fork a child process and let
			// the child process execute user-specified command with its options/arguments.
			// NOTE: fork() only needs to be called once. DO NOT CALL fork() more than one time.
			pid_t pid = fork();

			if (pid == -1) {
   				perror("fork");
    			exit(EXIT_FAILURE);
			}

			if (pid == 0) {
    			// This code runs in the child process

				// Lab 07 TODO: inside the child process, use dup2(2) to redirect
				// standard input and output as specified by the user command

				// Check for input redirection ("<")
				int input_fd = -1;
				for (int i = 0; args[i] != NULL; i++) {
    			if (strcmp(args[i], "<") == 0) {
        			if (args[i + 1] != NULL) {
            			input_fd = open(args[i + 1], O_RDONLY);
            			if (input_fd == -1) {
                			perror("open");
                			exit(EXIT_FAILURE);
            			}
            			dup2(input_fd, STDIN_FILENO);
            			close(input_fd);
        			}
        			args[i] = NULL; // Remove "<" from the args array
       				break;
    				}
				}

				// Check for output redirection (">")
				int output_fd = -1;
				for (int i = 0; args[i] != NULL; i++) {
    				if (strcmp(args[i], ">") == 0) {
        				if (args[i + 1] != NULL) {
            				output_fd = open(args[i + 1], O_WRONLY | O_CREAT | O_TRUNC, 0666);
            				if (output_fd == -1) {
                				perror("open");
                				exit(EXIT_FAILURE);
            				}
            				dup2(output_fd, STDOUT_FILENO);
            				close(output_fd);
        				}
        				args[i] = NULL; // Remove ">" from the args array
        				break;
    				}
				}


				// Lab 06 TODO: inside the child process, invoke execvp().
				// if execvp() returns -1, be sure to use exit(EXIT_FAILURE);
				// to terminate the child process
				if (execvp(args[0], args) == -1) {
        			perror("execvp");
        			exit(EXIT_FAILURE);
    			}
				} else {
				// Lab 06 TODO: inside the parent process, wait for the child process
				// You are not required to do anything special with the child's
				// termination status
				wait(NULL);
				}

		} // if
	} // while

} // main
