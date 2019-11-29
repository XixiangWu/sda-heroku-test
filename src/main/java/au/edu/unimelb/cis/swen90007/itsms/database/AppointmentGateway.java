package au.edu.unimelb.cis.swen90007.itsms.database;

import au.edu.unimelb.cis.swen90007.itsms.domain.AppointmentStatus;
import au.edu.unimelb.cis.swen90007.itsms.domain.IssueStatus;
import com.sun.tools.corba.se.idl.constExpr.Times;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * The IssueGateway class provides a data-layer interface between
 * local Issue objects and database Issue objects, including
 * methods fot creating, updating, and deleting Issue object data
 * in the connected database.
 */
public class AppointmentGateway implements IGateway {

    private int id;
    private int reporterId;
    private int issueId;
    private AppointmentStatus status;
    private String description;
    private boolean stickied;
    private Timestamp timeSubmitted;
    private Timestamp timeEstimated;

    private static final String updateAppointmentStatement =
            "UPDATE APP.appointments" +
                    " SET status = ?, description = ?, stickied = ?, time_estimated = ?" +
                    " WHERE id = ?";

    private static final String insertAppointmentStatement =
            "INSERT INTO app.appointments(reporter_id, issues_id, status, description, stickied, time_submitted, time_estimated)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String deleteAppointmentStatement =
            "DELETE FROM APP.appointments WHERE id = ?";

    private static final String lockAppointmentStatement =
            "SELECT * FROM app.appointments WHERE id = ? FOR SHARE";

    public AppointmentGateway(int id, int reporterId, int issueId,
                              AppointmentStatus status, String description,
                              boolean stickied, Timestamp timeSubmitted, Timestamp timeEstimated) {
        this.id = id;
        this.reporterId = reporterId;
        this.issueId = issueId;
        this.status = status;
        this.description = description;
        this.stickied = stickied;
        this.timeSubmitted = timeSubmitted;
        this.timeEstimated = timeEstimated;
    }

    public AppointmentGateway(int reporterId, int issueId,
                              AppointmentStatus status, String description,
                              boolean stickied, Timestamp timeSubmitted, Timestamp timeEstimated) {
        this.reporterId = reporterId;
        this.issueId = issueId;
        this.status = status;
        this.description = description;
        this.stickied = stickied;
        this.timeSubmitted = timeSubmitted;
        this.timeEstimated = timeEstimated;
    }

    public AppointmentGateway(int reporterId, int issueId,
                              AppointmentStatus status, String description,
                              boolean stickied, Timestamp timeSubmitted) {
        this.reporterId = reporterId;
        this.issueId = issueId;
        this.status = status;
        this.description = description;
        this.stickied = stickied;
        this.timeSubmitted = timeSubmitted;
    }

    /**
     * Getter and Setter block
     */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getReporterId() {
        return reporterId;
    }
    public void setReporterId(int reporterId) {
        this.reporterId = reporterId;
    }
    public int getIssueId() { return issueId; }
    public void setIssueId(int issueId) {
        this.issueId = id;
    }
    public AppointmentStatus getStatus() {
        return status;
    }
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isStickied() {
        return stickied;
    }
    public void setStickied(boolean stickied) {
        this.stickied = stickied;
    }
    public Timestamp getTimeSubmitted() {
        return timeSubmitted;
    }
    public void setTimeSubmitted(Timestamp timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }
    public Timestamp getTimeEstimated() {
        return  timeEstimated;
    }
    public void setTimeEstimated(Timestamp timeEstimated) {
        this.timeEstimated = timeEstimated;
    }

    /**
     * Creates a new AppointmentGateway object based on data retrieved
     * from the connected database.
     * @param rs the results from the database retrieval query.
     * @return an AppointmentGateway with data from the provided database
     *           results.
     * @throws SQLException
     */
    protected static AppointmentGateway load(ResultSet rs) throws SQLException {
        int idArg = rs.getInt(1);
        int reporterIdArg = rs.getInt(2);
        int issueId = rs.getInt(3);
        AppointmentStatus statusArg = AppointmentStatus.valueOf(rs.getString(4).toUpperCase());
        String descriptionArg = rs.getString(5);
        boolean stickiedArg = rs.getBoolean(6);
        Timestamp timeSubmittedArg = rs.getTimestamp(7);
        Timestamp timeEstimatedArg = rs.getTimestamp(8);

        AppointmentGateway result =  new AppointmentGateway(idArg, reporterIdArg, issueId,
                statusArg, descriptionArg, stickiedArg, timeSubmittedArg, timeEstimatedArg);
        Registry.addAppointment(result);
        return result;
    }

    /**
     * Attempts to update the data of the corresponding Appointment entry in
     * the connected database with the data from the AppointmentGateway object.
     */
    public void update() {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = DBConnection.prepare(updateAppointmentStatement);
            updateStatement.setString(1, getStatus().toString());
            updateStatement.setString(2, getDescription());
            updateStatement.setBoolean(3, isStickied());
            updateStatement.setTimestamp(4, getTimeEstimated());
            updateStatement.setInt(5, getId());
            updateStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to delete the corresponding Issue entry in the
     * connected database.
     */
    public void delete() {
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = DBConnection.prepare(deleteAppointmentStatement);
            deleteStatement.setInt(1, getId());
            deleteStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to insert the data of the AppointmentGateway into the
     * connected database.
     * @return the new id returned by the database.
     */
    public int insert() {
        ResultSet autoGenerated = null;
        PreparedStatement insertStatement = null;
        try {
            insertStatement = DBConnection.prepare(insertAppointmentStatement, PreparedStatement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, getReporterId());
            insertStatement.setInt(2, getIssueId());
            insertStatement.setString(3, getStatus().toString());
            insertStatement.setString(4, getDescription());
            insertStatement.setBoolean(5, isStickied());
            insertStatement.setTimestamp(6, getTimeSubmitted());
            insertStatement.setTimestamp(7, getTimeEstimated());
            insertStatement.executeUpdate();

            autoGenerated = insertStatement.getGeneratedKeys();
            if (autoGenerated.next()) {
                setId(autoGenerated.getInt(1));
            }

            Registry.addAppointment(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getId();
    }

    /**
     * Lock a row to stop appointment change by others
     */
    public void lock() {
        PreparedStatement lockStatement = null;
        try {
            lockStatement = DBConnection.prepare(lockAppointmentStatement);
            lockStatement.setInt(1, id);
            lockStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
