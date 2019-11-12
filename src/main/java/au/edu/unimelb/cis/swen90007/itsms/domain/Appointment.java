package au.edu.unimelb.cis.swen90007.itsms.domain;

import au.edu.unimelb.cis.swen90007.itsms.database.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The Issue class defines the Domain layer interface
 * for Issue data,
 */
public class Appointment {

    private int id;
    private int reporterId;
    private int issueId;
    private AppointmentStatus status;
    private String description;
    private boolean stickied;
    private Timestamp timeSubmitted;
    private Timestamp timeEstimated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReporterId() {
        return reporterId;
    }

    public void setReporterId(int reporterId) {
        this.reporterId = reporterId;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStickied() {
        return stickied;
    }

    public void setStickied(boolean stickied) {
        this.stickied = stickied;
    }

    public Timestamp getTimeSubmitted() {
        return timeSubmitted;
    }

    public void setTimeSubmitted(Timestamp timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    public Timestamp getTimeEstimated() {
        return timeEstimated;
    }

    public void setTimeEstimated(Timestamp timeEstimated) {
        this.timeEstimated = timeEstimated;
    }

    public Appointment(int id, int reporterId, int issueId,
                       AppointmentStatus status, String description,
                       boolean stickied, Timestamp timeSubmitted, Timestamp timeEstimated) {
        this.id = id;
        this.reporterId = reporterId;
        this.issueId = issueId;
        this.status = status;
        this.description = description;
        this.stickied = stickied;
        this.timeSubmitted = timeSubmitted;
        this.timeEstimated = timeEstimated;
    }

    public Appointment(int reporterId, int issueId,
                              AppointmentStatus status, String description,
                              boolean stickied, Timestamp timeSubmitted, Timestamp timeEstimated) {
        this.reporterId = reporterId;
        this.issueId = issueId;
        this.status = status;
        this.description = description;
        this.stickied = stickied;
        this.timeSubmitted = timeSubmitted;
        this.timeEstimated = timeEstimated;
    }

    public Appointment(AppointmentGateway appointmentGateway) {
        this.id = appointmentGateway.getId();
        this.reporterId = appointmentGateway.getReporterId();
        this.issueId = appointmentGateway.getIssueId();
        this.status = appointmentGateway.getStatus();
        this.description = appointmentGateway.getDescription();
        this.stickied = appointmentGateway.isStickied();
        this.timeSubmitted = appointmentGateway.getTimeSubmitted();
        this.timeEstimated = appointmentGateway.getTimeEstimated();
    }

    public Appointment(int reporterId, int issueId,
                              AppointmentStatus status, String description,
                              boolean stickied, Timestamp timeSubmitted) {
        this.reporterId = reporterId;
        this.issueId = issueId;
        this.status = status;
        this.description = description;
        this.stickied = stickied;
        this.timeSubmitted = timeSubmitted;
    }

    public Appointment(int id) {
        this.id = id;
    }


    public static Appointment getAppointment(int appointmentId) {
        AppointmentFinder finder = new AppointmentFinder();
        AppointmentGateway appointment = finder.find(appointmentId);
        if (appointment == null)
            return null;
        return new Appointment(appointment);
    }

    public static List<Appointment> getAllAppointments() {
        AppointmentFinder finder = new AppointmentFinder();
        List<Appointment> result = new ArrayList<>();
        List<AppointmentGateway> appointmentGateways = finder.findAllAppointments();

        for (AppointmentGateway appointmentGateway : appointmentGateways) {
            Appointment appointment = new Appointment(appointmentGateway);
            result.add(appointment);
        }
        return result;
    }

    public static List<Appointment> getAppointmentsByUser(int userid) {
        AppointmentFinder finder = new AppointmentFinder();
        List<Appointment> result = new ArrayList<>();
        List<AppointmentGateway> appointmentGateways = finder.findAppointmentsByUser(userid);

        for (AppointmentGateway appointmentGateway : appointmentGateways) {
            Appointment appointment = new Appointment(appointmentGateway);
            result.add(appointment);
        }
        return result;
    }

    public static void updateAppointment(Appointment appointment, UnitOfWork unitOfWork) {
        AppointmentFinder finder = new AppointmentFinder();
        AppointmentGateway appointmentGateway = finder.find(appointment.getId());
        appointmentGateway.setId(appointment.getId());
        appointmentGateway.setReporterId(appointment.getReporterId());
        appointmentGateway.setIssueId(appointment.issueId);
        appointmentGateway.setStatus(appointment.getStatus());
        appointmentGateway.setDescription(appointment.getDescription());
        appointmentGateway.setStickied(appointment.isStickied());
        appointmentGateway.setTimeSubmitted(appointment.getTimeSubmitted());
        appointmentGateway.setTimeEstimated(appointment.getTimeEstimated());
        unitOfWork.registerDirty(appointmentGateway);
    }

    public static void createAppointment(Appointment appointment, UnitOfWork unitOfWork) {
        AppointmentGateway appointmentGateway = new AppointmentGateway(appointment.getReporterId(),
                appointment.getIssueId(), appointment.getStatus(), appointment.getDescription(),
                appointment.isStickied(), appointment.getTimeSubmitted(), appointment.getTimeEstimated());
        unitOfWork.registerNew(appointmentGateway);
    }

    public static void deleteAppointment(Appointment appointment, UnitOfWork unitOfWork) {
        AppointmentFinder finder = new AppointmentFinder();
        AppointmentGateway appointmentGateway = finder.find(appointment.getId());
        unitOfWork.registerDeleted(appointmentGateway);
    }
}
