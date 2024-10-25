package solvd.laba.mysqldaos;

import solvd.laba.dao.AbstractDAO;
import solvd.laba.tableclasses.Scholarship;
import solvd.laba.connections.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ScholarshipDAO extends AbstractDAO<Scholarship, Integer> {

    private static final String INSERT_SCHOLARSHIP = "INSERT INTO scholarships (scholarship_id, name, description, sponsored_percentage) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SCHOLARSHIP = "UPDATE scholarships SET name = ?, description = ?, sponsored_percentage = ? WHERE scholarship_id = ?";
    private static final String DELETE_SCHOLARSHIP = "DELETE FROM scholarships WHERE scholarship_id = ?";
    private static final String SELECT_SCHOLARSHIP_BY_ID = "SELECT * FROM scholarships WHERE scholarship_id = ?";
    public static final String SELECT_ALL_SCHOLARSHIPS = "SELECT * FROM scholarships";

    public ScholarshipDAO(ConnectionPool pool) {
        super(pool);
    }

    @Override
    public void insert(Scholarship scholarship) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(INSERT_SCHOLARSHIP);
            st.setInt(1, scholarship.scholarshipId);
            st.setString(2, scholarship.name);
            st.setString(3, scholarship.description);
            st.setInt(4, scholarship.sponsoredPercentage);
            st.executeUpdate();
        }
        catch(SQLException exc){
            System.out.println(exc.toString());
        }
        finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    public Scholarship read(Integer id){
        Scholarship scholarship = null;
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_SCHOLARSHIP_BY_ID);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                scholarship = this.mapRecord(rs);
            }
        }
        catch(SQLException exc){
            System.out.println(exc.toString());
        }
        finally {
            this.pool.releaseConnection(conn);
        }
        return scholarship;
    }

    @Override
    public void update(Scholarship scholarship) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(UPDATE_SCHOLARSHIP);
            st.setString(1, scholarship.name);
            st.setString(2, scholarship.description);
            st.setInt(3, scholarship.sponsoredPercentage);
            st.setInt(4, scholarship.scholarshipId);
            st.executeUpdate();
        }
        catch(SQLException exc){
            System.out.println(exc.toString());
        }
        finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(DELETE_SCHOLARSHIP);
            st.setInt(1, id);
            st.executeUpdate();
        }
        catch(SQLException exc){
            System.out.println(exc.toString());
        }
        finally {
            this.pool.releaseConnection(conn);
        }
    }

    @Override
    protected Scholarship mapRecord(ResultSet rs) throws SQLException {
        int scholarshipId = rs.getInt("scholarship_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        int sponsoredPercentage = rs.getInt("sponsored_percentage");
        return new Scholarship(scholarshipId, name, description, sponsoredPercentage);
    }

    @Override
    public List<Scholarship> findAll() throws SQLException {
        List<Scholarship> scholarships;
        Connection conn = this.pool.getConnection();
        try {
            PreparedStatement st = conn.prepareStatement(SELECT_ALL_SCHOLARSHIPS);
            ResultSet rs = st.executeQuery();
            scholarships = this.mapResultSet(rs);
        } finally {
            this.pool.releaseConnection(conn);
        }
        return scholarships;
    }
}