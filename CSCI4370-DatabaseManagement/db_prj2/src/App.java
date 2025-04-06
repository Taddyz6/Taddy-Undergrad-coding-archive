
import java.util.Scanner;

/**
 * Launches the user interface in the terminal.
 *
 * @author Junhao Zhang
 */
public class App {
    /**
     * User inputs their root password for the MySQL database, then inserts a number for one of the prompts.
     * Upon entering a numbered prompt, a method from DataQueries class is called. First to fourth prompts
     * automatically outputs the query result, whereas the fifth and sixth prompt asks for another user
     * input before making the query.
     *
     * @author Junhao Zhang
     * @author Youran Roh
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter MySQL root password: ");
        String password = scanner.nextLine();

        DatabaseQueries dbQueries = new DatabaseQueries(password);

        while (true) {
            // Menu options
            System.out.println("Select an option (Enter in a number in the terminal):");
            System.out.println("1. List departments with the maximum ratio of average female salaries to average male salaries.");
            System.out.println("2. List managers who hold office for the longest duration.");
            System.out.println("3. List number of employees born in each decade and their average salaries for each department.");
            System.out.println("4. List female managers born before 1990 with a salary over 80K.");
            System.out.println("5. Find 1 degree of separation between 2 given employees E1 and E");
            System.out.println("6. Find 2 degrees of separation between 2 given employees E1 and E2");
            System.out.println("7. Exit.");

            // User input
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    dbQueries.listDepartmentsWithMaxSalaryRatio();
                    break;
                case 2:
                    dbQueries.listManagersWithLongestDuration();
                    break;
                case 3:
                    dbQueries.listEmployeesByDecadeAndAverageSalary();
                    break;
                case 4:
                    dbQueries.listFemaleManagersBornBefore1990WithSalaryOver80K();
                    break;
                case 5:
                    Scanner nameScanner = new Scanner(System.in);
                    System.out.print("Give employee 1 name: ");
                    String emp1_5 = nameScanner.nextLine();
                    System.out.print("Give employee 2 name: ");
                    String emp2_5 = nameScanner.nextLine();
                    //"Sumant", "Bartek"
                    dbQueries.findOneDegOfSeparationBetweenTwoGivenEmployees(emp1_5, emp2_5);
                    break;
                case 6: 
                    Scanner nameScanner2 = new Scanner(System.in);
                    System.out.print("Give employee 1 name: ");
                    String emp1_6 = nameScanner2.nextLine();
                    System.out.print("Give employee 2 name: ");
                    String emp2_6 = nameScanner2.nextLine();
                    //"Guther", "Anwar"
                    dbQueries.findTwoDegreesOfSeparationBetweenTwoGivenEmployees(emp1_6, emp2_6);
                    break;
                case 7: 
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        } 
    }
}
