package au.edu.unimelb.cis.swen90007.itsms.domain;

import au.edu.unimelb.cis.swen90007.itsms.database.IssueCommentFinder;
import au.edu.unimelb.cis.swen90007.itsms.database.IssueCommentGateway;
import au.edu.unimelb.cis.swen90007.itsms.database.UnitOfWork;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The IssueComment class defines the Domain layer interface
 * for IssueComment data,
 */
public class IssueComment {

    private int id;

    private int issueId;

    private int posterId;

    private String content;

    private Timestamp timeSubmitted;

    public IssueComment(int id, int issueId, int posterId, String content,
                        Timestamp timeSubmitted) {
        this.id = id;
        this.issueId = issueId;
        this.posterId = posterId;
        this.content = content;
        this.timeSubmitted = timeSubmitted;
    }

    public IssueComment(IssueCommentGateway issueComment) {
        this.id = issueComment.getId();
        this.issueId = issueComment.getIssueId();
        this.posterId = issueComment.getPosterId();
        this.content = issueComment.getContent();
        this.timeSubmitted = issueComment.getTimeSubmitted();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public int getPosterId() {
        return posterId;
    }

    public void setPosterId(int posterId) {
        this.posterId = posterId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimeSubmitted() {
        return timeSubmitted;
    }

    public void setTimeSubmitted(Timestamp timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    public static IssueComment getIssueComment(int issueCommentId) {
        IssueCommentFinder finder = new IssueCommentFinder();
        IssueCommentGateway issueComment = finder.find(issueCommentId);
        if (issueComment == null)
            return null;
        return new IssueComment(issueComment);
    }

    public static List<IssueComment> getAllCommentsForIssue(int issueId) {
        IssueCommentFinder finder = new IssueCommentFinder();
        List<IssueComment> result = new ArrayList<>();
        List<IssueCommentGateway> issueCommentGateways =
                finder.findAllCommentsForIssue(issueId);

        for (IssueCommentGateway issueCommentGateway : issueCommentGateways) {
            IssueComment issueComment = new IssueComment(issueCommentGateway);
            result.add(issueComment);
        }
        return result;
    }

    public static void updateIssueComment(IssueComment issueComment,
                                          UnitOfWork unitOfWork) {
        IssueCommentFinder finder = new IssueCommentFinder();
        IssueCommentGateway issueGateway = finder.find(issueComment.getId());
        issueGateway.setContent(issueComment.getContent());
        unitOfWork.registerDirty(issueGateway);
    }

    public static void createIssueComment(IssueComment issueComment,
                                          UnitOfWork unitOfWork) {
        IssueCommentGateway issueCommentGateway = new IssueCommentGateway(
                issueComment.getIssueId(), issueComment.getPosterId(),
                issueComment.getContent(), issueComment.getTimeSubmitted());
        unitOfWork.registerNew(issueCommentGateway);
    }

    public static void deleteIssueComment(IssueComment issueComment,
                                          UnitOfWork unitOfWork) {
        IssueCommentFinder finder = new IssueCommentFinder();
        IssueCommentGateway issueGateway = finder.find(issueComment.getId());
        unitOfWork.registerDeleted(issueGateway);
    }

}
