package au.edu.unimelb.cis.swen90007.itsms.scripts;

import au.edu.unimelb.cis.swen90007.itsms.database.AppointmentGateway;
import au.edu.unimelb.cis.swen90007.itsms.database.IssueFinder;
import au.edu.unimelb.cis.swen90007.itsms.database.Registry;
import au.edu.unimelb.cis.swen90007.itsms.domain.Appointment;
import au.edu.unimelb.cis.swen90007.itsms.domain.Employee;
import au.edu.unimelb.cis.swen90007.itsms.domain.Issue;
import au.edu.unimelb.cis.swen90007.itsms.domain.User;
import au.edu.unimelb.cis.swen90007.itsms.factory.FrontEndFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class ViewAppointment extends HttpServlet {
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

        Appointment appointment = new Appointment(Registry.getAppointment(Integer.parseInt(request.getParameter("appointmentid"))));
        Issue issue = new Issue(Registry.getIssue(appointment.getIssueId()));
        String appointmentTimeEstimated = "";
        if (appointment.getTimeEstimated() == null) {
            appointmentTimeEstimated =  "<td>No appointment time, please wait for Tech team to check. </td>";
        } else {
            appointmentTimeEstimated =  "<td>" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(appointment.getTimeEstimated()) + "</td>";
        }

        String buttons = "<a class='btn btn-primary' href='/resolveAppointment?appointmentid=" + appointment.getId() + "' role='button'>Resolve</a>";
        buttons += "<a class='btn btn-secondary' href='/deleteAppointment?appointmentid=" + appointment.getId() + "' role='button'>Delete</a>";
        // Pass the user name
        request.setAttribute("navbar", FrontEndFactory.navBarGenerator(user.getFirstName() + " " + user.getLastName()));
        request.setAttribute("user", user.getFirstName() + " " + user.getLastName());
        request.setAttribute("issue_title", issue.getTitle());
        request.setAttribute("issue_description", issue.getDescription());
        request.setAttribute("appointment_time_submitted", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(appointment.getTimeSubmitted()));
        request.setAttribute("appointment_status", FrontEndFactory.appointmentBadgeGenerator(appointment.getStatus().toString()));
        request.setAttribute("appointment_time_estimated", appointmentTimeEstimated);
        request.setAttribute("script", FrontEndFactory.scriptGenerator());
        request.setAttribute("buttons", buttons);
        request.getRequestDispatcher("viewAppointment.jsp").forward(request, response);
    }
}
