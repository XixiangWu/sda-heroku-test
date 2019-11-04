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
public class IssueFinder {

    private static final String findIssueStatementString =
            "SELECT * FROM APP.issues WHERE id = ?";

    private static final String findAllIssuesStatementString =
            "SELECT * FROM APP.issues";

    /**
     * Retrieves the data for the issue associated with the
     * provided Issue ID, if it exists.
     * @param issueId the issue's ID.
     * @return a IssueGateway object with the issue's data.
     */
    public IssueGateway find(int issueId) {
        IssueGateway result = Registry.getIssue(issueId);
        if (result != null)
            return result;
        try {
            PreparedStatement findIssueStatement = DBConnection.prepare(findIssueStatementString);
            findIssueStatement.setInt(1, issueId);
            ResultSet rs = findIssueStatement.executeQuery();
            rs.next();
            result = IssueGateway.load(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Retrieves the data for all issues in the connected database.
     * @return a List of IssueGateway objects for each issue.
     */
    public List<IssueGateway> findAllIssues() {
        List<IssueGateway> result = new ArrayList<>();
        try {
            PreparedStatement findAllIssuesStatement =
                    DBConnection.prepare(findAllIssuesStatementString);
            ResultSet rs = findAllIssuesStatement.executeQuery();
            while (rs.next()) {
                result.add(IssueGateway.load(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
