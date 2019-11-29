package au.edu.unimelb.cis.swen90007.itsms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The IssueFinder class provides methods for retrieving Issue
 * data from the database.
 */
public class AppointmentFinder {

    private static final String findAppointmentStatementString =
            "SELECT * FROM APP.appointments WHERE id = ?";

    private static final String findAllAppointmentsStatementString =
            "SELECT * FROM APP.appointments";

    private static final String findAppointmentByUserStatementString =
            "SELECT * FROM APP.appointments WHERE reporter_id = ?";

    /**
     * Retrieves the data for the appointment associated with the
     * provided Issue ID, if it exists.
     * @param appointmentId the appointment's ID.
     * @return a AppointmentGateway object with the appointment's data.
     */
    public AppointmentGateway find(int appointmentId) {
        AppointmentGateway result = Registry.getAppointment(appointmentId);
        if (result != null)
            return result;
        try {
            PreparedStatement findAppointmentStatement = DBConnection.prepare(findAppointmentStatementString);
            findAppointmentStatement.setInt(1, appointmentId);
            ResultSet rs = findAppointmentStatement.executeQuery();
            rs.next();
            result = AppointmentGateway.load(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Retrieves the data for all appointments in the connected database.
     * @return a List of AppointmentGateway objects for each issue.
     */
    public List<AppointmentGateway> findAllAppointments() {
        List<AppointmentGateway> result = new ArrayList<>();
        try {
            PreparedStatement findAllAppointmentsStatement =
                    DBConnection.prepare(findAllAppointmentsStatementString);
            ResultSet rs = findAllAppointmentsStatement.executeQuery();
            while (rs.next()) {
                result.add(AppointmentGateway.load(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Retrieves the data for all appointments in the connected database.
     * @return a List of AppointmentGateway objects for each issue.
     */
    public List<AppointmentGateway> findAppointmentsByUser(int userid) {
        List<AppointmentGateway> result = new ArrayList<>();
        try {
            PreparedStatement findAppointmentsByUserStatement =
                    DBConnection.prepare(findAppointmentByUserStatementString);
            findAppointmentsByUserStatement.setInt(1, userid);
            ResultSet rs = findAppointmentsByUserStatement.executeQuery();
            while (rs.next()) {
                result.add(AppointmentGateway.load(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
