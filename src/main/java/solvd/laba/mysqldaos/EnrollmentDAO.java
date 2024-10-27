package solvd.laba.mysqldaos;

import solvd.laba.connections.ConnectionPool;
import solvd.laba.dao.SqlAbstractDAO;
import solvd.laba.tableclasses.Enrollment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

public class EnrollmentDAO extends SqlAbstractDAO<Enrollment, Integer> {

    //  the grade will not be modified by manual values.

    private static final String INSERT_ENROLLMENT = "INSERT INTO enrollments (enrollment_id, date, student_id, course_id, professor_id, classroom_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ENROLLMENT = "UPDATE enrollments SET date = ?, student_id = ?, course_id = ?, professor_id = ?, classroom_id = ? WHERE enrollment_id = ?";
    private static final String DELETE_ENROLLMENT = "DELETE FROM enrollments WHERE enrollment_id = ?";
    private static final String SELECT_ENROLLMENT_BY_ID = "SELECT * FROM enrollments WHERE enrollment_id = ?";
    public static final String SELECT_ALL = "SELECT * FROM enrollments";

    public EnrollmentDAO(ConnectionPool connPool) {
        super(connPool);
    }

    @Override
    public void insert(Enrollment enrollment) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(INSERT_ENROLLMENT);
            st.setInt(1, enrollment.enrollmentId);
            st.setDate(2, enrollment.date);
            st.setInt(3, enrollment.studentId);
            st.setInt(4, enrollment.courseId);
            st.setInt(5, enrollment.professorId);
            st.setInt(6, enrollment.classroomId);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println(exc.toString()); // Show SQL error message
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    public Enrollment read(Integer id) {
        Enrollment enrollment = null;
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_ENROLLMENT_BY_ID);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                enrollment = mapRecord(rs);
            }
        } catch (SQLException exc) {
            System.out.println("Error, no such enrollment with the specified ID");
        } finally {
            this.pool.releaseConnection(conn);
        }
        return enrollment;
    }

    @Override
    public void update(Enrollment enrollment) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(UPDATE_ENROLLMENT);
            st.setDate(1, enrollment.date);
            st.setInt(2, enrollment.studentId);
            st.setInt(3, enrollment.courseId);
            st.setInt(4, enrollment.professorId);
            st.setInt(5, enrollment.classroomId);
            st.setInt(6, enrollment.enrollmentId);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Query not executed");
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(DELETE_ENROLLMENT);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Deletion not executed. Likely, no enrollment matches the ID.");
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    protected Enrollment mapRecord(ResultSet rs) throws SQLException {
        int enrollmentId = rs.getInt("enrollment_id");
        Date date = rs.getDate("date");
        int studentId = rs.getInt("student_id");
        Float finalGrade = (Float) rs.getObject("final_grade"); // MENTION ON CALL.
        int courseId = rs.getInt("course_id");
        int professorId = rs.getInt("professor_id");
        int classroomId = rs.getInt("classroom_id");
        return new Enrollment(enrollmentId, date, studentId, courseId, professorId, classroomId);
    }

    @Override
    public List<Enrollment> findAll() {
        List<Enrollment> enrollments = new ArrayList<>();
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_ALL);
            ResultSet rs = st.executeQuery();
            enrollments = mapResultSet(rs);
        } catch (SQLException exc) {
            System.out.println("SQL query not executed.");
        } finally {
            this.pool.releaseConnection(conn);
        }
        return enrollments;
    }
}