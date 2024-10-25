package solvd.laba.tableclasses;

import java.sql.Date;

public class Student {

    public int studentId;
    public String name;
    public String surname;
    public Date dateOfBirth;
    public String phoneNumber;
    public String email;
    public int careerId;


    /**
     * Constructor added to properly use reflection
     * @param id            , the identifier for the Student.
     * @param name          , the name of the student
     * @param surname       , the surname of the student
     * @param dateString    , the date of birth for the student, as a string (Should use yyyy-MM-dd format)
     *                      The format is not verified, but it will throw an exception if the parameter is
     *                      in another format.
     * @param phoneNum      , the phone number for the student.
     *                      It has this format to allow the inclusion of area codes and such (EX: (+54)2944...)
     * @param email         The email of the student
     * @param careerId      the ID for the career associated to the student.
     */
    public Student(int id, String name, String surname, String dateString,
                   String phoneNum, String email, int careerId){
        this(id, name, surname,
                Date.valueOf(dateString),
                phoneNum, email, careerId);
    }


    public Student(int id, String name, String surname, Date dob,
                   String phoneNum, String email, int careerId){
        this.studentId = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dob;
        this.phoneNumber = phoneNum;
        this.email = email;
        this.careerId = careerId;
    }

    @Override
    public String toString(){
        return "{Student " + studentId + ", full name: " + name + " " + surname + "\n"
                + "Date of birth: " + dateOfBirth + "\n"
                + "Phone: " + phoneNumber + "\n"
                + "E-mail: " + email + "\n"
                + "Career id: " + careerId + "}\n";
    }


}
