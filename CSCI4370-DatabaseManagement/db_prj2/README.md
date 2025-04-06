# db_prj2

The list of predefined SQL queries to be implemented in the application are as follows (consider both past and current data when answering all queries):  
1. List department(s) with maximum ratio of average female salaries to average men salaries 
2. List manager(s) who holds office for the longest duration. A person can be a manager for multiple departments at different time frames.
3. For each department, list number of employees born in each decade and their average salaries
4. List employees, who are female, born before Jan 1, 1990, makes more than 80K annually and hold a manager position
5. Find 1 degree of separation between 2 given employees E1 and E2:
	1 degree: E1 --> D1 <-- E2 (E1 and E2 work at department D1 at the same time)
6. Find 2 degrees of separation between 2 given employees E1 and E2:
	2 degrees: E1 --> D1 <-- E3 --> D2 <-- E2 (E1 and E3 work at D1 at the same time; E3 and E2 work at D2 at the same time)

### Group Members: 

1. Shahrima Ishrat
2. Youran Roh
3. Junhao Zhang

### Compile and Run

1. Compile: `javac -cp lib/mysql-connector-j-9.0.0.jar -d bin src/*.java`
2. Run: `java -cp bin:lib/mysql-connector-j-9.0.0.jar App`

### How to access javadoc
1. access the javadoc directory
2. find `index.html` file.



