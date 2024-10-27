package solvd.laba.dao;

import java.util.List;


/**
 * Core DAO interface, for tables with a primary key composed of an ID type.
 * @param <T>
 * @param <ID>
 */
public interface CoreDAO <T, ID>{

    void insert(T entity);
    T read(ID id);
    void update(T entity);
    void delete(ID id);
    //T mapRecord(ResultSet rs) throws SQLException;    //   Delegated to AbstractDAO

    List<T> findAll();


}
