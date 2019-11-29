package au.edu.unimelb.cis.swen90007.itsms.scripts;

import au.edu.unimelb.cis.swen90007.itsms.database.*;
import au.edu.unimelb.cis.swen90007.itsms.domain.Appointment;
import au.edu.unimelb.cis.swen90007.itsms.domain.User;
import au.edu.unimelb.cis.swen90007.itsms.lock.LockManager;
import au.edu.unimelb.cis.swen90007.itsms.session.AppSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteAppointment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            response.sendRedirect("/login");
        }
        if (user == null) {
            getServletContext().getRequestDispatcher("/viewAppointments").forward(request, response);
        }

        /* Write Lock */
        try {
            LockManager.getInstance().acquireWriteLock(AppSession.refreshSession(request.getSession()).getUser());
        } catch (InterruptedException e) {
            System.out.println("Acquiring write lock when deleting appointment failed :(");
        }

        int appointmentId = Integer.parseInt(request.getParameter("appointmentid"));
        Appointment appointment = new Appointment(appointmentId);
        System.out.println(appointmentId);
        UnitOfWork unitOfWork = new UnitOfWork();
        Appointment.deleteAppointment(appointment, unitOfWork);
        unitOfWork.commit();

        /* Release Write Lock */
        LockManager.getInstance().releaseWriteLock(AppSession.refreshSession(request.getSession()).getUser());

        response.sendRedirect("/viewAppointments");
    }
}
