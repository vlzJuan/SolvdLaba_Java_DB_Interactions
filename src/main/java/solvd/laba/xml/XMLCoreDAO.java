package solvd.laba.xml;

import org.w3c.dom.Node;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Core DAO interface, for tables with a primary key composed of an ID type.
 * @param <T>
 * @param <ID>
 */
public interface XMLCoreDAO<T, ID>{

    void insert(T entity);
    T read(ID id);
    void update(T entity);
    void delete(ID id);
    T mapRecord(Node node);
    List<T> findAll();


}
