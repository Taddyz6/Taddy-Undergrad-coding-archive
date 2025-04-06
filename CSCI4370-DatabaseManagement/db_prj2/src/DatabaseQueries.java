import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Sets the database connection with the database's URL, user, and password.
 * Each method answers a prompt in using a query and outputs a query result.
 *
 * @author Junhao Zhang
 * @author Youran Roh
 */
public class DatabaseQueries {
    // Database credentials
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/employees";
    private static final String USER = "root";
    //private static final String PASS = "Shdbfks1!!!"; // Replace with your MySQL root password
    private String password;

    /**
     * Constructor of DatabaseQueries
     *
     * @author Junhao Zhang
     * @author Youran Roh
     * @param password the user's root password to access the database
     */
    // Constructor that accepts the password
    public DatabaseQueries(String password) {
        this.password = password;
    }

    /**
     * Prints the query result in listing the departments with the maximum ratio of average
     * female salaries to average male salaries.
     *
     * @author Junhao Zhang
     */
    // 1. Method to list departments with the maximum ratio of average female salaries to average male salaries
    public void listDepartmentsWithMaxSalaryRatio() {
        String sql = "SELECT d.dept_name, " +
                     "AVG(CASE WHEN e.gender = 'F' THEN s.salary END) / AVG(CASE WHEN e.gender = 'M' THEN s.salary END) AS ratio " +
                     "FROM departments d " +
                     "JOIN dept_emp de ON d.dept_no = de.dept_no " +
                     "JOIN employees e ON de.emp_no = e.emp_no " +
                     "JOIN salaries s ON e.emp_no = s.emp_no " +
                     "WHERE s.to_date = '9999-01-01' " +
                     "GROUP BY d.dept_name " +
                     "ORDER BY ratio DESC " +
                     "LIMIT 1;";
        executeAndPrintQuery(sql, "Department: %s, Ratio: %.2f");
    } //1

    /**
     * Prints the query result in listing managers who hold office for longest duration.
     *
     * @author Junhao Zhang
     */
    // 2. Method to list managers who hold office for the longest duration
    public void listManagersWithLongestDuration() {
        String sql = "SELECT emp_no, dept_no, MAX(DATEDIFF(to_date, from_date)) AS duration " +
                     "FROM dept_manager " +
                     "GROUP BY emp_no, dept_no " +
                     "ORDER BY duration DESC;";
        executeAndPrintQuery(sql, "Manager ID: %d, Department: %s, Duration: %d days");
    } //2

    /**
     * Prints the query result in listing the number of employees born in each decade and their
     * average salaries for each department.
     *
     * @author Junhao Zhang
     */
    // 3. Method to list the number of employees born in each decade and their average salaries for each department
    public void listEmployeesByDecadeAndAverageSalary() {
        String sql = "SELECT d.dept_name, " +
                     "FLOOR(YEAR(e.birth_date) / 10) * 10 AS birth_decade, " +
                     "COUNT(*) AS employee_count, " +
                     "AVG(s.salary) AS average_salary " +
                     "FROM employees e " +
                     "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                     "JOIN departments d ON de.dept_no = d.dept_no " +
                     "JOIN salaries s ON e.emp_no = s.emp_no " +
                     "GROUP BY d.dept_name, birth_decade " +
                     "ORDER BY d.dept_name, birth_decade;";
        executeAndPrintQuery(sql, "Department: %s, Decade: %d, Count: %d, Average Salary: $%.2f");
    } //3


