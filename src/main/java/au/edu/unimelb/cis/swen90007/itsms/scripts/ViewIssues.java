package au.edu.unimelb.cis.swen90007.itsms.scripts;

import au.edu.unimelb.cis.swen90007.itsms.domain.Issue;
import au.edu.unimelb.cis.swen90007.itsms.domain.User;
import au.edu.unimelb.cis.swen90007.itsms.factory.FrontEndFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ViewIssues extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = null;
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/login");
            return;
        } else {
            user = User.getUser((Integer) session.getAttribute("userId"));
            if (user == null) {
                response.sendRedirect("/login");
                return;
            }
        }

        // Build the view issues table
        String table = "";
        table += "<table class='table table-striped table-responsive-md'>";
        table += "<thead>";
        table += "<tr>";
        table += "<th scope='col'>ID</th>";
        table += "<th scope='col'>Title</th>";
        table += "<th scope='col'>Actions</th>";
        table += "</tr>";
        table += "</thead>";
        table += "<tbody>";
        List<Issue> issueList = Issue.getAllIssues();
        for (Issue issue : issueList) {
            table += "<tr>";
            table += "<th scope='row'>" +issue.getId() + "</th>";
            table += "<td>" + issue.getTitle() + "</td>";
            table += "<td><a class='btn btn-primary' href='/view?issueid=" + issue.getId() +"' role='button'>View</a>" +
                    "<a class='btn btn-secondary' href='/edit?issueid=" + issue.getId() + "' role='button'>Edit</a></td>";
            table += "</tr>";
        }
        table += "</tbody>";
        table += "</table>";

        // TODO: Order issues by if stickied
        // TODO: Add list of all issue details for issue specified in url
        // TODO: Redirect to original table if issueid in url is invalid
        // TODO: Vary page based on if Tech or Employee (Employee can only see own issues)

        // Pass the user name
        request.setAttribute("navbar", FrontEndFactory.navBarGenerator(user.getUsername()));
        request.setAttribute("header", FrontEndFactory.headerGenerator());
        request.setAttribute("table", table);
        request.setAttribute("script", FrontEndFactory.scriptGenerator());
        request.getRequestDispatcher("viewIssues.jsp").forward(request, response);
    }
}
