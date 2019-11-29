package au.edu.unimelb.cis.swen90007.itsms.domain;

import au.edu.unimelb.cis.swen90007.itsms.database.AppointmentGateway;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.sql.Timestamp;

public class AppointmentDTO implements Serializable {

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

    public AppointmentDTO(int id, int reporterId, int issueId,
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

    public AppointmentDTO(int reporterId, int issueId,
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

    public AppointmentDTO(AppointmentGateway appointmentGateway) {
        this.id = appointmentGateway.getId();
        this.reporterId = appointmentGateway.getReporterId();
        this.issueId = appointmentGateway.getIssueId();
        this.status = appointmentGateway.getStatus();
        this.description = appointmentGateway.getDescription();
        this.stickied = appointmentGateway.isStickied();
        this.timeSubmitted = appointmentGateway.getTimeSubmitted();
        this.timeEstimated = appointmentGateway.getTimeEstimated();
    }

    public AppointmentDTO(int reporterId, int issueId,
                       AppointmentStatus status, String description,
                       boolean stickied, Timestamp timeSubmitted) {
        this.reporterId = reporterId;
        this.issueId = issueId;
        this.status = status;
        this.description = description;
        this.stickied = stickied;
        this.timeSubmitted = timeSubmitted;
    }

    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.reporterId = appointment.getReporterId();
        this.issueId = appointment.getIssueId();
        this.status = appointment.getStatus();
        this.description = appointment.getDescription();
        this.stickied = appointment.isStickied();
        this.timeSubmitted = appointment.getTimeSubmitted();
        this.timeEstimated = appointment.getTimeEstimated();
    }

    public static void toXML(AppointmentDTO appointmentDTO, OutputStream outputStream){
        XMLEncoder encoder = new XMLEncoder(outputStream);
        encoder.writeObject(appointmentDTO);
        encoder.close();
    }

    public static AppointmentDTO fromXML(InputStream inputStream) {
        XMLDecoder decoder = new XMLDecoder(inputStream);
        AppointmentDTO result = (AppointmentDTO) decoder.readObject();
        decoder.close();
        return result;
    }


}
