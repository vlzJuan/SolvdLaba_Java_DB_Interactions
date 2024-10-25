package solvd.laba.tableclasses;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
     * @param id
     * @param name
     * @param surname
     * @param dateString
     * @param phoneNum
     * @param email
     * @param careerId
     */
    public Student(int id, String name, String surname, String dateString,
                   String phoneNum, String email, int careerId){
        this(id, name, surname,
                Date.valueOf(LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
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
