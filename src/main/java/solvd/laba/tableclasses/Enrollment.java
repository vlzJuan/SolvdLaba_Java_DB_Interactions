package solvd.laba.tableclasses;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Enrollment {

    public int enrollmentId;
    public Date date;
    public int studentId;
    public Float finalGrade;
    public int courseId;
    public int professorId;
    public int classroomId;


    public Enrollment(int enrollmentId, String dateString, int studentId,
                      int courseId, int professorId, int classroomId){
        this(enrollmentId, Date.valueOf(LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                studentId, courseId, professorId, classroomId);
    }


    public Enrollment(int enrollmentId, Date date, int studentId,
                      int courseId, int professorId, int classroomId){
        this.enrollmentId = enrollmentId;
        this.date = date;
        this.studentId = studentId;
        this.courseId = courseId;
        this.professorId = professorId;
        this.classroomId = classroomId;
    }


    // Getters, setters and other methods TBI.
    // IMPORTANT: The average calculation has to be performed using queries, will do this here later.


}
