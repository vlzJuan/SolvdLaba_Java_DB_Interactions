package solvd.laba.mysqldaos;

import solvd.laba.connections.ConnectionPool;
import solvd.laba.dao.AbstractDualKeyDAO;
import solvd.laba.tableclasses.JoinedTableStudentScholarship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JoinedTableStudentScholarshipDAO extends AbstractDualKeyDAO<JoinedTableStudentScholarship, Integer, Integer> {

    private static final String INSERT_QUERY = "INSERT INTO joined_table_student_scholarship (student_id, scholarship_id) VALUES (?, ?)";
    private static final String SELECT_QUERY = "SELECT * FROM joined_table_student_scholarship WHERE student_id = ? AND scholarship_id = ?";
    private static final String UPDATE_QUERY = "UPDATE joined_table_student_scholarship SET scholarship_id = ? WHERE student_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM joined_table_student_scholarship WHERE student_id = ? AND scholarship_id = ?";
    public static final String SELECT_ALL_QUERY = "SELECT * FROM joined_table_student_scholarship";

    public JoinedTableStudentScholarshipDAO(ConnectionPool connPool) {
        super(connPool);
    }

    @Override
    public void insert(JoinedTableStudentScholarship entity) throws SQLException {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(INSERT_QUERY);
            ps.setInt(1, entity.studentId);
            ps.setInt(2, entity.scholarshipId);
            ps.executeUpdate();
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    public JoinedTableStudentScholarship read(Integer studentId, Integer scholarshipId) throws SQLException {
        JoinedTableStudentScholarship ret = null;
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(SELECT_QUERY);
            ps.setInt(1, studentId);
            ps.setInt(2, scholarshipId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ret = mapRecord(rs);
            }
        } finally {
            this.pool.releaseConnection(conn);
        }
        return ret;
    }

    @Override
    public void update(JoinedTableStudentScholarship entity) throws SQLException {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(UPDATE_QUERY);
            ps.setInt(1, entity.scholarshipId);
            ps.setInt(2, entity.studentId);
            ps.executeUpdate();
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    public void delete(Integer studentId, Integer scholarshipId) throws SQLException {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(DELETE_QUERY);
            ps.setInt(1, studentId);
            ps.setInt(2, scholarshipId);
            ps.executeUpdate();
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    protected JoinedTableStudentScholarship mapRecord(ResultSet rs) throws SQLException {
        int studentId = rs.getInt("student_id");
        int scholarshipId = rs.getInt("scholarship_id");
        return new JoinedTableStudentScholarship(studentId, scholarshipId);
    }

    @Override
    public List<JoinedTableStudentScholarship> findAll() throws SQLException {
        List<JoinedTableStudentScholarship> ret;
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(SELECT_ALL_QUERY);
            ResultSet rs = ps.executeQuery();
            ret = mapResultSet(rs);
        } finally {
            this.pool.releaseConnection(conn);
        }
        return ret;
    }
}
