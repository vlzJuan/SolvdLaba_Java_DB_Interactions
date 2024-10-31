package solvd.laba.jaxb.daos;


import solvd.laba.dao.JaxbAbstractDAO;
import solvd.laba.tableclasses.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JaxbStudentDAO extends JaxbAbstractDAO<Student, Integer> {

    public JaxbStudentDAO(String filePath) {
        super(filePath);
        if (this.universityWrapper.getStudents() == null) {
            this.universityWrapper.setStudents(new ArrayList<>());
        }
    }

    @Override
    public void insert(Student entity) {
        List<Student> students = universityWrapper.getStudents();
        // Verify unique id condition
        if (students.stream().noneMatch(student -> student.studentId == entity.studentId)) {
            students.add(entity);
            saveToFile();
        }
        // Add here log support for when data is repeated.
    }

    @Override
    public Student read(Integer id) {
        return universityWrapper.getStudents().stream()
                .filter(student -> student.studentId == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Student entity) {
        List<Student> students = universityWrapper.getStudents();
        Optional<Student> existingStudent = students.stream()
                .filter(student -> student.studentId == entity.studentId)
                .findFirst();

        if (existingStudent.isPresent()) {
            students.remove(existingStudent.get());
            students.add(entity);
            saveToFile();
        }
    }

    @Override
    public void delete(Integer id) {
        universityWrapper.getStudents().removeIf(student -> student.studentId == id);
        saveToFile();
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(universityWrapper.getStudents());
    }

}
