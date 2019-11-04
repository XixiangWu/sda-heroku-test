package au.edu.unimelb.cis.swen90007.itsms.database;

import java.sql.*;

/**
 * The UserGateway abstract class provides a data-layer interface between
 * local User objects and database User objects, defining the methods
 * for loading and deleting User object data used by inheriting classes.
 */
public abstract class UserGateway implements IGateway {

    private int id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    public UserGateway(int id, String username, String password, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Creates a new UserGateway object based on data retrieved
     * from the connected database.
     * @param rs the results from the database retrieval query.
     * @return a UserGateway with data from the provided database
     *           results.
     * @throws SQLException
     */
    public static UserGateway load(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        UserGateway result = Registry.getUser(id);
        if (result != null)
            return result;
        int type = rs.getInt(7);
        switch (type) {
            case 1:
                result = EmployeeGateway.load(rs);
                break;
            case 2:
                result = TechGateway.load(rs);
                break;
        }
        Registry.addUser(result);
        return result;
    }

    /**
     * Attempts to delete the corresponding User entry in the
     * connected database.
     */
    public void delete() {

    }
}
