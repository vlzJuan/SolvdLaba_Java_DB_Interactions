package solvd.laba.dao;

import solvd.laba.connections.ConnectionPool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T, ID> implements CoreDAO<T, ID> {

    protected final ConnectionPool pool;

    public AbstractDAO(ConnectionPool pool){
        this.pool = pool;
    }

    /**
     * Specific to each DAO
     * @param rs    the resultset, already pointing to the record to parse.
     * @return  The record parsed to the type, or null of it wasn't parsed.
     * @throws SQLException when the data cannot be queried for any reason.
     */
    protected abstract T mapRecord(ResultSet rs) throws SQLException;

    /**
     * Implemented method to parse a ResultSet to a list of T's.
     * @param rs    the resultset to parse
     * @return  the resultset parsed to a list
     * @throws SQLException when the data cannot be queried for any reason
     */
    public List<T> mapResultSet(ResultSet rs) throws SQLException {
        List<T> ret = new ArrayList<>();
        while(rs.next()) {
            T aux = this.mapRecord(rs);
            if(aux!=null){
                ret.add(aux);
            }
        }
        return ret;
    }


}
