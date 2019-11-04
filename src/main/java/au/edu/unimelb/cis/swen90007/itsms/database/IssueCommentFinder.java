package au.edu.unimelb.cis.swen90007.itsms.database;

import au.edu.unimelb.cis.swen90007.itsms.domain.IssueComment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The IssueCommentFinder class provides methods for retrieving IssueComment
 * data from the database.
 */
public class IssueCommentFinder {
    private static final String findIssueCommentStatementString =
            "SELECT * FROM APP.issues WHERE id = ?";

    private static final String findCommentsForIssueStatementString =
            "SELECT * FROM APP.issues WHERE issue_id = ?";

    /**
     * Retrieves the data for the issue comment associated with the
     * provided IssueComment ID, if it exists.
     * @param issueCommentId the issue comment's ID.
     * @return a IssueCommentGateway object with the issue comment's data.
     */
    public IssueCommentGateway find(int issueCommentId) {
        IssueCommentGateway result = Registry.getIssueComment(issueCommentId);
        if (result != null)
            return result;
        try {
            PreparedStatement findIssueCommentStatement =
                    DBConnection.prepare(findIssueCommentStatementString);
            findIssueCommentStatement.setInt(1, issueCommentId);
            ResultSet rs = findIssueCommentStatement.executeQuery();
            rs.next();
            result = IssueCommentGateway.load(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Retrieves the data for all issue comments associated with a specified
     * issue.
     * @param issueId the issue's ID.
     * @return a List of IssueCommentGateway objects for each associated
     *          issue comment.
     */
    public List<IssueCommentGateway> findAllCommentsForIssue(int issueId) {
        List<IssueCommentGateway> result = new ArrayList<>();
        try {
            PreparedStatement findAllCommentsStatement =
                    DBConnection.prepare(findCommentsForIssueStatementString);
            findAllCommentsStatement.setInt(1, issueId);
            ResultSet rs = findAllCommentsStatement.executeQuery();
            while (rs.next()) {
                result.add(IssueCommentGateway.load(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
