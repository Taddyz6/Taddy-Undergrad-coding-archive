# hw02 From Exceptional to Enhanced Cat

![Approved for: Spring 2022](https://img.shields.io/badge/Approved%20for-Spring%202022-purple)

This homework assignment is designed to familiarize students with exceptions and file I/O in Java.

## Prerequisite Knowledge

* Basic knowledge of Java exceptions, including checked exceptions, unchecked exceptions, and
  the use of `try`-`catch`, `throw`, and `throws`.
* Familiarity with program command-line arguments in Java.

## Course-Specific Learning Outcomes

* **LO2.b:** Define, throw, and propagate exceptions appropriately in a software solution.

## Questions

In your notes, clearly answer the following questions. These instructions assume that you are 
logged into the Odin server. 

**NOTE:** For each step, please provide in your notes the full command that you typed to make the related 
action happen along with an explanation of why that command worked. Some commands require multiple options. 
It is important to not only recall what you typed but also why you typed each of them. If context is necessary 
(e.g., the command depends on your present working directory), then please note that context as well.
You won't need to submit your notes in your final submission. However, if done properly, your exercise notes 
will serve as a helpful study guide for the exam.

## Exercise Steps

### Checkpoint 1 Steps

1. Use Git to clone the repository for this exercise onto Odin into a subdirectory called `cs1302-hw02`:

   ```
   $ git clone --depth 1 https://github.com/cs1302uga/cs1302-hw02.git
   ```

1. Change into the `cs1302-hw02` directory that was just created and look around. There should be
   multiple Java files somewhere in the directory structure. You may want to execute the `find` command
   on the `src` directory for a quick, easy-to-read view of the directory contents.

   * What are the fully qualified names for the classes contained in the Java files?
   * What is the path to the default package for _source code_ relative to the `cs1302-hw02`
     directory?

1. The directory you downloaded contains a Java implementation of the Unix `cat` utility. Remember, the commands
   you have been executing in Unix are just programs that were installed by the system administrators. `MyCat.java`
   works similarly to `cat` but was written by your instructors and will be compiled by you. Before compiling, use 
   the Unix `cat` utility to print the contents of `MyCat.java` to the terminal. Write the command you used to do 
   this in your notes.

1. Read through the Java code in `MyCat.java` and `Printer.java`. Note, there is a dependency between the two files.
   Based on the dependencies, which `.java` file must be compiled first?

1. From the `cs1302-hw02` directory, try to compile each Java file separately, specifying `bin`
   as the default package for _compiled code_. 

   **Note** In this step, you will encounter a compile-time (syntax) error related to exceptions and exception handling. 
   **Hint**: the error should not be a "cannot find symbol" error. If it is, you will need to adjust your compilation command.
   
   Answer the following in your notes about the compile-time error:

   * In what file is the error?
   * On what line in the source code is the error?
   * What specifically is causing this error?
   
   Fix that specific compile-time error and recompile the code. If you notice any logical errors in the code, don't worry about 
   fixing them at this time.

1. Execute the `find src` command from directly within your `cs1302-hw02` directory. You should see the following output:
   
   ```
   src
   src/cs1302
   src/cs1302/exceptions
   src/cs1302/exceptions/MyCat.java
   src/cs1302/exceptions/Printer.java
   ```
   
1. Execute the `find bin` command from directly within your `cs1302-hw02` directory. If everything was compiled properly,
   you should see the following output:
   
   ```
   bin
   bin/cs1302
   bin/cs1302/exceptions
   bin/cs1302/exceptions/Printer.class
   bin/cs1302/exceptions/MyCat.class
   ```
   
<hr/>

![CP](https://img.shields.io/badge/Just%20Finished%20Checkpoint-1-success?style=for-the-badge)

<hr/>

### Checkpoint 2 Steps - Using and Enhancing Your Cat

1. Answer the following questions about `MyCat.java` in your notes:

   * What is the name of the variable that stores the first command-line argument?
   
   * What method is called if you run with `MyCat` with a single `-` as the only command-line argument?
      * Test it out! execute the code from your `cs1302-hw02` directory using the following command:
        ```console
        $ java -cp bin cs1302.exceptions.MyCat -
        ```
      * Notice that the program is blocked waiting for you to type. Go ahead and type a few words.
      * When you're finished, you can trigger the end of file (a.k.a. the `EOF`) by pressing `C-d`.
      * Try running the unix `cat` command with a single `-`. Notice how it behaves the same way.
   
   * What method is called if you pass in the name of a regular file?
      * Test it out! execute the code from your `cs1302-hw02` directory by passing in the relative path
        to the `Printer.java` file using the following command:
        ```console
        $ java -cp bin cs1302.exceptions.MyCat src/cs1302/exceptions/Printer.java
        ```
      * Test the Unix `cat utility with the same input. Cool, huh?
      
1. Interesting Side Note: Take a close look at the following command:

   ```console
   $ java -cp bin cs1302.exceptions.MyCat - < src/cs1302/exceptions/Printer.java
   ```
   
   Notice that this execution has a single command-line argument (`-`). However, we are using redirecting the
   input to come from `Printer.java`! So, the program will output the contents of the file even though the
   internal `Scanner` object will be created with `System.in`!

1. Take a few moments to understand the three methods in `Printer.java`. Note that both `printStdInLines` and
   `printFileLines` both call `printLines`. The difference is in how the `Scanner` object is created. Although
   the `Scanner` objects are created differently, the tasks of printing lines of a file and printing lines from
   standard input are nearly identical!
   
   Also notice how moving the methods to print lines into the `Printer` class greatly simplified the logic in our `MyCat`
   program. As of now, `MyCat` doesn't even need to directly contain any loops!
   
1. From the `cs1302-hw02` directory, run the `MyCat` program with **no** command-line arguments. A run-time
   exception should occur. Before attempting to fix the exception, answer the following questions about the 
   exception in your notes:
   
   * What is the name of the exception?
   * Why did the exception occur?
   * Is this exception a checked or an unchecked exception? How can you tell?

1. There are multiple ways to fix the run-time exception that you encountered in the last step. You
   are allowed to change the code in `MyCat` by moving lines around, deleting existing code, adding code, 
   etc. Just make sure you fix the problem in such a way that the following criteria are met whenever 
   the exception occurs:
   
   * The program does not crash.
   * The exception message is displayed to standard error (using `System.err.println` instead of `System.out.println`). 
     To print the exception message, you can call the `toString()` method on the exception object reference given in 
     the `catch` statement.

   When displaying the exception message, something like the following will suffice 
   (replacing `<message>` with the actual exception message generated from calling `toString` on the exception object):

   ```
   MyCat: <message>
   ```

1. From the `cs1302-hw02` directory, run the `MyCat` program with no command-line arguments. If implemented properly,
   you should see the output below if no command-line arguments are given:
   
   ```
   MyCat: java.lang.ArrayIndexOutOfBoundsException: Index 0 out of bounds for length 0
   ```
   
   What's the difference between this execution of the program and the one performed two steps earlier? Take a minute
   to think about why catching the exception is beneficial before moving on.
   
   **Answer:** When you execute this code and you see the new error message, the code is not crashing. In fact, it has
   _recovered_ from the crash. If you were to add additional code below the `try/catch` block, you would see that
   code execute after the error message is printed. Before we added our `try/catch` block, the code crashed when
   no command-line argument was given. So, despite the fact that the error message looks similar in both cases, the
   result is actually quite different.
   
<hr/>

![CP](https://img.shields.io/badge/Just%20Finished%20Checkpoint-2-success?style=for-the-badge)

<hr/>

### Checkpoint 3 Steps - Further Enhancing Your Cat

1. Now, let's add some more functionality to the `MyCat` program. Change the code so that one or more
   command-line arguments are accepted. The expected behavor is that `MyCat` should print the files, in
   order, to standard output, effectively con<b>cat</b>enating the contents of the supplied files.
   
1. With this change, your program may no longer generate an `ArrayIndexOutOfBoundsException` if the user
   doesn't provide any command-line arguments. However, we still want to provide a helpful message to let
   the user know how to properly use the program. Update your code so that when the user provides no 
   command-line arguments, the program outputs: `Usage: MyCat [filename]...`

1. From the `cs1302-hw02` directory, use your enhanced `MyCat` program to display the contents of the 
   following three files all passed in at once:`Printer.java`, standard input ("-"), and `MyCat.java` 
   in that order! If your program does not currently allow "-" to be specified for arbitrary file names 
   in the list of command-line arguments, then modify it to accomodate that feature.

1. Run your enhanced `MyCat` program by passing in two filenames as command-line arguments. Make sure
   the first file does not exist in the file system. Your program should catch the `FileNotFoundException`,
   print the appropriate message, and still print the contents of the second file (assuming it exists).

1. Update the comments in the source code to reflect any functionality that has been added since
   the beginning of this exercise.

1. Try additional test cases to test the robustness of your application. If you come up with a good idea for
   a test case, feel free to share it on Piazza!
   
1. Verify that all of your code passes the `checkstyle` audit using the command `check1302 src`. Note: if you 
   receive any error messages as a result of running this command, you can find more information about the error
   and how to fix it in the [1302 Style Guide](https://github.com/cs1302uga/cs1302-styleguide).
   
1. Generate the API documentation website for all of the code in the cs1302 package. Host the documentation 
   on Odin using `cs1302-hw02-doc` as the name for your symbolic link.

<hr/>

![CP](https://img.shields.io/badge/Just%20Finished%20Checkpoint-3-success?style=for-the-badge)

<hr/>

### Submission Steps

**Each student needs to individually submit their own work.**

1. Create a plain text file called `SUBMISSION.md` directly inside the `cs1302-hw02`
   directory with the following information:

   1. Your name and UGA ID number; and
   1. Full URL for your hosted API website 
   
   Here is an example of the contents of `SUBMISSION.md`.
   
   ```
   Sally Smith (811-000-999)
   https://webwork.cs.uga.edu/~your_username/cs1302-hw02-doc/
   ```

1. Change directories to the parent of `cs1302-hw02` (e.g., `cd ..` from `cs1302-hw02`). If you would like
   to make a backup tar file, the instructions are in the submissions steps for [hw01](https://github.com/cs1302uga/cs1302-hw01).
   We won't repeat those steps here and you can view them as optional.
   
1. Use the `submit` command to submit this exercise to `csci-1302`:
   
   ```
   $ submit cs1302-hw02 csci-1302
   ```
   
   Read the output of the submit command very carefully. If there is an error while submitting, then it will displayed 
   in that output. Additionally, if successful, the submit command creates a new receipt file in the directory you 
   submitted. The receipt file begins with rec and contains a detailed list of all files that were successfully submitted. 
   Look through the contents of the rec file and always remember to keep that file in case there is an issue with your submission.

   **Note:** You must be on Odin to submit.

<hr/>

![CP](https://img.shields.io/badge/Just%20Finished-Submission-success?style=for-the-badge)

<hr/>

[![License: CC BY-NC-ND 4.0](https://img.shields.io/badge/License-CC%20BY--NC--ND%204.0-lightgrey.svg)](http://creativecommons.org/licenses/by-nc-nd/4.0/) [![License: CC BY-NC 4.0](https://img.shields.io/badge/Instructor%20License-CC%20BY--NC%204.0-lightgrey.svg)](http://creativecommons.org/licenses/by-nc/4.0/)

<small>
Copyright &copy; Michael E. Cotterell, Bradley J. Barnes, and the University of Georgia.
This work is licensed under 
a <a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/">Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License</a> to students and the public and licensed under
a <a rel="license" href="http://creativecommons.org/licenses/by-nc/4.0/">Creative Commons Attribution-NonCommercial 4.0 International License</a> to instructors at institutions of higher education.
The content and opinions expressed on this Web page do not necessarily reflect the views of nor are they endorsed by the University of Georgia or the University System of Georgia.
</small>
