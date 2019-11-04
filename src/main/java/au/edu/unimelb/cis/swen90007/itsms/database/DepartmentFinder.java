package au.edu.unimelb.cis.swen90007.itsms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The DepartmentFinder class provides methods for retrieving Department
 * data from the database.
 */
public class DepartmentFinder {

    private static final String findDepartmentString =
            "SELECT * FROM APP.departments WHERE id = ?";

    /**
     * Retrieves the data for the Department associated with the
     * provided Department ID, if it exists.
     * @param departmentId the Department's ID.
     * @return a DepartmentGateway object with the Department data.
     */
    public DepartmentGateway find(int departmentId) {
        DepartmentGateway result = Registry.getDepartment(departmentId);
        if (result != null)
            return result;
        try {
            PreparedStatement findDepartmentStatement =
                    DBConnection.prepare(findDepartmentString);
            findDepartmentStatement.setInt(1, departmentId);
            ResultSet rs = findDepartmentStatement.executeQuery();
            rs.next();
            result = DepartmentGateway.load(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
