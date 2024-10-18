package solvd.laba.dao;

import solvd.laba.connections.ConnectionPool;
import solvd.laba.tableclasses.Professor;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorDAO {

    private static final String INSERT_PROFESSOR = "INSERT INTO professors (professor_id, name, surname, email, department_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_PROFESSOR = "UPDATE professors SET name = ?, surname = ?, email = ?, department_id = ? WHERE professor_id = ?";
    private static final String DELETE_PROFESSOR = "DELETE FROM professors WHERE professor_id = ?";
    private static final String SELECT_PROFESSOR_BY_ID = "SELECT * FROM professors WHERE professor_id = ?";
    private static final String SELECT_ALL_PROFESSORS = "SELECT * FROM professors";

    private final ConnectionPool pool;

    public ProfessorDAO(ConnectionPool connPool) {
        this.pool = connPool;
    }

    public void insert(Professor professor) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(INSERT_PROFESSOR);
            st.setInt(1, professor.professorId);
            st.setString(2, professor.name);
            st.setString(3, professor.surname);
            st.setString(4, professor.email);
            st.setInt(5, professor.departmentId);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Query not executed. Professor likely already in DB.");
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    public Professor read(Integer id) {
        Professor ret = null;
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_PROFESSOR_BY_ID);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            ret = mapResultSetToProfessor(rs);
        } catch (SQLException exc) {
            System.out.println("Error, no such professor with the specified ID.");
        } finally {
            this.pool.releaseConnection(conn);
        }
        return ret;
    }

    public void update(Professor professor) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(UPDATE_PROFESSOR);
            statement.setString(1, professor.name);
            statement.setString(2, professor.surname);
            statement.setString(3, professor.email);
            statement.setInt(4, professor.departmentId);
            statement.setInt(5, professor.professorId);
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
            PreparedStatement st = conn.prepareStatement(DELETE_PROFESSOR);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Deletion not executed. Likely no professor matches the ID.");
        } finally {
            this.pool.releaseConnection(conn);
        }
    }

    private Professor mapResultSetToProfessor(ResultSet resultSet) throws SQLException {
        Professor ret = null;
        if (resultSet.next()) {
            int professorId = resultSet.getInt("professor_id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String email = resultSet.getString("email");
            int departmentId = resultSet.getInt("department_id");
            ret = new Professor(professorId, name, surname, email, departmentId);
        }
        return ret;
    }

    public List<Professor> findAll() {
        List<Professor> ret = null;
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_ALL_PROFESSORS);
            ResultSet rs = st.executeQuery();
            ret = mapResultSet(rs);
        } catch (SQLException exc) {
            System.out.println("SQL query not executed.");
        } finally {
            this.pool.releaseConnection(conn);
        }
        return ret;
    }

    private List<Professor> mapResultSet(ResultSet rs) throws SQLException {
        List<Professor> ret = new ArrayList<>();
        Professor aux;
        while ((aux = this.mapResultSetToProfessor(rs)) != null) {
            ret.add(aux);
        }
        return ret;
    }


}
