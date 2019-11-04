package au.edu.unimelb.cis.swen90007.itsms.scripts;

import au.edu.unimelb.cis.swen90007.itsms.domain.Issue;
import au.edu.unimelb.cis.swen90007.itsms.domain.User;

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
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">");
        if (request.getParameter("issueid") == null)
            out.println("<title>View All Issues</title>");
        else
            out.println("<title>View Issue " + request.getParameter("issueid") + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<div class='row justify-content-center'>");
        out.println("<div class='col-md-10'>");
        out.println("<h2>Issues List</h2>");
        out.println("<h3>Hello " + user.getFirstName() + "</h3>");
        out.println("<table class='table table-bordered'>");
        out.println("<thead class='thead-dark'>");
        out.println("<tr>");
        out.println("<th scope='col'>ID</th>");
        out.println("<th scope='col'>Title</th>");
        out.println("<th scope='col'>Actions</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        List<Issue> issueList = Issue.getAllIssues();
        for (Issue issue : issueList) {
            out.println("<tr>");
            out.println("<th scope='row'>" +issue.getId() + "</th>");
            out.println("<td>" + issue.getTitle() + "</td>");
            out.println("<td><a class='btn btn-primary' href='/view?issueid=" + issue.getId() +"' role='button'>View</a>" +
                    "<a class='btn btn-secondary' href='/edit?issueid=" + issue.getId() + "' role='button'>Edit</a></td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        // TODO: Order issues by if stickied
        // TODO: Add list of all issue details for issue specified in url
        // TODO: Redirect to original table if issueid in url is invalid
        // TODO: Vary page based on if Tech or Employee (Employee can only see own issues)
        out.println("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }
}