    /**
     * Prints the query result in listing employees who are female, born before Jan 1, 1990, make more than
     * 80K annually, and hold a manager position.
     *
     * @author Junhao Zhang
     */
    // 4. Method to list employees who are female, born before Jan 1, 1990, make more than 80K annually, and hold a manager position
    public void listFemaleManagersBornBefore1990WithSalaryOver80K() {
        String sql = "SELECT e.emp_no, e.first_name, e.last_name, s.salary " +
                     "FROM employees e " +
                     "JOIN salaries s ON e.emp_no = s.emp_no " +
                     "JOIN dept_manager dm ON e.emp_no = dm.emp_no " +
                     "WHERE e.gender = 'F' " +
                     "AND e.birth_date < '1990-01-01' " +
                     "AND s.salary > 80000;";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, this.password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            // Loop through the result set and print each row
            while (rs.next()) {
                int empNo = rs.getInt("emp_no");            // Employee number (int)
                String firstName = rs.getString("first_name"); // First name (string)
                String lastName = rs.getString("last_name");   // Last name (string)
                double salary = rs.getDouble("salary");     // Salary (double)
    
                // Print the result using the specified output format
                System.out.printf("Employee ID: %d, Name: %s %s, Salary: $%.2f%n", empNo, firstName, lastName, salary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } //4


    /**
     * From taking two employee names into the query, prints the query result of the
     * one-degree of separation between the two employees.
     *
     * @author Youran Roh
     * @param employee1Name the name of first employee (E1)
     * @param employee2Name the name of second employee (E2)
     */
    // 5. Find 1 degree of separation between 2 given employees E1 and E2
    public void findOneDegOfSeparationBetweenTwoGivenEmployees(String employee1Name, String employee2Name) {
        String sql = "SELECT e1.emp_no AS emp1, e2.emp_no AS emp2, de1.dept_no " +
                     "FROM employees e1 " +
                     "JOIN dept_emp de1 ON e1.emp_no = de1.emp_no " +
                     "JOIN dept_emp de2 ON de1.dept_no = de2.dept_no " +
                     "JOIN employees e2 ON de2.emp_no = e2.emp_no " +
                     "WHERE e1.first_name = '" + employee1Name + "' " +
                     "AND e2.first_name = '" + employee2Name + "' " +
                     "AND de1.from_date <= de2.to_date " +
                     "AND de2.from_date <= de1.to_date " +
                     "LIMIT 1;";  // Ensure only 1 result is returned
    
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, this.password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            // Check if a result is returned
            if (rs.next()) {
                int emp1 = rs.getInt("emp1");
                int emp2 = rs.getInt("emp2");
                String deptNo = rs.getString("dept_no");
    
                // Print only one combination
                System.out.printf("%d - %s - %d%n", emp1, deptNo, emp2);
            } else {
                System.out.println("No connection found between the given employees.");
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//5

    /**
     * From taking two employee names into the query, prints the query result of the
     * two-degree of separation between the two employees.
     *
     * @author Youran Roh
     * @param employee1Name the name of first employee (E1)
     * @param employee2Name the name of second employee (E2)
     */
    // 6. Find 2 degree of separation between 2 given employees E1 and E2
    public void findTwoDegreesOfSeparationBetweenTwoGivenEmployees(String employee1Name, String employee2Name) {
        String sql = "SELECT e1.emp_no AS emp1, e2.emp_no AS emp2, e3.emp_no AS emp3, de1.dept_no AS dept1, de2.dept_no AS dept2 " +
                     "FROM employees e1 " +
                     "JOIN dept_emp de1 ON e1.emp_no = de1.emp_no " +
                     "JOIN dept_emp de3 ON de1.dept_no = de3.dept_no AND de1.emp_no != de3.emp_no " + // Join intermediate employees in the same department
                     "JOIN employees e3 ON de3.emp_no = e3.emp_no " +
                     "JOIN dept_emp de2 ON e3.emp_no = de2.emp_no AND de3.dept_no = de2.dept_no " + // Ensure same department for e2
                     "JOIN employees e2 ON de2.emp_no = e2.emp_no " +
                     "WHERE e1.first_name = '" + employee1Name + "' " +
                     "AND e2.first_name = '" + employee2Name + "' " +
                     "AND de1.from_date <= de2.to_date " +
                     "AND de2.from_date <= de1.to_date " +
                     "LIMIT 100;";  // Limit to 100 results
    
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, this.password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            // Print the header
            System.out.println("E1 --- D1 --- E3 --- D2 --- E2");
    
            // Fetch up to 100 results
            while (rs.next()) {
                int emp1 = rs.getInt("emp1");
                int emp2 = rs.getInt("emp2");
                int emp3 = rs.getInt("emp3");
                String dept1 = rs.getString("dept1");
                String dept2 = rs.getString("dept2");
    
                // Print the result in the format: E1 --- D1 --- E3 --- D2 --- E2
                System.out.printf("%d\t%s\t%d\t%s\t%d%n", emp1, dept1, emp3, dept2, emp2);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//6


    /**
     * Executes the SQL query to print the results based on given output format.
     *
     * @author Youran Roh
     * @param sql the query as input to MySQL
     * @param outputFormat the format of the query result
     */
    // Helper method to execute the SQL query and print results in formatted way
    private void executeAndPrintQuery(String sql, String outputFormat) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, this.password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            // Loop through the result set and dynamically print based on the column count
            while (rs.next()) {
                int columnCount = rs.getMetaData().getColumnCount();

                Object[] outputValues = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    // Check column type to determine how to fetch the value
                    int columnType = rs.getMetaData().getColumnType(i);
                    switch (columnType) {
                        case java.sql.Types.VARCHAR:
                        case java.sql.Types.CHAR:
                            outputValues[i - 1] = rs.getString(i);  // String value
                            break;
                        case java.sql.Types.INTEGER:
                            outputValues[i - 1] = rs.getInt(i);     // Integer value
                            break;
                        case java.sql.Types.DOUBLE:
                            outputValues[i - 1] = rs.getDouble(i);  // Double value
                            break;
                        default:
                            outputValues[i - 1] = rs.getObject(i);  // Generic Object
                            break;
                    }
                }

                // Print the result using the specified output format
                System.out.printf(outputFormat + "%n", outputValues);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
