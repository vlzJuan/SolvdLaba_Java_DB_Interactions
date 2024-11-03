package solvd.laba;

import solvd.laba.jackson.daos.JacksonStudentDAO;
import solvd.laba.tableclasses.Student;

import java.util.List;

public class MainTestJackson {
    public static void main(String[] args) {
        String filePath = "src/main/resources/university.json";

        // Create the DAO instance
        JacksonStudentDAO studentService = new JacksonStudentDAO(filePath);

        // Insert a new student
        Student newStudent = new Student(19, "Stephen", "Johnson", "1980-01-01", "+00123", "s.king@gmail.com", 101);
        studentService.insert(newStudent);

        // Attempt to repeat the insert to see if it is prevented
        studentService.insert(newStudent);

        // Create and insert another student
        Student disposable = new Student(77, "Deletable", "Parca", "2000-01-01", "+000", "nomaik@gmail.com", 201);
        studentService.insert(disposable);

        // Read a student
        Student student = studentService.read(77);
        if (student != null) {
            System.out.println("Read Student: " + student.toString());
        } else {
            System.out.println("No such student: Id " + 77);
        }

        // Update a student
        newStudent.surname = "King";
        studentService.update(newStudent);

        // Find all students
        List<Student> allStudents = studentService.findAll();
        System.out.println("All Students:");
        allStudents.forEach(s -> System.out.println(s.toString()));

        // Delete the disposable student
        studentService.delete(77);

        // Find all students after deletion
        List<Student> remainingStudents = studentService.findAll();
        System.out.println("Remaining Students:");
        remainingStudents.forEach(s -> System.out.println(s.toString()));

    }
}
