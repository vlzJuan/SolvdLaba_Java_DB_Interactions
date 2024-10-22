package solvd.laba.dao;

import solvd.laba.connections.ConnectionPool;
import solvd.laba.tableclasses.ExamScore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ExamScoreDAO extends AbstractDualKeyDAO<ExamScore, Integer, Integer> {

    private static final String INSERT_EXAM_SCORE = "INSERT INTO exam_scores (exam_id, enrollment_id, score, retry_score) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_EXAM_SCORE = "UPDATE exam_scores SET score = ?, retry_score = ? WHERE exam_id = ? AND enrollment_id = ?";
    private static final String DELETE_EXAM_SCORE = "DELETE FROM exam_scores WHERE exam_id = ? AND enrollment_id = ?";
    private static final String SELECT_EXAM_SCORE = "SELECT * FROM exam_scores WHERE exam_id = ? AND enrollment_id = ?";
    private static final String SELECT_ALL_EXAM_SCORES = "SELECT * FROM exam_scores";

    public ExamScoreDAO(ConnectionPool pool) {
        super(pool);
    }

    @Override
    public void insert(ExamScore examScore) throws SQLException {
        Connection conn = pool.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(INSERT_EXAM_SCORE);
            statement.setInt(1, examScore.examId);
            statement.setInt(2, examScore.enrollmentId);
            statement.setInt(3, examScore.score);
            statement.setInt(4, examScore.retryScore);
            statement.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Error during insertion: " + exc.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public ExamScore read(Integer examId, Integer enrollmentId) throws SQLException {
        ExamScore examScore = null;
        Connection conn = pool.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(SELECT_EXAM_SCORE);
            statement.setInt(1, examId);
            statement.setInt(2, enrollmentId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                examScore = mapRecord(rs);
            }
        } catch (SQLException exc) {
            System.out.println("Error retrieving the record: " + exc.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return examScore;
    }

    @Override
    public void update(ExamScore examScore) throws SQLException {
        Connection conn = pool.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(UPDATE_EXAM_SCORE);
            statement.setInt(1, examScore.score);
            statement.setInt(2, examScore.retryScore);
            statement.setInt(3, examScore.examId);
            statement.setInt(4, examScore.enrollmentId);
            statement.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Error during update: " + exc.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public void delete(Integer examId, Integer enrollmentId) throws SQLException {
        Connection conn = pool.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(DELETE_EXAM_SCORE);
            statement.setInt(1, examId);
            statement.setInt(2, enrollmentId);
            statement.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Error during deletion: " + exc.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public List<ExamScore> findAll() throws SQLException {
        List<ExamScore> examScores;
        Connection conn = pool.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(SELECT_ALL_EXAM_SCORES);
            ResultSet rs = statement.executeQuery();
            examScores = mapResultSet(rs);
        } catch (SQLException exc) {
            System.out.println("Error fetching all records: " + exc.getMessage());
            return null;
        } finally {
            pool.releaseConnection(conn);
        }
        return examScores;
    }

    @Override
    protected ExamScore mapRecord(ResultSet rs) throws SQLException {
        int examId = rs.getInt("exam_id");
        int enrollmentId = rs.getInt("enrollment_id");
        int score = rs.getInt("score");
        int retryScore = rs.getInt("retry_score");
        return new ExamScore(examId, enrollmentId, score, retryScore);
    }
}