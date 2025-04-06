import java.util.Scanner;

public class BenchmarkTests {

    public static void main(String[] args) {
        // Use TupleGeneratorImpl instead of TestTupleGenerator
        TupleGeneratorImpl test = new TupleGeneratorImpl();

        // Add schemas and generate data

        test.addRelSchema ("Student",
                           "id name address status",
                           "Integer String String String",
                           "id",
                           null);
        
        test.addRelSchema ("Professor",
                           "id name deptId",
                           "Integer String String",
                           "id",
                           null);
        
        test.addRelSchema ("Course",
                           "crsCode deptId crsName descr",
                           "String String String String",
                           "crsCode",
                           null);
        
        test.addRelSchema ("Teaching",
                           "crsCode semester profId",
                           "String String Integer",
                           "crcCode semester",
                           new String [][] {{ "profId", "Professor", "id" },
                                            { "crsCode", "Course", "crsCode" }});
        
        test.addRelSchema ("Transcript",
                           "studId crsCode semester grade",
                           "Integer String String String",
                           "studId crsCode semester",
                           new String [][] {{ "studId", "Student", "id"},
                                            { "crsCode", "Course", "crsCode" },
                                            { "crsCode semester", "Teaching", "crsCode semester" }});

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of tuples for Student and Transcript: ");
        int tupSize = scanner.nextInt();
        var tups   = new int [] { tupSize, 1000, 2000, 50000, tupSize};
        var resultTest =  test.generate(tups);

        // Initialize tables with different indexing techniques
        Table tableTreeMap = new Table("Student", new String[]{"id", "name", "address", "status"}, new Class[]{Integer.class, String.class, String.class, String.class}, new String[]{"id"}, "TreeMap");
        Table tableHashMap = new Table("Student", new String[]{"id", "name", "address", "status"}, new Class[]{Integer.class, String.class, String.class, String.class}, new String[]{"id"}, "HashMap");
        Table tableLinHashMap = new Table("Student", new String[]{"id", "name", "address", "status"}, new Class[]{Integer.class, String.class, String.class, String.class}, new String[]{"id"}, "LinHashMap");
        Table noIndexTable = new Table("Student", new String[]{"id", "name", "address", "status"}, new Class[]{Integer.class, String.class, String.class, String.class}, new String[]{"id"}, "NoIndex");

        // For join operations, create a second table
        Table transcriptTable = new Table("Transcript", new String[]{"studId", "crsCode", "semester", "grade"}, new Class[]{Integer.class, String.class, String.class, String.class}, new String[]{"studId"}, "TreeMap");

        for (Comparable[] tuple : resultTest[0]) {
            noIndexTable.insert(tuple);
        }

        for (Comparable[] tuple : resultTest[4]) {
            transcriptTable.insert(tuple);
        }
        
        // Insert tuples into each table
        for (Comparable[] tuple : resultTest[0]) {
            tableTreeMap.insert(tuple);
            tableHashMap.insert(tuple);
            tableLinHashMap.insert(tuple);
        }

        // Comparable[][] transcriptTuples = generator.generate(new int[]{1000})[0];  // Generate 1000 tuples for the Student table

        for(Comparable[] tuple: resultTest[4])
        {
            transcriptTable.insert(tuple);
        }

        System.out.println();
        System.out.println("Number of tuples for test: " + tupSize);
        System.out.println();
        // Benchmark select operations
        benchmarkNoIndexSelectOperation(noIndexTable, "NoIndex Select Time");
        benchmarkNoIndexJoinOperation(noIndexTable, transcriptTable, "NoIndex Join Time");
        System.out.println();
        benchmarkSelectOperation(tableTreeMap, "TreeMap Select Time");
        benchmarkSelectOperation(tableHashMap, "HashMap Select Time");
        benchmarkSelectOperation(tableLinHashMap, "LinHashMap Select Time");
        System.out.println();

        // Benchmark join operations
        benchmarkJoinOperation(tableTreeMap, transcriptTable, "TreeMap Join Time");
        benchmarkJoinOperation(tableHashMap, transcriptTable, "HashMap Join Time");
        benchmarkJoinOperation(tableLinHashMap, transcriptTable, "LinHashMap Join Time");
    }

    private static void benchmarkSelectOperation(Table table, String description) {
        long start = System.nanoTime();
        table.select(new KeyType(new Comparable[]{10000})); // Example select by primary key
        long elapsed = System.nanoTime() - start;
        System.out.println(description + ": " + elapsed + " ns");
    }

    private static void benchmarkJoinOperation(Table table1, Table table2, String description) {
        long start = System.nanoTime();
        table1.join("id", "studId", table2); // Example join on primary key
        long elapsed = System.nanoTime() - start;
        System.out.println(description + ": " + elapsed + " ns");
    }

    private static void benchmarkNoIndexSelectOperation(Table table, String description) {
        long start = System.nanoTime();
        table.noIndexSelect(new KeyType(new Comparable[]{10000})); // Example select by primary key
        long elapsed = System.nanoTime() - start;
        System.out.println(description + ": " + elapsed + " ns");
    }
    
    private static void benchmarkNoIndexJoinOperation(Table table1, Table table2, String description) {
        long start = System.nanoTime();
        table1.noIndexjoin("id", "studId", table2); // Example join operation
        long elapsed = System.nanoTime() - start;
        System.out.println(description + ": " + elapsed + " ns");
    }
}
