package solvd.laba.dao;

import solvd.laba.connections.ConnectionPool;
import solvd.laba.tableclasses.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO extends AbstractDAO<Course, Integer> {

    private static final String INSERT_COURSE = "INSERT INTO courses (course_id, name, credits, prerequisite) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_COURSE = "UPDATE courses SET name = ?, credits = ?, prerequisite = ? WHERE course_id = ?";
    private static final String DELETE_COURSE = "DELETE FROM courses WHERE course_id = ?";
    private static final String SELECT_COURSE_BY_ID = "SELECT * FROM courses WHERE course_id = ?";
    private static final String SELECT_ALL = "SELECT * FROM courses";

    public CourseDAO(ConnectionPool connPool) {
        super(connPool);
    }

    @Override
    public void insert(Course course) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(INSERT_COURSE);
            st.setInt(1, course.courseId);
            st.setString(2, course.name);
            st.setInt(3, course.credits);
            if (course.prerequisite != null) {
                st.setInt(4, course.prerequisite);
            } else {
                st.setNull(4, Types.INTEGER);
            }
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Error inserting course: " + exc.getMessage());
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    public Course read(Integer id) {
        Course ret = null;
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_COURSE_BY_ID);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                ret = mapRecord(rs);
            }
        } catch (SQLException exc) {
            System.out.println("Error reading course: " + exc.getMessage());
        } finally {
            this.pool.releaseConnection(conn);
        }
        return ret;
    }

    @Override
    public void update(Course course) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(UPDATE_COURSE);
            st.setString(1, course.name);
            st.setInt(2, course.credits);
            if (course.prerequisite > 0) {
                st.setInt(3, course.prerequisite);
            } else {
                st.setNull(3, Types.INTEGER);
            }
            st.setInt(4, course.courseId);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Error updating course: " + exc.getMessage());
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(DELETE_COURSE);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Error deleting course: " + exc.getMessage());
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    protected Course mapRecord(ResultSet rs) throws SQLException {
        int courseId = rs.getInt("course_id");
        String name = rs.getString("name");
        int credits = rs.getInt("credits");
        int prerequisite = (Integer )rs.getObject("prerequisite");

        return new Course(courseId, name, credits, prerequisite);
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_ALL);
            ResultSet rs = st.executeQuery();
            courses = mapResultSet(rs);
        } catch (SQLException exc) {
            System.out.println("Error fetching all courses: " + exc.getMessage());
        } finally {
            this.pool.releaseConnection(conn);
        }
        return courses;
    }
}