package solvd.laba.tableclasses;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;


@XmlRootElement(name="department")
@XmlType(propOrder = {"studentId", "name", "surname", "dateOfBirth", "phoneNumber", "email", "careerId"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Department {

    @XmlElement(name="departmentId")
    public int departmentId;
    @XmlElement(name="name")
    public String name;
    @XmlElement(name="officeId")
    public int officeId;

    public Department(int departmentId, String name, int officeId){
        this.departmentId = departmentId;
        this.name = name;
        this.officeId = officeId;
    }

    //  No-arg constructor for JAXB
    @SuppressWarnings("unused")
    public Department(){}

    @Override
    public String toString(){
        return "Department " + departmentId + ", \"" + name + ", at office " + officeId + "\n";
    }



}
