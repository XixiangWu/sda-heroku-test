package au.edu.unimelb.cis.swen90007.itsms.scripts;

import au.edu.unimelb.cis.swen90007.itsms.database.UnitOfWork;
import au.edu.unimelb.cis.swen90007.itsms.domain.Issue;
import au.edu.unimelb.cis.swen90007.itsms.domain.IssueStatus;
import au.edu.unimelb.cis.swen90007.itsms.domain.Tech;
import au.edu.unimelb.cis.swen90007.itsms.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SubmitIssue extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = null;
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/login");
            return;
        } else {
            user = User.getUser((Integer) session.getAttribute("userId"));
            if (user instanceof Tech) {
                response.sendRedirect("/view");
                return;
            }
        }
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        Issue issue = new Issue(user.getId(), title, IssueStatus.OPEN,
                description, false, timestamp);
        UnitOfWork unitOfWork = new UnitOfWork();
        Issue.createIssue(issue, unitOfWork);
        unitOfWork.commit();
        response.sendRedirect("/view");

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
            if (user instanceof Tech) {
                response.sendRedirect("/view");
                return;
            }
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">");
        out.println("<title>Submit Issue</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<div class='row justify-content-center'>");
        out.println("<div class='col-md-8'>");
        out.println("<div class='card'>");
        out.println("<div class='card-header'>Submit Issue</div>");
        out.println("<div class='card-body'>");
        out.println("<form action='' method='post'>");
        out.println("<div class='form-group row'>");
        out.println("<label for='title' class='col-md-4 col-form-label text-md-right'>Title</label>");
        out.println("<div class='col-md-6'>");
        out.println("<input type='text' id='title' class='form-control' name='title' required autofocus>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='form-group row'>");
        out.println("<label for='description' class='col-md-4 col-form-label text-md-right'>Description</label>");
        out.println("<div class='col-md-6'>");
        out.println("<textarea id='description' class='form-control' rows='10' name='description' required></textarea>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='col-md-6 offset-md-4'>");
        out.println("<button type='submit' class='btn btn-primary'>Submit</button>");
        out.println("</div>");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        out.println("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }
}
