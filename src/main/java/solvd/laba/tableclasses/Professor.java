package solvd.laba.tableclasses;

public class Professor {

    public int professorId;
    public String name;
    public String surname;
    public String email;
    public int departmentId;


    public Professor(int professorId, String name, String surname,
                     String email, int departmentId){
        this.professorId = professorId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.departmentId = departmentId;
    }

    @Override
    public String toString(){
        return "Professor " + professorId + " from department " + departmentId
                + " full name: " + name + " " + surname + "\n"
                + "E-mail: " + email + "\n";
    }

    // Getters
    //public int getProfessorId(){    return this.studentId; }
    //public String getName(){        return this.name;}
    //public String getSurname(){     return this.surname;}
    //public String getEmail(){       return this.email;}
    //public int getDepartmentId(){   return this.departmentId;}




}
