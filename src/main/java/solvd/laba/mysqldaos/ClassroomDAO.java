package solvd.laba.mysqldaos;

import solvd.laba.connections.ConnectionPool;
import solvd.laba.dao.AbstractDAO;
import solvd.laba.tableclasses.Classroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassroomDAO extends AbstractDAO<Classroom, Integer> {

    private static final String INSERT_CLASSROOM = "INSERT INTO classrooms (classroom_id, building, room) VALUES (?, ?, ?)";
    private static final String UPDATE_CLASSROOM = "UPDATE classrooms SET building = ?, room = ? WHERE classroom_id = ?";
    private static final String DELETE_CLASSROOM = "DELETE FROM classrooms WHERE classroom_id = ?";
    private static final String SELECT_CLASSROOM_BY_ID = "SELECT * FROM classrooms WHERE classroom_id = ?";
    public static final String SELECT_ALL = "SELECT * FROM classrooms";

    public ClassroomDAO(ConnectionPool connPool) {
        super(connPool);
    }

    @Override
    public void insert(Classroom classroom) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(INSERT_CLASSROOM);
            st.setInt(1, classroom.classroomId);
            st.setString(2, classroom.building);
            st.setString(3, classroom.room);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println(exc.toString()); // Show in CommandLine the SQL error message
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    public Classroom read(Integer id) {
        Classroom classroom = null;
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_CLASSROOM_BY_ID);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery(); // Should only have up to one, since column 1 is PK
            if (rs.next()) {
                classroom = mapRecord(rs);
            }
        } catch (SQLException exc) {
            System.out.println("Error, no such classroom with the specified ID");
        } finally {
            this.pool.releaseConnection(conn);
        }
        return classroom;
    }

    @Override
    public void update(Classroom classroom) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(UPDATE_CLASSROOM);
            statement.setString(1, classroom.building);
            statement.setString(2, classroom.room);
            statement.setInt(3, classroom.classroomId);
            statement.executeUpdate();
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
            PreparedStatement st = conn.prepareStatement(DELETE_CLASSROOM);
            st.setInt(1, id);
            st.executeUpdate(); // Should only have up to one, since column 1 is PK
        } catch (SQLException exc) {
            System.out.println("Deletion not executed. Likely, no classroom matches the ID.");
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    protected Classroom mapRecord(ResultSet resultSet) throws SQLException {
        int classroomId = resultSet.getInt("classroom_id");
        String building = resultSet.getString("building");
        String room = resultSet.getString("room");
        return new Classroom(classroomId, building, room);
    }

    public List<Classroom> findAll() {
        List<Classroom> classrooms = new ArrayList<>();
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_ALL);
            ResultSet rs = st.executeQuery();
            classrooms = mapResultSet(rs);
        } catch (SQLException exc) {
            System.out.println("SQL query not executed.");
        } finally {
            this.pool.releaseConnection(conn);
        }
        return classrooms;
    }
}