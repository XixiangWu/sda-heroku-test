package au.edu.unimelb.cis.swen90007.itsms.scripts;

import au.edu.unimelb.cis.swen90007.itsms.database.*;
import au.edu.unimelb.cis.swen90007.itsms.domain.*;
import au.edu.unimelb.cis.swen90007.itsms.factory.FrontEndFactory;
import au.edu.unimelb.cis.swen90007.itsms.lock.LockManager;
import au.edu.unimelb.cis.swen90007.itsms.session.AppSession;
import com.sun.tools.internal.ws.wsdl.document.Input;
import com.sun.tools.internal.ws.wsdl.document.Output;
import sun.misc.IOUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.channels.Channel;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class ViewAppointment extends HttpServlet {
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

        AppointmentDTO appointmentDTO = new AppointmentDTO(Registry.getAppointment(Integer.parseInt(request.getParameter("appointmentid"))));
        Issue issue = new Issue(Registry.getIssue(appointmentDTO.getIssueId()));
        UserFinder userFinder = new UserFinder();
        UserGateway submitterGateway = userFinder.findUserById(issue.getReporterId());

        String appointmentTimeEstimated = "";
        if (appointmentDTO.getTimeEstimated() == null) {
            appointmentTimeEstimated =  "<td>No appointment time, please wait for Tech team to check. </td>";
        } else {
            appointmentTimeEstimated =  "<td>" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(appointmentDTO.getTimeEstimated()) + "</td>";
        }

        String buttons = "<a class='btn btn-primary' href='/resolveAppointment?appointmentid=" + appointmentDTO.getId() + "' role='button'>Resolve</a>";
        buttons += "<a class='btn btn-secondary' href='/deleteAppointment?appointmentid=" + appointmentDTO.getId() + "' role='button'>Delete</a>";

        request.setAttribute("user", submitterGateway.getFirstName() + " " + submitterGateway.getLastName());
        request.setAttribute("issue_title", issue.getTitle());
        request.setAttribute("issue_description", issue.getDescription());
        request.setAttribute("appointment_time_submitted", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(appointmentDTO.getTimeSubmitted()));
        request.setAttribute("appointment_status", FrontEndFactory.appointmentBadgeGenerator(appointmentDTO.getStatus().toString()));
        request.setAttribute("appointment_time_estimated", appointmentTimeEstimated);
        request.setAttribute("buttons", buttons);

        getServletContext().getRequestDispatcher("/viewAppointment.jsp").forward(request, response);

    }
}
