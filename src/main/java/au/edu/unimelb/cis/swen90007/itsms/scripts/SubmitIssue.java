package au.edu.unimelb.cis.swen90007.itsms.scripts;

import au.edu.unimelb.cis.swen90007.itsms.database.*;
import au.edu.unimelb.cis.swen90007.itsms.domain.Issue;
import au.edu.unimelb.cis.swen90007.itsms.domain.IssueStatus;
import au.edu.unimelb.cis.swen90007.itsms.domain.Tech;
import au.edu.unimelb.cis.swen90007.itsms.domain.User;
import au.edu.unimelb.cis.swen90007.itsms.factory.FrontEndFactory;
import au.edu.unimelb.cis.swen90007.itsms.session.AppSession;

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

        /* Session Verification */
        User user = null;
        if (AppSession.isAuthenticated()) {
            /* All user roles can access this page */
            if (AppSession.hasRole(AppSession.EMPLOYEE_ROLE)) {
                user = AppSession.getUser();
            } else {
                response.sendRedirect("/viewAppointments");
            }
        } else {
            response.sendRedirect("/login");
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

        /* Session Verification */
        User user = null;
        if (AppSession.isAuthenticated()) {
            /* All user roles can access this page */
            if (AppSession.hasRole(AppSession.EMPLOYEE_ROLE)) {
                user = AppSession.getUser();
            }
        } else {
            response.sendRedirect("/login");
        }
        if (user == null) {
            getServletContext().getRequestDispatcher("/viewAppointments").forward(request, response);
        }

        String submit = "";
        submit += "<form action='' method='post'>";
        submit += "<div class='form-group row'>";
        submit += "<label for='title' class='col-md-4 col-form-label text-md-right'>Title</label>";
        submit += "<div class='col-md-6'>";
        submit += "<input type='text' id='title' class='form-control' name='title' required autofocus>";
        submit += "</div>";
        submit += "</div>";
        submit += "<div class='form-group row'>";
        submit += "<label for='description' class='col-md-4 col-form-label text-md-right'>Description</label>";
        submit += "<div class='col-md-6'>";
        submit += "<textarea id='description' class='form-control' rows='10' name='description' required></textarea>";
        submit += "</div>";
        submit += "</div>";
        submit += "<div class='col-md-6 offset-md-4'>";
        submit += "<button type='submit' class='btn btn-primary'>Submit</button>";
        submit += "</div>";
        submit += "</div>";
        submit += "</form>";

        request.setAttribute("navbar", FrontEndFactory.navBarGenerator("username"));
        request.setAttribute("submit", submit);
        request.setAttribute("script", FrontEndFactory.scriptGenerator());
        request.getRequestDispatcher("submitNewIssue.jsp").forward(request, response);

    }
}
