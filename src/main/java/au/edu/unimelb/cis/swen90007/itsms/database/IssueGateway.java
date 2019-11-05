package au.edu.unimelb.cis.swen90007.itsms.database;

import au.edu.unimelb.cis.swen90007.itsms.domain.IssueStatus;

import java.sql.*;

/**
 * The IssueGateway class provides a data-layer interface between
 * local Issue objects and database Issue objects, including
 * methods fot creating, updating, and deleting Issue object data
 * in the connected database.
 */
public class IssueGateway implements IGateway {

    private int id;
    private int reporterId;
    private String title;
    private IssueStatus status;
    private String description;
    private boolean stickied;
    private Timestamp timeSubmitted;

    private static final String updateIssueStatement =
            "UPDATE APP.issues" +
                    " SET status = ?, description = ?, stickied = ?" +
                    " WHERE id = ?";

    private static final String insertIssueStatement =
            "INSERT INTO APP.issues(reporter_id, title, status, description, stickied, time_submitted)" +
                    " VALUES (?, ?, ?, ?, ?, ?)";

    private static final String deleteIssueStatement =
            "DELETE FROM APP.issues WHERE id = ?";

    public IssueGateway(int id, int reporterId, String title,
                        IssueStatus status, String description,
                        boolean stickied, Timestamp timeSubmitted) {
        this.id = id;
        this.reporterId = reporterId;
        this.title = title;
        this.status = status;
        this.description = description;
        this.stickied = stickied;
        this.timeSubmitted = timeSubmitted;
    }

    public IssueGateway(int reporterId, String title, IssueStatus status,
                        String description, boolean stickied,
                        Timestamp timeSubmitted) {
        this.reporterId = reporterId;
        this.title = title;
        this.status = status;
        this.description = description;
        this.stickied = stickied;
        this.timeSubmitted = timeSubmitted;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
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

    /**
     * Creates a new IssueGateway object based on data retrieved
     * from the connected database.
     * @param rs the results from the database retrieval query.
     * @return an IssueGateway with data from the provided database
     *           results.
     * @throws SQLException
     */
    protected static IssueGateway load(ResultSet rs) throws SQLException {
        int idArg = rs.getInt(1);
        int reporterIdArg = rs.getInt(2);
        String titleArg = rs.getString(3);
        IssueStatus statusArg = IssueStatus.valueOf(rs.getString(4).toUpperCase());
        String descriptionArg = rs.getString(5);
        boolean stickiedArg = rs.getBoolean(6);
        Timestamp timeSubmittedArg = rs.getTimestamp(7);

        IssueGateway result =  new IssueGateway(idArg, reporterIdArg, titleArg,
                statusArg, descriptionArg, stickiedArg, timeSubmittedArg);
        Registry.addIssue(result);
        return result;
    }

    /**
     * Attempts to update the data of the corresponding Issue entry in
     * the connected database with the data from the IssueGateway object.
     */
    public void update() {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = DBConnection.prepare(updateIssueStatement);
            updateStatement.setString(1, getStatus().toString());
            updateStatement.setString(2, getDescription());
            updateStatement.setBoolean(3, isStickied());
            updateStatement.setInt(4, getId());
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
            deleteStatement = DBConnection.prepare(deleteIssueStatement);
            deleteStatement.setInt(1, getId());
            deleteStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to insert the data of the IssueGateway into the
     * connected database.
     * @return the new id returned by the database.
     */
    public int insert() {
        ResultSet autoGenerated = null;
        PreparedStatement insertStatement = null;
        try {
            insertStatement = DBConnection.prepare(insertIssueStatement, PreparedStatement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, getReporterId());
            insertStatement.setString(2, getTitle());
            insertStatement.setString(3, getStatus().toString());
            insertStatement.setString(4, getDescription());
            insertStatement.setBoolean(5, isStickied());
            insertStatement.setTimestamp(6, getTimeSubmitted());
            insertStatement.executeUpdate();

            autoGenerated = insertStatement.getGeneratedKeys();
            if (autoGenerated.next()) {
                setId(autoGenerated.getInt(1));
            }

            Registry.addIssue(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getId();
    }
}
