package au.edu.unimelb.cis.swen90007.itsms.scripts;

import au.edu.unimelb.cis.swen90007.itsms.database.IssueFinder;
import au.edu.unimelb.cis.swen90007.itsms.domain.*;
import au.edu.unimelb.cis.swen90007.itsms.factory.FrontEndFactory;
import au.edu.unimelb.cis.swen90007.itsms.session.AppSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ViewAppointments extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /* Session Verification */
        User user = null;
        if (AppSession.isAuthenticated()) {
            /* All user roles can access this page */
            if (AppSession.hasRole(AppSession.EMPLOYEE_ROLE) ||
                AppSession.hasRole(AppSession.TECH_ROLE)) {
                user = AppSession.getUser();
            }
        } else {
            response.sendRedirect("/login.jsp");
        }

        // Build the view issues table
        String table = "";
        table += "<table class='table table-striped table-responsive-md'>";
        table += "<thead>";
        table += "<tr>";
        table += "<th scope='col'>ID</th>";
        table += "<th scope='col'>Issue</th>";
        table += "<th scope='col'>Status</th>";
        table += "<th scope='col'>Description</th>";
        table += "<th scope='col'>Time Submitted</th>";
        table += "<th scope='col'>Time Estimated</th>";
        table += "<th scope='col'>Actions</th>";
        table += "</tr>";
        table += "</thead>";
        table += "<tbody>";

        List<Appointment> appointmentsList = new ArrayList<Appointment>();
        IssueFinder issueFinder = new IssueFinder();

        if (user instanceof Employee) {
            appointmentsList = Appointment.getAppointmentsByUser(user.getId());
            for (Appointment appointment : appointmentsList) {
                table += "<tr>";
                table += "<th scope='row'>" + appointment.getId() + "</th>";
                table += "<td>" + issueFinder.find(appointment.getIssueId()).getTitle() + "</td>";
                table += "<td>" + FrontEndFactory.appointmentBadgeGenerator(appointment.getStatus().toString()) + "</td>";
                table += "<td>" + appointment.getDescription() + "</td>";
                table += "<td>" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(appointment.getTimeSubmitted()) + "</td>";
                if (appointment.getTimeEstimated() == null) {
                    table += "<td> - </td>";
                } else {
                    table += "<td>" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(appointment.getTimeEstimated()) + "</td>";
                }
                table += "<td><a class='btn btn-primary' href='/viewAppointment?appointmentid=" + appointment.getId() + "' role='button'>View</a>" +
                        "<a class='btn btn-primary' href='/editAppointment?appointmentid=" + appointment.getId() + "' role='button'>Edit</a>" +
                        "<a class='btn btn-secondary' href='/deleteAppointment?appointmentid=" + appointment.getId() + "' role='button'>Delete</a></td>";
                table += "</tr>";
            }
            table += "</tbody>";
            table += "</table>";

        } else if (user instanceof Tech) {

            appointmentsList = Appointment.getAllAppointments();
            for (Appointment appointment : appointmentsList) {
                table += "<tr>";
                table += "<th scope='row'>" + appointment.getId() + "</th>";
                table += "<td>" + issueFinder.find(appointment.getIssueId()).getTitle() + "</td>";
                table += "<td>" + FrontEndFactory.appointmentBadgeGenerator(appointment.getStatus().toString()) + "</td>";
                table += "<td>" + appointment.getDescription() + "</td>";
                table += "<td>" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(appointment.getTimeSubmitted()) + "</td>";
                if (appointment.getTimeEstimated() == null) {
                    table += "<td> - </td>";
                } else {
                    table += "<td>" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(appointment.getTimeEstimated()) + "</td>";
                }
                table += "<td><a class='btn btn-primary' href='/viewAppointment?appointmentid=" + appointment.getId() + "' role='button'>View</a>" +
                        "<a class='btn btn-primary' href='/addTimeEstimatedAppointment?appointmentid=" + appointment.getId() + "' role='button'>Add Time</a>" +
                        "<a class='btn btn-secondary' href='/deleteAppointment?appointmentid=" + appointment.getId() + "' role='button'>Delete</a></td>";
                table += "</tr>";
            }
            table += "</tbody>";
            table += "</table>";
        }

        request.setAttribute("table", table);
        getServletContext().getRequestDispatcher("/viewAppointments.jsp").forward(request, response);
    }
}
