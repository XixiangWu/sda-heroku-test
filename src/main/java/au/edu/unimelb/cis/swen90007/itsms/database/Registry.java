package au.edu.unimelb.cis.swen90007.itsms.database;

import java.util.HashMap;
import java.util.Map;

/**
 * The Registry class provides an Identity Map implementation for all
 * Gateway base classes.
 */
public class Registry {

    private static Map<Integer, UserGateway> userMap = new HashMap<>();
    private static Map<Integer, IssueGateway> issueMap = new HashMap<>();
    private static Map<Integer, IssueCommentGateway> issueCommentMap = new HashMap<>();
    private static Map<Integer, DepartmentGateway> departmentMap = new HashMap<>();
    private static Map<Integer, TechSkillGateway> techSkillMap = new HashMap<>();
    private static Map<Integer, AppointmentGateway> appointmentMap = new HashMap<>();

    public static UserGateway getUser(int id) {
        return userMap.get(id);
    }

    public static void addUser(UserGateway userGateway) {
        userMap.put(userGateway.getId(), userGateway);
    }

    public static IssueGateway getIssue(int id) {
        return issueMap.get(id);
    }

    public static void addIssue(IssueGateway issueGateway) {
        issueMap.put(issueGateway.getId(), issueGateway);
    }

    public static AppointmentGateway getAppointment(int id) { return appointmentMap.get(id); }

    public static void addAppointment(AppointmentGateway appointmentGateway) {
        appointmentMap.put(appointmentGateway.getId(), appointmentGateway);
    }

    public static IssueCommentGateway getIssueComment(int id) {
        return issueCommentMap.get(id);
    }

    public static void addIssueComment(IssueCommentGateway issueCommentGateway) {
        issueCommentMap.put(issueCommentGateway.getId(), issueCommentGateway);
    }

    public static DepartmentGateway getDepartment(int id) {
        return departmentMap.get(id);
    }

    public static void addDepartment(DepartmentGateway departmentGateway) {
        departmentMap.put(departmentGateway.getId(), departmentGateway);
    }

    public static TechSkillGateway getTechSkill(int id) {
        return techSkillMap.get(id);
    }

    public static void addTechSkill(TechSkillGateway techSkillGateway) {
        techSkillMap.put(techSkillGateway.hashCode(), techSkillGateway);
    }
}
