package au.edu.unimelb.cis.swen90007.itsms.scripts;

import au.edu.unimelb.cis.swen90007.itsms.database.*;
import au.edu.unimelb.cis.swen90007.itsms.domain.*;
import au.edu.unimelb.cis.swen90007.itsms.lock.LockManager;
import au.edu.unimelb.cis.swen90007.itsms.session.AppSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class EditAppointment extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        /* Write Lock */
        try {
            LockManager.getInstance().acquireWriteLock(AppSession.refreshSession(request.getSession()).getUser());
        } catch (InterruptedException e) {
            System.out.println("Acquiring write lock when view appointment failed :(");
        }

        String appointmentId = request.getParameter("appointmentid");
        String description = request.getParameter("description");
        String issueId = request.getParameter("issueid");
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        Appointment appointment = new Appointment(Integer.parseInt(appointmentId), user.getId(),
                Integer.parseInt(issueId), AppointmentStatus.WAITING, description, false, timestamp, null);

        UnitOfWork unitOfWork = new UnitOfWork();
        Appointment.updateAppointment(appointment, unitOfWork);
        unitOfWork.commit();

        /* Release Write Lock */
        LockManager.getInstance().releaseWriteLock(AppSession.refreshSession(request.getSession()).getUser());

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

        int appointmentId = Integer.parseInt(request.getParameter("appointmentid"));
        AppointmentFinder appointmentFinder = new AppointmentFinder();
        AppointmentGateway appointmentGateway = appointmentFinder.find(appointmentId);
        IssueFinder issueFinder = new IssueFinder();
        IssueGateway issueGateway = issueFinder.find(appointmentGateway.getIssueId());

        List<IssueGateway> issueLst = issueFinder.findIssuesByUser(user.getId());

        String options = "";
        for (IssueGateway issueGatewayElement: issueLst) {
            if (issueGatewayElement.getId() != issueGateway.getId()) {
                options += "<option value=\"" + issueGatewayElement.getId() + "\">" + issueGatewayElement.getTitle() + "</option>\n";
            } else {
                options += "<option value=\"" + issueGatewayElement.getId() + "\" selected>" + issueGatewayElement.getTitle() + "</option>\n";
            }
        }

        request.setAttribute("options", options);
        request.setAttribute("description", appointmentGateway.getDescription());
        request.setAttribute("appointmentid", appointmentGateway.getId());

        getServletContext().getRequestDispatcher("/editAppointment.jsp").forward(request, response);
    }
}
