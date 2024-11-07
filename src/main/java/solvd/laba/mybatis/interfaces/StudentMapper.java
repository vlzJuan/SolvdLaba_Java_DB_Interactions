package solvd.laba.mybatis.interfaces;

import org.apache.ibatis.annotations.Mapper;
import solvd.laba.dao.CoreDAO;
import solvd.laba.tableclasses.Student;
import java.util.List;

/**
 * Interface for MyBatis
 */
@Mapper
public interface StudentMapper extends CoreDAO<Student, Integer> {
    // No need to explicitly override methods here
}
