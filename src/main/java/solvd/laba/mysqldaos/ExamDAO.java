package solvd.laba.mysqldaos;

import solvd.laba.connections.ConnectionPool;
import solvd.laba.dao.SqlAbstractDAO;
import solvd.laba.tableclasses.Exam;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamDAO extends SqlAbstractDAO<Exam, Integer> {

    private static final String INSERT_EXAM = "INSERT INTO exams (exam_id, name, topics_covered) VALUES (?, ?, ?)";
    private static final String UPDATE_EXAM = "UPDATE exams SET name = ?, topics_covered = ? WHERE exam_id = ?";
    private static final String DELETE_EXAM = "DELETE FROM exams WHERE exam_id = ?";
    private static final String SELECT_EXAM_BY_ID = "SELECT * FROM exams WHERE exam_id = ?";
    public static final String SELECT_ALL = "SELECT * FROM exams";

    public ExamDAO(ConnectionPool connPool) {
        super(connPool);
    }

    @Override
    public void insert(Exam exam){
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(INSERT_EXAM);
            st.setInt(1, exam.examId);
            st.setString(2, exam.name);
            st.setString(3, exam.topicsCovered);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println(exc.toString());
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    public Exam read(Integer id){
        Exam exam = null;
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_EXAM_BY_ID);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                exam = mapRecord(rs);
            }
        } catch (SQLException exc) {
            System.out.println("Error, no such exam with the specified ID");
        } finally {
            this.pool.releaseConnection(conn);
        }
        return exam;
    }

    @Override
    public void update(Exam exam){
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(UPDATE_EXAM);
            st.setString(1, exam.name);
            st.setString(2, exam.topicsCovered);
            st.setInt(3, exam.examId);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Query not executed");
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    public void delete(Integer id){
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(DELETE_EXAM);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Deletion not executed. Likely, no exam matches the ID.");
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    protected Exam mapRecord(ResultSet rs) throws SQLException {
        int examId = rs.getInt("exam_id");
        String name = rs.getString("name");
        String topicsCovered = rs.getString("topics_covered");
        return new Exam(examId, name, topicsCovered);
    }

    @Override
    public List<Exam> findAll() throws SQLException {
        List<Exam> exams = new ArrayList<>();
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_ALL);
            ResultSet rs = st.executeQuery();
            exams = mapResultSet(rs);
        } catch (SQLException exc) {
            System.out.println("SQL query not executed.");
        } finally {
            this.pool.releaseConnection(conn);
        }
        return exams;
    }
}