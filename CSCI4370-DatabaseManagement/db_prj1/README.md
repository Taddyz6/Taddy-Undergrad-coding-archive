# db_prj1
Database Management Project 1\
We are going to implement the following relational algebra operator: Join, Select, Project, Union and Minus by using Java.

### Group Members: 

1. Shahrima Ishrat (javaDoc, Minus)
2. Youran Roh (join, typecheck, readme)
3. Junhao Zhang (project, union)

### How to compile 
1. Download the zip file
2. Open the file directory with the terminal
3. Use the following commands to create the class: `javac -d bin *.java`
4. then enter: `java -cp bin MovieDB` to compile.

### To open the javadoc
click on `javadoc/Table.html`

### Side note
on line `186`, the line `HashSet keyNames = new HashSet(Arrays.asList(key));` gave the following note:`Note: Table.java uses unchecked or unsafe operations.` hence changed it to be `HashSet<String> keyNames = new HashSet<>(Arrays.asList(key));`.
