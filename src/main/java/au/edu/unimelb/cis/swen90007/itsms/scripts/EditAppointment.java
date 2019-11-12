package au.edu.unimelb.cis.swen90007.itsms.scripts;

import au.edu.unimelb.cis.swen90007.itsms.database.*;
import au.edu.unimelb.cis.swen90007.itsms.domain.*;
import au.edu.unimelb.cis.swen90007.itsms.factory.FrontEndFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class EditAppointment extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Session Check
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

        String appointmentId = request.getParameter("appointmentid");
        String description = request.getParameter("description");
        String issueId = request.getParameter("issueid");
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        Appointment appointment = new Appointment(Integer.parseInt(appointmentId), user.getId(),
                Integer.parseInt(issueId), AppointmentStatus.WAITING, description, false, timestamp, null);

        UnitOfWork unitOfWork = new UnitOfWork();
        Appointment.updateAppointment(appointment, unitOfWork);
        unitOfWork.commit();
        response.sendRedirect("/viewAppointments");
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
        request.setAttribute("navbar", FrontEndFactory.navBarGenerator(user.getFirstName() + " " + user.getLastName()));
        request.setAttribute("script", FrontEndFactory.scriptGenerator());
        request.setAttribute("description", appointmentGateway.getDescription());
        request.setAttribute("appointmentid", appointmentGateway.getId());
        request.getRequestDispatcher("editAppointment.jsp").forward(request, response);
    }
}