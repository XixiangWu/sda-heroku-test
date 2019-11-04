package au.edu.unimelb.cis.swen90007.itsms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The EmployeeGateway class provides a data-layer interface between
 * local Employee objects and database Employee User objects, including
 * methods fot creating, updating, and deleting Employee object data
 * in the connected database.
 */
public class EmployeeGateway extends UserGateway{


    private int departmentId;

    private static final String updateUserTableString =
            "UPDATE APP.users SET username = ?, password = ?," +
                    " first_name = ?, last_name = ?, department_id = ?" +
                    " WHERE id = ?";

    private static final String insertUserTableString =
            "INSERT INTO APP.users(username, password, first_name, last_name, department_id, staff_type)" +
                    " VALUES (?, ?, ?, ?, ?, ?)";


    public EmployeeGateway(int id, String username, String password,
                       String firstName, String lastName, int departmentId) {
        super(id, username, password, firstName, lastName);
        this.departmentId = departmentId;
    }

    /**
     * Creates a new EmployeeGateway object based on data retrieved
     * from the connected database.
     * @param rs the results from the database retrieval query.
     * @return an EmployeeGateway with data from the provided database
     *           results.
     * @throws SQLException
     */
    public static EmployeeGateway load(ResultSet rs) throws SQLException {
        int idArg = rs.getInt(1);
        String usernameArg = rs.getString(2);
        String passwordArg = rs.getString(3);
        String firstNameArg = rs.getString(4);
        String lastNameArg = rs.getString(5);
        int departmentIdArg = rs.getInt(6);
        return new EmployeeGateway(idArg, usernameArg, passwordArg, firstNameArg,
                lastNameArg, departmentIdArg);
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Attempts to update the data of the corresponding Employee User entry in
     * the connected database with the data from the EmployeeGateway object.
     */
    public void update() {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = DBConnection.prepare(updateUserTableString);
            updateStatement.setString(1, getUsername());
            updateStatement.setString(2, getPassword());
            updateStatement.setString(3, getFirstName());
            updateStatement.setString(4, getLastName());
            updateStatement.setInt(5, getDepartmentId());
            updateStatement.setInt(6, getId());
            updateStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to insert the data of the Employee into the
     * connected database.
     * @return the new id returned by the database.
     */
    public int insert() {
        ResultSet autoGenerated = null;
        PreparedStatement insertStatement = null;
        try {
            insertStatement = DBConnection.prepare(insertUserTableString,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, getUsername());
            insertStatement.setString(2, getPassword());
            insertStatement.setString(3, getFirstName());
            insertStatement.setString(4, getLastName());
            insertStatement.setInt(5, getDepartmentId());
            insertStatement.setInt(6, 1);
            insertStatement.executeUpdate();

            autoGenerated = insertStatement.getGeneratedKeys();
            if (autoGenerated.next()) {
                setId(autoGenerated.getInt(1));
            }

            Registry.addUser(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getId();
    }
}