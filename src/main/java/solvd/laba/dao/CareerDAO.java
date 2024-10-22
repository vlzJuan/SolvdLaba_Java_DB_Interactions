package solvd.laba.dao;

import solvd.laba.connections.ConnectionPool;
import solvd.laba.tableclasses.Career;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CareerDAO extends AbstractDAO<Career, Integer> {

    private static final String INSERT_CAREER = "INSERT INTO careers (career_id, name, level, department_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_CAREER = "UPDATE careers SET name = ?, level = ?, department_id = ? WHERE career_id = ?";
    private static final String DELETE_CAREER = "DELETE FROM careers WHERE career_id = ?";
    private static final String SELECT_CAREER_BY_ID = "SELECT * FROM careers WHERE career_id = ?";
    private static final String SELECT_ALL_CAREERS = "SELECT * FROM careers";


    public CareerDAO(ConnectionPool connPool) {
        super(connPool);
    }

    public void insert(Career career) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(INSERT_CAREER);
            st.setInt(1, career.careerId);
            st.setString(2, career.name);
            st.setString(3, career.level);
            st.setInt(4, career.departmentId);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Query not executed. Career likely already in DB.");
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    public Career read(Integer id) {
        Career ret = null;
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_CAREER_BY_ID);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                ret = mapRecord(rs);
            }
        } catch (SQLException exc) {
            System.out.println("Error, no such career with the specified ID.");
        } finally {
            this.pool.releaseConnection(conn);
        }
        return ret;
    }

    public void update(Career career) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(UPDATE_CAREER);
            statement.setString(1, career.name);
            statement.setString(2, career.level);
            statement.setInt(3, career.departmentId);
            statement.setInt(4, career.careerId);
            statement.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Query not executed.");
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    public void delete(Integer id) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(DELETE_CAREER);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Deletion not executed. Likely no career matches the ID.");
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    protected Career mapRecord(ResultSet resultSet) throws SQLException {
        Career ret = null;
        if (resultSet.next()) {
            int careerId = resultSet.getInt("career_id");
            String name = resultSet.getString("name");
            String level = resultSet.getString("level");
            int departmentId = resultSet.getInt("department_id");
            ret = new Career(careerId, name, level, departmentId);
        }
        return ret;
    }

    public List<Career> findAll() {
        List<Career> ret = null;
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_ALL_CAREERS);
            ResultSet rs = st.executeQuery();
            ret = mapResultSet(rs);
        } catch (SQLException exc) {
            System.out.println("SQL query not executed.");
        } finally {
            this.pool.releaseConnection(conn);
        }
        return ret;
    }



}
