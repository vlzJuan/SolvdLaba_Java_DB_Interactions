package solvd.laba.jaxb.wrappers;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import solvd.laba.tableclasses.*;

import java.util.List;

@XmlRootElement(name = "university")
public class UniversityWrapper {

    private List<Student> students;
    private List<Career> careers;
    private List<Department> departments;
    private List<Professor> professors;
    private List<Office> offices;

    @XmlElement(name = "students")
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @XmlElement(name = "careers")
    @SuppressWarnings("unused")
    public List<Career> getCareers() {
        return careers;
    }

    @SuppressWarnings("unused")
    public void setCareers(List<Career> careers) {
        this.careers = careers;
    }

    @XmlElement(name = "departments")
    @SuppressWarnings("unused")
    public List<Department> getDepartments() {
        return departments;
    }

    @SuppressWarnings("unused")
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    @XmlElement(name = "professors")
    @SuppressWarnings("unused")
    public List<Professor> getProfessors() {
        return professors;
    }

    @SuppressWarnings("unused")
    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }

    @XmlElement(name = "offices")
    @SuppressWarnings("unused")
    public List<Office> getOffices() {
        return offices;
    }

    @SuppressWarnings("unused")
    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }
}