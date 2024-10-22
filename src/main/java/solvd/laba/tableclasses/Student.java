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


    // Getters
    //public int getStudentId(){      return this.studentId; }
    //public String getName(){        return this.name;}
    //public String getSurname(){     return this.surname;}
    //public Date getDateOfBirth(){   return this.dateOfBirth;}
    //public String getPhoneNumber(){ return this.phoneNumber;}
    //public String getEmail(){       return this.email;}
    //public int getCareerId(){       return this.careerId;}

}
