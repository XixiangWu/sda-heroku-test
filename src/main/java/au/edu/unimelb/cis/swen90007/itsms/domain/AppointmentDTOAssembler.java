package au.edu.unimelb.cis.swen90007.itsms.domain;

import au.edu.unimelb.cis.swen90007.itsms.database.AppointmentFinder;
import au.edu.unimelb.cis.swen90007.itsms.database.AppointmentGateway;
import au.edu.unimelb.cis.swen90007.itsms.database.Registry;
import au.edu.unimelb.cis.swen90007.itsms.database.UnitOfWork;

public class AppointmentDTOAssembler {

    public static AppointmentDTO makeAppointmentDTO(Appointment appointment) {
        AppointmentDTO result = new AppointmentDTO(appointment);
        return result;
    }

    public static Appointment makeAppointment(AppointmentDTO appointmentDTO) {
        Appointment result = new Appointment(
                appointmentDTO.getId(),
                appointmentDTO.getReporterId(),
                appointmentDTO.getIssueId(),
                appointmentDTO.getStatus(),
                appointmentDTO.getDescription(),
                appointmentDTO.isStickied(),
                appointmentDTO.getTimeSubmitted(),
                appointmentDTO.getTimeEstimated());
        return result;
    }

    public static void updateAppointment(AppointmentDTO appointmentDTO) {
        AppointmentFinder appointmentFinder = new AppointmentFinder();
        Appointment appointment = new Appointment(appointmentFinder.find(appointmentDTO.getId()));
        UnitOfWork unitOfWork = new UnitOfWork();
        Appointment.updateAppointment(appointment, unitOfWork);
        unitOfWork.commit();
    }


}
