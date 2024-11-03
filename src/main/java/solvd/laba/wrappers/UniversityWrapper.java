package solvd.laba.wrappers;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import solvd.laba.tableclasses.Student;
import solvd.laba.tableclasses.Professor;
import solvd.laba.tableclasses.Office;
import solvd.laba.tableclasses.Department;
import solvd.laba.tableclasses.Career;

import java.util.List;

@XmlRootElement(name = "university")
@JsonRootName(value = "university")
public class UniversityWrapper {

    private List<Student> students;
    private List<Career> careers;
    private List<Department> departments;
    private List<Professor> professors;
    private List<Office> offices;

    @XmlElement(name = "students")
    @JsonProperty("students")
    @SuppressWarnings("unused")
    public List<Student> getStudents() {
        return students;
    }

    @SuppressWarnings("unused")
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @XmlElement(name = "careers")
    @JsonProperty("careers")
    @SuppressWarnings("unused")
    public List<Career> getCareers() {
        return careers;
    }

    @SuppressWarnings("unused")
    public void setCareers(List<Career> careers) {
        this.careers = careers;
    }

    @XmlElement(name = "departments")
    @JsonProperty("departments")
    @SuppressWarnings("unused")
    public List<Department> getDepartments() {
        return departments;
    }

    @SuppressWarnings("unused")
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    @XmlElement(name = "professors")
    @JsonProperty("professors")
    @SuppressWarnings("unused")
    public List<Professor> getProfessors() {
        return professors;
    }

    @SuppressWarnings("unused")
    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }

    @XmlElement(name = "offices")
    @JsonProperty("offices")
    @SuppressWarnings("unused")
    public List<Office> getOffices() {
        return offices;
    }

    @SuppressWarnings("unused")
    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }
}