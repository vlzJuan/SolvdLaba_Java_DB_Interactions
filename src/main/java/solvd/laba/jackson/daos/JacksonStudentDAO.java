package solvd.laba.jackson.daos;

import solvd.laba.dao.JacksonAbstractDAO;
import solvd.laba.tableclasses.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JacksonStudentDAO extends JacksonAbstractDAO<Student, Integer> {

    public JacksonStudentDAO(String filePath) {
        super(filePath);
    }

    @Override
    public void insert(Student entity) {
        // Verify unique ID condition before inserting
        if (entities.stream().noneMatch(student -> student.studentId == entity.studentId)) {
            entities.add(entity);
            saveToFile();
        }
    }

    @Override
    public Student read(Integer id) {
        return entities.stream()
                .filter(student -> student.studentId == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Student entity) {
        Optional<Student> existingStudent = entities.stream()
                .filter(student -> student.studentId == entity.studentId)
                .findFirst();

        if (existingStudent.isPresent()) {
            entities.remove(existingStudent.get());
            entities.add(entity);
            saveToFile();
        }
    }

    @Override
    public void delete(Integer id) {
        entities.removeIf(student -> student.studentId == id);
        saveToFile();
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(entities);
    }

    @Override
    protected Class<Student> getEntityClass() {
        return Student.class;
    }
}
