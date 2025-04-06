 
/*****************************************************************************************
 * @file  TestTupleGenerator.java
 *
 * @author   Sadiq Charaniya, John Miller
 */

import static java.lang.System.out;

import java.util.Scanner;

/*****************************************************************************************
 * This class tests the TupleGenerator on the Student Registration Database defined in the
 * Kifer, Bernstein and Lewis 2006 database textbook (see figure 3.6).  The primary keys
 * (see figure 3.6) and foreign keys (see example 3.2.2) are as given in the textbook.
 */
public class TestTupleGenerator
{
    /*************************************************************************************
     * The main method is the driver for TestGenerator.
     * @param args  the command-line arguments
     */
    public static void main (String [] args)
    {
        var test = new TupleGeneratorImpl ();

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
                           "crsCode semester",
                           new String [][] {{ "profId", "Professor", "id" },
                                            { "crsCode", "Course", "crsCode" }});
        
        test.addRelSchema ("Transcript",
                           "studId crsCode semester grade",
                           "Integer String String String",
                           "studId crsCode semester",
                           new String [][] {{ "studId", "Student", "id"},
                                            { "crsCode", "Course", "crsCode" },
                                            { "crsCode semester", "Teaching", "crsCode semester" }});
        
        var tables = new String [] { "Student", "Professor", "Course", "Teaching", "Transcript" };
        var tups   = new int [] { 10000, 1000, 2000, 50000, 5000 };
    
        var resultTest = test.generate (tups);

        // Defines table, then insert generated tuples into the tables
        Table student = new Table("Student",
        "id name address status",
        "Integer String String String",
        "id");

        Table professor = new Table("Professor",
        "id name deptId",
        "Integer String String",
        "id");
        
        Table course = new Table("Course",
        "crsCode deptId crsName descr",
        "String String String String",
        "crsCode");

        Table teaching = new Table("Teaching",
        "crsCode semester profId",
        "String String Integer",
        "crsCode semester");
                
        Table transcript = new Table("Transcript",
        "studId crsCode semester grade",
        "Integer String String String",
        "studId crsCode semester");        

        for(var tuple : resultTest[0]){
            student.insert(tuple);
        }
        student.save();
        
        for(var tuple : resultTest[0]){
            student.insert(tuple);
        }
        student.save();

        for(var tuple : resultTest[1]){                    
            professor.insert(tuple);
        }
        professor.save();

        for(var tuple : resultTest[2]){                    
            course.insert(tuple);
        }
        course.save();

        for(var tuple : resultTest[3]){                    
            teaching.insert(tuple);
        }
        teaching.save();
        
        for(var tuple : resultTest[4]){                    
            transcript.insert(tuple);
        }
        transcript.save();
    } // main

} // TestTupleGenerator

