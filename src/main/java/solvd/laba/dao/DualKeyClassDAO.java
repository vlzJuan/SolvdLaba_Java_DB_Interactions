package solvd.laba.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DualKeyClassDAO <T, KEY1, KEY2>{

    void insert(T entity) throws SQLException;
    T read(KEY1 id1, KEY2 id2) throws SQLException;
    void update(T entity) throws SQLException;
    void delete(KEY1 id1, KEY2 id2 ) throws SQLException;
    //T mapRecord(ResultSet rs) throws SQLException;    //   Delegated to AbstractDAO
    List<T> mapResultSet(ResultSet rs) throws SQLException;
    List<T> findAll() throws SQLException;


}
