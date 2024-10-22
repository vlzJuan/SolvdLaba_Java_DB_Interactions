package solvd.laba.dao;

import solvd.laba.connections.ConnectionPool;
import solvd.laba.tableclasses.Student;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends AbstractDAO<Student, Integer> {

    private static final String INSERT_STUDENT = "INSERT INTO students (student_id, name, surname, date_of_birth, phone_number, email, career_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_STUDENT = "UPDATE students SET name = ?, surname = ?, date_of_birth = ?, phone_number = ?, email = ?, career_id = ? WHERE student_id = ?";
    private static final String DELETE_STUDENT = "DELETE FROM students WHERE student_id = ?";
    private static final String SELECT_STUDENT_BY_ID = "SELECT * FROM students WHERE student_id = ?";
    private static final String SELECT_ALL = "SELECT * FROM students";


    public StudentDAO(ConnectionPool connPool){
        super(connPool);
    }

    public void insert(Student student){
        Connection conn = this.pool.getConnection();
        try{
            PreparedStatement st = conn.prepareStatement(INSERT_STUDENT);
            st.setInt(1, student.studentId);
            st.setString(2, student.name);
            st.setString(3, student.surname);
            st.setDate(4, Date.valueOf(student.dateOfBirth.toLocalDate()));
            st.setString(5, student.phoneNumber);
            st.setString(6, student.email);
            st.setInt(7, student.careerId);
            st.executeUpdate();
        }
        catch(SQLException exc){
            System.out.println(exc.toString()); // Show in CommandLine the SQL error message
        }
        finally{
            this.pool.releaseConnection(conn);
        }
    }

    public Student read(Integer id){
        Student ret = null;
        Connection conn = this.pool.getConnection();
        try{
            PreparedStatement st = conn.prepareStatement(SELECT_STUDENT_BY_ID);
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();    // Should only have up to one, since col 1 is PK
            if(rs.next()){
                ret = mapRecord(rs);
            }
        }
        catch (SQLException exc){
            System.out.println("Error, no such student with the specified ID");
        }
        finally{
            this.pool.releaseConnection(conn);
        }
        return ret;
    }

    public void update(Student student){
        Connection conn = this.pool.getConnection();
        try{
            PreparedStatement statement = conn.prepareStatement(UPDATE_STUDENT);
            statement.setString(1, student.name);
            statement.setString(2, student.surname);
            statement.setDate(3, Date.valueOf(student.dateOfBirth.toLocalDate()));
            statement.setString(4, student.phoneNumber);
            statement.setString(5, student.email);
            statement.setInt(6, student.careerId);
            statement.setInt(7, student.studentId);
            statement.executeUpdate();
        }
        catch(SQLException exc){
            System.out.println("Query not executed");
        }
        finally{
            this.pool.releaseConnection(conn);
        }
    }


    public void delete(Integer id){
        Connection conn = this.pool.getConnection();
        try{
            PreparedStatement st = conn.prepareStatement(DELETE_STUDENT);
            st.setInt(1,id);
            st.executeUpdate();    // Should only have up to one, since col 1 is PK
        }
        catch(SQLException exc){
            System.out.println("Deletion not executed. Likely, no student matches the ID.");
        }
        finally{
            this.pool.releaseConnection(conn);
        }
    }

    /**
     * Should be used with a resultset that is known to have only one student.
     * DOES NOT MOVE THE RESULTSET POINTER. SHOULD BE USED WITH CARE.
     * @param resultSet , the result of the query
     * @return  a Student object.
     * @throws SQLException when the operation is invalid.
     */
    protected Student mapRecord(ResultSet resultSet) throws SQLException {
        Student ret;    // Defaults to null
        int studentId = resultSet.getInt("student_id");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        Date dateOfBirth = resultSet.getDate("date_of_birth");
        String phoneNumber = resultSet.getString("phone_number");
        String email = resultSet.getString("email");
        int careerId = resultSet.getInt("career_id");
        ret = new Student(studentId, name, surname, dateOfBirth, phoneNumber, email, careerId);

        return ret;
    }


    public List<Student> findAll(){
        List<Student> ret = new ArrayList<>();
        Connection conn = this.pool.getConnection();
        try{
            PreparedStatement st = conn.prepareStatement(SELECT_ALL);
            ResultSet rs = st.executeQuery();
            ret = mapResultSet(rs);
        }
        catch(SQLException exc){
            System.out.println("SQL query not executed.");
        }
        finally{
            this.pool.releaseConnection(conn);
        }
        return ret;
    }

}
