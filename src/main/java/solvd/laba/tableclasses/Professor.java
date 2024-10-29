package solvd.laba.tableclasses;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="professor")
@XmlType(propOrder = {"professorId", "name", "surname", "email", "departmentId"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Professor {

    @XmlElement(name="professorId")
    public int professorId;
    @XmlElement(name="name")
    public String name;
    @XmlElement(name="surname")
    public String surname;
    @XmlElement(name="email")
    public String email;
    @XmlElement(name="departmentId")
    public int departmentId;


    public Professor(int professorId, String name, String surname,
                     String email, int departmentId){
        this.professorId = professorId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.departmentId = departmentId;
    }

    // No-arg constructor for JAXB
    @SuppressWarnings("unused")
    public Professor(){}

    @Override
    public String toString(){
        return "Professor " + professorId + " from department " + departmentId
                + " full name: " + name + " " + surname + "\n"
                + "E-mail: " + email + "\n";
    }


}
