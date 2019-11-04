package au.edu.unimelb.cis.swen90007.itsms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The UserFinder class provides methods for retrieving User
 * data from the database.
 */
public class UserFinder {

    private static final String findUserByIdString =
            "SELECT * FROM APP.users WHERE id = ?";

    private static final String findUserByUsernameString =
            "SELECT * FROM APP.users WHERE username = ?";

    /**
     * Retrieves the data for the User associated with the
     * provided User ID, if they exist.
     * @param userId the User's ID.
     * @return a UserGateway object with the User's data.
     */
    public UserGateway findUserById(int userId) {
        UserGateway result = Registry.getUser(userId);
        if (result != null)
            return result;
        try {
            PreparedStatement findStatement =
                    DBConnection.prepare(findUserByIdString);
            findStatement.setInt(1, userId);
            ResultSet rs = findStatement.executeQuery();
            if (rs.next()) {
                result = UserGateway.load(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Retrieves the data for the User associated with the
     * provided User username, if they exist.
     * @param username the User's username.
     * @return a UserGateway object with the User's data.
     */
    public UserGateway findUserByUsername(String username) {
        UserGateway result = null;
        try {
            PreparedStatement findStatement =
                    DBConnection.prepare(findUserByUsernameString);
            findStatement.setString(1, username);
            ResultSet rs = findStatement.executeQuery();
            if (rs.next()) {
                result = UserGateway.load(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
