package solvd.laba.dao;

import solvd.laba.connections.ConnectionPool;
import solvd.laba.tableclasses.Office;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OfficeDAO extends AbstractDAO<Office, Integer> {

    private static final String INSERT_OFFICE = "INSERT INTO offices (office_id, building, room) VALUES (?, ?, ?)";
    private static final String UPDATE_OFFICE = "UPDATE offices SET building = ?, room = ? WHERE office_id = ?";
    private static final String DELETE_OFFICE = "DELETE FROM offices WHERE office_id = ?";
    private static final String SELECT_OFFICE_BY_ID = "SELECT * FROM offices WHERE office_id = ?";
    private static final String SELECT_ALL = "SELECT * FROM offices";

    public OfficeDAO(ConnectionPool connPool) {
        super(connPool);
    }

    @Override
    public void insert(Office office) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(INSERT_OFFICE);
            st.setInt(1, office.officeId);
            st.setString(2, office.building);
            st.setString(3, office.room);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println(exc.toString());
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    public Office read(Integer id) {
        Office office = null;
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_OFFICE_BY_ID);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                office = mapRecord(rs);
            }
        } catch (SQLException exc) {
            System.out.println("Error, no such office with the specified ID");
        } finally {
            this.pool.releaseConnection(conn);
        }
        return office;
    }

    @Override
    public void update(Office office) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(UPDATE_OFFICE);
            st.setString(1, office.building);
            st.setString(2, office.room);
            st.setInt(3, office.officeId);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Update not executed");
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(DELETE_OFFICE);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Deletion not executed. Likely, no office matches the ID.");
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    protected Office mapRecord(ResultSet rs) throws SQLException {
        int officeId = rs.getInt("office_id");
        String building = rs.getString("building");
        String room = rs.getString("room");
        return new Office(officeId, building, room);
    }

    @Override
    public List<Office> findAll() {
        List<Office> offices = new ArrayList<>();
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_ALL);
            ResultSet rs = st.executeQuery();
            offices = mapResultSet(rs);
        } catch (SQLException exc) {
            System.out.println("SQL query not executed.");
        } finally {
            this.pool.releaseConnection(conn);
        }
        return offices;
    }
}