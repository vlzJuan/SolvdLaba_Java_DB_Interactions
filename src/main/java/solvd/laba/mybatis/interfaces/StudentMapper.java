package solvd.laba.mybatis.interfaces;

import solvd.laba.dao.CoreDAO;
import solvd.laba.tableclasses.Student;

import java.util.List;

/**
 * Interface for MyBatis
 */
public interface StudentMapper extends CoreDAO<Student, Integer> {

    List<Student> findByName(String name);

}
