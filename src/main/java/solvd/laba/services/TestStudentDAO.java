package solvd.laba.services;

import solvd.laba.connections.ConnectionPool;
import solvd.laba.tableclasses.Student;
import solvd.laba.dao.StudentDAO;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class TestStudentDAO {
    public static void execute() {
        //Credentials moved to /src/java/resources/mysqlconnection.credentials

        try(ConnectionPool connPool = new ConnectionPool(5)){
            StudentDAO studentOperations = new StudentDAO(connPool);

            // Delete previous iteration's reemplazo
            System.out.println("Deleting student 6 from previous interaction\n");
            studentOperations.delete(6);


            // Create a new student, then insert it into DB
            Student reemplazo = new Student(6, "Alan", "Wake",
                    Date.valueOf(LocalDate.of(1998, 5, 12)),
                    "001", "alan@gmail.com", 101);
            studentOperations.insert(reemplazo);

            // Retrieve the inserted Student from DB
            System.out.println("Estudiante 6, retrieved from DB:");
            System.out.println(studentOperations.read(6).toString());


            // Update the student in DB, then retrieve it.
            System.out.println("Updating the student local to this scope, then retrieving it\n");
            reemplazo.surname = "Marcus";
            reemplazo.email = "AlanMarcus@gmail.com";
            studentOperations.update(reemplazo);
            System.out.println(studentOperations.read(6).toString());

            System.out.println("Retrieving ALL students:\n");
            ArrayList<Student> retrieved = (ArrayList<Student>) studentOperations.findAll();

            for(Student individual:retrieved){
                System.out.println(individual);
            }

        }
        catch(SQLException exc){
            System.out.println("Error SQL");
        }
        catch(IOException exc){
            System.out.println("Error getting the credentials from file. Aborted.");
        }
    }
}