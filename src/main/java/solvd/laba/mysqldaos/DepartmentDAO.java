package solvd.laba.mysqldaos;

import solvd.laba.connections.ConnectionPool;
import solvd.laba.dao.SqlAbstractDAO;
import solvd.laba.tableclasses.Department;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentDAO extends SqlAbstractDAO<Department, Integer> {

    private static final String INSERT_DEPARTMENT = "INSERT INTO departments (department_id, name, office_id) VALUES (?, ?, ?)";
    private static final String UPDATE_DEPARTMENT = "UPDATE departments SET name = ?, office_id = ? WHERE department_id = ?";
    private static final String DELETE_DEPARTMENT = "DELETE FROM departments WHERE department_id = ?";
    private static final String SELECT_DEPARTMENT_BY_ID = "SELECT * FROM departments WHERE department_id = ?";
    public static final String SELECT_ALL = "SELECT * FROM departments";

    public DepartmentDAO(ConnectionPool connPool) {super(connPool);
    }

    @Override
    public void insert(Department department) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(INSERT_DEPARTMENT);
            st.setInt(1, department.departmentId);
            st.setString(2, department.name);
            st.setInt(3, department.officeId);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Error inserting department: " + exc.getMessage());
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    public Department read(Integer id) {
        Department ret = null;
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_DEPARTMENT_BY_ID);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                ret = mapRecord(rs);
            }
        } catch (SQLException exc) {
            System.out.println("Error reading department: " + exc.getMessage());
        } finally {
            this.pool.releaseConnection(conn);
        }
        return ret;
    }

    @Override
    public void update(Department department) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(UPDATE_DEPARTMENT);
            st.setString(1, department.name);
            st.setInt(2, department.officeId);
            st.setInt(3, department.departmentId);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Error updating department: " + exc.getMessage());
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(DELETE_DEPARTMENT);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Error deleting department: " + exc.getMessage());
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    protected Department mapRecord(ResultSet rs) throws SQLException {
        int departmentId = rs.getInt("department_id");
        String name = rs.getString("name");
        int officeId = rs.getInt("office_id");
        return new Department(departmentId, name, officeId);
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_ALL);
            ResultSet rs = st.executeQuery();
            departments = mapResultSet(rs);
        } catch (SQLException exc) {
            System.out.println("Error fetching all departments: " + exc.getMessage());
        } finally {
            this.pool.releaseConnection(conn);
        }
        return departments;
    }
}