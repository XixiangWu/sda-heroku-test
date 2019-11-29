package au.edu.unimelb.cis.swen90007.itsms.scripts;

import au.edu.unimelb.cis.swen90007.itsms.database.IssueFinder;
import au.edu.unimelb.cis.swen90007.itsms.database.IssueGateway;
import au.edu.unimelb.cis.swen90007.itsms.database.UnitOfWork;
import au.edu.unimelb.cis.swen90007.itsms.domain.*;
import au.edu.unimelb.cis.swen90007.itsms.session.AppSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class SubmitAppointment extends HttpServlet {
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

        int issueId = Integer.parseInt(request.getParameter("issueid"));
        String description = request.getParameter("description");
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        Appointment appointment = new Appointment(user.getId(), issueId, AppointmentStatus.WAITING,
                description, false, timestamp);
        UnitOfWork unitOfWork = new UnitOfWork();
        Appointment.createAppointment(appointment, unitOfWork);
        unitOfWork.commit();

        response.sendRedirect("/viewAppointments");
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


        IssueFinder issueFinder = new IssueFinder();
        List<IssueGateway> issueLst = issueFinder.findIssuesByUser(user.getId());

        String options = "";
        for (IssueGateway issueGateway: issueLst) {
            options += "<option value=\"" + issueGateway.getId() + "\">" + issueGateway.getTitle() + "</option>\n";
        }

        request.setAttribute("options", options);
        getServletContext().getRequestDispatcher("/submitNewAppointment.jsp").forward(request, response);

    }
}
