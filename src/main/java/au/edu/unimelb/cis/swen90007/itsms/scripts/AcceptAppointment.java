package au.edu.unimelb.cis.swen90007.itsms.scripts;

import au.edu.unimelb.cis.swen90007.itsms.database.AppointmentFinder;
import au.edu.unimelb.cis.swen90007.itsms.database.AppointmentGateway;
import au.edu.unimelb.cis.swen90007.itsms.database.UnitOfWork;
import au.edu.unimelb.cis.swen90007.itsms.domain.Appointment;
import au.edu.unimelb.cis.swen90007.itsms.domain.AppointmentStatus;
import au.edu.unimelb.cis.swen90007.itsms.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AcceptAppointment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        String appointmentId = request.getParameter("appointmentid");
        AppointmentFinder appointmentFinder = new AppointmentFinder();
        AppointmentGateway appointmentGateway = appointmentFinder.find(Integer.parseInt(appointmentId));
        Appointment appointment = new Appointment(appointmentGateway);
        appointment.setStatus(AppointmentStatus.ACCEPTED);
        UnitOfWork unitOfWork = new UnitOfWork();
        Appointment.updateAppointment(appointment, unitOfWork);
        unitOfWork.commit();
        response.sendRedirect("/viewAppointments");
    }
}
