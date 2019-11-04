package au.edu.unimelb.cis.swen90007.itsms.domain;

import au.edu.unimelb.cis.swen90007.itsms.database.IssueFinder;
import au.edu.unimelb.cis.swen90007.itsms.database.IssueGateway;
import au.edu.unimelb.cis.swen90007.itsms.database.UnitOfWork;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Issue class defines the Domain layer interface
 * for Issue data,
 */
public class Issue {

    private int id;

    private int reporterId;

    private String title;

    private IssueStatus status;

    private String description;

    private boolean stickied;

    private Timestamp timeSubmitted;

    public Issue(int id, int reporterId, String title, IssueStatus status,
                 String description, boolean stickied, Timestamp timeSubmitted) {
        this.id = id;
        this.reporterId = reporterId;
        this.title = title;
        this.status = status;
        this.description = description;
        this.stickied = stickied;
        this.timeSubmitted = timeSubmitted;
    }

    public Issue(IssueGateway issueGateway) {
        this.id = issueGateway.getId();
        this.reporterId = issueGateway.getReporterId();
        this.title = issueGateway.getTitle();
        this.status = issueGateway.getStatus();
        this.description = issueGateway.getDescription();
        this.stickied = issueGateway.isStickied();
        this.timeSubmitted = issueGateway.getTimeSubmitted();
    }

    public Issue(int reporterId, String title, IssueStatus status,
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


    public static Issue getIssue(int issueId) {
        IssueFinder finder = new IssueFinder();
        IssueGateway issue = finder.find(issueId);
        if (issue == null)
            return null;
        return new Issue(issue);
    }

    public static List<Issue> getAllIssues() {
        IssueFinder finder = new IssueFinder();
        List<Issue> result = new ArrayList<>();
        List<IssueGateway> issueGateways = finder.findAllIssues();

        for (IssueGateway issueGateway : issueGateways) {
            Issue issue = new Issue(issueGateway);
            result.add(issue);
        }
        return result;
    }

    public static void updateIssue(Issue issue, UnitOfWork unitOfWork) {
        IssueFinder finder = new IssueFinder();
        IssueGateway issueGateway = finder.find(issue.getId());
        issueGateway.setStatus(issue.getStatus());
        issueGateway.setDescription(issue.getDescription());
        issueGateway.setStickied(issue.isStickied());
        unitOfWork.registerDirty(issueGateway);
    }

    public static void createIssue(Issue issue, UnitOfWork unitOfWork) {
        IssueGateway issueGateway = new IssueGateway(issue.getReporterId(),
                issue.getTitle(), issue.getStatus(), issue.getDescription(),
                issue.isStickied(), issue.getTimeSubmitted());
        unitOfWork.registerNew(issueGateway);
    }

    public static void deleteIssue(Issue issue, UnitOfWork unitOfWork) {
        IssueFinder finder = new IssueFinder();
        IssueGateway issueGateway = finder.find(issue.getId());
        unitOfWork.registerDeleted(issueGateway);
    }
}
