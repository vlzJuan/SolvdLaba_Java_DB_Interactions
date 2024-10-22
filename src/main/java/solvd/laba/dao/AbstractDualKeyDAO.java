package solvd.laba.dao;

import solvd.laba.connections.ConnectionPool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDualKeyDAO<T, KEY1, KEY2> implements DualKeyClassDAO<T, KEY1, KEY2> {

    protected final ConnectionPool pool;

    public AbstractDualKeyDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    protected abstract T mapRecord(ResultSet rs) throws SQLException;


    @Override
    public List<T> mapResultSet(ResultSet rs) throws SQLException {
        List<T> ret = new ArrayList<>();
        while (rs.next()) {
            T aux = this.mapRecord(rs);
            if (aux != null) {
                ret.add(aux);
            }
        }
        return ret;
    }

}