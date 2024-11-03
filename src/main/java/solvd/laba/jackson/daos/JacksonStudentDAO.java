package solvd.laba.jackson.daos;

import solvd.laba.dao.JacksonAbstractDAO;
import solvd.laba.tableclasses.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JacksonStudentDAO extends JacksonAbstractDAO<Student, Integer> {

    public JacksonStudentDAO(String filePath) {
        super(filePath);
        if (this.universityWrapper.getStudents() == null) {
            this.universityWrapper.setStudents(new ArrayList<>());
        }
    }

    @Override
    public void insert(Student entity) {
        // Verify unique ID condition before inserting
        if (getEntityList().stream().noneMatch(student -> student.studentId == entity.studentId)) {
            getEntityList().add(entity);
            saveToFile();
        }
    }

    @Override
    public Student read(Integer id) {
        return getEntityList().stream()
                .filter(student -> student.studentId == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Student entity) {
        Optional<Student> existingStudent = getEntityList().stream()
                .filter(student -> student.studentId == entity.studentId)
                .findFirst();

        if (existingStudent.isPresent()) {
            getEntityList().remove(existingStudent.get());
            getEntityList().add(entity);
            saveToFile();
        }
    }

    @Override
    public void delete(Integer id) {
        getEntityList().removeIf(student -> student.studentId == id);
        saveToFile();
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(getEntityList());
    }

    @Override
    protected List<Student> getEntityList() {
        return universityWrapper.getStudents(); // Return the list of students from the wrapper
    }
}
