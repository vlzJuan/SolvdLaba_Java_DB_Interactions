package solvd.laba.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Core DAO interface, for tables with a primary key composed of an ID type.
 * @param <T>
 * @param <ID>
 */
public interface CoreDAO <T, ID>{

    void insert(T entity) throws SQLException;
    T read(ID id) throws SQLException;
    void update(T entity) throws SQLException;
    void delete(ID id) throws SQLException;
    List<T> mapResultSet(ResultSet rs) throws SQLException;
    List<T> findAll() throws SQLException;


}
