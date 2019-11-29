package au.edu.unimelb.cis.swen90007.itsms.database;

import au.edu.unimelb.cis.swen90007.itsms.domain.Appointment;
import au.edu.unimelb.cis.swen90007.itsms.domain.AppointmentDTO;
import au.edu.unimelb.cis.swen90007.itsms.domain.AppointmentDTOAssembler;

public class RemoteFacade {

    public void makeAppointment(AppointmentDTO appointmentDTO) {
        AppointmentDTOAssembler.makeAppointment(appointmentDTO);
    }

    public void makeAppointmentDTO(Appointment appointment) {
        AppointmentDTOAssembler.makeAppointmentDTO(appointment);
    }

}
