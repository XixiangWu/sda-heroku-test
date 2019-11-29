package au.edu.unimelb.cis.swen90007.itsms.scripts;

import au.edu.unimelb.cis.swen90007.itsms.database.*;
import au.edu.unimelb.cis.swen90007.itsms.domain.Appointment;
import au.edu.unimelb.cis.swen90007.itsms.domain.AppointmentStatus;
import au.edu.unimelb.cis.swen90007.itsms.domain.Tech;
import au.edu.unimelb.cis.swen90007.itsms.domain.User;
import au.edu.unimelb.cis.swen90007.itsms.factory.FrontEndFactory;
import au.edu.unimelb.cis.swen90007.itsms.session.AppSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTimeEstimatedAppointment extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /* Session Verification */
        User user = null;
        if (AppSession.isAuthenticated()) {
            /* All user roles can access this page */
            if (AppSession.hasRole(AppSession.TECH_ROLE)) {
                user = AppSession.getUser();
            }
        } else {
            response.sendRedirect("/login");
        }
        if (user == null) {
            getServletContext().getRequestDispatcher("/viewAppointments").forward(request, response);
        }


//        HttpSession session = request.getSession(true);
//        session.setAttribute("currentSessionUser", user.getUsername());
//
//
//        if (request.getSession().getAttribute("currentSessionUser") == null) {
//            response.sendRedirect("/login");
//            return;
//        }

        String appointmentId = request.getParameter("appointmentid");
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        Timestamp timestamp_estimated = Timestamp.valueOf(
                request.getParameter("date") + " " + request.getParameter("time") + ":00.000000000");

        AppointmentFinder appointmentFinder = new AppointmentFinder();
        AppointmentGateway appointmentGateway = appointmentFinder.find(Integer.parseInt(appointmentId));
        Appointment appointment = new Appointment(appointmentGateway);
        appointment.setTimeEstimated(timestamp_estimated);
        UnitOfWork unitOfWork = new UnitOfWork();
        Appointment.updateAppointment(appointment, unitOfWork);
        unitOfWork.commit();
        String url = "/acceptAppointment?appointmentid=" + appointmentId;
        response.sendRedirect(url);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /* Session Verification */
        User user = null;
        if (AppSession.isAuthenticated()) {
            /* All user roles can access this page */
            if (AppSession.hasRole(AppSession.TECH_ROLE)) {
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

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOptions = "";

        // 3 days in advance at top
        for (int i = 1; i < 4; i++ ) {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, i);
            dateOptions += "<option value=\"" + dateFormat.format(cal.getTime()) + "\" selected>" + dateFormat.format(cal.getTime()) + "</option>\n";
        }

        String timeOptions = "";
        List<String> timeLst = new ArrayList<>();
        timeLst.add(":00");
        timeLst.add(":15");
        timeLst.add(":30");
        timeLst.add(":45");
        int startTime = 9;

        // 9:00 ~ 18:00
        for (int i = 0; i < 9; i++ ) {
            for (String time: timeLst) {
                timeOptions += "<option value=\"" + (startTime+i) + time + "\" selected>" + (startTime+i) + time + "</option>\n";
            }
        }


        request.setAttribute("options", options);
        request.setAttribute("description", appointmentGateway.getDescription());
        request.setAttribute("appointmentid", appointmentGateway.getId());
        request.setAttribute("dateOptions", dateOptions);
        request.setAttribute("timeOptions", timeOptions);

        getServletContext().getRequestDispatcher("/addTimeEstimatedAppointment.jsp").forward(request, response);

    }
}
